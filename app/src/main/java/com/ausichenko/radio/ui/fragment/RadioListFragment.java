package com.ausichenko.radio.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ausichenko.radio.R;
import com.ausichenko.radio.presentation.view.RadioListView;
import com.ausichenko.radio.presentation.presenter.RadioListPresenter;

import com.arellomobile.mvp.MvpFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

public class RadioListFragment extends MvpFragment implements RadioListView {
    public static final String TAG = "RadioListFragment";
    @InjectPresenter
    RadioListPresenter mRadioListPresenter;

    public static RadioListFragment newInstance() {
        RadioListFragment fragment = new RadioListFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_radio_list, container, false);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
