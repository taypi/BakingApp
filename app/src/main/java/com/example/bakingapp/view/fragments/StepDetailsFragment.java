package com.example.bakingapp.view.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.bakingapp.R;
import com.example.bakingapp.model.Step;
import com.example.bakingapp.viewmodel.StepDetailsViewModel;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class StepDetailsFragment extends Fragment {

    private static final String ARG_STEP_NUMBER = "step_number";

    private StepDetailsViewModel mViewModel;
    private SimpleExoPlayer mPlayer;
    private PlayerView mPlayerView;
    private TextView mVideoNotAvailable;
    private TextView mShortDescription;
    private TextView mDescription;
    private int mIndex;

    public static StepDetailsFragment getInstance(int index) {
        StepDetailsFragment fragment = new StepDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_STEP_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(StepDetailsViewModel.class);
        if (getArguments() != null) {
            mIndex = getArguments().getInt(ARG_STEP_NUMBER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_details, container, false);

        mPlayerView = view.findViewById(R.id.player);
        mVideoNotAvailable = view.findViewById(R.id.tv_video_not_available);
        mShortDescription = view.findViewById(R.id.tv_short_description);
        mDescription = view.findViewById(R.id.tv_description);

        setUi(mViewModel.getStep(mIndex));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initializePlayer();
    }

    @Override
    public void onPause() {
        releasePlayer();
        super.onPause();
    }

    private void initializePlayer() {
        mPlayer = ExoPlayerFactory.newSimpleInstance(getContext());
        mPlayerView.setPlayer(mPlayer);

        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(),
                Util.getUserAgent(getContext(), getActivity().getString(R.string.app_name)));
        // This is the MediaSource representing the media to be played.
        MediaSource videoSource = new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(mViewModel.getStep(mIndex).getVideoURL()));
        mPlayer.prepare(videoSource);
        mPlayer.setPlayWhenReady(true);
    }

    private void releasePlayer() {
        mPlayer.stop(true);
        mPlayer.release();
        mPlayer = null;
    }

    private void setUi(Step step) {
        mPlayerView.setPlayer(mPlayer);
        if (mShortDescription != null && mDescription != null) {
            mShortDescription.setText(mViewModel.getShortDescription(mIndex));
            mDescription.setText(mViewModel.getDescription(mIndex));
        }

        if (step.getVideoURL().isEmpty()) {
            mPlayerView.setVisibility(GONE);
            mVideoNotAvailable.setVisibility(VISIBLE);
        } else {
            mPlayerView.setVisibility(VISIBLE);
            mVideoNotAvailable.setVisibility(GONE);
        }
    }
}
