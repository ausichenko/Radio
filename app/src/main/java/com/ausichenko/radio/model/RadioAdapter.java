package com.ausichenko.radio.model;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ausichenko.radio.R;
import com.ausichenko.radio.model.pojo.Radio;

import java.util.ArrayList;
import java.util.List;

public class RadioAdapter extends RecyclerView.Adapter<RadioAdapter.ViewHolder> {

    private List<Radio> mRadioList = new ArrayList<>();

    private OnClickRadioListener mOnClickListener;

    public RadioAdapter(OnClickRadioListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    public void setRadioList(List<Radio> radioList) {
        mRadioList = radioList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.radio_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Radio radio = mRadioList.get(position);
        holder.bind(radio, mOnClickListener);
    }

    @Override
    public int getItemCount() {
        return mRadioList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView preview;
        public TextView textView;

        public ViewHolder(View view) {
            super(view);
            preview = view.findViewById(R.id.preview);
            textView = view.findViewById(R.id.name);
        }

        public void bind(final Radio radio, final OnClickRadioListener listener) {
            textView.setText(radio.getName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onClick(radio);
                }
            });
        }
    }
}
