package com.showcase.todoapp.ui.todolist;

import android.database.Cursor;
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

    public interface OnRowClickListener
    {
        void onRowClick(int position);

        void onRowLongClick(int position, String message, int row_id);
    }

    public TodoListAdapter(Cursor cursor, OnRowClickListener clickListener)
    {
        super(cursor);
        mClickListener = clickListener;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, Cursor cursor)
    {
        holder.textView.setText(cursor.getString(cursor.getColumnIndex(TodoContract.Todo.Columns
                .DATA)));
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout
                .todo_list_row, parent, false));
    }

    //    private static final String[] STRINGS = new String[]{
//            "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten",
//            "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten"
//    };
//
//    private final List<String> mItems = new ArrayList<>();
//
//    public TodoListAdapter()
//    {
//        mItems.addAll(Arrays.asList(STRINGS));
//    }
//
//    @Override
//    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
//    {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_list_row,
// parent,
//                false);
//        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
//        return itemViewHolder;
//    }
//
//    @Override
//    public void onBindViewHolder(final ItemViewHolder holder, int position)
//    {
//        holder.textView.setText(mItems.get(position));
//    }
//
//    @Override
//    public void onItemDismiss(int position)
//    {
//        mItems.remove(position);
//        notifyItemRemoved(position);
//    }
//
//    @Override
//    public void onItemMove(int fromPosition, int toPosition)
//    {
//        String prev = mItems.remove(fromPosition);
//        mItems.add(toPosition > fromPosition ? toPosition - 1 : toPosition, prev);
//        notifyItemMoved(fromPosition, toPosition);
//    }
//
//    @Override
//    public int getItemCount()
//    {
//        return mItems.size();
//    }
//
    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View
            .OnLongClickListener
    {
        private final TextView textView;

        public ItemViewHolder(View itemView)
        {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.todo_list_row_tv_title);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view)
        {
            if (mClickListener != null)
            {
                mClickListener.onRowClick(getAdapterPosition());
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
                    int columnDataIndex = mCursor.getColumnIndex(TodoContract.Todo.Columns.DATA);
                    mClickListener.onRowLongClick(getAdapterPosition(), mCursor.getString
                            (columnDataIndex), mCursor.getInt(columnIdIndex));
                }
            }
            return true;
        }
    }
}
