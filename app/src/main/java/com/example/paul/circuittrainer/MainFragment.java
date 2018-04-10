package com.example.paul.circuittrainer;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment implements RoundStartFragment.OnFragmentInteractionListener, View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String num_round_str = "numrd";
    private static final String round_min_str = "rdmin";
    private static final String round_sec_str = "rdsec";
    private static final String rest_min_str = "restmin";
    private static final String rest_sec_str = "restsec";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public static Bundle args = new Bundle();

    EditText etNumRounds;
    EditText etStartMins;
    EditText etStartSecs;
    EditText etRestMins;
    EditText etRestSecs;

    Button startButton;
    Integer numRounds;
    Integer roundMins;
    Integer roundSecs;
    Integer restMins;
    Integer restSecs;

    private OnFragmentInteractionListener mListener;

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RoundStartFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.main_fragment, container, false);
        etNumRounds = v.findViewById(R.id.num_rounds);
        etStartMins = v.findViewById(R.id.round_time_mins);
        etStartSecs = v.findViewById(R.id.round_time_secs);
        etRestMins = v.findViewById(R.id.rest_time_mins);
        etRestSecs = v.findViewById(R.id.rest_time_secs);

        startButton = v.findViewById(R.id.start_button);


        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etNumRounds.getText().length() > 0) {
                    numRounds = Integer.parseInt(etNumRounds.getText().toString());
                }
                else{
                    numRounds = 5;
                }
                if (etStartMins.getText().length() > 0) {
                    roundMins = Integer.parseInt(etStartMins.getText().toString());
                }
                else{
                    roundMins = 0;
                }
                if (etStartSecs.getText().length() > 0) {
                    roundSecs = Integer.parseInt(etStartSecs.getText().toString());
                }
                else{
                    roundSecs = 5;
                }
                if (etRestMins.getText().length() > 0) {
                    restMins = Integer.parseInt(etRestMins.getText().toString());
                }
                else{
                    restMins = 0;
                }
                if (etRestSecs.getText().length() > 0) {
                    restSecs = Integer.parseInt(etRestSecs.getText().toString());
                }
                else{
                    restSecs = 5;
                }

                args.putInt(num_round_str, numRounds);
                args.putInt(round_min_str, roundMins);
                args.putInt(round_sec_str, roundSecs);
                args.putInt(rest_min_str, restMins);
                args.putInt(rest_sec_str, restSecs);

                RoundStartFragment rsFragment = new RoundStartFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.main_activity_activity, rsFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return v;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        //look at same unfction in Main
        return;
    }

    @Override
    public void onClick(View view) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);

    }
}
