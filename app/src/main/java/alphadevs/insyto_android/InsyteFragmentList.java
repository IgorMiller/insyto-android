package alphadevs.insyto_android;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import alphadevs.insyto_android.adapter.InsytoRecyclerViewAdapter;
import alphadevs.insyto_android.models.InsyteItemData;
import alphadevs.insyto_android.helper.InsyteItemTouchHelperCallback;
import alphadevs.insyto_android.listener.InsyteItemClickListenerImpl;
import alphadevs.insyto_android.listener.RecyclerLoadMoreListener;
import alphadevs.insyto_android.preferences.MainPrefs;


public class InsyteFragmentList extends Fragment {

    private final static Gson gson = InsytoGson.getInstance();

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
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.insyte_list, container, false);

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
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
                new InsyteItemClickListenerImpl((OnInsyteListInteractionListener) getActivity()),
                new ArrayList<InsyteItemData>());
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnScrollListener(new RecyclerLoadMoreListener(mLayoutManager) {
            @Override
            public void onLoadMore() {
                Snackbar.make(rootView, "Load more insytes?", Snackbar.LENGTH_LONG)
                        .setAction("loadMore", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                loadMoreInsytes(getCurrentPage(), false);
                            }
                        }).show();
            }
            @Override
            public void onEndReached() {
                Snackbar.make(rootView, "No more insytes in sight", Snackbar.LENGTH_LONG)
                        .setAction("nomore",null).show();
            }

            @Override
            public void onScrolledToTop() {
            }

            @Override
            public void onScrolledToBottom() {
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int topRowVerticalPosition =
                        (recyclerView == null || recyclerView.getChildCount() == 0) ?
                                0 : recyclerView.getChildAt(0).getTop();
                SwipeRefreshLayout swipeContainer = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container);
                swipeContainer.setEnabled(getFirstVisibleItem() == 0 && topRowVerticalPosition >= 0);
            }
        });

        SwipeRefreshLayout swipeContainer = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadNewerInsytes();
            }
        });


        ItemTouchHelper.Callback callback = new InsyteItemTouchHelperCallback(mAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loadMoreInsytes(1, false);
        // TODO faking data for the moment
        /*
        String fakeData = "[{\"id\":101,\"title\":\"FAKE DATA!!!\",\"description\":\"Roll n rock again\",\"created_at\":\"2015-11-25T20:40:37.000Z\",\"updated_at\":\"2015-11-25T20:40:37.000Z\",\"media_id\":7,\"media_type\":\"Video\",\"category_id\":5,\"category_name\":\"Geography\",\"media\":{\"id\":7,\"url\":\"https://github.com/rails/rails/blob/master/activerecord/lib/active_record/nested_attributes.rb\",\"insyte_id\":null,\"created_at\":\"2015-11-25T20:40:37.000Z\",\"updated_at\":\"2015-11-25T20:40:37.000Z\"}},{\"id\":100,\"title\":\"Hello world, wanna rock?\",\"description\":\"Roll n rock again\",\"created_at\":\"2015-11-25T20:38:46.000Z\",\"updated_at\":\"2015-11-25T20:38:46.000Z\",\"media_id\":6,\"media_type\":\"Video\",\"category_id\":5,\"category_name\":\"Geography\",\"media\":{\"id\":6,\"url\":\"https://github.com/rails/rails/blob/master/activerecord/lib/active_record/nested_attributes.rb\",\"insyte_id\":null,\"created_at\":\"2015-11-25T20:38:46.000Z\",\"updated_at\":\"2015-11-25T20:38:46.000Z\"}},{\"id\":99,\"title\":\"Hello world, wanna rock?\",\"description\":\"Roll n rock again\",\"created_at\":\"2015-11-25T20:37:39.000Z\",\"updated_at\":\"2015-11-25T20:37:39.000Z\",\"media_id\":5,\"media_type\":\"Video\",\"category_id\":5,\"category_name\":\"Geography\",\"media\":{\"id\":5,\"url\":\"https://github.com/rails/rails/blob/master/activerecord/lib/active_record/nested_attributes.rb\",\"insyte_id\":null,\"created_at\":\"2015-11-25T20:37:39.000Z\",\"updated_at\":\"2015-11-25T20:37:39.000Z\"}},{\"id\":98,\"title\":\"Hello world, wanna rock?\",\"description\":\"Roll n rock again\",\"created_at\":\"2015-11-25T19:56:19.000Z\",\"updated_at\":\"2015-11-25T19:56:19.000Z\",\"media_id\":4,\"media_type\":\"Video\",\"category_id\":5,\"category_name\":\"Geography\",\"media\":{\"id\":4,\"url\":\"https://github.com/rails/rails/blob/master/activerecord/lib/active_record/nested_attributes.rb\",\"insyte_id\":null,\"created_at\":\"2015-11-25T19:56:19.000Z\",\"updated_at\":\"2015-11-25T19:56:19.000Z\"}},{\"id\":97,\"title\":\"Hello world, wanna rock?\",\"description\":\"Roll n rock again\",\"created_at\":\"2015-11-25T19:54:51.000Z\",\"updated_at\":\"2015-11-25T19:54:51.000Z\",\"media_id\":7,\"media_type\":\"Text\",\"category_id\":5,\"category_name\":\"Geography\",\"media\":{\"id\":7,\"content\":\"aaaaaaaaaaaaaaaaaaaaaaaaaaa\",\"insyte_id\":null,\"created_at\":\"2015-11-25T19:54:51.000Z\",\"updated_at\":\"2015-11-25T19:54:51.000Z\"}}]";
        Type listInsytesType = new TypeToken<List<InsyteItemData>>() {}.getType();
        List<InsyteItemData> insytesData = gson.fromJson(fakeData, listInsytesType);
        mAdapter.addAll(insytesData, mAdapter.getItemCount());
        //*/
    }

    public void reloadAll()
    {
        loadMoreInsytes(1, true);
    }

    private void loadMoreInsytes(int page, final boolean removeExisting) {
        // Request a string response
        MainPrefs prefs = new MainPrefs(getActivity().getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                (prefs.getNearbyActive())
                        ? InsytoUrlBuilder.getInsytesUrlGPS(page,
                                prefs.getLastKnownLongitude(), prefs.getLastKnownLatitude(),
                                prefs.getNearbyRadius())
                        : InsytoUrlBuilder.getInsytesUrl(page),
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Type listInsytesType = new TypeToken<List<InsyteItemData>>() {
                    }.getType();
                    List<InsyteItemData> insytesData = gson.fromJson(response, listInsytesType);
                    if(removeExisting)
                    {
                        mAdapter.clear();
                    }
                    mAdapter.addAll(insytesData, mAdapter.getItemCount());
                    doneLoading();
                }
            }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                // Error handling
                System.out.println("Something went wrong!");
                error.printStackTrace();

                toastAnError();
                doneLoading();
            }
        });


        // Add the request to the queue
        iVolley.add(stringRequest);
        System.out.println(stringRequest.getUrl());
    }

    private void loadNewerInsytes() {
        long secondsToLive = mAdapter.getFirstItemCreatedDate().getTime() / 1000 + 1;
        System.out.println(secondsToLive);
        MainPrefs prefs = new MainPrefs(getContext());
        // Request a string response
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                (prefs.getNearbyActive())
                        ? InsytoUrlBuilder.getNewerInsytesUrlGPS(secondsToLive,
                        prefs.getLastKnownLongitude(), prefs.getLastKnownLatitude(),
                        prefs.getNearbyRadius())
                        : InsytoUrlBuilder.getNewerInsytesUrl(secondsToLive),
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Type listInsytesType = new TypeToken<List<InsyteItemData>>() {
                    }.getType();
                    List<InsyteItemData> insytesData = gson.fromJson(response, listInsytesType);
                    mAdapter.addAll(insytesData, 0);
                    doneLoading();
                }
            }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                // Error handling
                System.out.println("Something went wrong!");
                error.printStackTrace();

                toastAnError();
                doneLoading();
            }
        });

        // Add the request to the queue
        iVolley.add(stringRequest);
        System.out.println(stringRequest.getUrl());
    }

    private void toastAnError() {
        Snackbar.make(rootView, "Could not get insytes", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    private void doneLoading() {
        SwipeRefreshLayout swipeContainer = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container);
        swipeContainer.setRefreshing(false);
        mRecyclerView.scrollToPosition(0);
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
        void replaceInsyteFragment(Integer id);
    }
}
