package com.showcase.todoapp.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.DatePicker;

import com.showcase.todoapp.R;
import com.showcase.todoapp.ui.tododetails.TodoDetailsFragment;
import com.showcase.todoapp.ui.todolist.TodoListFragment;
import com.showcase.todoapp.utils.DatePickerFragment;
import com.showcase.todoapp.utils.Utility;

import java.util.Calendar;

public class TodoItemsActivity extends AppCompatActivity implements TodoListFragment
        .TodoListFragmentListener, TodoDetailsFragment.TodoDetailsFragmentListener,
        DatePickerDialog.OnDateSetListener/* implements
        View.OnClickListener,
        TodoItemsContract.ViewReaction, QueryHandler.AsyncQueryListener*/
{
    private static final String TAG_FRAGMENT_DETAILS = "detailsfragment";
//    private QueryHandler mQueryHandler;


//    @Override
//    protected void onResume()
//    {
//        super.onResume();
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_items);
//        mQueryHandler = new QueryHandler(this, this);
//        initViews();
        showTodoListFragment();
    }

    private void showTodoListFragment()
    {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.activity_todo_items_fl_fragment_container, new TodoListFragment());
//        transaction.addToBackStack(null);
        transaction.commit();
    }


//
//    @Override
//    public void onQueryComplete(int token, Object cookie, Uri uri)
//    {
//        if (token == 1)
//        {
//            if (uri != null)
//            {
//                Cursor cursor = getContentResolver().query(uri, null, null, null, null);
//                if (cursor != null)
//                {
//                    while (cursor.moveToNext())
//                    {
//                        Log.i("cursor-value", cursor.getString(cursor.getColumnIndex(TodoContract
//                                .Todo.Columns.DATA)) + "  ID:" + cursor.getString(cursor
//                                .getColumnIndex(TodoContract
//                                        .Todo.Columns._ID)));
//                    }
//                    cursor.close();
//                }
//                else
//                {
//                    Log.i("cursor-value", "null");
//                }
//            }
//
//        }
//    }

    @Override
    public void onBackPressed()
    {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void displayTodoDetailsFragment(Bundle bundle)
    {
        TodoDetailsFragment fragment = new TodoDetailsFragment();
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().add(R.id
                .activity_todo_items_fl_fragment_container, fragment, TAG_FRAGMENT_DETAILS)
                .addToBackStack(null).commit();
    }

    @Override
    public void displayDatePickerDialog()
    {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        TodoDetailsFragment fragment = (TodoDetailsFragment) getSupportFragmentManager()
                .findFragmentByTag(TAG_FRAGMENT_DETAILS);
        if (fragment != null)
        {
            fragment.setDate(Utility.getDate(calendar));
        }
    }
}
