package com.ogettoweb.dealers.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ogettoweb.dealers.R;
import com.ogettoweb.dealers.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);

        SpannableString spanString = new SpannableString(binding.dealers.getText());
        spanString.setSpan(new UnderlineSpan(), 0, spanString.length() - 2, 0);
        binding.dealers.setText(spanString );

        return binding.getRoot();
    }
}
