package com.example.juniorf.mylastaplicationandroid.data;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.juniorf.mylastaplicationandroid.R;

import java.util.ArrayList;

/**
 * Created by juniorf on 20/10/16.
 */

public class ProjetoExpandableListAdapter extends BaseExpandableListAdapter{
    ArrayList<Contact> contactList;
    Activity activity;

    public ProjetoExpandableListAdapter(Activity activity, ArrayList<Contact> contactList ){
        this.contactList = contactList;
        this.activity = activity;
    }

    @Override
    public int getGroupCount() {
        return contactList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return contactList.get( groupPosition ).getNome();
    }

    @Override
    public Object getChild( int groupPosition, int childPosition ) {
        return contactList.get( groupPosition );
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    //Retorna uma View para cada titulo (nome do contato) na lista
    //Esse contato está na posição groupPosition no ArrayList contactList
    //Para cada contato, ele infla a View usando o layout contact_name
    //O conteúdo que será mostrado no layout vem do métod getGroup
    public View getGroupView( int groupPosition, boolean isExpanded, View convertView, ViewGroup parent ) {

        String groupName = (String) getGroup( groupPosition );

        if( convertView == null ){

            LayoutInflater infalInflater = (LayoutInflater) activity.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            convertView = infalInflater.inflate( R.layout.item_lista, null );

        }

        TextView txView = ( TextView )convertView.findViewById( R.id.textViewNome);

        txView.setText( groupName );

        return convertView;

    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        Contact contact = ( Contact )getChild( groupPosition, childPosition );

        if( convertView == null ){
            LayoutInflater inflater = (LayoutInflater) activity.getLayoutInflater();
            convertView = inflater.inflate( R.layout.item_lista, null );
        }

        TextView textViewTelefone = ( TextView )convertView.findViewById( R.id.textViewTelefone);
        textViewTelefone.setText( contact.getTelefone() );

        TextView textViewId = ( TextView )convertView.findViewById( R.id.textViewId );
        textViewId.setText( new Integer( contact.getContactId() ).toString() );

        TextView textViewEndereco = ( TextView )convertView.findViewById( R.id.textViewEndereco );
        textViewEndereco.setText( contact.getEndereco() );

        return convertView;

    }

}
