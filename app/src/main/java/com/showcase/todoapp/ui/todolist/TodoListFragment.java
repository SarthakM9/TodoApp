package com.showcase.todoapp.ui.todolist;

import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
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
import android.widget.ViewSwitcher;

import com.showcase.todoapp.R;
import com.showcase.todoapp.TodoApplication;
import com.showcase.todoapp.database.QueryHandler;
import com.showcase.todoapp.database.TodoContract;
import com.showcase.todoapp.utils.SpacesItemDecoration;
import com.showcase.todoapp.utils.Utility;

public class TodoListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>,
        TodoListAdapter.OnRowClickListener
{
    private TodoListAdapter mAdapter;
    private TodoListFragmentListener mFragmentListener;
    private ViewSwitcher mSwitcher;
    public static final String URI_KEY = "uri";
    private static final int LOADER_ID = 1;

    public TodoListFragment()
    {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getActivity() instanceof TodoListFragmentListener)
        {
            mFragmentListener = (TodoListFragmentListener) getActivity();
        }
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

        View view = getView();
        mSwitcher = (ViewSwitcher) view.findViewById(R.id.fragment_todo_list_switcher);

        getLoaderManager().initLoader(LOADER_ID, null, this);

        mAdapter = new TodoListAdapter(getContext(), null, this);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id
                .fragment_todo_list_rv_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter);

        recyclerView.addItemDecoration(new SpacesItemDecoration((int) Utility.dpToPx(5,
                getContext())));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        view.findViewById(R.id.fragment_button).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                displayDetailsFragment(null);
            }
        });
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args)
    {
        String[] projection = {TodoContract.Todo.Columns._ID, TodoContract.Todo.Columns.TITLE,
                TodoContract.Todo.Columns.DATE, TodoContract.Todo.Columns.PRIORITY};
        String sortOrder = TodoContract.Todo.Columns.PRIORITY + " ASC";
        return new CursorLoader(getContext(), TodoContract.Todo.CONTENT_URI, projection, null,
                null, sortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data)
    {
        if (data.getCount() > 0)
        {
            mAdapter.swapCursor(data);

            if (R.id.fragment_todo_list_rv_list == mSwitcher.getNextView().getId())
            {
                mSwitcher.showNext();
            }
        }
        else if (R.id.fragment_todo_list_tv_empty_text == mSwitcher.getNextView().getId())
        {
            mSwitcher.showNext();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader)
    {
        mAdapter.swapCursor(null);
    }

    @Override
    public void onRowClick(Uri uri)
    {
        Bundle bundle = new Bundle();
        bundle.putParcelable(URI_KEY, uri);
        displayDetailsFragment(bundle);
    }

    private void displayDetailsFragment(Bundle bundle)
    {
        if (mFragmentListener != null)
        {
            mFragmentListener.displayTodoDetailsFragment(bundle);
        }
    }

    @Override
    public void onRowLongClick(String message, int rowId)
    {
        showDialog(message, rowId);
    }

    private void showDialog(String message, final int rowId)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                String selection = TodoContract.Todo.Columns._ID + " = ?";
                QueryHandler queryHandler = new QueryHandler(getContext(), null);
                queryHandler.startDelete(QueryHandler.OperationToken.TOKEN_DELETE, null,
                        TodoContract.Todo.CONTENT_URI, selection, new
                                String[]{String.valueOf(rowId)});
            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);
        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.setTitle(getString(R.string.delete_dialog_title));
        dialog.setMessage(getString(R.string.delete_dialog_message) + message);
        dialog.show();
    }

    @Override
    public void onDestroy()
    {
        TodoApplication.getRefWatcher(getContext()).watch(this);
        super.onDestroy();
    }

    public interface TodoListFragmentListener
    {
        void displayTodoDetailsFragment(Bundle bundle);
    }
}
