package com.ogettoweb.dealers.fragments;

import android.support.v4.app.Fragment;

import com.ogettoweb.dealers.activities.MainActivity;
import com.ogettoweb.dealers.databinding.ActivityMainBinding;

public class BaseFragment extends Fragment {
    protected String title;
    protected String back_title;

    @Override
    public void onStart() {
        super.onStart();
        final ActivityMainBinding binding = ((MainActivity) getActivity()).getBinding();
        binding.backText.setText(back_title);
        binding.title.setText(title);
    }
}
