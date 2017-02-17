package es.cice.moviedbfinalproject;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import es.cice.moviedbfinalproject.asynctasks.TheMovieDBMoviesAsyncTask;
import es.cice.moviedbfinalproject.model.Genre;
import es.cice.moviedbfinalproject.model.ListGenres;
import es.cice.moviedbfinalproject.model.setupDB;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getCanonicalName();
    private Context ctx;
    private ActionBar aBar;
    private EditText searchET;
    private String urlBaseImage;
    public static final String API_KEY= "857ef84cbaec1f89f981c0ac344c4630";
    private static final String URL_CONFIG="https://api.themoviedb.org/3/configuration?api_key=" + API_KEY;
    private static final String URL_GENRES ="https://api.themoviedb.org/3/genre/movie/list?api_key="+ API_KEY;
    private Spinner spGenre;
    private RecyclerView moviesRV;
    private ImageView imageMovieIV;
    private final static String URL_POPULAR_MOVIES="https://api.themoviedb.org/3/movie/popular?api_key="+API_KEY;
    private List<Genre> genreList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ctx = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.includedToolbar);
        setSupportActionBar(toolbar);
        aBar = getSupportActionBar();
        aBar.setTitle("PELÍCULAS");

        spGenre= (Spinner) findViewById(R.id.genreSPIN);
        //spGenre= (Spinner) findViewById(R.id.genreSP);
        moviesRV = (RecyclerView) findViewById(R.id.moviesRV);
        imageMovieIV=(ImageView) findViewById(R.id.movieImageIV);
        getBaseUrlImage();
       // getGenres();
        //getMovies();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }
    public void getGenres(){
        TheMovieDBGetGenresAsynTask at = new TheMovieDBGetGenresAsynTask();
        at.execute(URL_GENRES);
    }

    public void getMovies() {
        Log.d("En el getMovies", " "+urlBaseImage);
        TheMovieDBMoviesAsyncTask at = new TheMovieDBMoviesAsyncTask(moviesRV,this,imageMovieIV,genreList, urlBaseImage);
        at.execute(URL_POPULAR_MOVIES);  }

    public void getBaseUrlImage(){
        TheMovieDBUrlBaseAsynTask at = new TheMovieDBUrlBaseAsynTask();
        at.execute(URL_CONFIG); }

    public class TheMovieDBUrlBaseAsynTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            getGenres();
        }

        @Override
        protected String doInBackground(String... urls) {
            BufferedReader in = null;
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

                setupDB setupdb = gson.fromJson(json, setupDB.class);
                urlBaseImage = setupdb.getImages().getBaseUrl();
                Log.d("perooooo..",urlBaseImage);
                return urlBaseImage;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

        }
    }

    //En el primer parámetro le pasamos la URL para obtener los géneros disponibles
    public class TheMovieDBGetGenresAsynTask extends AsyncTask<String, Void, List<Genre>> {
        @Override
        protected List<Genre> doInBackground(String... urls) {
            BufferedReader in = null;
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
                String json = data.toString();
                ListGenres lg = gson.fromJson(json, ListGenres.class);
                genreList=lg.getGenres();
                return genreList;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Genre> genres) {
            super.onPostExecute(genres);
            getMovies();

            List<String> listag = new ArrayList<>();

            for (int i = 0; i < genres.size(); i++) {
                listag.add(genres.get(i).getName());
                //Log.d("Un género", "" + genres.get(i).getName());
            }

//Creamos el adaptador
            ArrayAdapter spAdapter = new ArrayAdapter(ctx, android.R.layout.simple_spinner_item, listag);
//Añadimos el layout para el menú y se lo damos al spinner
            spAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spGenre.setAdapter(spAdapter);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.generoIT:
                Log.d(TAG, "Género item...");
                aBar.setDisplayShowCustomEnabled(true);
                spGenre.setVisibility(View.VISIBLE);
                //aBar.setCustomView(R.layout.genre_layout);
                //aBar.setDisplayShowTitleEnabled(false);

                return true;
           /* case R.id.buscarIT:
                Log.d(TAG, "Search item...");
                aBar.setDisplayShowCustomEnabled(true);
                aBar.setCustomView(R.layout.search_layout);
                aBar.setDisplayShowTitleEnabled(false);

                searchET = (EditText) aBar.getCustomView().findViewById(R.id.searchET);
                searchET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView textView, int action, KeyEvent keyEvent) {
                        if (action == EditorInfo.IME_ACTION_SEARCH) {
                            CharSequence searchText = searchET.getText();
                            Log.d(TAG, "search: " + searchText);
                            InputMethodManager imn =
                                    (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imn.hideSoftInputFromWindow(searchET.getWindowToken(), 0);
                            aBar.setDisplayShowCustomEnabled(false);
                            aBar.setDisplayShowTitleEnabled(true);
                            //empezar la busqueda
                            adapter.getFilter().filter(searchText);
                            return true;
                        }
                        return false;
                    }
                });

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(searchET, InputMethodManager.SHOW_IMPLICIT);
                searchET.requestFocus();
                break;*/
        }


                return super.onOptionsItemSelected(item);
        }



    }




