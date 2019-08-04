package com.kalistdev.breathtraining.main.adapter;

import java.util.Date;
import java.util.List;
import android.view.View;
import com.google.gson.Gson;
import android.view.ViewGroup;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import android.view.LayoutInflater;
import androidx.annotation.NonNull;
import com.kalistdev.breathtraining.R;
import android.annotation.SuppressLint;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.kalistdev.breathtraining.Record.Journal;
import com.kalistdev.breathtraining.main.dialog.JournalDialog;
import com.kalistdev.breathtraining.widget.StarsManager;
import com.kalistdev.breathtraining.training.ParametersManager;
import com.kalistdev.breathtraining.widget.Stopwatch;

/**
 * Breath TrainingRecord Application
 *
 * This file is part of the Breath TrainingRecord package.
 *
 * @author  Dmitriy Kalistratov <kalistratov.d.m@gmail.com>
 * @version 1.0
 */
public class JournalRV extends RecyclerView.Adapter<JournalRV.ViewHolder> {

    /** A list of journal records. */
    private List<Journal> mJournal;

    /**
     * Constructor - initialize full object. */
    public JournalRV() {
        this.mJournal = Journal.listAll(Journal.class);
    }

    @NonNull
    @Override
    public final ViewHolder onCreateViewHolder(final @NonNull ViewGroup parent,
                                         final int viewType) {
        View v
            = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_element_journal, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public final void onBindViewHolder(final @NonNull ViewHolder h,
                                 final int i) {
        final Journal journal = mJournal.get(i);
        final int size = 25, margin = 5;

        Date date = new Gson().fromJson(journal.getTime(), Date.class);
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat format = new SimpleDateFormat("dd.MMMM.yy | HH:mm");

        ParametersManager pm = new Gson()
                    .fromJson(journal.getParamsInfo(), ParametersManager.class);

        h.mDate.setText(format.format(date));
        h.mParamsInfo.setText(new Stopwatch(pm.getTotalTime()).getTime());

        h.mStarManager.hideAllText();
        h.mStarManager.setSizeStar(size, margin);
        h.mStarManager.setStars(journal.getCountStars());
        h.mStarManager.setClickable(false);

        h.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                FragmentManager fragmentManager =  ((AppCompatActivity)
                        view.getContext()).getSupportFragmentManager();
                JournalDialog recordDialog = new JournalDialog(mJournal.get(i));
                recordDialog.show(fragmentManager, null);
            }
        });

    }

    @Override
    public final int getItemCount() {
        return mJournal.size();
    }

    /** Update UI. */
    public void dataChanged() {
        mJournal = Journal.listAll(Journal.class);
        notifyDataSetChanged();
    }

    /**
     * Breath TrainingRecord Application
     *
     * The class describes a markup instance.
     *
     * @author Dmitriy Kalistratov <kalistratov.d.m@gmail.com>
     * @version 1.0
     */
    final class ViewHolder extends RecyclerView.ViewHolder {

        /** Card - parent object. */
        private CardView cardView;

        /** TextView object - displays information. */
        private TextView mDate,
                         mParamsInfo;

        /** Star manager. */
        private StarsManager mStarManager;

        /**
         * Constructor - initialize full objects.
         *
         * @param v - super view.
         */
        private ViewHolder(final View v) {
            super(v);
            mDate           = v.findViewById(R.id.element_journal_date);
            mParamsInfo     = v.findViewById(R.id.element_journal_params_info);
            mStarManager    = v.findViewById(R.id.element_journal_stars);
            cardView        = v.findViewById(R.id.element_journal_card);
        }
    }

}
