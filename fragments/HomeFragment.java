package com.example.abyteofbraille.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.abyteofbraille.R;

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.letters_button_STL).setOnClickListener(v -> NavHostFragment.findNavController(HomeFragment.this)
                .navigate(R.id.home_to_letters_stl));

        view.findViewById(R.id.numbers_button_STL).setOnClickListener(v -> NavHostFragment.findNavController(HomeFragment.this)
                .navigate(R.id.home_to_numbers_stl));

        view.findViewById(R.id.mixed_button_STL).setOnClickListener(v -> NavHostFragment.findNavController(HomeFragment.this)
                .navigate(R.id.home_to_mixed_stl));

        view.findViewById(R.id.letters_button_LTS).setOnClickListener(v -> NavHostFragment.findNavController(HomeFragment.this)
                .navigate(R.id.home_to_letters_lts));

        view.findViewById(R.id.numbers_button_LTS).setOnClickListener(v -> NavHostFragment.findNavController(HomeFragment.this)
                .navigate(R.id.home_to_numbers_lts));

        view.findViewById(R.id.mixed_button_LTS).setOnClickListener(v -> NavHostFragment.findNavController(HomeFragment.this)
                .navigate(R.id.home_to_mixed_lts));

        view.findViewById(R.id.skills_button).setOnClickListener(v -> NavHostFragment.findNavController(HomeFragment.this)
                .navigate(R.id.home_to_skills));

        view.findViewById(R.id.patronImage).setOnClickListener(v -> openPatreon());

        view.findViewById(R.id.kofiImage).setOnClickListener(v -> openKofi());

        view.findViewById(R.id.telegramImage).setOnClickListener(v -> openTG());
    }

    public void openKofi()
    {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://ko-fi.com/abyte0flove"));
        startActivity(browserIntent);
    }

    public void openPatreon()
    {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.patreon.com/abyteoflove"));
        startActivity(browserIntent);
    }

    public void openTG()
    {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/AByte0fLove"));
        startActivity(browserIntent);
    }
}