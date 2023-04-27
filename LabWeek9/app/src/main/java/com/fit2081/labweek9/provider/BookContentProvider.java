package com.fit2081.labweek9.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class BookContentProvider extends ContentProvider {
    public static final Uri CONTENT_URI = Uri.parse("content://fit2081.app.michael");
    public static final String TABLE_NAME = "books";
    public static final String COLUMN_ID = "id";
    public static final int MULTIPLE_BOOKS = 1;
    public static final int SINGLE_BOOKS = 2;
    public static final UriMatcher uriMatcher = createUriMatcher();
    BookDatabase bookDatabase;


    public BookContentProvider() {
    }

    private static UriMatcher createUriMatcher() {
        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = "fit2081.app.michael";

        uriMatcher.addURI(authority, TABLE_NAME, MULTIPLE_BOOKS);
        uriMatcher.addURI(authority, TABLE_NAME + "/#", SINGLE_BOOKS);

        return uriMatcher;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int deleteCount = bookDatabase
                      .getOpenHelper()
                      .getWritableDatabase()
                      .delete(TABLE_NAME, selection, selectionArgs);

        return deleteCount;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long rowId = bookDatabase
                .getOpenHelper()
                .getWritableDatabase()
                .insert(TABLE_NAME, 0, values);

        return ContentUris.withAppendedId(CONTENT_URI, rowId);
    }

    @Override
    public boolean onCreate() {
        bookDatabase = BookDatabase.getDatabase(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(TABLE_NAME);
        String query = queryBuilder.buildQuery(projection, selection, null, null, sortOrder, null);

        final Cursor cursor = bookDatabase
                .getOpenHelper()
                .getReadableDatabase()
                .query(query);

        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int updateCount = bookDatabase
                .getOpenHelper()
                .getWritableDatabase()
                .update(TABLE_NAME, 0, values, selection, selectionArgs);

        return updateCount;
    }
}