package alphadevs.insyto_android;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import alphadevs.insyto_android.adapter.InsytoRecyclerViewAdapter;
import alphadevs.insyto_android.data.InsyteItemData;
import alphadevs.insyto_android.helper.InsyteItemTouchHelperCallback;
import alphadevs.insyto_android.helper.OnStartDragListener;
import alphadevs.insyto_android.listener.InsyteItemClickListenerImpl;


public class InsyteFragmentList extends Fragment implements OnStartDragListener {

    private final static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").create(); // TODO singleton pls

    private View rootView;
    protected RecyclerView mRecyclerView;
    protected InsytoRecyclerViewAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    private ItemTouchHelper mItemTouchHelper;

    private InsytoVolley iVolley = InsytoVolley.getInstance();

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public InsyteFragmentList() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.insyte_list, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.insyte_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new InsytoRecyclerViewAdapter(
                new InsyteItemClickListenerImpl((OnInsyteListInteractionListener)getActivity()),
                this,
                new ArrayList<InsyteItemData>());
        mRecyclerView.setAdapter(mAdapter);


        ItemTouchHelper.Callback callback = new InsyteItemTouchHelperCallback(mAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TESTING VOLLEY CHUCK JOKE
        String url = "http://10.0.2.2:3000/v1/insytes"; // TODO works only in emulator!!! (if it works)
        // 5 times
            // Request a string response
         /*   StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Type listInsytesType = new TypeToken<List<InsyteItemData>>() {}.getType();
                            List<InsyteItemData> insytesData = gson.fromJson(response, listInsytesType);
                            mAdapter.addAll(insytesData, mAdapter.getItemCount());

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    // Error handling
                    System.out.println("Something went wrong!");
                    error.printStackTrace();

                }
            });


            // Add the request to the queue
            iVolley.add(stringRequest);*/

        // TODO faking data for the moment
        String fakeData = "[{\"id\":1,\"title\":\"Hello world\",\"description\":\"Best course ever\",\"created_at\":\"2011-11-11T00:00:00.000Z\",\"updated_at\":\"2011-11-11T00:00:00.000Z\",\"media_id\":null,\"media_type\":null},{\"id\":2,\"title\":\"LOVE ROCK N ROLL\",\"description\":\"Best OF THE BEST ever\",\"created_at\":\"2011-11-11T00:00:00.000Z\",\"updated_at\":\"2011-11-11T00:00:00.000Z\",\"media_id\":null,\"media_type\":null}]";
        Type listInsytesType = new TypeToken<List<InsyteItemData>>() {}.getType();
        List<InsyteItemData> insytesData = gson.fromJson(fakeData, listInsytesType);
        mAdapter.addAll(insytesData, mAdapter.getItemCount());
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnInsyteListInteractionListener {
        void switchFragment(String id);
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

}
