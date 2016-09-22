package com.showcase.todoapp.ui.todolist;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.showcase.todoapp.R;
import com.showcase.todoapp.database.QueryHandler;
import com.showcase.todoapp.database.TodoContract;

import java.util.Random;

public class TodoListFragment extends Fragment implements QueryHandler.AsyncQueryListener,
        LoaderManager.LoaderCallbacks<Cursor>, TodoListAdapter.OnRowClickListener
{
    //    private RecyclerView mTodoList;
    QueryHandler mQueryHandler;
    TodoListAdapter adapter;
    private TodoListFragmentListener mFragmentListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getActivity() instanceof TodoListFragmentListener)
        {
            mFragmentListener = (TodoListFragmentListener) getActivity();
        }
    }

    public TodoListFragment()
    {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_todo_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        mQueryHandler = new QueryHandler(getContext(), this);
        mQueryHandler.startQuery(1, null, TodoContract.Todo.CONTENT_URI, null, null, null, null);

        getLoaderManager().initLoader(1, null, this);

        adapter = new TodoListAdapter(null, this);

        RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id
                .fragment_todo_list_rv_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        getView().findViewById(R.id.fragment_button).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Random random = new Random();
                ContentValues values = new ContentValues();
                values.put(TodoContract.Todo.Columns.DATA, "test " + random.nextInt(100));
                mQueryHandler.startInsert(1, null, TodoContract.Todo.CONTENT_URI, values);
            }
        });
    }

    @Override
    public void onQueryComplete(int token, Object cookie, Cursor cursor)
    {
        if (cursor != null)
        {

        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args)
    {
        return new CursorLoader(getContext(), TodoContract.Todo.CONTENT_URI, null, null, null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data)
    {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader)
    {
        adapter.swapCursor(null);
    }

    @Override
    public void onRowClick(int position)
    {
        if (mFragmentListener != null)
        {
            mFragmentListener.displayTodoDetailsFragment();
        }
    }

    @Override
    public void onRowLongClick(int position, String message, int rowId)
    {
        showDialog(message, rowId);
    }

    private void showDialog(String message, final int rowId)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                String selection = TodoContract.Todo.Columns._ID + " = ?";
                mQueryHandler.startDelete(4, 0, TodoContract.Todo.CONTENT_URI, selection, new
                        String[]{String.valueOf(rowId)});
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                // User cancelled the dialog
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.setTitle("Delete TODO");
        dialog.setMessage("Are you sure you want to delete your TODO:\n" +
                message);
        dialog.show();
    }

    public interface TodoListFragmentListener
    {
        void displayTodoDetailsFragment();
    }
}
