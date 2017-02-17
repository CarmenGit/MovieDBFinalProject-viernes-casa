package es.cice.moviedbfinalproject.asynctasks;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import es.cice.moviedbfinalproject.MainActivity;
import es.cice.moviedbfinalproject.adapters.MoviesAdapter;
import es.cice.moviedbfinalproject.model.Genre;
import es.cice.moviedbfinalproject.model.ListGenres;
import es.cice.moviedbfinalproject.model.Movie;
import es.cice.moviedbfinalproject.model.MovieTitleAndImage;
import es.cice.moviedbfinalproject.model.MoviesList;

/**
 * Created by Carmen on 15/02/2017.
 */

public class TheMovieDBMoviesAsyncTask extends AsyncTask<String, Void, List<Movie>> {
    private static final String TAG = MainActivity.class.getCanonicalName();
    private RecyclerView moviesRV;
    private Context ctx;
    private ImageView imageMovieIV;
    private List<Genre> lg;
    private String urlBaseImage;
    //parámetro de entrada la URL

   /* public void setMoviesRV(RecyclerView filmsRV) {
        this.moviesRV = filmsRV;
    }
    public void setContext(Context ctx) {
        this.ctx = ctx;
    }
    public void setImageView(ImageView i){
        this.imageMovieIV=i;
    }
*/
    public TheMovieDBMoviesAsyncTask(RecyclerView moviesRV, Context ctx, ImageView imageMovieIV, List<Genre> lg, String urlBaseImage) {
        this.moviesRV = moviesRV;
        this.ctx = ctx;
        this.imageMovieIV = imageMovieIV;
        this.lg = lg;
        this.urlBaseImage = urlBaseImage;
    }

    @Override
    protected List<Movie> doInBackground(String... urls) {

        //devuelve la lista de títulos
        BufferedReader in = null;

        List<MovieTitleAndImage> movieList = new ArrayList<>();

        //Retrofit evita tener que gestionar la conexion http
        //Retrofit no está disponible en android, hay que añadirla
        try {
            URL url = new URL(urls[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuffer data = new StringBuffer();
            //Insertar los datos obtenidos con in en el StringBuffer
            String line = null;
            while ((line = in.readLine()) != null) {
                data.append(line);
            }
            Gson gson = new Gson();
            String json=data.toString();
            MoviesList ml= gson.fromJson(json, MoviesList.class);
            return ml.getResults();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }  finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<Movie> movieList) {
        MoviesAdapter adapter = new MoviesAdapter(ctx, movieList,urlBaseImage,lg);
        moviesRV.setAdapter(adapter);
        moviesRV.setLayoutManager(new LinearLayoutManager(ctx));
    }
}

