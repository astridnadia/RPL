package com.tbash.tablereservation;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import android.content.Context;

public class VolleySingleton {
    private static VolleySingleton instance;
    private RequestQueue requestQueue;
    private static Context context;

    private VolleySingleton(Context context) {
        this.context = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized VolleySingleton getInstance(Context context) {
        if (instance == null) {
            instance = new VolleySingleton(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            // Get the application context
            context = context.getApplicationContext();
            // Create a new RequestQueue
            requestQueue = Volley.newRequestQueue(context);
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> request) {
        getRequestQueue().add(request);
    }
}
