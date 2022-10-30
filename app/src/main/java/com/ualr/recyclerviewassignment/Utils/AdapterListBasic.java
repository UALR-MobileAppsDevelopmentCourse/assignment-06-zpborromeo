package com.ualr.recyclerviewassignment.Utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.ualr.recyclerviewassignment.R;
import com.ualr.recyclerviewassignment.model.Inbox;

import java.util.List;

/**
 * Created by irconde on 2019-09-25.
 * Updated for Assignment 6 by zpborromeo on 2022-10-23.
 */

public class AdapterListBasic extends RecyclerView.Adapter{
    private List<Inbox> mInbox;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;

    public AdapterListBasic(Context context, List<Inbox> items) {
        this.mInbox = items;
        this.mContext = context;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, Inbox inbox, int position);

        void onIconClick(View view, Inbox inbox, int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public void addItem(int position, Inbox item) {
        mInbox.add(position, item);
        notifyItemInserted(position);
    }

    public void removeItem(int position) {
        if (position >= mInbox.size()){
            return;
        }
        mInbox.remove(position);
        notifyItemRemoved(position);
    }

    public void clearSelected(){
        for (Inbox email : mInbox){
            email.setSelected(false);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return this.mInbox.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inbox_item, parent, false);
        return new InboxViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        InboxViewHolder vh = (InboxViewHolder) holder;
        vh.mailIcon.setText(mInbox.get(position).getFrom().substring(0,1));
        vh.mailSender.setText(mInbox.get(position).getFrom());
        vh.mailTitle.setText(mInbox.get(position).getEmail());
        vh.mailContent.setText(mInbox.get(position).getMessage());
        vh.mailTimeSent.setText(mInbox.get(position).getDate());

        int mailSelectedColor = mContext.getResources().getColor(R.color.grey_20);
        int iconSelectedColor = mContext.getResources().getColor(R.color.colorAccent);
        int iconDefaultColor = mContext.getResources().getColor(R.color.colorPrimary);

        Drawable defaultIcon = mContext.getDrawable(R.drawable.shape_circle);
        Drawable deleteIcon = mContext.getDrawable(R.drawable.ic_delete_24px);
        Drawable selectedIcon = mContext.getDrawable(R.drawable.shape_circle);
        selectedIcon.setBounds(0, 0, 24, 24);
        defaultIcon.mutate().setColorFilter(iconDefaultColor, PorterDuff.Mode.SRC_IN);
        selectedIcon.mutate().setColorFilter(iconSelectedColor, PorterDuff.Mode.SRC_IN);

        Inbox mainInbox = mInbox.get(position);

        if (mainInbox.isSelected()){
            vh.mainInboxView.setBackgroundColor(mailSelectedColor);
            vh.mailIcon.setBackground(selectedIcon);
            vh.mailIcon.setCompoundDrawablesRelativeWithIntrinsicBounds(deleteIcon, null, null, null);
        }else{
            vh.mainInboxView.setBackgroundColor(Color.TRANSPARENT);
            vh.mailIcon.setBackground(defaultIcon);
            vh.mailIcon.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
        }
    }

    public class InboxViewHolder extends RecyclerView.ViewHolder {
        TextView mailIcon;
        TextView mailSender;
        TextView mailTitle;
        TextView mailContent;
        TextView mailTimeSent;
        View mainInboxView;

        public InboxViewHolder(View itemView) {
            super(itemView);

            mailIcon = itemView.findViewById(R.id.tvIcon);
            mailSender = itemView.findViewById(R.id.emailSender);
            mailTitle = itemView.findViewById(R.id.emailTitle);
            mailContent = itemView.findViewById(R.id.emailContent);
            mailTimeSent = itemView.findViewById(R.id.emailTimeSent);
            mainInboxView = itemView.findViewById(R.id.inboxLayout);

            mailIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onIconClick(view, mInbox.get(getLayoutPosition()), getLayoutPosition());
                }
            });
            mainInboxView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(view, mInbox.get(getLayoutPosition()), getLayoutPosition());
                }
            });
        }
    }

}
