package com.example.juniorf.mylastaplicationandroid;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListView;

import com.example.juniorf.mylastaplicationandroid.data.Contact;
import com.example.juniorf.mylastaplicationandroid.data.DBContact;
import com.example.juniorf.mylastaplicationandroid.data.ProjetoExpandableListAdapter;

import java.util.ArrayList;


public class ExpandableList extends AppCompatActivity {

    ProjetoExpandableListAdapter expandableListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ArrayList<Contact> list = DBContact.getList();
        expandableListAdapter = new ProjetoExpandableListAdapter( this, list );

        ExpandableListView listView = (ExpandableListView)findViewById( R.id.expandableListView );
        listView.setAdapter( expandableListAdapter );
    }

}
