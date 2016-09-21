package com.showcase.todoapp.database;

import android.content.AsyncQueryHandler;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.lang.ref.WeakReference;

public class QueryHandler extends AsyncQueryHandler
{
    private WeakReference<AsyncQueryListener> mListener;

    /**
     * Interface to listen for completed query operations.
     */
    public interface AsyncQueryListener
    {
        void onQueryComplete(int token, Object cookie, Cursor cursor);
    }

    public QueryHandler(Context context, AsyncQueryListener listener)
    {
        super(context.getContentResolver());
        setQueryListener(listener);
    }

    /**
     * Assign the given {@link AsyncQueryListener} to receive query events from
     * asynchronous calls. Will replace any existing listener.
     */
    public void setQueryListener(AsyncQueryListener listener)
    {
        mListener = new WeakReference<AsyncQueryListener>(listener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onQueryComplete(int token, Object cookie, Cursor cursor)
    {
        final AsyncQueryListener listener = mListener.get();
        if (listener != null)
        {
            listener.onQueryComplete(token, cookie, cursor);
        }
        else if (cursor != null)
        {
            cursor.close();
        }
    }
//
//    @Override
//    protected void onInsertComplete(int token, Object cookie, Uri uri)
//    {
//        final AsyncQueryListener listener = mListener.get();
//        if (listener != null)
//        {
//            listener.onQueryComplete(token, cookie, uri);
//        }
//    }
}
