package alphadevs.insyto_android;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import alphadevs.insyto_android.adapter.InsytoRecyclerViewAdapter;
import alphadevs.insyto_android.models.InsyteItemData;
import alphadevs.insyto_android.helper.InsyteItemTouchHelperCallback;
import alphadevs.insyto_android.listener.InsyteItemClickListenerImpl;
import alphadevs.insyto_android.listener.RecyclerLoadMoreListener;


public class InsyteFragmentList extends Fragment {

    private final static Gson gson = InsytoGsonBuilder.create();

    private View rootView;
    protected RecyclerView mRecyclerView;
    protected InsytoRecyclerViewAdapter mAdapter;
    protected LinearLayoutManager mLayoutManager;
    private ItemTouchHelper mItemTouchHelper;

    private ArrayList<InsyteItemData> insytesList = new ArrayList<InsyteItemData>();

    private InsytoVolley iVolley = InsytoVolley.getInstance();

    private final static String INSYTES_LIST = "INSYTES_LIST";

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
        TextView shareInsyte = (TextView) rootView.findViewById(R.id.share_insyte_edit_list);
        shareInsyte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CreateInsyteFragment createFragment = CreateInsyteFragment.newInstance();

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack so the user can navigate back
                transaction.replace(R.id.insyte_list, createFragment);
                transaction.addToBackStack(null);

                // Commit the transaction
                transaction.commit();
            }
        });
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
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
                new ArrayList<InsyteItemData>());
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnScrollListener(new RecyclerLoadMoreListener(mLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                loadMoreInsytes(current_page);
            }

            @Override
            public void onScrolledToTop() {
                loadNewerInsytes();
            }

            @Override
            public void onScrolledToBottom() {

            }
        });


        ItemTouchHelper.Callback callback = new InsyteItemTouchHelperCallback(mAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // loadMoreInsytes(1);
        // TODO faking data for the moment
        String fakeData = "[{\"id\":101,\"title\":\"FAKE DATA!!!\",\"description\":\"Roll n rock again\",\"created_at\":\"2015-11-25T20:40:37.000Z\",\"updated_at\":\"2015-11-25T20:40:37.000Z\",\"media_id\":7,\"media_type\":\"Video\",\"category_id\":5,\"category_name\":\"Geography\",\"media\":{\"id\":7,\"url\":\"https://github.com/rails/rails/blob/master/activerecord/lib/active_record/nested_attributes.rb\",\"insyte_id\":null,\"created_at\":\"2015-11-25T20:40:37.000Z\",\"updated_at\":\"2015-11-25T20:40:37.000Z\"}},{\"id\":100,\"title\":\"Hello world, wanna rock?\",\"description\":\"Roll n rock again\",\"created_at\":\"2015-11-25T20:38:46.000Z\",\"updated_at\":\"2015-11-25T20:38:46.000Z\",\"media_id\":6,\"media_type\":\"Video\",\"category_id\":5,\"category_name\":\"Geography\",\"media\":{\"id\":6,\"url\":\"https://github.com/rails/rails/blob/master/activerecord/lib/active_record/nested_attributes.rb\",\"insyte_id\":null,\"created_at\":\"2015-11-25T20:38:46.000Z\",\"updated_at\":\"2015-11-25T20:38:46.000Z\"}},{\"id\":99,\"title\":\"Hello world, wanna rock?\",\"description\":\"Roll n rock again\",\"created_at\":\"2015-11-25T20:37:39.000Z\",\"updated_at\":\"2015-11-25T20:37:39.000Z\",\"media_id\":5,\"media_type\":\"Video\",\"category_id\":5,\"category_name\":\"Geography\",\"media\":{\"id\":5,\"url\":\"https://github.com/rails/rails/blob/master/activerecord/lib/active_record/nested_attributes.rb\",\"insyte_id\":null,\"created_at\":\"2015-11-25T20:37:39.000Z\",\"updated_at\":\"2015-11-25T20:37:39.000Z\"}},{\"id\":98,\"title\":\"Hello world, wanna rock?\",\"description\":\"Roll n rock again\",\"created_at\":\"2015-11-25T19:56:19.000Z\",\"updated_at\":\"2015-11-25T19:56:19.000Z\",\"media_id\":4,\"media_type\":\"Video\",\"category_id\":5,\"category_name\":\"Geography\",\"media\":{\"id\":4,\"url\":\"https://github.com/rails/rails/blob/master/activerecord/lib/active_record/nested_attributes.rb\",\"insyte_id\":null,\"created_at\":\"2015-11-25T19:56:19.000Z\",\"updated_at\":\"2015-11-25T19:56:19.000Z\"}},{\"id\":97,\"title\":\"Hello world, wanna rock?\",\"description\":\"Roll n rock again\",\"created_at\":\"2015-11-25T19:54:51.000Z\",\"updated_at\":\"2015-11-25T19:54:51.000Z\",\"media_id\":7,\"media_type\":\"Text\",\"category_id\":5,\"category_name\":\"Geography\",\"media\":{\"id\":7,\"content\":\"aaaaaaaaaaaaaaaaaaaaaaaaaaa\",\"insyte_id\":null,\"created_at\":\"2015-11-25T19:54:51.000Z\",\"updated_at\":\"2015-11-25T19:54:51.000Z\"}}]";
        Type listInsytesType = new TypeToken<List<InsyteItemData>>() {}.getType();
        List<InsyteItemData> insytesData = gson.fromJson(fakeData, listInsytesType);
        mAdapter.addAll(insytesData, mAdapter.getItemCount());
    }

    private void loadMoreInsytes(int page)
    {
        // Request a string response
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                InsytoUrlBuilder.getInsytesUrl(page),
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

                toastAnError();
            }
        });


        // Add the request to the queue
        iVolley.add(stringRequest);
    }

    private void loadNewerInsytes()
    {
        long secondsToLive = mAdapter.getFirstItemCreatedDate().getTime()/1000 + 1;
        System.out.println(secondsToLive);
        // Request a string response
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                InsytoUrlBuilder.getNewerInsytesUrl(secondsToLive),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Type listInsytesType = new TypeToken<List<InsyteItemData>>() {}.getType();
                        List<InsyteItemData> insytesData = gson.fromJson(response, listInsytesType);
                        mAdapter.addAll(insytesData, 0);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                // Error handling
                System.out.println("Something went wrong!");
                error.printStackTrace();

                toastAnError();
            }
        });

        // Add the request to the queue
        iVolley.add(stringRequest);
    }

    private void toastAnError()
    {
        Toast.makeText(this.getContext(), "Could not get insytes", Toast.LENGTH_SHORT).show();
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
        void replaceInsyteFragment(String id);
    }
}
