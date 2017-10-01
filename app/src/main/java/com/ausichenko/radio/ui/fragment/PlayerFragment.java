package com.ausichenko.radio.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
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
import com.squareup.picasso.Picasso;

public class PlayerFragment extends MvpAppCompatFragment implements PlayerView {

    private static final String RADIO_KEY = "radio_key";

    @InjectPresenter
    PlayerPresenter mPlayerPresenter;

    private Radio mRadio;

    private ImageView mPlayButton;
    private SeekBar mVolumeBar;

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

        initPreview(view);
        initShareButton(view);
        initPlayButton(view);
        initInfoButton(view);
        initControls(view);

        return view;
    }

    private void initPreview(View fragmentView) {
        ImageView preview = fragmentView.findViewById(R.id.preview);
        Picasso.with(getContext())
                .load(mRadio.getImage().getUrl())
                //.placeholder(R.drawable.ic_placeholder)
                .into(preview);
    }

    private void initShareButton(View fragmentView) {
        ImageView shareButton = fragmentView.findViewById(R.id.shareButton);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Dirble radio\n" + mRadio.getName() + "\n" + mRadio.getWebsite());
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });
    }

    private void initPlayButton(View fragmentView) {
        mPlayButton = fragmentView.findViewById(R.id.playButton);
        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPlayerPresenter.playStop(getContext(), mRadio.getStreams());
            }
        });
    }

    private void initInfoButton(View fragmentView) {
        ImageView infoButton = fragmentView.findViewById(R.id.infoButton);
        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog dialog = new AlertDialog.Builder(getContext())
                        .setTitle(mRadio.getName())
                        .setMessage(mRadio.toString())
                        .create();

                dialog.show();
            }
        });
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPlayerPresenter.initAudioManager(getContext());
        mPlayerPresenter.playStop(getContext(), mRadio.getStreams());
    }

    private void initControls(View fragmentView) {
        mVolumeBar = fragmentView.findViewById(R.id.volumeBar);

        mVolumeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar arg0) {}

            @Override
            public void onStartTrackingTouch(SeekBar arg0) {}

            @Override
            public void onProgressChanged(SeekBar arg0, int progress, boolean arg2) {
                mPlayerPresenter.setVolume(progress);
            }
        });
    }

    @Override
    public void onDestroyView() {
        mPlayerPresenter.stop(getContext());
        super.onDestroyView();
    }

    @Override
    public void play() {
        mPlayButton.setImageResource(R.drawable.ic_pause);
    }

    @Override
    public void stop() {
        mPlayButton.setImageResource(R.drawable.ic_play);
    }

    @Override
    public void setMaxVolume(int maxVolume) {
        mVolumeBar.setMax(maxVolume);
    }

    @Override
    public void volumeChanged(int volume) {
        mVolumeBar.setProgress(volume);
    }
}
