package algonquin.cst2335.androidfinalproject.song;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class RequestQueueSingleton {

    private static RequestQueueSingleton instance;
    private RequestQueue requestQueue;
    public static Context context;

    private RequestQueueSingleton(Context context) {
        this.context = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized RequestQueueSingleton getInstance(Context context) {
        if (instance == null) {
            instance = new RequestQueueSingleton(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            // Create a new instance of RequestQueue using Volley.newRequestQueue()
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    // Method to add a request to the request queue
    public <T> void addToRequestQueue(com.android.volley.Request<T> req) {
        getRequestQueue().add(req);
    }
}
