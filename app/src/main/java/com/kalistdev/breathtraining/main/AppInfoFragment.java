package com.kalistdev.breathtraining.main;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.kalistdev.breathtraining.R;

/**
 * Breath TrainingRecord Application
 *
 * This file is part of the Breath TrainingRecord package.
 *
 * @author  Dmitriy Kalistratov <kalistratov.d.m@gmail.com>
 * @version 1.0
 */
public class AppInfoFragment extends Fragment {

    @Override
    public final View onCreateView(final LayoutInflater inflater,
                             final ViewGroup container,
                             final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_info,  container, false);
    }

    @Override
    public final void onViewCreated(@NonNull final View view,
                                    final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
