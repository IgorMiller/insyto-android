package alphadevs.insyto_android;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import alphadevs.insyto_android.data.InsyteItemData;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link InsyteFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link InsyteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InsyteFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    static final String ARG_INSYTE_ID= "id";
    static final String ARG_INSYTE_TITLE = "param2";

    // TODO: Rename and change types of parameters
    private String mInsyteId;
    private String mParam2;


    private View rootView;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param id Insyte id.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InsyteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InsyteFragment newInstance(String id, String param2) {
        InsyteFragment fragment = new InsyteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_INSYTE_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    public InsyteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mInsyteId = getArguments().getString(ARG_INSYTE_ID);
        }
    }

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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_insyte, container, false);

        /*GetInsyteByIdTask insyteTask = new GetInsyteByIdTask(rootView);
        insyteTask.execute(mInsyteId);*/

        // TESTING VOLLEY CHUCK JOKE
        String url = "http://api.icndb.com/jokes/" + mInsyteId;

// Request a string response
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Gson gson = new Gson();
                        InsyteItemData insyteData = gson.fromJson(response, InsyteItemData.class);
                        TextView cardTitle = (TextView) rootView.findViewById(R.id.card_title);
                        cardTitle.setText("Chuck Norris id:" + insyteData.getChuck().getId());

                        TextView cardText = (TextView) rootView.findViewById(R.id.card_text);
                        cardText.setText(insyteData.getChuck().getJoke());


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
        Volley.newRequestQueue(this.getContext()).add(stringRequest);



        // TESTING Volley for image loading
        String urlImage = "http://i.imgur.com/Nwk25LA.jpg";
        final ImageView cardImage = (ImageView) rootView.findViewById(R.id.card_image);

        ImageRequest imgRequest = new ImageRequest(urlImage,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        cardImage.setImageBitmap(response);
                    }
                }, 0, 0, ImageView.ScaleType.FIT_XY, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                cardImage.setBackgroundColor(Color.parseColor("#ff0000"));
                error.printStackTrace();
            }
        });

        Volley.newRequestQueue(this.getContext()).add(imgRequest);

        return rootView;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    // Private async task to get data
    /*private class GetInsyteByIdTask extends AsyncTask<String, String, InsyteItemData> {

        private static final String ChuckNorrisUrl = "http://api.icndb.com/jokes/";

        private View mRootView;

        public GetInsyteByIdTask(View rootView)
        {
            mRootView = rootView;
        }

        @Override
        protected InsyteItemData doInBackground(String... params) {

            HttpURLConnection urlConnection = null;
            InsyteItemData insyte = null;

            try {

                URL url = new URL(ChuckNorrisUrl + params[0]);

                urlConnection = (HttpURLConnection) url.openConnection();


                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                Gson gson = new Gson();
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
            TextView cardTitle = (TextView) mRootView.findViewById(R.id.card_title);
            cardTitle.setText("Chuck Norris id:" + result.getChuck().getId());

            TextView cardText = (TextView) mRootView.findViewById(R.id.card_text);
            cardText.setText(result.getChuck().getJoke());
        }

    } // end AsyncTask
    */
}
