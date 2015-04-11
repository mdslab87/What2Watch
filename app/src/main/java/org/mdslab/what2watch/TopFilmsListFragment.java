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

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TopFilmsListFragment extends Fragment {

    String url;

    ArrayList<Film> topFilms;

    ListView listFilm;

    public TopFilmsListFragment() {
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


        url = "http://www.myapifilms.com/imdb/top?format=JSON&start=1&end=25&data=F";

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
        request.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        App.getInstance().addToRequestQueue(request);

    }

    public void finishRequest(String response, VolleyError error, boolean success){

        if(success){
            if(response!=null){
                try{
                    Gson gson = new Gson();

                    topFilms = new ArrayList<>();
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i<array.length(); i++){
                        JSONObject filmJson = array.getJSONObject(i);
                        Film film = gson.fromJson(filmJson.toString(), Film.class);
                        topFilms.add(film);

                    }
                    FilmAdapter adapter = new FilmAdapter(topFilms, getActivity());
                    listFilm.setAdapter(adapter);

                }
                catch (JSONException e){
                    e.printStackTrace();
                }
            }

        }
        else {
            Toast.makeText(getActivity(),"ERROR", Toast.LENGTH_SHORT).show();
            /////ERROR

        }
    }
}

