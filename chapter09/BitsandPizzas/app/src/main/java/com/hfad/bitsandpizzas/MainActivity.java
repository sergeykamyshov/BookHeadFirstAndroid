package com.hfad.bitsandpizzas;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ShareActionProvider;

import com.hfad.bitsandpizzas.fragments.PastaFragment;
import com.hfad.bitsandpizzas.fragments.PizzaFragment;
import com.hfad.bitsandpizzas.fragments.StoresFragment;
import com.hfad.bitsandpizzas.fragments.TopFragment;

public class MainActivity extends Activity {

    private ShareActionProvider shareActionProvider;
    private String[] titles;
    private ListView drawerList;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        titles = getResources().getStringArray(R.array.titles);
        drawerList = (ListView) findViewById(R.id.listView);
        drawerList.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, titles));
        drawerList.setOnItemClickListener(new DrawerItemClickListener());

        // при старте загружаем TopActivity
        if (savedInstanceState == null) {
            selectItem(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem shareItem = menu.findItem(R.id.action_share);
        shareActionProvider = (ShareActionProvider) shareItem.getActionProvider();
        setIntent("This is example text");

        return true;
    }

    private void setIntent(String text) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, text);
        shareActionProvider.setShareIntent(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_create_order:
                startActivity(new Intent(this, OrderActvity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
            setActionBarTitle(position);
            drawerLayout.closeDrawer(drawerList);
        }
    }

    private void selectItem(int position) {
        Fragment fragment;
        switch (position) {
            case 1:
                fragment = new PizzaFragment();
                break;
            case 2:
                fragment = new PastaFragment();
                break;
            case 3:
                fragment = new StoresFragment();
                break;
            default:
                fragment = new TopFragment();
                break;
        }

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.container_frame, fragment);g
        ft.addToBackStack(null);
        ft.commit();
    }

    private void setActionBarTitle(int position) {
        String title;
        if (position == 0) {
            title = getResources().getString(R.string.app_name);
        } else {
            title = titles[position];
        }
        getActionBar().setTitle(title);
    }
}
