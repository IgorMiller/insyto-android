package alphadevs.insyto_android.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import alphadevs.insyto_android.R;
import alphadevs.insyto_android.models.InsyteItemData;
import alphadevs.insyto_android.helper.ItemTouchHelperAdapter;
import alphadevs.insyto_android.helper.ItemTouchHelperViewHolder;
import alphadevs.insyto_android.listener.InsyteItemClickListener;

public class InsytoRecyclerViewAdapter extends RecyclerView
        .Adapter<InsytoRecyclerViewAdapter.ItemViewHolder> implements ItemTouchHelperAdapter {
    private static String LOG_TAG = "InsytoRecyclerViewAdapter";
    private Context mContext;
    private ArrayList<InsyteItemData> mItems;
    private InsyteItemClickListener insyteItemClickListener;

    public static class ItemViewHolder
            extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {
        TextView title;
        TextView description;
        TextView author;
        TextView category;

        public ItemViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.insyte_title);
            description = (TextView) itemView.findViewById(R.id.insyte_description);
            category = (TextView) itemView.findViewById(R.id.insyte_category);
            author = (TextView) itemView.findViewById(R.id.insyte_author);
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

    public InsytoRecyclerViewAdapter(InsyteItemClickListener insyteItemClickListener, ArrayList<InsyteItemData> dataset) {
        this.mItems = dataset;
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
        holder.description.setText(insyteItem.getDescription());
        holder.category.setText(insyteItem.getCategory_name());
        holder.author.setText("AUTHOR TODO");
        // TODO all these listeners
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insyteItemClickListener.onClick(insyteItem.getId());
            }
        });
    }

    public void addItem(InsyteItemData dataObj, int index) {
        mItems.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void addAll(Collection<? extends InsyteItemData> items, int index)
    {
        mItems.addAll(index, items);
        notifyItemRangeInserted(index, items.size());
    }

    public void deleteItem(int index) {
        mItems.remove(index);
        notifyItemRemoved(index);
    }

    public Date getFirstItemCreatedDate()
    {
        return mItems.get(0).getCreatedAt();
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
