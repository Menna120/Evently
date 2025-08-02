package com.example.evently;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class OnboardingThreeFragment extends Fragment {

    public OnboardingThreeFragment() {
        // Required empty public constructor
    }

    public static OnboardingThreeFragment newInstance(String param1, String param2) {
        OnboardingThreeFragment fragment = new OnboardingThreeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_onboarding_three, container, false);
    }
}