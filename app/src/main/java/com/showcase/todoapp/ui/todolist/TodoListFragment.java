package com.showcase.todoapp.ui.todolist;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.showcase.todoapp.R;
import com.showcase.todoapp.database.QueryHandler;
import com.showcase.todoapp.database.TodoContract;

import java.util.Random;

public class TodoListFragment extends Fragment implements QueryHandler.AsyncQueryListener
{
    //    private RecyclerView mTodoList;
    QueryHandler mQueryHandler;

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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);


//        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
//        mItemTouchHelper = new ItemTouchHelper(callback);
//        mItemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void onQueryComplete(int token, Object cookie, Cursor cursor)
    {
        if (cursor != null)
        {
            TodoListAdapter adapter = new TodoListAdapter(cursor);

            RecyclerView recyclerView = (RecyclerView) getView().findViewById(R.id
                    .fragment_todo_list_rv_list);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
    }
}
