package org.mdslab.what2watch;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;


public class MainActivity extends FragmentActivity {


    FragmentTabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
        tabHost.setup(this,getSupportFragmentManager(),R.id.realtabcontent);

        tabHost.getTabWidget().setBackgroundColor(Color.DKGRAY);
        tabHost.getTabWidget().getLayoutParams().height = (int) (40 * (getResources().getDisplayMetrics().density));

        int padding = (int)(3 * (getResources().getDisplayMetrics().density));

        Bundle argumentsTop = new Bundle();
        argumentsTop.putInt("FILM_TYPE", 0);

        ImageView imageViewTopFilms = new ImageView(this);
        imageViewTopFilms.setImageResource(R.drawable.topfilms);
        imageViewTopFilms.setPadding(0, padding, 0, padding);
        tabHost.addTab(tabHost.newTabSpec("top_film").setIndicator(imageViewTopFilms), TopFilmsListFragment.class, argumentsTop);


        Bundle argumentsInTheaters = new Bundle();
        argumentsInTheaters.putInt("FILM_TYPE", 1);

        ImageView imageVewInTheaters = new ImageView(this);
        imageVewInTheaters.setImageResource(R.drawable.theatersfilms);
        imageVewInTheaters.setPadding(0, padding, 0, padding);
        tabHost.addTab(tabHost.newTabSpec("in_theaters").setIndicator(imageVewInTheaters), InTheatersFilmListFragment.class, argumentsInTheaters);

        Bundle argumentsComingSoon = new Bundle();
        argumentsComingSoon.putInt("FILM_TYPE", 2);

        ImageView imageViewComingSoon = new ImageView(this);
        imageViewComingSoon.setImageResource(R.drawable.comingsoon);
        imageViewComingSoon.setPadding(0, padding, 0, padding);
        tabHost.addTab(tabHost.newTabSpec("coming_soon").setIndicator(imageViewComingSoon), ComingSoonFilmsListFragment.class, argumentsComingSoon);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
