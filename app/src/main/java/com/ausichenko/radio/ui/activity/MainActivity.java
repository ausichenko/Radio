package com.ausichenko.radio.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ausichenko.radio.R;
import com.ausichenko.radio.ui.fragment.RadioListFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showRadioListFragment();
    }

    private void showRadioListFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, RadioListFragment.newInstance())
                .commit();
    }
}
