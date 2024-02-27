package org.hse.base;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ViewHolderHeader extends RecyclerView.ViewHolder {
    private Context context;
    private OnItemClick onItemClick;
    private TextView title;
    public ViewHolderHeader(View itemView, Context context, OnItemClick onItemClick) {
        super(itemView);
        this.context=context;
        this.onItemClick=onItemClick;
        title=itemView.findViewById(R.id.title);
    }
    public void bind(final ScheduleItemHeader data) {title.setText(data.getTitle());}
}
