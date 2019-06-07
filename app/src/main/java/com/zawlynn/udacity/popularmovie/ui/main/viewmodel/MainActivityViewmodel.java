package com.zawlynn.udacity.popularmovie.ui.main.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.zawlynn.udacity.popularmovie.R;
import com.zawlynn.udacity.popularmovie.constants.Constants;
import com.zawlynn.udacity.popularmovie.model.Movie;
import com.zawlynn.udacity.popularmovie.data.network.NetworkUtils;
import com.zawlynn.udacity.popularmovie.utils.JSONUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivityViewmodel extends AndroidViewModel {

    private MutableLiveData<List<Movie>> _movies=new MutableLiveData<>();
    private MutableLiveData<String> _error=new MutableLiveData<>();
    public MainActivityViewmodel(@NonNull Application application) {
        super(application);
    }
    public void getPopularMovies(Context context) {
        RequestQueue queue = NetworkUtils.getInstance(context).getRequestQueue();
        String uri = String.format(Constants.POPULAR + "?api_key=%1$s", Constants.API_KEY);
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, uri,
                null, response -> {
            VolleyLog.d(response.toString());
            List<Movie> movies = JSONUtils.getInstance().parseMovies(response);
            if (movies != null) {
                _movies.postValue(movies);
            }
        }, errorListener) {
            @Override
            public Priority getPriority() {
                return Priority.LOW;
            }
        };
        queue.add(stringRequest);
    }
    public void getTopRated(Context context) {
        RequestQueue queue = NetworkUtils.getInstance(context).getRequestQueue();
        String uri = String.format(Constants.TOP_RATED + "?api_key=%1$s", Constants.API_KEY);
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, uri,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                ArrayList<Movie> movies = JSONUtils.getInstance().parseMovies(response);
                if (movies != null) {
                    _movies.postValue(movies);
                }
            }
        }, errorListener) {
            @Override
            public Priority getPriority() {
                return Priority.LOW;
            }
        };
        queue.add(stringRequest);
    }
    private Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            if (error instanceof NetworkError) {
                _error.postValue(getApplication().getString(R.string.no_internet));
            } else {
                _error.postValue(getApplication().getString(R.string.no_internet));
            }
        }
    };
    public MutableLiveData<List<Movie>> get_movies() {
        return _movies;
    }

    public MutableLiveData<String> get_error() {
        return _error;
    }
}
