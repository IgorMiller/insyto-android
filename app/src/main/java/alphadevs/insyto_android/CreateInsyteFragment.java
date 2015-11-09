package alphadevs.insyto_android;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import alphadevs.insyto_android.data.InsyteItemData;


public class CreateInsyteFragment extends Fragment {
    private static final Gson gson = InsytoGsonBuilder.create();


    private EditText title, description, content;
    private Button create_button;

    private InsytoVolley iVolley = InsytoVolley.getInstance();

    private View rootView;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    public static CreateInsyteFragment newInstance() {
        CreateInsyteFragment fragment = new CreateInsyteFragment();
        /*Bundle args = new Bundle();
        args.putString(ARG_INSYTE_ID, id);
        fragment.setArguments(args);*/
        return fragment;
    }

    public CreateInsyteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.create_insyte, container, false);

        title = (EditText) rootView.findViewById(R.id.edit_title);
        description = (EditText) rootView.findViewById(R.id.edit_description);
        content = (EditText) rootView.findViewById(R.id.edit_content);

        create_button = (Button) rootView.findViewById(R.id.create_insyte_btn);

        create_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postInsyte();
            }
        });

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    /*@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loadInsyte();
    }*/

    private void postInsyte()
    {
        /*String url = "http://10.0.2.2:3000/v1/insytes/"; // TODO works only in emulator!!! (if it works)

        // Request a string response
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        InsyteItemData insyteData = gson.fromJson(response, InsyteItemData.class);
                        TextView cardTitle = (TextView) rootView.findViewById(R.id.card_title);
                        cardTitle.setText(insyteData.getTitle());

                        TextView cardText = (TextView) rootView.findViewById(R.id.card_text);
                        cardText.setText(insyteData.getDescription());

                        getActivity().getSupportFragmentManager().popBackStack(); // Return to previous fragment
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
    public interface CreateInsyteListener {
        void previousFragment();
    }
}
