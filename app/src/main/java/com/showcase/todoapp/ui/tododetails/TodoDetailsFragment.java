package com.showcase.todoapp.ui.tododetails;

import android.content.ContentValues;
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
import com.showcase.todoapp.database.QueryHandler;
import com.showcase.todoapp.database.TodoContract;
import com.showcase.todoapp.ui.todolist.TodoListFragment;

import java.util.Calendar;

import static com.showcase.todoapp.utils.Utility.getDate;

public class TodoDetailsFragment extends Fragment implements View.OnClickListener
{
    private TextView mDate;
    private TodoDetailsFragmentListener mFragmentListener;
    private boolean isUpdating = false;
    private EditText mTitle;
    private EditText mDescription;
    private Spinner mSpinner;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getActivity() instanceof TodoListFragment.TodoListFragmentListener)
        {
            mFragmentListener = (TodoDetailsFragmentListener) getActivity();
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        View view = getView();
        mSpinner = (Spinner) view.findViewById(R.id.fragment_todo_details_spn_priority);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.priority_array, android.R.layout.simple_spinner_item);
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
                    Toast.makeText(getContext(), "Title cannot be blank", Toast.LENGTH_SHORT)
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
        String title = mTitle.getText().toString().replaceAll("\\s{2,}", " ").replaceAll("" +
                        "(\\n\\r?)+",
                " ").trim();
        String description = mDescription.getText().toString().replaceAll("\\s{2,}", " ")
                .replaceAll("(\\n\\r?)+",
                        " ").trim();
        String date = mDate.getText().toString();
        int priority = mSpinner.getSelectedItemPosition();
        if (isUpdating)
        {

//            queryHandl
        }
        else
        {
            ContentValues values = new ContentValues();
            values.put(TodoContract.Todo.Columns.TITLE, title);
            values.put(TodoContract.Todo.Columns.DESCRIPTION, description);
            values.put(TodoContract.Todo.Columns.DATE, date);
            values.put(TodoContract.Todo.Columns.PRIORITY, priority);
            queryHandler.startInsert(1, null, TodoContract.Todo.CONTENT_URI, values);
        }
        getActivity().getSupportFragmentManager().popBackStack();
    }

    public void setDate(String date)
    {
        mDate.setText(date);
    }

    public interface TodoDetailsFragmentListener
    {
        void displayDatePickerDialog();
    }
}
