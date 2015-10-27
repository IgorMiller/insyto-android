package alphadevs.insyto_android.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import alphadevs.insyto_android.R;
import alphadevs.insyto_android.data.InsyteItemData;
import alphadevs.insyto_android.helper.ItemTouchHelperAdapter;
import alphadevs.insyto_android.helper.ItemTouchHelperViewHolder;
import alphadevs.insyto_android.helper.OnStartDragListener;
import alphadevs.insyto_android.listener.InsyteItemClickListener;

public class InsytoRecyclerViewAdapter extends RecyclerView
        .Adapter<InsytoRecyclerViewAdapter.ItemViewHolder> implements ItemTouchHelperAdapter {
    private static String LOG_TAG = "InsytoRecyclerViewAdapter";
    private Context mContext;
    private ArrayList<InsyteItemData> mItems;
    private InsyteItemClickListener insyteItemClickListener;

    private final OnStartDragListener mDragStartListener;

    public static class ItemViewHolder
            extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {
        TextView title;
        TextView description;
        ImageView thumbnail;

        public ItemViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.insyte_title);
            description = (TextView) itemView.findViewById(R.id.insyte_description);
            thumbnail = (ImageView) itemView.findViewById(R.id.insyte_thumbnail);
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }
    }

    public InsytoRecyclerViewAdapter(InsyteItemClickListener insyteItemClickListener, OnStartDragListener dragStartListener, ArrayList<InsyteItemData> dataset) {
        this.mItems = dataset;
        mDragStartListener = dragStartListener;
        this.insyteItemClickListener = insyteItemClickListener;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.insyte_item, parent, false);

        ItemViewHolder dataObjectHolder = new ItemViewHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {
        final InsyteItemData insyteItem = mItems.get(position);
        holder.title.setText(insyteItem.getTitle()); // TODO
        holder.description.setText(insyteItem.getChuck().getJoke().replace("&quot;", "\""));
        holder.thumbnail.setImageResource(R.drawable.chuck_norris); //TODO testing
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insyteItemClickListener.onClick(insyteItem.getChuck().getId());
            }
        });
        // Start a drag whenever the handle view it touched
        holder.thumbnail.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mDragStartListener.onStartDrag(holder);
                }
                return false;
            }
        });
    }

    public void addItem(InsyteItemData dataObj, int index) {
        mItems.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mItems.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mItems, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        mItems.remove(position);
        notifyItemRemoved(position);
    }
}
