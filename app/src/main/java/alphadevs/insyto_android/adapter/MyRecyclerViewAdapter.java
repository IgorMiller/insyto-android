package alphadevs.insyto_android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import alphadevs.insyto_android.R;
import alphadevs.insyto_android.data.InsyteItemData;
import alphadevs.insyto_android.listener.InsyteItemClickListener;

public class MyRecyclerViewAdapter extends RecyclerView
        .Adapter<MyRecyclerViewAdapter
        .DataObjectHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private Context mContext;
    private ArrayList<InsyteItemData> mDataset;
    private InsyteItemClickListener insyteItemClickListener;

    public static class DataObjectHolder
            extends RecyclerView.ViewHolder{
        TextView label;
        TextView dateTime;
        ImageView thumbnail;

        public DataObjectHolder(View itemView) {
            super(itemView);
            label = (TextView) itemView.findViewById(R.id.insyte_title);
            dateTime = (TextView) itemView.findViewById(R.id.insyte_description);
            thumbnail = (ImageView) itemView.findViewById(R.id.insyte_thumbnail);
        }
    }

    public MyRecyclerViewAdapter(InsyteItemClickListener insyteItemClickListener,ArrayList<InsyteItemData> myDataset) {
        this.mDataset = myDataset;
        this.insyteItemClickListener = insyteItemClickListener;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.insyte_item, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, final int position) {
        final InsyteItemData insyteItem = mDataset.get(position);
        holder.label.setText(insyteItem.getTitle());
        holder.dateTime.setText(insyteItem.getDescription());
        holder.thumbnail.setImageResource(R.drawable.sahara_test); //TODO testing
        holder.itemView.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View v) {
                                                   insyteItemClickListener.onClick(insyteItem.getId());
                                               }
                                           });
    }

    public void addItem(InsyteItemData dataObj, int index) {
        mDataset.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
