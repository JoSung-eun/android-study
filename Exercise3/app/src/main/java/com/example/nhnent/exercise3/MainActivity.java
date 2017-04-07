package com.example.nhnent.exercise3;

import android.Manifest;
import android.app.Activity;
import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.Loader;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor> {
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Contact> contacts = new ArrayList<>();
    EditText queryEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        queryEdit = (EditText) findViewById(R.id.edit_query);
        queryEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                getContacts(getContentResolver(), s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        adapter = new CustomAdapter(contacts);
        recyclerView.setAdapter(adapter);

        getContacts(this.getContentResolver(), "");
    }

    public void getContacts(ContentResolver contentResolver, String searchQuery) {
        String[] projection = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};
        String[] selectionArgs = {"%" + searchQuery + "%"};

        try (Cursor cursor =
                    contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, projection,
                            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " LIKE ?", selectionArgs,
                            ContactsContract.Contacts.SORT_KEY_PRIMARY + " ASC")) {

            contacts.clear();

            while (cursor != null && cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                contacts.add(new Contact(name, phoneNumber));
            }

            recyclerView.getAdapter().notifyDataSetChanged();
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
