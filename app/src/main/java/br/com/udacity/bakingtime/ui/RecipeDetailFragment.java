package br.com.udacity.bakingtime.ui;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import br.com.udacity.bakingtime.R;
import br.com.udacity.bakingtime.model.Step;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A fragment representing a single Recipe detail screen.
 * This fragment is contained in a {@link RecipeDetailActivity}
 * either in two-pane mode (on tablets, side by side with a list of steps)
 * or a on handsets (by itself).
 */
public class RecipeDetailFragment extends Fragment {

    /**
     * The Recipe this fragment is presenting.
     */
    private Step mStep;

    @BindView(R.id.step_description)
    TextView mStepDescriptionTextView;

    /**
     * ExoPlayer attributes
     */
    @BindView(R.id.playerView)
    SimpleExoPlayerView mPlayerView;

    private SimpleExoPlayer mExoPlayer;

    boolean mPlayWhenReady = true;
    int mCurrentWindow;
    long mPlaybackPosition;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RecipeDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();

        if (savedInstanceState != null) {
            mStep = savedInstanceState.getParcelable(getString(R.string.intent_recipe_step));
            mPlayWhenReady = savedInstanceState.getBoolean(getString(R.string.player_ready));
            mCurrentWindow = savedInstanceState.getInt(getString(R.string.player_window));
            mPlaybackPosition = savedInstanceState.getLong(getString(R.string.player_position));
        } else if (getArguments().containsKey(getString(R.string.intent_recipe_step))) {
            mStep = arguments.getParcelable(getString(R.string.intent_recipe_step));
        }

        if (mStep != null) {
            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mStep.getShortDesc());
            }
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_detail, container, false);
        ButterKnife.bind(this, rootView);

        if (mStep != null) {
            String longDesc, videoUrl, pictureUrl;
            longDesc = mStep.getLongDesc();
            videoUrl = mStep.getVideoUrl();
            pictureUrl = mStep.getPictureUrl();


            mStepDescriptionTextView.setText((longDesc != null && !longDesc.isEmpty())
                    ? mStep.getLongDesc() : "");


            if (videoUrl != null && !videoUrl.isEmpty()) {
                initializePlayer(Uri.parse(videoUrl));
                mPlayerView.setVisibility(View.VISIBLE);
            } else if (pictureUrl != null && !pictureUrl.isEmpty()) {
                initializePlayer(Uri.parse(pictureUrl));
                mPlayerView.setVisibility(View.VISIBLE);
            } else
                mPlayerView.setVisibility(View.GONE);
        }

        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    /**
     * Saving the recipe step to be able to restore the state of the fragment
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(getString(R.string.intent_recipe_step), mStep);
        outState.putBoolean(getString(R.string.player_ready), mPlayWhenReady);
        outState.putInt(getString(R.string.player_window), mCurrentWindow);
        outState.putLong(getString(R.string.player_position), mPlaybackPosition);
    }

    private void initializePlayer(Uri mediaUri) {

        mExoPlayer = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(getContext()),
                new DefaultTrackSelector(), new DefaultLoadControl());

        mPlayerView.setPlayer(mExoPlayer);

        mExoPlayer.setPlayWhenReady(mPlayWhenReady);
        mExoPlayer.seekTo(mCurrentWindow, mPlaybackPosition);

        MediaSource mediaSource = buildMediaSource(mediaUri);
        mExoPlayer.prepare(mediaSource, true, false);

        ComponentListener listener = new ComponentListener();
        mExoPlayer.addListener(listener);
    }

    private MediaSource buildMediaSource(Uri uri) {
        String userAgent = Util.getUserAgent(getContext(), getString(R.string.app_name));
        return new ExtractorMediaSource(uri, new DefaultHttpDataSourceFactory(userAgent),
                new DefaultExtractorsFactory(), null, null);
    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
            mPlaybackPosition = mExoPlayer.getCurrentPosition();
            mCurrentWindow = mExoPlayer.getCurrentWindowIndex();
            mPlayWhenReady = mExoPlayer.getPlayWhenReady();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    /**
     * Inner class used to capture the ExoPlayer events
     */
    private class ComponentListener implements ExoPlayer.EventListener {

        @Override
        public void onTimelineChanged(Timeline timeline, Object manifest) {

        }

        @Override
        public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

        }

        @Override
        public void onLoadingChanged(boolean isLoading) {

        }

        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

        }

        @Override
        public void onRepeatModeChanged(int repeatMode) {

        }

        @Override
        public void onPlayerError(ExoPlaybackException error) {

        }

        @Override
        public void onPositionDiscontinuity() {

        }

        @Override
        public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

        }
    }
}