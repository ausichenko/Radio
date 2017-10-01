package com.ausichenko.radio.model.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ausichenko.radio.R;
import com.ausichenko.radio.model.callback.OnClickRadioListener;
import com.ausichenko.radio.model.pojo.Radio;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RadioAdapter extends RecyclerView.Adapter<RadioAdapter.ViewHolder> {

    private List<Radio> mRadioList = new ArrayList<>();

    private Context mContext;
    private OnClickRadioListener mOnClickListener;

    public RadioAdapter(Context context, OnClickRadioListener onClickListener) {
        mContext = context;
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
        holder.bind(mContext, radio, mOnClickListener);
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

        public void bind(Context context, final Radio radio, final OnClickRadioListener listener) {
            Picasso.with(context).load(radio.getImage().getUrl()).into(preview);
            textView.setText(radio.getName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onClick(radio);
                }
            });
        }
    }
}
