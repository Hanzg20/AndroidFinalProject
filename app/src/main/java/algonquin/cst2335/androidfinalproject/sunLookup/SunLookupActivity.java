package algonquin.cst2335.androidfinalproject.sunLookup;

import static algonquin.cst2335.androidfinalproject.song.RequestQueueSingleton.context;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.androidfinalproject.R;
import algonquin.cst2335.androidfinalproject.databinding.ActivitySunLookupBinding;
import algonquin.cst2335.androidfinalproject.sunLookup.Dao.AppDatabase;
import algonquin.cst2335.androidfinalproject.sunLookup.Dao.FavoriteLocation;
import algonquin.cst2335.androidfinalproject.sunLookup.Dao.FavoriteLocationDao;

public class SunLookupActivity extends AppCompatActivity {

    private ActivitySunLookupBinding binding;
    private LocationViewModel locationModel;
    private RecyclerView.Adapter myAdapter;
    private FavoriteLocationDao favoriteLocationDao;
    private ArrayList<FavoriteLocation> favoriteLocations;

    private EditText latitudeEditText, longitudeEditText, SunriseResults, SunsetResults;
    private TextView sunriseView, sunsetView;
    private Button lookupButton, saveButton, deleteButton, FavoriteButton;

    private SharedPreferences sharedPreferences;
    private RequestQueue requestQueue;
    private SunLookupActivity Binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySunLookupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "app_database").build();
        favoriteLocationDao = db.favoriteLocationDao();

        locationModel = new ViewModelProvider(this).get(LocationViewModel.class);
        favoriteLocations = locationModel.favoriteLocations.getValue();
        if (favoriteLocations == null) {
            locationModel.favoriteLocations.setValue(favoriteLocations = new ArrayList<>());

            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() -> {
                favoriteLocations.addAll(favoriteLocationDao.getAllFavoriteLocations());
                runOnUiThread(() -> {
                    binding.recycleView.setAdapter(myAdapter);
                });
            });
        }

        sunriseView = findViewById(R.id.sunriseView);
        sunsetView = findViewById(R.id.sunsetView);
        latitudeEditText = findViewById(R.id.latitudeEditText);
        longitudeEditText = findViewById(R.id.longitudeEditText);
        lookupButton = findViewById(R.id.lookupButton);saveButton = findViewById(R.id.saveButton);
        FavoriteButton = findViewById(R.id.FavoriteButton);

        sharedPreferences = getSharedPreferences("SunriseSunsetPrefs", Context.MODE_PRIVATE);
        requestQueue = Volley.newRequestQueue(this);

        latitudeEditText.setText(sharedPreferences.getString("latitude", ""));
        longitudeEditText.setText(sharedPreferences.getString("longitude", ""));

        binding.lookupButton.setOnClickListener(v -> {
            String latitude = latitudeEditText.getText().toString();
            String longitude = longitudeEditText.getText().toString();

            if (latitude.isEmpty() || longitude.isEmpty()) {
                Toast.makeText(this, "Please enter latitude and longitude", Toast.LENGTH_SHORT).show();
                return;
            }

            String apiUrl = "https://api.sunrisesunset.io/json?lat=" + latitude + "&lng=" + longitude + "&timezone=UTC&date=today";

            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.GET, apiUrl, null,
                    response -> {
                        try {
                            // Parse the JSON response and extract all relevant information
                            JSONObject results = response.getJSONObject("results");
                            String sunrise = results.getString("sunrise");
                            String sunset = results.getString("sunset");
                            String firstLight = results.getString("first_light");
                            String lastLight = results.getString("last_light");
                            String dawn = results.getString("dawn");
                            String dusk = results.getString("dusk");
                            String solarNoon = results.getString("solar_noon");
                            String goldenHour = results.getString("golden_hour");
                            String dayLength = results.getString("day_length");
                            String timezone = results.getString("timezone");
                            int utcOffset = results.getInt("utc_offset");

                            // Concatenate all the information into a single String
                            String allInfo = "Sunrise: " + sunrise + "\n" +
                                    "Sunset: " + sunset + "\n" +
                                    "First Light: " + firstLight + "\n" +
                                    "Last Light: " + lastLight + "\n" +
                                    "Dawn: " + dawn + "\n" +
                                    "Dusk: " + dusk + "\n" +
                                    "Solar Noon: " + solarNoon + "\n" +
                                    "Golden Hour: " + goldenHour + "\n" +
                                    "Day Length: " + dayLength + "\n" +
                                    "Timezone: " + timezone + "\n" +
                                    "UTC Offset: " + utcOffset;

                            // Display the concatenated information in the TextView
                            sunriseView.setText(allInfo);
                            sunsetView.setText("");

                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("latitude", latitude);
                            editor.putString("longitude", longitude);
                            editor.apply();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },
                    error -> Toast.makeText(SunLookupActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show()
            );

            requestQueue.add(request);

            binding.saveButton.setOnClickListener(click -> {
                if (latitude.isEmpty() || longitude.isEmpty()) {
                    Toast.makeText(this, "Please enter latitude and longitude", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    double lat = Double.parseDouble(latitude);
                    double lon = Double.parseDouble(longitude);

                    FavoriteLocation favoriteLocation = new FavoriteLocation(lon, lat);

                    favoriteLocations.add(favoriteLocation);
                    myAdapter.notifyItemInserted(favoriteLocations.size() - 1);

                    latitudeEditText.setText("");
                    longitudeEditText.setText("");

                    Executor thread = Executors.newSingleThreadExecutor();
                    thread.execute(() -> favoriteLocationDao.insert(favoriteLocation));
                } catch (NumberFormatException e) {
                    Toast.makeText(this, "Invalid latitude or longitude format", Toast.LENGTH_SHORT).show();
                }
            });

            binding.DeleteButton.setOnClickListener(click -> {
                if (latitude.isEmpty() || longitude.isEmpty()) {
                    Toast.makeText(this, "Please enter latitude and longitude", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    double lat = Double.parseDouble(latitude);
                    double lon = Double.parseDouble(longitude);

                    FavoriteLocation favoriteLocationToDelete = favoriteLocationDao.getFavoriteLocationByLatLng(lat, lon);

                    if (favoriteLocationToDelete != null) {
                        favoriteLocationDao.delete(favoriteLocationToDelete);
                        Toast.makeText(this, "Location deleted from favorites", Toast.LENGTH_SHORT).show();

                        favoriteLocations.remove(favoriteLocationToDelete);
                        myAdapter.notifyItemRemoved(favoriteLocations.indexOf(favoriteLocationToDelete));
                    } else {
                        Toast.makeText(this, "Favorite location not found", Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(this, "Invalid latitude or longitude format", Toast.LENGTH_SHORT).show();
                }
            });

            binding.FavoriteButton.setOnClickListener(click -> {
                if (latitude.isEmpty() || longitude.isEmpty()) {
                    Toast.makeText(this, "Please enter latitude and longitude", Toast.LENGTH_SHORT).show();
                    return;
                }

               try {
                    double lat = Double.parseDouble(latitude);
                    double lon = Double.parseDouble(longitude);

                    FavoriteLocation favoriteLocation = favoriteLocationDao.getFavoriteLocationByLatLng(lat, lon);

                    if (favoriteLocation != null) {
                        Log.d("Favorite Location", "Latitude: " + favoriteLocation.getLatitude() + ", Longitude: " + favoriteLocation.getLongitude());
                    } else {
                        Toast.makeText(this, "Favorite location not found", Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(this, "Invalid latitude or longitude format", Toast.LENGTH_SHORT).show();
                }


            });
        });
    }
}