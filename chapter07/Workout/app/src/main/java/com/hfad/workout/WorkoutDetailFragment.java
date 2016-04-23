package com.hfad.workout;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class WorkoutDetailFragment extends Fragment {

    private long workoutId;

    // пустой конструктор необходим чтобы Android имел возможность пересоздавать фрагмент когда понадобится
    public WorkoutDetailFragment() {
        // Required empty public constructor
    }


    // вызывает по создании фрагмента
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            workoutId = savedInstanceState.getLong("workoutId");
        } else {
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            StopwatchFragment stopwatchFragment = new StopwatchFragment();
            transaction.replace(R.id.stopwatch_container, stopwatchFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        return inflater.inflate(R.layout.fragment_workout_detail, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        if (view != null) {
            Workout workout = Workout.workouts[(int) workoutId];

            TextView titleView = (TextView) view.findViewById(R.id.textTitle);
            TextView desciptionView = (TextView) view.findViewById(R.id.textDescription);

            titleView.setText(workout.getName());
            desciptionView.setText(workout.getDescription());
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putLong("workoutId", workoutId);
    }

    public void setWorkoutId(long workoutId) {
        this.workoutId = workoutId;
    }
}
