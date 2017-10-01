package com.ausichenko.radio.ui.activity;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.ausichenko.radio.R;
import com.ausichenko.radio.model.pojo.Radio;
import com.ausichenko.radio.ui.fragment.PlayerFragment;
import com.ausichenko.radio.ui.fragment.RadioListFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initBackStackListener();
        showRadioListFragment();
    }

    private void initBackStackListener() {
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                boolean isEmpty = getSupportFragmentManager().getBackStackEntryCount() == 0;
                getSupportActionBar().setDisplayHomeAsUpEnabled(!isEmpty);
                if (isEmpty) {
                    getSupportActionBar().setTitle(R.string.app_name);
                }
            }
        });
    }

    private void showRadioListFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, RadioListFragment.newInstance())
                .commit();
    }

    public void showPlayerFragment(Radio radio) {
        getSupportActionBar().setTitle(radio.getName());
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, PlayerFragment.newInstance(radio))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
