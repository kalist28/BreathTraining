package com.kalistdev.breathtraining.main;

import android.view.View;
import android.os.Bundle;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import androidx.fragment.app.Fragment;
import com.kalistdev.breathtraining.R;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.kalistdev.breathtraining.main.dialog.EditorDialog;
import com.kalistdev.breathtraining.main.adapter.ListTrainingRV;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


/**
 * Breath TrainingRecord Application
 *
 * This file is part of the Breath TrainingRecord package.
 *
 * @author  Dmitriy Kalistratov <kalistratov.d.m@gmail.com>
 * @version 1.0
 */
public class ListTrainingFragment extends Fragment {

    /** RecyclerView adapter for training. */
    private static ListTrainingRV rvAdapter;

    @Override
    public final View onCreateView(final LayoutInflater inflater,
                             final ViewGroup container,
                             final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }


    @Override
    public final void onViewCreated(@NonNull final View view,
                                    final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.rv);

        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(getContext());

        rvAdapter = new ListTrainingRV();

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(rvAdapter);
        updateList();

        FloatingActionButton floatingActionButton =
                view.findViewById(R.id.FloatingActionButton);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                EditorDialog createNewTrainingDialog =
                        new EditorDialog(EditorDialog.CREATE);
                assert getFragmentManager() != null;
                createNewTrainingDialog.show(getFragmentManager(), null);
            }
        });
    }

    /** Updating the list of training. */
    public static void updateList() {
        rvAdapter.dataChanged();
    }
}
