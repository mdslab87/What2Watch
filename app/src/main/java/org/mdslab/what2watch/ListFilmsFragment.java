package org.mdslab.what2watch;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ListFilmsFragment extends Fragment {

    int filmType;

    String url;

    ArrayList<Film> films;

    ArrayList<Film> topFilms;
    ArrayList<Film> inTheatersFilms;
    ArrayList<Film> comingSoonFIlms;

    ListView listFilm;

    public ListFilmsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_films, container, false);

        listFilm = (ListView)view.findViewById(R.id.listViewFilms);

        Bundle arguments = getArguments();
        filmType = arguments.getInt("FILM_TYPE");

        

        switch (filmType){
            case 0 :{
                url = "http://www.myapifilms.com/imdb/top?format=JSON&start=1&end=25&data=F";
                break;
            }
            case 1 :{
                url = "http://www.myapifilms.com/imdb/inTheaters?format=JSON&lang=en-us";
                break;
            }
            case 2 :{
                url = "http://www.myapifilms.com/imdb/comingSoon?format=JSON&lang=en-us&date=2015-04";
                break;
            }
        }


        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
     ///// http request  and setAdapterList;

        final ProgressDialog pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.show();

        StringRequest request = new StringRequest(Request.Method.GET,url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pDialog.hide();
                finishRequest(response, null, true);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.hide();
                finishRequest(null, error, false);
            }
        });
        App.getInstance().addToRequestQueue(request);

    }


    public void finishRequest(String response, VolleyError error, boolean success){
        films = new ArrayList<Film>();
        if(success){
            if(response!=null){
                try{
                    Gson gson = new Gson();
                    switch (filmType){
                        case 0:{ // top
                            topFilms = new ArrayList<>();
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i<array.length(); i++){
                                JSONObject filmJson = array.getJSONObject(i);
                                Film film = gson.fromJson(filmJson.toString(), Film.class);
                                topFilms.add(film);


                            }
                            FilmAdapter adapter = new FilmAdapter(topFilms, getActivity());
                            listFilm.setAdapter(adapter);

                            break;
                        }
                        case 1:{ // in theaters
                            inTheatersFilms = new ArrayList<>();
                            JSONArray jsonArray = new JSONArray(response);
                            for(int i = 0; i<jsonArray.length(); i++){
                                JSONObject currentObject = jsonArray.getJSONObject(i);
                                JSONArray moviesArray = currentObject.getJSONArray("movies");
                                for (int j = 0; j<moviesArray.length(); j++){
                                    JSONObject filmJson = moviesArray.getJSONObject(i);
                                    Film film = gson.fromJson(filmJson.toString(), Film.class);
                                    inTheatersFilms.add(film);
                                }
                            }
                            FilmAdapter adapter = new FilmAdapter(inTheatersFilms, getActivity());
                            listFilm.setAdapter(adapter);

                            break;
                        }
                        case 2:{ // coming soon
                            comingSoonFIlms = new ArrayList<>();
                            JSONArray jsonArray = new JSONArray(response);
                            for(int i = 0; i<jsonArray.length(); i++){
                                JSONObject currentObject = jsonArray.getJSONObject(i);
                                JSONArray moviesArray = currentObject.getJSONArray("movies");
                                for (int j = 0; j<moviesArray.length(); j++){
                                    JSONObject filmJson = moviesArray.getJSONObject(i);
                                    Film film = gson.fromJson(filmJson.toString(), Film.class);
                                    comingSoonFIlms.add(film);
                                }
                            }
                            FilmAdapter adapter = new FilmAdapter(comingSoonFIlms, getActivity());
                            listFilm.setAdapter(adapter);
                            break;
                        }
                    }

                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }

        }
        else {
            Toast.makeText(getActivity(),error.getLocalizedMessage(),Toast.LENGTH_SHORT);
            /////ERROR

        }
    }
}
