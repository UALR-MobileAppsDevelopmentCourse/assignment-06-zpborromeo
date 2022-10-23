package com.ualr.recyclerviewassignment.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ualr.recyclerview.R;
import com.ualr.recyclerview.model.Item;
import com.ualr.recyclerview.model.People;
import com.ualr.recyclerview.model.SectionHeader;
import com.ualr.recyclerview.utils.Tools;
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

    // TODO 01: Define the removeItem method
    public void removeItem(int position) {
        if (position >= mInbox.size()){
            return;
        }
        // TODO 02: Remove the item from the data set
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
        // TODO 03: Notify the adapter that a item has been removed
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
        // TODO 04: Notify the adapter that a set of items has changed
        notifyItemRangeChanged(position, getItemCount());
    }

    // TODO 06: Define the addItem method
    public void addItem(int position, Inbox item) {
        // TODO 07: Add new item to the data set at the provided position
        mInbox.add(position, item);
        // TODO 08: Notify the adapter that an item inserted
        notifyItemInserted(position);
    }

    @Override
    public int getItemViewType(int position) {
        return this.mInbox.get(position).isSection()? HEADER_VIEW : PERSON_VIEW;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder vh = null;
        View itemView = null;

        switch (viewType) {
            case (HEADER_VIEW) :
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_section, parent, false);
                vh = new SectionHeaderViewHolder(itemView);
                break;
            case (PERSON_VIEW):
                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_people_chat, parent, false);
                vh = new PersonViewHolder(itemView);
                break;
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        Inbox item = mInbox.get(position);
        if (holder instanceof PersonViewHolder) {
            PersonViewHolder viewHolder = (PersonViewHolder) holder;
            People person = (People) item;
            viewHolder.name.setText(person.getName());
            Tools.displayImageRound(mContext, viewHolder.image, person.getImage());
        } else {
            // Instance of SectionHeaderViewHolder
            SectionHeaderViewHolder viewHolder = (SectionHeaderViewHolder) holder;
            SectionHeader section = (SectionHeader) item;
            viewHolder.label.setText(section.getLabel());
        }
    }

    @Override
    public int getItemCount() {
        return this.mInbox.size();
    }

    public class PersonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView image;
        public TextView name;
        public View lyt_parent;

        /**
         * PersonViewHolder constructor
         * Retrieves references to the ImageView and the TextView inside the the view provided as input parameter
         * The adapter later retrieves view references from these properties when it updates the child views with new data
         *
         * @param v parent item view reference
         */
        public PersonViewHolder(@NonNull View v) {
            super(v);
            image = v.findViewById(R.id.image);
            name = v.findViewById(R.id.name);
            lyt_parent = v.findViewById(R.id.lyt_parent);
            lyt_parent.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mOnItemClickListener.onItemClick(getLayoutPosition());
        }
    }

    public class SectionHeaderViewHolder extends RecyclerView.ViewHolder {

        public TextView label;

        public SectionHeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            this.label = itemView.findViewById(R.id.title_section);
        }
    }

}
