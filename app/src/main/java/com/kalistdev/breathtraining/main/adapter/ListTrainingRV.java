package com.kalistdev.breathtraining.main.adapter;

import java.util.List;
import android.view.View;
import com.google.gson.Gson;
import android.widget.Button;
import android.content.Intent;
import android.view.ViewGroup;
import android.widget.TextView;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import com.google.gson.GsonBuilder;
import com.kalistdev.breathtraining.R;
import android.annotation.SuppressLint;
import android.view.animation.Animation;
import androidx.cardview.widget.CardView;
import android.view.animation.AnimationUtils;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.kalistdev.breathtraining.Record.Training;
import com.kalistdev.breathtraining.main.dialog.EditorDialog;
import com.kalistdev.breathtraining.training.TrainingActivity;

/**
 * Breath TrainingRecord Application
 *
 * This file is part of the Breath TrainingRecord package.
 *
 * @author  Dmitriy Kalistratov <kalistratov.d.m@gmail.com>
 * @version 1.0
 */
public class ListTrainingRV
        extends RecyclerView.Adapter<ListTrainingRV.ViewHolder> {

    /** A list of workouts. */
    private List<Training> mTrainings;

    /** Animation object. */
    private Animation animation;


    /**
     * Constructor - initialize full object. */
    public ListTrainingRV() {
        mTrainings = Training.listAll(Training.class);
    }


    @Override
    public final ViewHolder onCreateViewHolder(final ViewGroup viewGroup,
                                               final int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.view_element_training,
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
        v.params.setText(v.context.getString(R.string.characteristic)
                + mTrainings.get(i).getInhale() + ", "
                + mTrainings.get(i).getPauseAfterInhale() + ", "
                + mTrainings.get(i).getExhale() + ", "
                + mTrainings.get(i).getPauseAfterExhale() + ".");
        v.time.setText(v.context.getString(R.string.Training_time)
                + mTrainings.get(i).getTime()
                + v.context.getString(R.string.minutes));

        v.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (!v.isOpenMenu) {
                    Intent intent = new Intent(view.getContext(),
                            TrainingActivity.class);
                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();
                    String json = gson.toJson(mTrainings.get(i));
                    intent.putExtra("training",
                            json);
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
                Training training
                        = Training.findById(Training.class,
                                            mTrainings.get(i).getId());
                training.delete();
                v.cardView.setVisibility(View.GONE);
            }
        });

        v.buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                EditorDialog dialog = new EditorDialog(EditorDialog.EDIT);
                dialog.setTraining(mTrainings.get(i));
                FragmentManager manager = ((AppCompatActivity)
                        view.getContext()).getSupportFragmentManager();
                dialog.show(manager, null);
                v.linearLayout.setVisibility(View.GONE);
                v.isOpenMenu = false;
            }
        });
    }

    /** Update UI. */
    public void dataChanged() {
        mTrainings = Training.listAll(Training.class);
        notifyDataSetChanged();
    }

    @Override
    public final int getItemCount() {
        return mTrainings.size();
    }

    /**
     * Breath TrainingRecord Application
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

        /** LinearLayout object - markup view_element_training. */
        private LinearLayout linearLayout;

        /** Button object - opens the edit menu. */
        private Button buttonEdit;

        /** Button object - removes a workout. */
        private Button buttonDelete;

        /** Session context. */
        private Context context;

        /**
         * Constructor - initialize full objects.
         *
         * @param v - super view.
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
            context = v.getContext();
        }
    }
}
