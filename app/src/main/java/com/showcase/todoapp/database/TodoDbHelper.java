package com.showcase.todoapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.showcase.todoapp.database.TodoContract.Todo.Columns;

public class TodoDbHelper extends SQLiteOpenHelper
{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "todoitems.db";
    private static final String COMMA_SEP = ",";
    private static final String SPACE = " ";

    private static final String CREATE_TABLE_TODO = "CREATE TABLE " + TodoContract.Todo.TABLE_NAME +
            "(" + Columns._ID + SPACE + "INTEGER PRIMARY KEY" + COMMA_SEP + Columns.TITLE + SPACE +
            "TEXT NOT NULL" + COMMA_SEP + Columns.DESCRIPTION + SPACE + "TEXT NOT NULL" +
            COMMA_SEP + Columns.DATE + SPACE + "TEXT NOT NULL" + COMMA_SEP + Columns.PRIORITY +
            SPACE + "INTEGER"
            + ")";
    private static final String DROP_TABLE_TODO = "DROP TABLE IF EXISTS " + TodoContract.Todo
            .TABLE_NAME;

    public TodoDbHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        sqLiteDatabase.execSQL(CREATE_TABLE_TODO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1)
    {
        // on upgrade drop older tables
        sqLiteDatabase.execSQL(DROP_TABLE_TODO);
        // create new tables
        onCreate(sqLiteDatabase);
    }
}
