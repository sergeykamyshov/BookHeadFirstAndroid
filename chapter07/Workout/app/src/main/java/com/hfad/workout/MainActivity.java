package com.hfad.workout;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements WorkoutListFragment.WorkoutListListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        WorkoutDetailFragment frag = (WorkoutDetailFragment) getSupportFragmentManager().findFragmentById(R.id.detail_frag);
//        frag.setWorkoutId(1);
    }

    @Override
    public void itemClicked(long id) {
        // получаем наш фрагмент у устанавливаем ему id workout
        WorkoutDetailFragment fragment = new WorkoutDetailFragment();
        fragment.setWorkoutId(id);

        // выполняем замену фрагмента в транзакции
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
//        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
