package com.example.juniorf.mylastaplicationandroid.data;

import java.util.ArrayList;

/**
 * Created by marcioefmaia on 10/6/16.
 */
public class DBContact {
    private static DBContact ourInstance = null;

    private  static ArrayList<Contact> contactList;

    public static DBContact getInstance() {

        if( ourInstance == null ){
            ourInstance = new DBContact();
        }

        return ourInstance;
    }

    private DBContact() {
        contactList = new ArrayList<Contact>();
    }

    public void addContact( Contact c ){
        this.contactList.add( c );
    }

    public static ArrayList<Contact> getContactList(){
        ArrayList<Contact> cs = new ArrayList<>();
        for( Contact c: contactList )
            cs.add( c);
        return  cs;
    }



    public Contact getContactByNome( String nome ){
        for( Contact c : contactList ) if( c.getNome().equals( nome ) ) return c;
        return null;
    }

    public static ArrayList<Contact> getList(){
        return  contactList;
    }

    public static Contact getContactByName(String nome) {
        for( Contact c : contactList) if( c.getNome().equals( nome ) ) return c;
        return null;
    }
}
