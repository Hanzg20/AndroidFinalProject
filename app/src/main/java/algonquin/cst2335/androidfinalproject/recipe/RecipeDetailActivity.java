package algonquin.cst2335.androidfinalproject.recipe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.androidfinalproject.R;
import algonquin.cst2335.androidfinalproject.databinding.ActivityRecipeDetailBinding;
import algonquin.cst2335.androidfinalproject.databinding.ActivityRecipesBinding;

public class RecipeDetailActivity extends AppCompatActivity {
    private final static String TAG = "RecipeDetailActivity";
    private ActivityRecipeDetailBinding binding;
    private RequestQueue mQueue;

    private RecipeDetailDAO mDAO;
    private long recipeId;
    private RecipeDetail recipe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        binding = ActivityRecipeDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mQueue = Volley.newRequestQueue(getApplicationContext());
        Intent intent = getIntent();
        recipeId = intent.getLongExtra("recipe_id", 0);
        if (recipeId>0) {
            loadRecipeDetail(recipeId);
        }
        else {
            handleError();
        }

        RecipeDatabase db = Room.databaseBuilder(getApplicationContext(), RecipeDatabase.class, "database-name").build();
        mDAO = db.cmDAO();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.recipe_detail_menu, menu);

        Executor thread = Executors.newSingleThreadExecutor();
        thread.execute(() ->
        {
            RecipeDetail detail =  mDAO.retrieve(recipeId);
            boolean isSaved = detail!=null;
            runOnUiThread( () -> menu.findItem(R.id.actionSave).setTitle(isSaved?"Remove":"Save"));
        });
         menu.findItem(R.id.actionSave).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
             @Override
             public boolean onMenuItemClick(@NonNull MenuItem item) {
                 Executor thread = Executors.newSingleThreadExecutor();
                 thread.execute(() ->
                 {
                     boolean isToSave= menu.findItem(R.id.actionSave).getTitle().equals(getString(R.string.save));
                     if(isToSave && recipe != null)
                     {
                         mDAO.createRecipe(recipe);
                     }
                     else {
                         mDAO.deleteMessage(recipe);
                     }
                     runOnUiThread( () -> menu.findItem(R.id.actionSave).setTitle(isToSave?getString(R.string.remove):getString(R.string.save)));
                 });
                 return false;
             }
         });

        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        mQueue.cancelAll(TAG);
    }

    private void loadRecipeDetail(long recipeId) {
        String url = String.format("https://api.spoonacular.com/recipes/%d/information?apiKey=%s",recipeId,"c918ec43605843bfbbb1e58441d40b7c");
        JsonObjectRequest request  = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                try {
                    recipe = new RecipeDetail(recipeId,
                            response.getString("title"),
                            response.getString("summary"),
                            response.getString("image"),
                            response.getString("instructions"));
                    binding.recipeTitle.setText(response.getString("title"));
                    binding.recipeSummary.setText(Html.fromHtml(response.getString("summary")));
                    binding.recipeInstructions.setText(Html.fromHtml(response.getString("instructions")));
                    Picasso.get().load(response.getString("image")).into(binding.recipeImage);

                } catch (JSONException e) {
                    handleError();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, error.toString());
                handleError();
            }
        });

        mQueue.add(request);
    }
    //Toast error and navigate back to previous activity
    private void handleError()
    {

    }

}