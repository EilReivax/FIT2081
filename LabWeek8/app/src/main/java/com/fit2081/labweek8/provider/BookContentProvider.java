package com.fit2081.labweek8.provider;

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
    private static final UriMatcher uriMatcher = createUriMatcher();
    BookDatabase bookDatabase;


    public BookContentProvider() {
    }

    private static UriMatcher createUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = "fit2081.app.michael";

        matcher.addURI(authority, TABLE_NAME, MULTIPLE_BOOKS);
        matcher.addURI(authority, TABLE_NAME + "/#", SINGLE_BOOKS);

        return matcher;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int deleteCount;

        switch (uriMatcher.match(uri)) {
            case MULTIPLE_BOOKS:
                deleteCount = bookDatabase
                      .getOpenHelper()
                      .getWritableDatabase()
                      .delete(TABLE_NAME, selection, selectionArgs);
                break;
            case SINGLE_BOOKS:
                String id = uri.getLastPathSegment();
                String selectionId = COLUMN_ID + " = ?";
                String [] selectionArgsId = new String[]{id};

                deleteCount = bookDatabase
                      .getOpenHelper()
                      .getWritableDatabase()
                      .delete(TABLE_NAME, selectionId, selectionArgsId);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        return deleteCount;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
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
                .query(query, selectionArgs);

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