package alphadevs.insyto_android;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import alphadevs.insyto_android.models.InsyteItemData;
import alphadevs.insyto_android.models.InsyteMediaText;
import alphadevs.insyto_android.models.PostInsyteItem;


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
        title.requestFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(title, InputMethodManager.SHOW_IMPLICIT);
    }

    /*@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loadInsyte();
    }*/

    private void postInsyte()
    {
        // Request a string response
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                InsytoUrlBuilder.getInsytesUrl(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Return to previous fragment
                        getActivity().getSupportFragmentManager().popBackStack();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                // Error handling
                System.out.println("Something went wrong!");
                error.printStackTrace();

                toastAnError();
            }
        }){
            @Override
            public byte[] getBody() throws AuthFailureError {
                InsyteItemData insyteData = new InsyteItemData();
                insyteData.setTitle(title.getText().toString());
                insyteData.setDescription(description.getText().toString());

                insyteData.setMedia_type("Text"); // TODO generic
                insyteData.setCategory_id(1);// TODO category
                insyteData.setMedia(new InsyteMediaText().withContent(content.getText().toString()));// TODO support other media types

                PostInsyteItem postInsyte = new PostInsyteItem(insyteData);
                String jsonBody = gson.toJson(postInsyte);
                return jsonBody.getBytes();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/json");
                return params;
            }
        };

        // Add the request to the queue
        iVolley.add(stringRequest);
    }

    private void toastAnError()
    {
        Toast.makeText(this.getContext(), "Error while creating insyte", Toast.LENGTH_SHORT).show();
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
