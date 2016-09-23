package com.showcase.todoapp.database;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public final class TodoContract
{
    public static final String CONTENT_AUTHORITY = "com.showcase.todoapp.provider";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    //Tables specific path:
    public static final String RELATIVE_TODO_URI = "todo";

    public static class Todo
    {
        // URI for the table
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath
                (RELATIVE_TODO_URI).build();

        // Entire table
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.com.showcase" + "" +
                ".todoapp.provider.todo";
        // Single row within the table
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.com.showcase"
                + ".todoapp.provider.todo";

        // Table name
        public static final String TABLE_NAME = "todo";

        // Define table columns
        public interface Columns extends BaseColumns
        {
            String TITLE = "title";
            String DESCRIPTION = "description";
            String DATE = "date";
            String PRIORITY = "priority";
        }

        public static Uri buildRowUri(long id)
        {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }
}
