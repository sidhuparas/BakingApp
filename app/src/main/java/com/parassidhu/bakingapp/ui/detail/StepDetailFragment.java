package com.parassidhu.bakingapp.ui.detail;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.parassidhu.bakingapp.R;
import com.parassidhu.bakingapp.model.Steps;
import com.parassidhu.bakingapp.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.prototypez.savestate.core.annotation.AutoRestore;

public class StepDetailFragment extends Fragment {

    @BindView(R.id.playerView) PlayerView mPlayerView;
    @BindView(R.id.next) TextView nextBtn;
    @BindView(R.id.previous) TextView previousBtn;

    public StepDetailFragment() { }

    private ArrayList<Steps> listItems = new ArrayList<>();
    private SimpleExoPlayer player;

    long playbackPosition;
    int currentWindow;

    @AutoRestore
    int position;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_detail, container, false);
        ButterKnife.bind(this, view);
        readBundle(getArguments());
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void startVideo() {
        ifToShowNextAndPrevious();
        initializePlayer(Uri.parse(listItems.get(position).getVideoURL()));
    }

    private void initializePlayer(Uri uri) {
        player = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(requireContext()),
                new DefaultTrackSelector(),
                new DefaultLoadControl()
        );

        mPlayerView.setPlayer(player);

        MediaSource source = new ExtractorMediaSource
                .Factory(new DefaultHttpDataSourceFactory(requireContext().getPackageName()))
                .createMediaSource(uri);

        player.prepare(source, true, false);

        player.seekTo(currentWindow, playbackPosition);
        player.setPlayWhenReady(true);
    }

    @OnClick(R.id.next)
    void onNextClick() {
        position++;
        startVideo();
        reset();
    }

    @OnClick(R.id.previous)
    void onPreviousClick() {
        position--;
        startVideo();
        reset();
    }

    private void ifToShowNextAndPrevious() {
        int size = listItems.size();

        if (position == size - 1) {
            nextBtn.setVisibility(View.GONE);
        } else {
            nextBtn.setVisibility(View.VISIBLE);
        }

        if (position == 0) {
            previousBtn.setVisibility(View.GONE);
        } else {
            previousBtn.setVisibility(View.VISIBLE);
        }
    }

    private void releasePlayer() {
        if (player != null) {
            currentWindow = player.getCurrentWindowIndex();
            playbackPosition = player.getCurrentPosition();

            player.release();
            player = null;

            if (getArguments() != null) {
                getArguments().putLong(Constants.PLAYER_POSITION, playbackPosition);
                getArguments().putLong(Constants.PLAYER_WINDOW, currentWindow);
            }
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    @Override
    public void onResume() {
        super.onResume();
        startVideo();
    }

    private void reset(){
        currentWindow = 0;
        playbackPosition = 0;
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            listItems = bundle.getParcelableArrayList(Constants.STEPS);
            position = bundle.getInt(Constants.POSITION, 0);

            playbackPosition = getArguments().getLong(Constants.PLAYER_POSITION);
            currentWindow = getArguments().getInt(Constants.PLAYER_WINDOW);
        }
    }

    public static StepDetailFragment newInstance(ArrayList<Steps> listItems, int position) {
        StepDetailFragment fragment = new StepDetailFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(Constants.STEPS, listItems);
        args.putInt(Constants.POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }
}
