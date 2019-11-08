package com.example.bakingapp.view.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.bakingapp.R;
import com.example.bakingapp.model.Step;
import com.example.bakingapp.viewmodel.StepDetailsViewModel;
import com.google.android.exoplayer2.C;
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

    private static final String KEY_STEP_NUMBER = "step_number";
    private static final String KEY_PLAYBACK_POSITION = "playback_position";
    private static final String KEY_PLAY_WHEN_READY = "play_when_ready";

    private StepDetailsViewModel mViewModel;
    private SimpleExoPlayer mPlayer;
    private PlayerView mPlayerView;
    private TextView mVideoNotAvailable;
    private TextView mDescription;
    private long mPlaybackPosition;
    private boolean mPlayWhenReady;
    private int mStepNumber;

    public static StepDetailsFragment getInstance(int index) {
        StepDetailsFragment fragment = new StepDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_STEP_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(StepDetailsViewModel.class);
        if (getArguments() != null) {
            mStepNumber = getArguments().getInt(KEY_STEP_NUMBER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_details, container, false);

        mPlayerView = view.findViewById(R.id.player);
        mVideoNotAvailable = view.findViewById(R.id.tv_video_not_available);
        mDescription = view.findViewById(R.id.tv_description);

        setInitialValues(savedInstanceState);
        setUi(mViewModel.getStep(mStepNumber));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initializePlayer();
    }

    @Override
    public void onPause() {
        saveCurrentState();
        releasePlayer();
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(KEY_PLAYBACK_POSITION, mPlaybackPosition);
        outState.putBoolean(KEY_PLAY_WHEN_READY, mPlayWhenReady);
    }

    private void setInitialValues(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mPlaybackPosition = savedInstanceState.getLong(KEY_PLAYBACK_POSITION);
            mPlayWhenReady = savedInstanceState.getBoolean(KEY_PLAY_WHEN_READY);
        } else {
            mPlaybackPosition = C.TIME_UNSET;
            mPlayWhenReady = true;
        }
    }

    private void initializePlayer() {
        if (mViewModel.getStep(mStepNumber).getVideoURL().isEmpty()) {
            return;
        }
        if (mPlayer == null) {
            mPlayer = ExoPlayerFactory.newSimpleInstance(getContext());
            mPlayerView.setPlayer(mPlayer);
        }
        mPlayer.setPlayWhenReady(mPlayWhenReady);

        boolean hasPlaybackPosition = mPlaybackPosition != C.INDEX_UNSET;
        if (hasPlaybackPosition) {
            mPlayer.seekTo(mPlaybackPosition);
        }

        mPlayer.prepare(getVideoSource(), !hasPlaybackPosition, false);
    }

    private MediaSource getVideoSource() {
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(),
                Util.getUserAgent(getContext(), getActivity().getString(R.string.app_name)));
        return new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(mViewModel.getStep(mStepNumber).getVideoURL()));
    }

    private void saveCurrentState() {
        if (mPlayer != null) {
            mPlaybackPosition = mPlayer.getCurrentPosition();
            mPlayWhenReady = mPlayer.getPlayWhenReady();
        }
    }

    private void releasePlayer() {
        if (mPlayer != null) {
            mPlayer.stop(true);
            mPlayer.release();
            mPlayer = null;
        }
    }

    private void setUi(Step step) {
        if (mDescription != null) {
            mDescription.setText(mViewModel.getDescription(mStepNumber));
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
