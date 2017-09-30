package com.ausichenko.radio.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.ausichenko.radio.R;
import com.ausichenko.radio.model.RadioAdapter;
import com.ausichenko.radio.model.pojo.Radio;
import com.ausichenko.radio.presentation.view.RadioListView;
import com.ausichenko.radio.presentation.presenter.RadioListPresenter;

import com.arellomobile.mvp.presenter.InjectPresenter;

import java.util.List;

public class RadioListFragment extends MvpAppCompatFragment implements RadioListView {
    public static final String TAG = "RadioListFragment";
    @InjectPresenter
    RadioListPresenter mRadioListPresenter;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private RadioAdapter mRadioAdapter;

    public static RadioListFragment newInstance() {
        RadioListFragment fragment = new RadioListFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_radio_list, container, false);

        mSwipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRadioListPresenter.getRadioList();
            }
        });

        initRecyclerView(view);

        mRadioListPresenter.getRadioList();

        return view;
    }

    private void initRecyclerView(View fragmentView) {
        mRecyclerView = fragmentView.findViewById(R.id.radio_list);
        mRadioAdapter = new RadioAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mRadioAdapter);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void showProgress() {
        Log.d(TAG, "showProgress: ");
    }

    @Override
    public void hideProgress() {
        Log.d(TAG, "hideProgress: ");
    }

    @Override
    public void showData(List<Radio> data) {
        Log.d(TAG, "showData: data = " + data.size() + " " + data.toString());
    }

    @Override
    public void showError(Throwable error) {
        Log.d(TAG, "showError: error = " + error.getMessage());
    }

    @Override
    public void showEmptyList() {
        Log.d(TAG, "showEmptyList: ");
    }
}
