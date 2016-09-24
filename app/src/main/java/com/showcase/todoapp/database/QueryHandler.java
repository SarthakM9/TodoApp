package com.showcase.todoapp.database;

import android.content.AsyncQueryHandler;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;

public class QueryHandler extends AsyncQueryHandler
{
    private WeakReference<AsyncQueryListener> mListener;

    // To separate calls for different operations if needed.
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({OperationToken.TOKEN_QUERY, OperationToken.TOKEN_INSERT, OperationToken
            .TOKEN_UPDATE, OperationToken.TOKEN_DELETE})
    public @interface OperationToken
    {
        int TOKEN_QUERY = 0;
        int TOKEN_INSERT = 1;
        int TOKEN_UPDATE = 2;
        int TOKEN_DELETE = 3;
    }

    /**
     * Interface to listen for completed query operations.
     */
    public interface AsyncQueryListener
    {
        void onQueryComplete(int token, Object cookie, Cursor cursor);
        // Add more listeners as needed.
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
        mListener = new WeakReference<>(listener);
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
}
