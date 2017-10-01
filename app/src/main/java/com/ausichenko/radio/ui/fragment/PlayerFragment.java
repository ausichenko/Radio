package com.ausichenko.radio.ui.fragment;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.ausichenko.radio.R;
import com.ausichenko.radio.model.pojo.Radio;
import com.ausichenko.radio.presentation.presenter.PlayerPresenter;
import com.ausichenko.radio.presentation.view.PlayerView;

public class PlayerFragment extends MvpAppCompatFragment implements PlayerView {

    private static final String RADIO_KEY = "radio_key";
    public static final String TAG = "PlayerFragment";
    @InjectPresenter
    PlayerPresenter mPlayerPresenter;

    private ImageView mPlayButton;

    private Radio mRadio;

    public static PlayerFragment newInstance(Radio radio) {
        PlayerFragment fragment = new PlayerFragment();

        Bundle args = new Bundle();
        args.putParcelable(RADIO_KEY, radio);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        mRadio = getArguments().getParcelable(RADIO_KEY);
        View view = inflater.inflate(R.layout.fragment_player, container, false);

        mPlayButton = view.findViewById(R.id.playButton);
        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPlayerPresenter.playStop(getContext(), mRadio.getStreams().get(0).getStream());
            }
        });

        initControls(view);

        return view;
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPlayerPresenter.playStop(getContext(), mRadio.getStreams().get(0).getStream());
    }

    private void initControls(View fragmentView) {
        try {
            SeekBar volumeBar = fragmentView.findViewById(R.id.volumeBar);
            final AudioManager audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
            volumeBar.setMax(audioManager
                    .getStreamMaxVolume(AudioManager.STREAM_MUSIC));
            volumeBar.setProgress(audioManager
                    .getStreamVolume(AudioManager.STREAM_MUSIC));


            volumeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onStopTrackingTouch(SeekBar arg0) {
                }

                @Override
                public void onStartTrackingTouch(SeekBar arg0) {
                }

                @Override
                public void onProgressChanged(SeekBar arg0, int progress, boolean arg2) {
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                            progress, 0);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void play() {
        mPlayButton.setImageResource(R.drawable.ic_pause);
    }

    @Override
    public void stop() {
        mPlayButton.setImageResource(R.drawable.ic_play);
    }
}
