package algonquin.cst2335.androidfinalproject.sunLookup;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import algonquin.cst2335.androidfinalproject.R;
import algonquin.cst2335.androidfinalproject.sunLookup.Dao.AppDatabase;
import algonquin.cst2335.androidfinalproject.sunLookup.Dao.FavoriteLocation;
import algonquin.cst2335.androidfinalproject.sunLookup.Dao.FavoriteLocationDao;

/**
 *
 */
public class SunLookupActivity extends AppCompatActivity {

    EditText latitudeEditText, longitudeEditText;
    TextView sunriseView, sunsetView;
    Button lookupButton, saveButton, deleteButton;
    SharedPreferences sharedPreferences;
    RequestQueue requestQueue;
    FavoriteLocationDao favoriteLocationDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sun_lookup);

        sunriseView = findViewById(R.id.sunriseView);
        sunsetView = findViewById(R.id.sunsetView);
        latitudeEditText = findViewById(R.id.latitudeEditText);
        longitudeEditText = findViewById(R.id.longitudeEditText);
        lookupButton = findViewById(R.id.lookupButton);
        saveButton = findViewById(R.id.saveButton);
        deleteButton = findViewById(R.id.DeleteButton);

        sharedPreferences = getSharedPreferences("SunriseSunsetPrefs", Context.MODE_PRIVATE);
        requestQueue = Volley.newRequestQueue(this);

        // Initialize Room database and DAO
        favoriteLocationDao = AppDatabase.getInstance(this).favoriteLocationDao();

        // Retrieve the saved latitude and longitude
        String savedLatitude = sharedPreferences.getString("latitude", "");
        String savedLongitude = sharedPreferences.getString("longitude", "");
        latitudeEditText.setText(savedLatitude);
        longitudeEditText.setText(savedLongitude);

        lookupButton.setOnClickListener(v -> lookupSunriseSunset());
        saveButton.setOnClickListener(v -> saveLocationToDatabase());
        deleteButton.setOnClickListener(v -> deleteFavoriteLocation());
    }

    private void lookupSunriseSunset() {
        String latitude = latitudeEditText.getText().toString();
        String longitude = longitudeEditText.getText().toString();

        if (latitude.isEmpty() || longitude.isEmpty()) {
            Toast.makeText(this, "Please enter latitude and longitude", Toast.LENGTH_SHORT).show();
            return;
        }

        String apiUrl = "https://api.sunrisesunset.io/json?lat=" + latitude + "&lng=" + longitude + "&timezone=UTC&date=today";

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, apiUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject results = response.getJSONObject("results");
                            String sunriseTime = results.getString("sunrise");
                            String sunsetTime = results.getString("sunset");

                            // Extract additional data from the response
                            String firstLight = results.getString("first_light");
                            String lastLight = results.getString("last_light");
                            String dawn = results.getString("dawn");
                            String dusk = results.getString("dusk");
                            String solarNoon = results.getString("solar_noon");
                            String goldenHour = results.getString("golden_hour");
                            String dayLength = results.getString("day_length");

                            // Display the retrieved data
                            sunriseView.setText("Sunrise: " + sunriseTime);
                            sunsetView.setText("Sunset: " + sunsetTime);
                            // You can similarly display other data if needed

                            // Save the latitude and longitude when the search is successful
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("latitude", latitude);
                            editor.putString("longitude", longitude);
                            editor.apply();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SunLookupActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
        );

        // Add the request to the RequestQueue
        requestQueue.add(request);
    }
    private void saveLocationToDatabase() {
        String latitude = latitudeEditText.getText().toString();
        String longitude = longitudeEditText.getText().toString();

        if (latitude.isEmpty() || longitude.isEmpty()) {
            Toast.makeText(this, "Please enter latitude and longitude", Toast.LENGTH_SHORT).show();
            return;
        }

        double lat = Double.parseDouble(latitude);
        double lon = Double.parseDouble(longitude);

        // Create a new FavoriteLocation object
        FavoriteLocation favoriteLocation = new FavoriteLocation(lat, lon);

        // Insert the favorite location into the database
        favoriteLocationDao.insert(favoriteLocation);

        // Show a toast message to indicate successful save
        Toast.makeText(this, "Location saved to favorites", Toast.LENGTH_SHORT).show();
    }

    private void deleteFavoriteLocation() {
        String latitude = latitudeEditText.getText().toString();
        String longitude = longitudeEditText.getText().toString();

        if (latitude.isEmpty() || longitude.isEmpty()) {
            Toast.makeText(this, "Please enter latitude and longitude", Toast.LENGTH_SHORT).show();
            return;
        }

        double lat = Double.parseDouble(latitude);
        double lon = Double.parseDouble(longitude);

        // Retrieve the favorite location from the database using latitude and longitude
        FavoriteLocation favoriteLocation = favoriteLocationDao.getFavoriteLocationByLatLng(lat, lon);

        if (favoriteLocation != null) {
            // Delete the favorite location from the database
            favoriteLocationDao.delete(favoriteLocation);

            // Show a toast message to indicate successful deletion
            Toast.makeText(this, "Location deleted from favorites", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Favorite location not found", Toast.LENGTH_SHORT).show();
        }
    }
}
