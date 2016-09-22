package com.showcase.todoapp.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.showcase.todoapp.R;
import com.showcase.todoapp.ui.tododetails.TodoDetailsFragment;
import com.showcase.todoapp.ui.todolist.TodoListFragment;

public class TodoItemsActivity extends AppCompatActivity implements TodoListFragment
        .TodoListFragmentListener/* implements View.OnClickListener,
        TodoItemsContract.ViewReaction, QueryHandler.AsyncQueryListener*/
{
//    private QueryHandler mQueryHandler;

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

//    private void initViews()
//    {
////        findViewById(R.id.activity_todo_items_fab_add_item).setOnClickListener(this);
//
//        findViewById(R.id.activity_todo_items_fl_fragment_container)
//    }
//
//    @Override
//    public void onClick(View view)
//    {
//        switch (view.getId())
//        {
//            case R.id.activity_todo_items_fab_add_item:
//                displayAddTodoItemDialog();
//                break;
//        }
//    }
//
//    private void displayAddTodoItemDialog()
//    {
//        ContentValues values = new ContentValues();
//        values.put(TodoContract.Todo.Columns.DATA, "test");
//        mQueryHandler.startInsert(1, null, TodoContract.Todo.CONTENT_URI, values);
//    }
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
    public void displayTodoDetailsFragment()
    {
        getSupportFragmentManager().beginTransaction().add(R.id
                .activity_todo_items_fl_fragment_container, new TodoDetailsFragment())
                .addToBackStack(null).commit();
    }
}
