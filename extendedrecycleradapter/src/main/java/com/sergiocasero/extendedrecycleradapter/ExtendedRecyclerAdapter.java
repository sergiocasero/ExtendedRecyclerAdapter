package com.sergiocasero.extendedrecycleradapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * ExtendedRecyclerAdapter
 */
public abstract class ExtendedRecyclerAdapter <T extends Object>
        extends RecyclerView.Adapter<ExtendedRecyclerAdapter.ExtendedRecyclerViewHolder> {

    private static final int POS_NOT_FOUND = -1;

    protected List<T> items = new ArrayList<>();

    protected OnItemClickListener<T> onItemClickListener;

    protected OnLongItemClickListener<T> onLongItemClickListener;

    protected OnNotFoundListener onNotFoundListener;

    protected abstract ExtendedRecyclerViewHolder getViewHolder(View view);

    protected abstract int getItemLayoutId();

    public void setOnItemClickListener(
            OnItemClickListener<T> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnLongItemClickListener(
            OnLongItemClickListener<T> onLongItemClickListener) {
        this.onLongItemClickListener = onLongItemClickListener;
    }

    public void setOnNotFoundListener(
            OnNotFoundListener onNotFoundListener) {
        this.onNotFoundListener = onNotFoundListener;
    }

    @Override
    public ExtendedRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(getItemLayoutId(), parent, false);
        return getViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ExtendedRecyclerAdapter.ExtendedRecyclerViewHolder holder, int position) {
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public void addAll(List<T> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void add(T item) {
        items.add(item);
        notifyItemInserted(items.indexOf(item));
    }

    public void add(T item, int position) {
        items.add(position, item);
        notifyItemInserted(position);
    }

    public void update(T item) {
        int pos = items.indexOf(item);
        if (pos != POS_NOT_FOUND) {
            items.remove(pos);
            items.add(pos, item);
            notifyItemChanged(pos);
        } else if (onNotFoundListener != null) {
            onNotFoundListener.onNotFound(item);
        }
    }

    public void remove(T item) {
        int pos = items.indexOf(item);
        if (pos != POS_NOT_FOUND) {
            notifyItemRemoved(pos);
            items.remove(item);
        } else if (onNotFoundListener != null) {
            onNotFoundListener.onNotFound(item);
        }
    }

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    public abstract class ExtendedRecyclerViewHolder extends RecyclerView.ViewHolder {

        public ExtendedRecyclerViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onClick(items.get(getAdapterPosition()), getAdapterPosition());
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (onLongItemClickListener != null) {
                        onLongItemClickListener.onLongClick(items.get(getAdapterPosition()), getAdapterPosition());
                        return true;
                    }
                    return false;
                }
            });
        }

        public abstract void bind(T item);
    }

    public interface OnItemClickListener <T extends Object> {
        void onClick(T item, int position);
    }

    public interface OnLongItemClickListener <T extends Object> {
        void onLongClick(T item, int position);
    }

    public interface OnNotFoundListener <T extends Object> {
        void onNotFound(T item);
    }
}
