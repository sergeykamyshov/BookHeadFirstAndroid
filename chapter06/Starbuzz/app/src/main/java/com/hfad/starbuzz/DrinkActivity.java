package com.hfad.starbuzz;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DrinkActivity extends AppCompatActivity {

    public static String EXTRA_DRINKNO = "drinkNo";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        // Получение напитка из интента
        int drinkNo = (int) getIntent().getExtras().get(EXTRA_DRINKNO);

        try {
            StarbuzzDatabaseHelper databaseHelper = new StarbuzzDatabaseHelper(this);
            SQLiteDatabase db = databaseHelper.getReadableDatabase();

            Cursor cursor = db.query("DRINK",
                    new String[]{"NAME", "DESCRIPTION", "IMAGE_RESOUCE_ID", "FAVORITE"},
                    "_id = ?",
                    new String[]{Integer.toString(drinkNo)},
                    null, null, null);

            if (cursor.moveToFirst()) {
                TextView name = (TextView) findViewById(R.id.name);
                name.setText(cursor.getString(0));

                TextView describtion = (TextView) findViewById(R.id.description);
                describtion.setText(cursor.getString(1));

                ImageView photo = (ImageView) findViewById(R.id.photo);
                photo.setImageResource(cursor.getInt(2));

                CheckBox favorite = (CheckBox) findViewById(R.id.favorite);
                boolean isFavorite = cursor.getInt(3) == 1;
                favorite.setChecked(isFavorite);
            }

            cursor.close();
            db.close();
        } catch (SQLiteException e) {
            Toast.makeText(this, "Database unavailable", Toast.LENGTH_LONG).show();
        }
    }

    public void onFavoriteClick(View view) {
        int drinkNo = getIntent().getExtras().getInt(EXTRA_DRINKNO);
        CheckBox favorite = (CheckBox) view;

        ContentValues values = new ContentValues();
        values.put("FAVORITE", favorite.isChecked());

        try {
            SQLiteOpenHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(this);
            SQLiteDatabase db = starbuzzDatabaseHelper.getWritableDatabase();

            db.update("DRINK", values, "_id = ?", new String[]{Integer.toString(drinkNo)});
            db.close();
        } catch (SQLiteException e) {
            Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT).show();
        }
    }
}
