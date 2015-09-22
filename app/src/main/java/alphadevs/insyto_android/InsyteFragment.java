package alphadevs.insyto_android;

import android.app.Activity;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


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
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InsyteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InsyteFragment newInstance(String param1, String param2) {
        InsyteFragment fragment = new InsyteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
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
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_insyte, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.card_text);

        textView.setText("I LOVE ROCK n ROLL, BABY!");
        textView.append(TEST_TEXT);
        textView.append(TEST_TEXT);
        textView.append(TEST_TEXT);
        textView.setMovementMethod(new ScrollingMovementMethod());

        TextView newTextView = new TextView(getActivity());
        newTextView.setText("Macaca is Watching you!");
        newTextView.setTextColor(Color.RED);

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
