package org.mdslab.what2watch;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

import java.util.List;

/**
 * Created by mdslab on 10/04/15.
 */
public class FilmAdapter extends BaseAdapter {

    List<Film> films;

    Activity currentActivity;

    public FilmAdapter(List<Film> films, Activity currentActivity){
        this.films = films;
        this.currentActivity = currentActivity;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    public int getCount() {
        return films.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Film getItem(int position) {
        return films.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = this.currentActivity.getLayoutInflater().inflate(R.layout.item_film, parent, false);
        final FilmView filmView = new FilmView();
        filmView.image = (ImageView)view.findViewById(R.id.imageView);
        filmView.title = (TextView)view.findViewById(R.id.textView_scrivereTitolo);
        filmView.rating = (TextView)view.findViewById(R.id.textView_scrivereRating);
        filmView.year = (TextView)view.findViewById(R.id.textView_scrivereAnno);
        view.setTag(filmView);

        Film currentFilm = getItem(position);

        filmView.title.setText(currentFilm.getTitle());
        filmView.year.setText(currentFilm.getYear());
        filmView.rating.setText(currentFilm.getRating());


        ImageRequest request = new ImageRequest(currentFilm.getImageURL(),
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        filmView.image.setImageBitmap(bitmap);
                    }
                }, 0, 0, null, null,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        //filmView.image.setImageResource(R.drawable.image_load_error);
                    }
                });

        App.getInstance().addToRequestQueue(request);


        return view;
    }

    static class FilmView{
        ImageView image;
        TextView title;
        TextView rating;
        TextView year;
    }
}
