package com.example.paul.circuittrainer;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RoundStartFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RoundStartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RoundStartFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters

    TextView tvRounds;
    TextView tvTotalRounds;
    TextView tvStartMin;
    TextView tvStartSec;

    Integer totalRounds = MainFragment.args.getInt("numrd");
    Integer numRound = totalRounds;

    Integer startMins = MainFragment.args.getInt("rdmin");
    Integer startSecs = MainFragment.args.getInt("rdsec");
    Integer restMins = MainFragment.args.getInt("restmin");
    Integer restSecs = MainFragment.args.getInt("restsec");

    Button pauseButton;
    Button stopButton;
    private OnFragmentInteractionListener mListener;

    public RoundStartFragment() {
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
    public static RoundStartFragment newInstance(String param1, String param2) {
        RoundStartFragment fragment = new RoundStartFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            return;

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        mParam3 = getArguments().getString("numrd");
        final View v = inflater.inflate(R.layout.fragment_round_start, container, false);

        stopButton = v.findViewById(R.id.stop_button);
        pauseButton = v.findViewById(R.id.pause_resume_button);

        tvRounds = v.findViewById(R.id.curr_round);
        tvTotalRounds = v.findViewById(R.id.total_rounds);
        tvStartMin = v.findViewById(R.id.formatted_time);


        tvRounds.setText(numRound.toString());
        tvTotalRounds.setText("/" + totalRounds.toString() + " ");
        tvStartMin.setText(Integer.toString(startMins) + ":" + Integer.toString(startSecs)+ " ");

        //this should decrement as timer.
        Integer timeInSecs;
        if (startMins == 0 && numRound == 0){
            timeInSecs = startSecs + 2 + (restMins * 60) + restSecs;
        }
        else if (startMins == 0){
            timeInSecs = numRound * startSecs + (2 * numRound) + (restMins * 60) + restSecs;
        }
        else{
            timeInSecs = numRound * startMins * 60 + (startSecs) + (2 * numRound) + (restMins * 60) + restSecs;
        }

        final Integer startSecsCopy = startSecs;
        final Integer startMinsCopy = startMins;
        final Integer restSecsCopy = restSecs;
        final Integer restMinsCopy = restMins;
        final boolean[] skipRest = {true};
        final boolean[] isPaused = {false};
        final CountDownTimer CDT = new CountDownTimer(2000+timeInSecs*1000,1000){

            @Override
            public void onTick(long l) {
                //use this.cancel to pause
                //update text, change to start
                //end of timer
                if (startSecs == 0 && startMins == 0 && numRound <= 1){
                    this.cancel();
                }

                if (restMins >= 0 && restSecs >= 0 && !skipRest[0]){
                    tvStartMin.setText("Rest Time: " + String.format("%02d",restMins) + ":" + String.format("%02d",restSecs) + " ");
                    restSecs--;
                }

                else if (startMins >= 0 || startSecs >= 0 && restMins == -1 && restSecs == -1){
                    tvStartMin.setText(String.format("%02d",startMins) + ":" + String.format("%02d",startSecs)+ " ");
                    startSecs--;
                }

                //Handle sounds here
                if (startMins == 0 && startSecs == 10){
                    MediaPlayer mp = MediaPlayer.create(v.getContext(), R.raw.tensec);
                    mp.start();
                }

                if (startMins > 0 && startSecs == -1){
                    startMins--;
                    startSecs = 59;
                }
                if (restMins > 0 && restSecs == -1){
                    restMins--;
                    restSecs = 59;
                }

                //End of Round logic here
                if (startSecs == -1 && startMins == 0 && numRound > 1) {
                    skipRest[0] = false;
                    restMins = restMinsCopy;
                    restSecs = restSecsCopy;
                    numRound--;
                    tvRounds.setText(Integer.toString(numRound));
                    startSecs = startSecsCopy;
                    startMins = startMinsCopy;

                }


            }

            @Override
            public void onFinish() {
//                tvStartMin.setText("Done");
                tvStartMin.setText("Done");


            }
        };
        CDT.start();
        //PAUSE RESUME
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPaused[0]){
                    pauseButton.setText("Pause");
                    CDT.start();
                    isPaused[0] = false;
                }
                else {
                    CDT.cancel();
                    pauseButton.setText("Resume");
                    isPaused[0] = true;
                }
            }
        });
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CDT.cancel();
                pauseButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //disable button
                        return;
                    }
                });
            }
        });

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
