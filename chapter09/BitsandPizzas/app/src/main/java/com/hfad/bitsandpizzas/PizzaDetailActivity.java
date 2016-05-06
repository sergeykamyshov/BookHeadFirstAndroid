package com.hfad.bitsandpizzas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ShareActionProvider;
import android.widget.TextView;

public class PizzaDetailActivity extends Activity {

    private ShareActionProvider shareActionProvider;
    public static final String EXTRA_PIZZANO = "pizzaNo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizza_detail);

        // устанавливаем кнопку "Вверх"
        getActionBar().setDisplayHomeAsUpEnabled(true);

        // получаем информацию из Intent
        int pizzaNo = (int) getIntent().getExtras().get(EXTRA_PIZZANO);
        String pizzaName = Pizza.pizzas[pizzaNo].getName();
        int pizzaImage = Pizza.pizzas[pizzaNo].getImageResourceId();

        // получаем и заполняем наше описание пиццы
        TextView textView = (TextView) findViewById(R.id.pizza_text);
        textView.setText(pizzaName);
        ImageView imageView = (ImageView) findViewById(R.id.pizza_image);
        imageView.setImageDrawable(getResources().getDrawable(pizzaImage));
        imageView.setContentDescription(pizzaName);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // используем название пиццы в ActionBar
        TextView textView = (TextView) findViewById(R.id.pizza_text);
        CharSequence pizzaName = textView.getText();
        MenuItem shareItem = menu.findItem(R.id.action_share);
        shareActionProvider = (ShareActionProvider) shareItem.getActionProvider();
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, pizzaName);
        // назчаним текст по-умолчанию для Share
        shareActionProvider.setShareIntent(intent);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_create_order:
                Intent intent = new Intent(this, OrderActvity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
