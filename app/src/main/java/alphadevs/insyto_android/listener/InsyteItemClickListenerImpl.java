package alphadevs.insyto_android.listener;

import alphadevs.insyto_android.InsyteFragmentList;

public class InsyteItemClickListenerImpl implements InsyteItemClickListener {

    private InsyteFragmentList.OnInsyteListInteractionListener clickListener;

    public InsyteItemClickListenerImpl(InsyteFragmentList.OnInsyteListInteractionListener clickListener)
    {
        this.clickListener = clickListener;
    }

    @Override
    public void onClick(Integer insyteId) {
        clickListener.replaceInsyteFragment(insyteId);
    }
}
