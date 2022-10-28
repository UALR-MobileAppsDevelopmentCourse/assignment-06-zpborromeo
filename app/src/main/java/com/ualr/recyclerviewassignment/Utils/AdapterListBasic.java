package com.ualr.recyclerviewassignment.Utils;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

    private static final int PERSON_VIEW = 0;
    private static final int HEADER_VIEW = 1;

    private List<Inbox> mInbox;
    private Context mContext;

    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }

    public AdapterListBasic(Context context, List<Inbox> items) {
        this.mInbox = items;
        this.mContext = context;
    }

    public void removeItem(int position) {
        if (position >= mInbox.size()){
            return;
        }
        mInbox.remove(position);
        /**
         * Notify any registered observers that the item previously located at position
         * has been removed from the data set. The items previously located at and
         * after position may now be found at oldPosition - 1.
         *
         * This is a structural change event. Representations of other existing items
         * in the data set are still considered up to date and will not be rebound,
         * though their positions may be altered.
         */
        notifyItemRemoved(position);
        /**
         * Notify any registered observers that the itemCount items starting at
         * position positionStart have changed. Equivalent to calling
         * notifyItemRangeChanged(position, itemCount, null);.
         *
         * This is an item change event, not a structural change event. It indicates
         * that any reflection of the data in the given position range is out of date
         * and should be updated. The items in the given range retain the same identity.
         */
        notifyItemRangeChanged(position, getItemCount());
    }

    public void addItem(int position, Inbox item) {
        mInbox.add(position, item);
        notifyItemInserted(position);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list_multi_selection, parent, false);
        return new InboxViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        holder.itemView.findViewById(R.id.emailSender).setText(mInbox.get(position).getFrom());
        holder.itemView.findViewById(R.id.emailTitle).setText(mInbox.get(position).getEmail());
        holder.itemView.findViewById(R.id.emailContent).setText(mInbox.get(position).getMessage());
        holder.itemView.findViewById(R.id.emailTimeSent).setText(mInbox.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return this.mInbox.size();
    }

    public class InboxViewHolder extends RecyclerView.ViewHolder {
        TextView mailIcon;
        TextView mailSender;
        TextView mailTitle;
        TextView mailContent;
        TextView mailTimeSent;

        public InboxViewHolder(@NonNull View itemView) {
            super(itemView);

            mailIcon = itemView.findViewById(R.id.tvIcon);
            mailSender = itemView.findViewById(R.id.emailSender);
            mailTitle = itemView.findViewById(R.id.emailTitle);
            mailContent = itemView.findViewById(R.id.emailContent);
            mailTimeSent = itemView.findViewById(R.id.emailTitle);
        }
    }

}
