package com.example.contextactionmode;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private ListViewAdapter adapter;
    private List<String> Fruits= new ArrayList<>();
    private List<String> UserSelection= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getFruits();

        listView = findViewById(R.id.mListView);

        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(modeListener);
        adapter = new ListViewAdapter(Fruits,this);
        listView.setAdapter(adapter);
    }
    private void getFruits()
    {
        String [] items = getResources().getStringArray(R.array.fruits);
        for (String item : items)
        {
            Fruits.add(item);
        }
    }


    AbsListView.MultiChoiceModeListener modeListener = new AbsListView.MultiChoiceModeListener() {
        @Override
        public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b)
        {
            if (UserSelection.contains(Fruits.get(i)))
            {
                UserSelection.remove(Fruits.get(i));
            }
            else
            {
                UserSelection.add(Fruits.get(i));
            }

            actionMode.setTitle(UserSelection.size()+"items selected...");
        }

        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            MenuInflater menuInflater = actionMode.getMenuInflater();
            menuInflater.inflate(R.menu.context_menu,menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {

            switch(menuItem.getItemId())
            {
                case R.id.delete:
                    adapter.removeItems(UserSelection);
                    actionMode.finish();
                    return true;
                default:
                    return false;
            }

        }

        @Override
        public void onDestroyActionMode(ActionMode actionMode) {
                UserSelection.clear();
        }
    };
}