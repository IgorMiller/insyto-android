package alphadevs.insyto_android;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.JsonReader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.google.gson.Gson;

import alphadevs.insyto_android.adapter.InsytoRecyclerViewAdapter;
import alphadevs.insyto_android.adapter.InsytoRecyclerViewAdapter;
import alphadevs.insyto_android.data.InsyteItemData;
import alphadevs.insyto_android.listener.InsyteItemClickListenerImpl;


public class InsyteFragmentList extends Fragment {

    private final static Gson gson = new Gson();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    protected RecyclerView mRecyclerView;
    protected InsytoRecyclerViewAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnInsyteListInteractionListener mListener;

    // TODO: Rename and change types of parameters
    public static InsyteFragmentList newInstance(String param1, String param2) {
        InsyteFragmentList fragment = new InsyteFragmentList();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public InsyteFragmentList() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }



        // TODO
        for(int i = 0; i< 5; i++) {
            GetInsytesTask insytesTask = new GetInsytesTask();
            insytesTask.execute();
        }
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnInsyteListInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.insyte_list, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.insyte_recycler_view);

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new InsytoRecyclerViewAdapter(
                new InsyteItemClickListenerImpl((OnInsyteListInteractionListener)getActivity()),
                new ArrayList<InsyteItemData>());
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    // TODO refactor all that
    private static final String TEST_TEXT = "Cards present information to users with a consistent look and feel across different apps. This lesson shows you how to create cards in your Android Wear apps.\n" +
            "\n" +
            "The Wearable UI Library provides implementations of cards specifically designed for wearable devices. This library contains the CardFrame class, which wraps views inside a card-styled frame with a white background, rounded corners, and a light-drop shadow. A CardFrame instance can only contain one direct child, usually a layout manager, to which you can add other views to customize the content inside the card.\n" +
            "\n" +
            "You can add cards to your app in two ways:\n" +
            "\n" +
            "    Use or extend the CardFragment class.\n" +
            "    Add a card inside a CardScrollView instance in your layout.\n" +
            "\n" +
            "Note: This lesson shows you how to add cards to Android Wear activities. Android notifications on wearable devices are also displayed as cards. For more information, see Adding Wearable Features to Notifications.";


    private class GetInsytesTask extends AsyncTask<String, String, InsyteItemData> {

        private static final String ChuckNorrisUrl = "http://api.icndb.com/jokes/random";

        @Override
        protected InsyteItemData doInBackground(String... params) {

            HttpURLConnection urlConnection = null;
            InsyteItemData insyte = null;

            try {

                URL url = new URL(ChuckNorrisUrl);

                urlConnection = (HttpURLConnection) url.openConnection();


                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                insyte = gson.fromJson(reader, InsyteItemData.class);


            } catch (Exception e ) {

                System.out.println(e.getMessage());
            }
            finally {
                if(urlConnection != null)
                {
                    urlConnection.disconnect();
                }
            }
            return insyte;

        }

        @Override
        protected void onPostExecute(InsyteItemData result) {
            mAdapter.addItem(result, 0);
        }

    } // end CallAPI

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
        public void switchFragment(String id);
    }

}
