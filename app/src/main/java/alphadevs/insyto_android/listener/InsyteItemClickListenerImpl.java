package alphadevs.insyto_android.listener;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ExpandableListView;

import alphadevs.insyto_android.InsyteFragment;
import alphadevs.insyto_android.InsyteFragmentList;
import alphadevs.insyto_android.R;

public class InsyteItemClickListenerImpl implements InsyteItemClickListener {

    private InsyteFragmentList.OnInsyteListInteractionListener clickListener;

    public InsyteItemClickListenerImpl(InsyteFragmentList.OnInsyteListInteractionListener clickListener)
    {
        this.clickListener = clickListener;
    }

    @Override
    public void onClick(String insyteId) {
        clickListener.switchFragment(insyteId);
    }
}
