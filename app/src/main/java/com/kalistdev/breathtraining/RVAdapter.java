package com.kalistdev.breathtraining;

import android.annotation.SuppressLint;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Breath Training Application
 *
 * This file is part of the Breath Training package.
 *
 * @author Dmitriy Kalistratov <kalistratov.d.m@gmail.com>
 * @version 1.0
 */
public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ViewHolder> {

    /** A list of workouts. */
    private List<Training> mTrainings;

    /** Animation object. */
    private Animation animation;

    /** The dialogue changes a workout. */
    private ListFragment.EditTrainingDialog mDialogFragment;

    /**
     * Constructor - initialize full object.
     *
     * @param trainings - a list of workouts.
     * @param dialogFragment - dialog to change training.
     */
    RVAdapter(final List<Training> trainings,
              final ListFragment.EditTrainingDialog dialogFragment) {
        this.mTrainings = trainings;
        this.mDialogFragment = dialogFragment;
    }

    @Override
    public final ViewHolder onCreateViewHolder(final ViewGroup viewGroup,
                                               final int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.element,
                        viewGroup,
                        false);
        return new ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public final void onBindViewHolder(final ViewHolder v,
                                 final int i) {
        v.isOpenMenu = false;
        v.linearLayout.setVisibility(View.GONE);
        v.name.setText(mTrainings.get(i).getName());
        v.params.setText("Параметры: "
                + mTrainings.get(i).getInhale() + ", "
                + mTrainings.get(i).getPauseAfterInhale() + ", "
                + mTrainings.get(i).getExhale() + ", "
                + mTrainings.get(i).getPauseAfterExhale() + ".");
        v.time.setText("Время тренировки: "
                + mTrainings.get(i).getTime() + " мин.");

        v.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (!v.isOpenMenu) {
                    Intent intent = new Intent(view.getContext(),
                            TrainingActivity.class);

                    intent.putExtra("Name",
                            mTrainings.get(i).getName());
                    intent.putExtra("AllTime",
                            mTrainings.get(i).getTime());
                    intent.putExtra("Inhale",
                            mTrainings.get(i).getInhale());
                    intent.putExtra("Exhale",
                            mTrainings.get(i).getExhale());
                    intent.putExtra("pauseAfterInhale",
                            mTrainings.get(i).getPauseAfterInhale());
                    intent.putExtra("pauseAfterExhale",
                            mTrainings.get(i).getPauseAfterExhale());
                    view.getContext().startActivity(intent);
                } else {
                    v.linearLayout.setVisibility(View.GONE);
                    v.isOpenMenu = false;
                }
            }
        });
        v.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View view) {
                if (!v.isOpenMenu) {
                    animation = AnimationUtils
                            .loadAnimation(view.getContext(),
                                    R.anim.bounce_open);
                    v.linearLayout.setVisibility(View.VISIBLE);
                    v.linearLayout.startAnimation(animation);
                    v.isOpenMenu = true;
                } else {
                    v.linearLayout.setVisibility(View.GONE);
                    v.isOpenMenu = false;
                }
                return true;
            }
        });

        v.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                SQLiteDatabase sqLiteDatabase =
                        new DataBaseHelper(view.getContext())
                                .getReadableDatabase();
                sqLiteDatabase.execSQL("DELETE FROM "
                        + DataBaseField.DATABASE_TABLE.getValue()
                        + " WHERE " + DataBaseField.KEY_ID.getValue()
                        + " = " + mTrainings.get(i).getId());
                sqLiteDatabase.close();
                v.cardView.setVisibility(View.GONE);
            }
        });

        v.buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                mDialogFragment.setTraining(mTrainings.get(i));
                FragmentManager manager = ((AppCompatActivity)
                        view.getContext()).getSupportFragmentManager();
                mDialogFragment.show(manager, null);
                v.linearLayout.setVisibility(View.GONE);
                v.isOpenMenu = false;
            }
        });
    }

    @Override
    public final int getItemCount() {
        return mTrainings.size();
    }

    /**
     * Breath Training Application
     *
     * The class describes a markup instance.
     *
     * @author Dmitriy Kalistratov <kalistratov.d.m@gmail.com>
     * @version 1.0
     */
    class ViewHolder extends RecyclerView.ViewHolder {

        /** The variable is responsible for opening/closing menu. */
        private boolean isOpenMenu;

        /** CardView object. */
        private CardView cardView;

        /** TextView object - displays the name of the workout. */
        private TextView name;

        /** TextView object - displays information about training parameters. */
        private TextView params;

        /** TextView object - displays time workout. */
        private TextView time;

        /** LinearLayout object - markup element. */
        private LinearLayout linearLayout;

        /** Button object - opens the edit menu. */
        private Button buttonEdit;

        /** Button object - removes a workout. */
        private Button buttonDelete;

        /**
         * Constructor - initialize full objects.
         *
         * @param v - super view element.
         */
        ViewHolder(final View v) {
            super(v);
            cardView = v.findViewById(R.id.cv);
            name = v.findViewById(R.id.name);
            params = v.findViewById(R.id.params);
            time = v.findViewById(R.id.time);
            linearLayout = v.findViewById(R.id.buttons_menu);
            buttonEdit = v.findViewById(R.id.btm_edit);
            buttonDelete = v.findViewById(R.id.btm_delete);
        }
    }
}
