package com.example.nhnent.contentproviderstudy;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

public class StudentsProvider extends ContentProvider {
    public StudentsDBManager dbManager = null;
    private static final String AUTHORITY = "com.example.nhnent.StudentsProvider";
    private static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/students");

    private static final int STUDENT_ALL = 1;
    private static final int STUDENT_ONE = 2;

    private static UriMatcher STUDENTS_URI_MATCHER = null;

    static {
        STUDENTS_URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        STUDENTS_URI_MATCHER.addURI(AUTHORITY, "students", STUDENT_ALL);
        STUDENTS_URI_MATCHER.addURI(AUTHORITY, "students/#", STUDENT_ONE);
    }

    public StudentsProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        dbManager.insert(values);
        return null;
    }

    @Override
    public boolean onCreate() {
        dbManager = StudentsDBManager.getInstance(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        switch (STUDENTS_URI_MATCHER.match(uri)) {
            case STUDENT_ONE:
                selection = "_id=" + ContentUris.parseId(uri);
                Log.d("StudentsProvider", "id : " + ContentUris.parseId(uri));
                selectionArgs = null;
                break;
            case STUDENT_ALL:
                break;
            case UriMatcher.NO_MATCH:
                return null;
        }
        return dbManager.query(projection, selection, selectionArgs, null, null, sortOrder);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
