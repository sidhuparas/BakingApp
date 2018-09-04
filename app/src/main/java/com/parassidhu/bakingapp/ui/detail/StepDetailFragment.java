package com.parassidhu.bakingapp.ui.detail;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.parassidhu.bakingapp.R;
import com.parassidhu.bakingapp.model.Steps;
import com.parassidhu.bakingapp.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.prototypez.savestate.core.annotation.AutoRestore;

public class StepDetailFragment extends Fragment implements LifecycleObserver {

    @BindView(R.id.playerView) PlayerView mPlayerView;
    @BindView(R.id.next) TextView nextBtn;
    @BindView(R.id.previous) TextView previousBtn;
    @BindView(R.id.title) TextView titleTv;
    @BindView(R.id.description) TextView descriptionTv;
    @BindView(R.id.thumbnail) ImageView thumbnailIv;

    public StepDetailFragment() { }

    private ArrayList<Steps> listItems = new ArrayList<>();
    private SimpleExoPlayer player;

    private long playbackPosition;
    private int currentWindow;
    private boolean playWhenReady;

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

    void startVideoAndSetDescription() {
        ifToShowNextAndPrevious();
        Steps step = listItems.get(position);

        Uri videoUri = Uri.parse(step.getVideoURL());

        if (!videoUri.toString().isEmpty()) {
            initializePlayer(Uri.parse(step.getVideoURL()));
        } else {
            handleErrorCase();
        }

        titleTv.setText(step.getShortDescription());
        descriptionTv.setText(step.getDescription());
    }

    private void initializePlayer(Uri uri) {
        mPlayerView.setVisibility(View.VISIBLE);
        thumbnailIv.setVisibility(View.INVISIBLE);

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
        player.setPlayWhenReady(playWhenReady);
    }

    private void handleErrorCase() {
        mPlayerView.setVisibility(View.INVISIBLE);
        thumbnailIv.setVisibility(View.VISIBLE);

        String thumbnailURL = listItems.get(position).getThumbnailURL();

        if (thumbnailURL.isEmpty()){
            Picasso.get().load(R.drawable.img_loading_error).into(thumbnailIv);
        } else {
            Picasso.get()
                    .load(thumbnailURL)
                    .placeholder(R.drawable.img_loading_cover)
                    .error(R.drawable.img_loading_error)
                    .into(thumbnailIv);
        }
    }

    private void softReleasePlayer() {
        if (player!=null) {
            player.release();
            player = null;
        }
    }

    @OnClick(R.id.next)
    void onNextClick() {
        position++;
        softReleasePlayer();
        startVideoAndSetDescription();
        reset();
    }

    @OnClick(R.id.previous)
    void onPreviousClick() {
        position--;
        softReleasePlayer();
        startVideoAndSetDescription();
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

    void releasePlayer() {
        if (player != null) {
            currentWindow = player.getCurrentWindowIndex();
            playbackPosition = player.getCurrentPosition();
            playWhenReady = player.getPlayWhenReady();

            player.release();
            player = null;

            if (getArguments() != null) {
                getArguments().putLong(Constants.PLAYER_POSITION, playbackPosition);
                getArguments().putInt(Constants.PLAYER_WINDOW, currentWindow);
                getArguments().putBoolean(Constants.PLAYER_PLAY_WHEN_READY, playWhenReady);
            }
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }

    @Override
    public void onResume() {
        super.onResume();
        startVideoAndSetDescription();
    }

    private void reset() {
        currentWindow = 0;
        playbackPosition = 0;
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            listItems = bundle.getParcelableArrayList(Constants.STEPS);
            position = bundle.getInt(Constants.POSITION, 0);

            playbackPosition = getArguments().getLong(Constants.PLAYER_POSITION);
            currentWindow = getArguments().getInt(Constants.PLAYER_WINDOW);
            playWhenReady = getArguments().getBoolean(Constants.PLAYER_PLAY_WHEN_READY);
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
