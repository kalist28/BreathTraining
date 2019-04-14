package com.kalistdev.breathtraining;

import android.annotation.SuppressLint;
import android.content.Intent;
import androidx.annotation.NonNull;
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

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.ViewHolder> {

    private List<Training> trainings;
    private Animation animation;
    private ListFragment.EditTrainingDialog dialogFragment;

    RVAdapter(List<Training> trainings, ListFragment.EditTrainingDialog dialogFragment){
        this.trainings = trainings;
        this.dialogFragment = dialogFragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.element, viewGroup, false);
        return new ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder v, @SuppressLint("RecyclerView") final int i) {
        v.isOpenMenu = false;
        v.linearLayout.setVisibility(View.GONE);
        v.name.setText(trainings.get(i).get_name());
        v.params.setText("Параметры: " +
                trainings.get(i).get_inhale() + ", " +
                trainings.get(i).get_pause_a() + ", " +
                trainings.get(i).get_exhale() + ", " +
                trainings.get(i).get_pause_b() + ".");
        v.time.setText("Время тренировки: " + trainings.get(i).get_time() + " мин.");

        v.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!v.isOpenMenu){
                    Intent intent = new Intent(view.getContext(), TrainingActivity.class);
                    intent.putExtra("Name", trainings.get(i).get_name());
                    intent.putExtra("AllTime", trainings.get(i).get_time());
                    intent.putExtra("Inhale",  trainings.get(i).get_inhale());
                    intent.putExtra("Exhale",  trainings.get(i).get_exhale());
                    intent.putExtra("Pause_a", trainings.get(i).get_pause_a());
                    intent.putExtra("Pause_b", trainings.get(i).get_pause_b());
                    view.getContext().startActivity(intent);
                }else{
                    v.linearLayout.setVisibility(View.GONE);
                    v.isOpenMenu = false;
                }
            }
        });
        v.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (!v.isOpenMenu){
                    animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.bounce_open);
                    v.linearLayout.setVisibility(View.VISIBLE);
                    v.linearLayout.startAnimation(animation);
                    v.isOpenMenu = true;
                }else{
                    v.linearLayout.setVisibility(View.GONE);
                    v.isOpenMenu = false;
                }
                return true;
            }
        });

        v.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase sqLiteDatabase = new DataBaseHelper(view.getContext()).getReadableDatabase();
                sqLiteDatabase.execSQL("DELETE FROM " + DataBaseHelper.DATABASE_TABLE +
                        " WHERE " + DataBaseHelper.KEY_ID + " = " + trainings.get(i).get_id());
                sqLiteDatabase.close();
                v.cardView.setVisibility(View.GONE);
            }
        });

        v.buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogFragment.setTraining(trainings.get(i));
                FragmentManager manager = ((AppCompatActivity) view.getContext()).getSupportFragmentManager();
                dialogFragment.show(manager , null);
                v.linearLayout.setVisibility(View.GONE);
                v.isOpenMenu = false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return trainings.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        boolean isOpenMenu;
        CardView cardView;
        TextView name;
        TextView params;
        TextView time;
        LinearLayout linearLayout;
        Button buttonEdit;
        Button buttonDelete;

        ViewHolder(@NonNull View v) {
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
