package org.hse.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends
        RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final static int TYPE_ITEM = 0;
    private final static int TYPE_HEADER = 1;
    private List<ScheduleItem> dataList = new ArrayList<>();
    private OnItemClick onItemClick;
    public ItemAdapter(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        if (viewType == TYPE_ITEM) {
            View contactView = inflater.inflate(R.layout.item_schedule, parent, false);
            return new ViewHolder(contactView, context, onItemClick);
        } else if (viewType == TYPE_HEADER) {
            View contactView = inflater.inflate(R.layout.item_schedule_header, parent, false);
            return new ViewHolderHeader(contactView,context, onItemClick);
        }

        try {
            throw new IllegalAccessException("Invalid view type");
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public int getItemViewType(int position) {
        ScheduleItem data = dataList.get(position);
        if (data instanceof ScheduleItemHeader) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ScheduleItem data = dataList.get(position);
        if (holder instanceof ViewHolder) {
            ((ViewHolder)holder).bind(data);
        } else if (holder instanceof ViewHolderHeader) {
            ((ViewHolderHeader)holder).bind(((ScheduleItemHeader) data).getTitle());
        }
    }

    @Override
    public int getItemCount() {
        return this.dataList.size();
    }

    public void setDataList(List<ScheduleItem> dataList){
        this.dataList = dataList;
        notifyDataSetChanged();
    }
}
