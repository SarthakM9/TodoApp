package com.showcase.todoapp.ui.todolist;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.showcase.todoapp.R;
import com.showcase.todoapp.database.TodoContract;
import com.showcase.todoapp.utils.CursorRecyclerAdapter;

public class TodoListAdapter extends CursorRecyclerAdapter<TodoListAdapter.ItemViewHolder>
{
    private OnRowClickListener mClickListener;
    private String[] mPriorityArray;
    private int[] mPrColorArray;

    public interface OnRowClickListener
    {
        void onRowClick(Uri uri);

        void onRowLongClick(String message, int row_id);
    }

    public TodoListAdapter(Context context, Cursor cursor, OnRowClickListener clickListener)
    {
        super(cursor);
        Resources resources = context.getResources();
        mClickListener = clickListener;
        mPriorityArray = resources.getStringArray(R.array.priority_array);
        mPrColorArray = new int[]{ContextCompat.getColor(context, android.R.color.holo_red_dark),
                ContextCompat.getColor(context, android.R.color.holo_orange_dark), ContextCompat
                .getColor(context, android.R.color.holo_green_dark)};
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, Cursor cursor)
    {
        holder.title.setText(cursor.getString(cursor.getColumnIndex(TodoContract.Todo.Columns
                .TITLE)));
        int priority = cursor.getInt(cursor.getColumnIndex(TodoContract.Todo.Columns.PRIORITY));
        holder.priority.setText(mPriorityArray[priority]);
        holder.priority.setTextColor(mPrColorArray[priority]);
        holder.date.setText(cursor.getString(cursor.getColumnIndex(TodoContract.Todo.Columns
                .DATE)));
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout
                .todo_list_row, parent, false));
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View
            .OnLongClickListener
    {
        private final TextView title;
        private final TextView date;
        private final TextView priority;

        public ItemViewHolder(View itemView)
        {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.todo_list_row_tv_title);
            date = (TextView) itemView.findViewById(R.id.todo_list_row_tv_date);
            priority = (TextView) itemView.findViewById(R.id.todo_list_row_tv_priority);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view)
        {
            if (mClickListener != null)
            {
                if (mCursor.moveToPosition(getAdapterPosition()))
                {
                    int columnIdIndex = mCursor.getColumnIndex(TodoContract.Todo.Columns._ID);
                    Uri uri = TodoContract.Todo.buildRowUri(mCursor.getInt(columnIdIndex));
                    mClickListener.onRowClick(uri);
                }
            }
        }

        @Override
        public boolean onLongClick(View view)
        {
            if (mClickListener != null)
            {
                if (mCursor.moveToPosition(getAdapterPosition()))
                {
                    int columnIdIndex = mCursor.getColumnIndex(TodoContract.Todo.Columns._ID);
                    int columnDataIndex = mCursor.getColumnIndex(TodoContract.Todo.Columns.TITLE);
                    mClickListener.onRowLongClick(mCursor.getString(columnDataIndex), mCursor
                            .getInt(columnIdIndex));
                }
            }
            return true;
        }
    }
}
