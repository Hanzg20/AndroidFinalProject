package algonquin.cst2335.androidfinalproject.sunLookup.API;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

public class VolleyRequestHandler {

    private Context context;
    private VolleyRequestQueue VolleyRequestQueueSingleton;


    public VolleyRequestHandler(Context context) {
        this.context = context;
    }

    public void getData(String url, final VolleyResponseListener listener) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listener.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onError(error.getMessage());
                    }
                });

        VolleyRequestQueueSingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }


    public interface VolleyResponseListener {

        void onSuccess(JSONObject response);


        void onError(String errorMessage);
    }
}
