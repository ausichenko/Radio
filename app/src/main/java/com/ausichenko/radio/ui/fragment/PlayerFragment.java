package com.ausichenko.radio.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.ausichenko.radio.presentation.view.PlayerView;
import com.ausichenko.radio.presentation.presenter.PlayerPresenter;

import com.arellomobile.mvp.MvpFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

public class PlayerFragment extends MvpFragment implements PlayerView {
    public static final String TAG = "PlayerFragment";
	@InjectPresenter
	PlayerPresenter mPlayerPresenter;
    public static PlayerFragment newInstance() {
        PlayerFragment fragment = new PlayerFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }
    @Override
    public View onCreateView(final LayoutInflater inflater, final   ViewGroup container,
            final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_player, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
