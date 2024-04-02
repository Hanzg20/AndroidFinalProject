package algonquin.cst2335.androidfinalproject.sunLookup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import algonquin.cst2335.androidfinalproject.R;
import algonquin.cst2335.androidfinalproject.sunLookup.API.VolleyRequestQueue;

public class SunLookupActivity extends AppCompatActivity {

    EditText latitudeEditText, longitudeEditText;
    TextView sunriseView, sunsetView;
    Button lookupButton, saveButton, deleteButton;
    SharedPreferences sharedPreferences;

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
                            sunriseView.setText("Sunrise: " + sunriseTime);
                            sunsetView.setText("Sunset: " + sunsetTime);
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

        VolleyRequestQueue.getInstance(this).addToRequestQueue(request);
    }

    private void saveLocationToDatabase() {
        // Implement saving location to database
    }

    private void deleteFavoriteLocation() {
        // Implement deletion of favorite location from database
    }
}
