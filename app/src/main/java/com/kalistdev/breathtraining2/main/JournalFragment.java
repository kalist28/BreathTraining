package com.kalistdev.breathtraining2.main;

import android.view.*;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.kalistdev.breathtraining2.R;
import android.annotation.SuppressLint;
import androidx.recyclerview.widget.RecyclerView;
import com.kalistdev.breathtraining2.Record.Journal;
import com.google.android.material.snackbar.Snackbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.kalistdev.breathtraining2.main.adapter.JournalRV;

/**
 * Breath TrainingRecord Application
 *
 * This file is part of the Breath TrainingRecord package.
 *
 * @author  Dmitriy Kalistratov <kalistratov.d.m@gmail.com>
 * @version 1.0
 */
public class JournalFragment extends Fragment {

    /** Recycler view adapter by Journal. */
    private static JournalRV adapter;

    /** Fragment view object. */
    @SuppressLint("StaticFieldLeak")
    private static View view;

    @Nullable
    @Override
    public final View onCreateView(
            @NonNull final LayoutInflater inflater,
            final ViewGroup container,
            final Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater
                .inflate(R.layout.fragment_journal, container, false);
    }

    @Override
    public final void onViewCreated(
            @NonNull final View v,
            final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        JournalFragment.view = v;
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(getContext());
        adapter = new JournalRV();

        RecyclerView recyclerView = view.findViewById(R.id.journal_rv);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public final void onCreateOptionsMenu(final Menu menu,
                                          final MenuInflater inflater) {
        inflater.inflate(R.menu.menu_journal, menu);
    }

    /** Clear journal in database and show snack bar for user. */
     static void clearJournal() {
        Journal.deleteAll(Journal.class);
        JournalFragment.adapter.dataChanged();
        Snackbar.make(view,
                "Журнал очищен",
                Snackbar.LENGTH_LONG)
                .show();
    }
}
