package alphadevs.insyto_android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.GroupGrantee;
import com.amazonaws.services.s3.model.Permission;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import alphadevs.insyto_android.models.InsyteItemData;
import alphadevs.insyto_android.models.InsyteMedia;
import alphadevs.insyto_android.models.InsyteMediaAudio;
import alphadevs.insyto_android.models.InsyteMediaText;
import alphadevs.insyto_android.models.InsyteMediaVideo;
import alphadevs.insyto_android.models.PostInsyteItem;
import alphadevs.insyto_android.utils.RealPathUtil;


public class CreateInsyteFragment extends Fragment {
    private static final Gson gson = InsytoGson.getInstance();

    private final static String S3_BUCKET_NAME = "insyto";

    private EditText title, description, content;
    private Button create_button;

    private InsytoVolley iVolley = InsytoVolley.getInstance();

    private View rootView;

    private static final int SELECT_AUDIO = 2;
    private static final int SELECT_VIDEO = 3;
    private String selectedPath = "";
    private InsyteMediaType selectedMediaType = InsyteMediaType.TEXT;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    public static CreateInsyteFragment newInstance() {
        CreateInsyteFragment fragment = new CreateInsyteFragment();
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
                createInsyte();
            }
        });

        // TODO change button style depending on content provided

        Button bText = (Button) rootView.findViewById(R.id.add_text_btn);
        bText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                content.setVisibility(View.VISIBLE);
                selectedMediaType = InsyteMediaType.TEXT;
            }
        });

        Button bVideo = (Button) rootView.findViewById(R.id.add_video_btn);
        bVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                content.setVisibility(View.INVISIBLE);
                selectedMediaType = InsyteMediaType.VIDEO;
                openGalleryVideo();
            }
        });

        Button bAudio = (Button) rootView.findViewById(R.id.add_audio_btn);
        bAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                content.setVisibility(View.INVISIBLE);
                selectedMediaType = InsyteMediaType.AUDIO;
                openGalleryAudio();
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

    public void openGalleryAudio(){

        Intent intent = new Intent();
        intent.setType("audio/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Audio "), SELECT_AUDIO);
    }

    public void openGalleryVideo(){

        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Video "), SELECT_VIDEO);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == SELECT_VIDEO || requestCode == SELECT_AUDIO)
            {
                Uri selectedUri = data.getData();
                selectedPath = getPathFromUri(selectedUri);
                System.out.println("SELECT Path : " + selectedPath);
            }

        }
    }

    private String getPathFromUri(Uri uri) {
        if (Build.VERSION.SDK_INT < 11)
            return RealPathUtil.getRealPathFromURI_BelowAPI11(getContext(), uri);
        else if (Build.VERSION.SDK_INT < 19)
            return RealPathUtil.getRealPathFromURI_API11to18(getContext(), uri);
        else if (Build.VERSION.SDK_INT < 22)
            return RealPathUtil.getRealPathFromURI_API19(getContext(), uri);
        return RealPathUtil.getRealPathFromURI_API22(getContext(), uri);
    }

    private void createInsyte()
    {
        if (selectedMediaType == InsyteMediaType.TEXT)
        {
            PostInsyteItem postInsyte = createInsyteItemToPost(selectedMediaType, "");
            postInsyte(postInsyte);
        } else {

            // Request a string response
            StringRequest stringRequest = new StringRequest(Request.Method.GET,
                    InsytoUrlBuilder.getAmazonApiKeysUrl(),
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            BasicAWSCredentials awsCred = gson.fromJson(response, BasicAWSCredentials.class);
                            uploadMedia(awsCred);
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
    }

    private PostInsyteItem createInsyteItemToPost(InsyteMediaType mediaType, String contentUrl)
    {
        InsyteItemData insyteData = new InsyteItemData();
        insyteData.setTitle(title.getText().toString());
        insyteData.setDescription(description.getText().toString());

        InsyteMedia insyteMedia;

        if(mediaType == InsyteMediaType.TEXT) {
            insyteMedia = new InsyteMediaText().withContent(content.getText().toString());
        } else if (mediaType == InsyteMediaType.VIDEO){
            insyteMedia = new InsyteMediaVideo().withUrl(contentUrl);
        } else { // It is Audio
            insyteMedia = new InsyteMediaAudio().withUrl(contentUrl);
        }

        insyteData.setMedia_type(mediaType.toString());


        insyteData.setCategory_id(1);// TODO category


        insyteData.setMedia_attributes(insyteMedia);

        PostInsyteItem postInsyte = new PostInsyteItem(insyteData);

        return postInsyte;
    }

    private void postInsyte(final PostInsyteItem postInsyte)
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

    private void uploadMedia(AWSCredentials awsCred)
    {

        final AmazonS3Client s3 = new AmazonS3Client(awsCred);

        // Set the region of your S3 bucket
        s3.setRegion(Region.getRegion(Regions.US_WEST_2));


        TransferUtility transferUtility = new TransferUtility(s3, getActivity().getApplicationContext());

        File file = new File(selectedPath);
        final String s3ObjectName = UUID.randomUUID().toString();



        TransferObserver observer = transferUtility.upload(
                S3_BUCKET_NAME,     /* The bucket to upload to */
                s3ObjectName,    /* The key for the uploaded object */
                file        /* The file where the data to upload exists */
        );

        String objectUrl = s3.getResourceUrl(S3_BUCKET_NAME, s3ObjectName);

        final PostInsyteItem postInsyte = createInsyteItemToPost(selectedMediaType, objectUrl);

        observer.setTransferListener(new TransferListener(){

            @Override
            public void onStateChanged(int id, TransferState state) {
                if( state == TransferState.COMPLETED) {

                    // Set permissions in AsyncTask
                    AsyncTask<Void, Void, Void> t = new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... v) {
                            AccessControlList accessControlList = s3.getBucketAcl(S3_BUCKET_NAME);
                            accessControlList.grantPermission(GroupGrantee.AllUsers, Permission.Read);
                            s3.setObjectAcl(S3_BUCKET_NAME, s3ObjectName, accessControlList);
                            return null;
                        }
                    }.execute();

                    postInsyte(postInsyte);
                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                int percentage = (int) (bytesCurrent/bytesTotal * 100);
                //Display percentage transfered to user
            }

            @Override
            public void onError(int id, Exception ex) {
                ex.printStackTrace();
                toastAnError();
            }

        });
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
