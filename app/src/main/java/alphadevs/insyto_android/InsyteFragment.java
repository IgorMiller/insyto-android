package alphadevs.insyto_android;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
    static final String ARG_INSYTE_ID= "id";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mInsyteId;
    private String mParam2;

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
        View rootView = inflater.inflate(R.layout.fragment_insyte, container, false);

        TextView cardTitle = (TextView) rootView.findViewById(R.id.card_title);
        cardTitle.setText("I LOVE ROCK n ROLL, BABY!!!" + mInsyteId);

        TextView textView = (TextView) rootView.findViewById(R.id.card_text);
        textView.append(TEST_TEXT);
        textView.append(TEST_TEXT);
        textView.append(TEST_TEXT);

        TextView newTextView = new TextView(getActivity());
        newTextView.setText("Macaca is Watching you!");
        newTextView.setTextColor(Color.RED);


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
}
