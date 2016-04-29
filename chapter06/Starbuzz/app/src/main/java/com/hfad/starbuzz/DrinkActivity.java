package com.hfad.starbuzz;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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
                    new String[]{"NAME", "DESCRIPTION", "IMAGE_RESOURCE_ID"},
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
            }

            cursor.close();
            db.close();
        } catch (SQLiteException e) {
            Toast.makeText(this, "Database unavailable", Toast.LENGTH_LONG).show();
        }
    }
}
