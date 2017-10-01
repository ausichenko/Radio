package com.ausichenko.radio.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.ausichenko.radio.R;
import com.ausichenko.radio.model.callback.OnClickRadioListener;
import com.ausichenko.radio.model.adapter.RadioAdapter;
import com.ausichenko.radio.model.pojo.Radio;
import com.ausichenko.radio.presentation.view.RadioListView;
import com.ausichenko.radio.presentation.presenter.RadioListPresenter;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.ausichenko.radio.ui.activity.MainActivity;

import java.util.List;

public class RadioListFragment extends MvpAppCompatFragment implements RadioListView {

    @InjectPresenter
    RadioListPresenter mRadioListPresenter;

    private RecyclerView mRadioList;
    private RadioAdapter mRadioAdapter;

    private LinearLayout mProgressLayout;
    private LinearLayout mEmptyLayout;
    private LinearLayout mErrorLayout;
    private TextView mErrorTextView;

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

        initRecyclerView(view);
        initViews(view);

        return view;
    }

    private void initRecyclerView(View fragmentView) {
        mRadioList = fragmentView.findViewById(R.id.radio_list);
        mRadioAdapter = new RadioAdapter(getActivity(), initOnClickListener());
        mRadioList.setHasFixedSize(true);
        mRadioList.setLayoutManager(new LinearLayoutManager(getContext()));
        mRadioList.setAdapter(mRadioAdapter);
    }

    private OnClickRadioListener initOnClickListener() {
        return new OnClickRadioListener() {
            @Override
            public void onClick(Radio radio) {
                ((MainActivity) getActivity()).showPlayerFragment(radio);
            }
        };
    }

    private void initViews(View fragmentView) {
        mProgressLayout = fragmentView.findViewById(R.id.progress_layout);
        mEmptyLayout = fragmentView.findViewById(R.id.empty_layout);
        mErrorLayout = fragmentView.findViewById(R.id.error_layout);
        mErrorTextView = fragmentView.findViewById(R.id.error_view);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRadioListPresenter.getRadioList();
    }

    @Override
    public void showProgress() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mRadioList.setVisibility(View.GONE);
                mProgressLayout.setVisibility(View.VISIBLE);
                mEmptyLayout.setVisibility(View.GONE);
                mErrorLayout.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void hideProgress() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProgressLayout.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void showData(List<Radio> data) {
        mRadioAdapter.setRadioList(data);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mRadioList.setVisibility(View.VISIBLE);
                mRadioAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void showError(final Throwable error) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mErrorLayout.setVisibility(View.VISIBLE);
                mErrorTextView.setText(error.getMessage());
            }
        });
    }

    @Override
    public void showEmptyList() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mEmptyLayout.setVisibility(View.VISIBLE);
            }
        });
    }
}
