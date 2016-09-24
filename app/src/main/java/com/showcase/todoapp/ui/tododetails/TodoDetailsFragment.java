package com.showcase.todoapp.ui.tododetails;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.showcase.todoapp.R;
import com.showcase.todoapp.TodoApplication;
import com.showcase.todoapp.database.QueryHandler;
import com.showcase.todoapp.database.TodoContract;
import com.showcase.todoapp.ui.todolist.TodoListFragment;

import java.util.Calendar;

import static com.showcase.todoapp.utils.Utility.getDate;

public class TodoDetailsFragment extends Fragment implements View.OnClickListener, QueryHandler
        .AsyncQueryListener
{
    private TextView mDate;
    private TodoDetailsFragmentListener mFragmentListener;
    private EditText mTitle;
    private EditText mDescription;
    private Spinner mSpinner;
    private Uri mUri;

    public TodoDetailsFragment()
    {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getActivity() instanceof TodoListFragment.TodoListFragmentListener)
        {
            mFragmentListener = (TodoDetailsFragmentListener) getActivity();
        }

        Bundle bundle = getArguments();
        if (bundle != null && !bundle.isEmpty())
        {
            mUri = bundle.getParcelable(TodoListFragment.URI_KEY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_todo_details, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        if (mUri != null)
        {
            QueryHandler handler = new QueryHandler(getContext(), this);
            handler.startQuery(QueryHandler.OperationToken.TOKEN_QUERY, null, mUri, null, null,
                    null, null);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        View view = getView();
        mSpinner = (Spinner) view.findViewById(R.id.fragment_todo_details_spn_priority);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R
                .array.priority_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);

        mDate = (TextView) view.findViewById(R.id.fragment_todo_details_tv_date);
        setDate(getDate(Calendar.getInstance()));

        mTitle = (EditText) view.findViewById(R.id.fragment_todo_details_et_title);
        mDescription = (EditText) view.findViewById(R.id.fragment_todo_details_et_desc);

        view.findViewById(R.id.fragment_todo_details_ll_date_container).setOnClickListener(this);
        view.findViewById(R.id.fragment_todo_details_btn_save_todo).setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.fragment_todo_details_ll_date_container:
                if (mFragmentListener != null)
                {
                    mFragmentListener.displayDatePickerDialog();
                }
                break;
            case R.id.fragment_todo_details_btn_save_todo:
                if (validateTitle())
                {
                    saveTodo();
                }
                else
                {
                    Toast.makeText(getContext(), R.string.err_title_blank, Toast.LENGTH_SHORT)
                            .show();
                }
                break;
        }
    }

    private boolean validateTitle()
    {
        return !mTitle.getText().toString().trim().isEmpty();
    }

    private void saveTodo()
    {
        QueryHandler queryHandler = new QueryHandler(getContext(), null);
        String title = mTitle.getText().toString().replaceAll("\\s{2,}", " ").replaceAll("" + "" +
                "(\\n\\r?)+", " ").trim();
        String description = mDescription.getText().toString().replaceAll("\\s{2,}", " ")
                .replaceAll("(\\n\\r?)+", " ").trim();
        String date = mDate.getText().toString();
        int priority = mSpinner.getSelectedItemPosition();
        ContentValues values = new ContentValues();
        values.put(TodoContract.Todo.Columns.TITLE, title);
        values.put(TodoContract.Todo.Columns.DESCRIPTION, description);
        values.put(TodoContract.Todo.Columns.DATE, date);
        values.put(TodoContract.Todo.Columns.PRIORITY, priority);
        if (mUri != null)
        {
            long _id = ContentUris.parseId(mUri);
            String selection = TodoContract.Todo.Columns._ID + " = ?";
            String[] selectionArg = {String.valueOf(_id)};
            queryHandler.startUpdate(QueryHandler.OperationToken.TOKEN_UPDATE, null, TodoContract
                    .Todo.CONTENT_URI, values, selection, selectionArg);
        }
        else
        {
            queryHandler.startInsert(QueryHandler.OperationToken.TOKEN_INSERT, null, TodoContract
                    .Todo.CONTENT_URI, values);
        }
        finishFragment();
    }

    private void finishFragment()
    {
        if (mFragmentListener != null)
        {
            mFragmentListener.finishDetailsFragment();
        }
    }

    @Override
    public void onDestroy()
    {
        TodoApplication.getRefWatcher(getContext()).watch(this);
        super.onDestroy();
    }

    public void setDate(String date)
    {
        mDate.setText(date);
    }

    @Override
    public void onQueryComplete(int token, Object cookie, Cursor cursor)
    {
        if (cursor != null && cursor.getCount() == 1 && cursor.moveToFirst())
        {
            mTitle.setText(cursor.getString(cursor.getColumnIndex(TodoContract.Todo.Columns
                    .TITLE)));

            mDescription.setText(cursor.getString(cursor.getColumnIndex(TodoContract.Todo.Columns
                    .DESCRIPTION)));
            mDate.setText(cursor.getString(cursor.getColumnIndex(TodoContract.Todo.Columns.DATE)));
            mSpinner.setSelection(cursor.getInt(cursor.getColumnIndex(TodoContract.Todo.Columns
                    .PRIORITY)), true);
            cursor.close();
        }
        else
        {
            Toast.makeText(getContext(), R.string.invalid_state, Toast.LENGTH_SHORT)
                    .show();
            finishFragment();
        }
    }

    public interface TodoDetailsFragmentListener
    {
        void displayDatePickerDialog();

        void finishDetailsFragment();
    }
}
