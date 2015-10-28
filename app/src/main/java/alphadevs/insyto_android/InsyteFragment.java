package alphadevs.insyto_android;

import android.graphics.Bitmap;
import android.graphics.Color;
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
import com.google.gson.Gson;

import alphadevs.insyto_android.data.InsyteItemData;


public class InsyteFragment extends Fragment {
    static final String ARG_INSYTE_ID= "id";
    private static final Gson gson = new Gson();
    private String mInsyteId;


    private InsytoVolley iVolley = InsytoVolley.getInstance();

    private View rootView;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    public static InsyteFragment newInstance(String id) {
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_insyte, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TESTING VOLLEY CHUCK JOKE
        String url = "http://api.icndb.com/jokes/" + mInsyteId;

        // Request a string response
        /*StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

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
        iVolley.add(stringRequest);*/




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

        iVolley.add(imgRequest);
    }
}
