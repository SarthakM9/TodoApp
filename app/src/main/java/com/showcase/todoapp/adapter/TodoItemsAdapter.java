package com.showcase.todoapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class TodoItemsAdapter extends RecyclerView.Adapter<TodoItemsAdapter.ItemsHolder>
{


    @Override
    public ItemsHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        return null;
    }

    @Override
    public void onBindViewHolder(ItemsHolder holder, int position)
    {

    }

    @Override
    public int getItemCount()
    {
        return 0;
    }

    static class ItemsHolder extends RecyclerView.ViewHolder
    {

        public ItemsHolder(View itemView)
        {
            super(itemView);

        }
    }
}
