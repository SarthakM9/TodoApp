package com.showcase.todoapp.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class TodoProvider extends ContentProvider
{
    private static final int TODO_TABLE_ID = 100;
    private static final int TODO_ROW_ID = 101;
    private TodoDbHelper mDbHelper;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static
    {
        sUriMatcher.addURI(TodoContract.CONTENT_AUTHORITY, TodoContract.RELATIVE_TODO_URI,
                TODO_TABLE_ID);
        sUriMatcher.addURI(TodoContract.CONTENT_AUTHORITY, TodoContract.RELATIVE_TODO_URI + "/#",
                TODO_ROW_ID);
    }

    @Override
    public boolean onCreate()
    {
        mDbHelper = new TodoDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri)
    {
        switch (sUriMatcher.match(uri))
        {
            case TODO_TABLE_ID:
                return TodoContract.Todo.CONTENT_TYPE;

            case TODO_ROW_ID:
                return TodoContract.Todo.CONTENT_ITEM_TYPE;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[]
            selectionArgs, String sortOrder)
    {
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        Cursor retCursor;
        switch (sUriMatcher.match(uri))
        {
            case TODO_TABLE_ID:
                retCursor = db.query(TodoContract.Todo.TABLE_NAME, projection, selection,
                        selectionArgs, null, null, sortOrder);
                break;

            case TODO_ROW_ID:
                long _id = ContentUris.parseId(uri);
                retCursor = db.query(TodoContract.Todo.TABLE_NAME, projection, TodoContract.Todo
                        .Columns._ID + " = ?", new String[]{String.valueOf(_id)}, null, null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // send notification to any attached content observers.
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues)
    {
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        long _id;
        Uri returnUri;

        if (sUriMatcher.match(uri) == TODO_TABLE_ID)
        {
            _id = db.insert(TodoContract.Todo.TABLE_NAME, null, contentValues);
            if (_id > 0)
            {
                returnUri = TodoContract.Todo.buildRowUri(_id);
            }
            else
            {
                throw new UnsupportedOperationException("Unable to insert rows into: " + uri);
            }
        }
        else
        {
            throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs)
    {
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int rows; // Number of rows effected
        if (sUriMatcher.match(uri) == TODO_TABLE_ID)
        {
            rows = db.delete(TodoContract.Todo.TABLE_NAME, selection, selectionArgs);
        }
        else
        {
            throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // Because null could delete all rows:
        if (selection == null || rows != 0)
        {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rows;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[]
            selectionArgs)
    {
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int rows = 0;
        if (sUriMatcher.match(uri) == TODO_TABLE_ID)
        {
            rows = db.update(TodoContract.Todo.TABLE_NAME, values, selection, selectionArgs);
        }
        else
        {
            throwUnsupportedUriException(uri);
        }
        if (rows != 0)
        {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rows;
    }

    private void throwUnsupportedUriException(Uri uri)
    {
        throw new UnsupportedOperationException("Unknown uri: " + uri);
    }
}
