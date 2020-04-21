package com.example.gameclientapi;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;



public class MyVolleyRequest
{
    int value = 5;
    private static MyVolleyRequest instance;
    private RequestQueue requestQueue;
    private Context context;
    private iVolley iVolley;

    private MyVolleyRequest(Context context, iVolley iVolley)
    {
        this.context = context;
        this.iVolley=iVolley;
        requestQueue = getRequestQueue();
    }

    private MyVolleyRequest(Context context)
    {
        this.context = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized  MyVolleyRequest getInstance(Context context)
    {
        instance = new MyVolleyRequest(context);
        return instance;
    }

    public static synchronized  MyVolleyRequest getInstance(Context context, iVolley iVolley)
    {
        instance = new MyVolleyRequest(context, iVolley);
        return instance;
    }

    private RequestQueue getRequestQueue()
    {
        if(requestQueue == null)
        {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public<T> void addToRequestQueue(Request<T> request)
    {
        getRequestQueue().add(request);
    }

    //GET REQUEST
    public void getRequest(String url)
    {
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>()
       {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        iVolley.onResponse(response.toString());
                    }
                }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                iVolley.onResponse(error.getMessage());
            }
        });

        addToRequestQueue(getRequest);
    }




    /*
    String url = "https://gameserverapirachellouise.azurewebsites.net/api/GameServerItems/";
    StringRequest postRequest = new StringRequest(Request.Method.POST,
            url,
            new Response.Listener<String>()
            {
                @Override
                public void onResponse(String response) {
                    // response
                    Log.d("Response", response);
                }
            },
            new Response.ErrorListener()
            {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // error
                    Log.d("Error.Response", response);
                }
            }
    ) {
        @Override
        protected Map<String, String> getParams()
        {
            Map<String, String>  params = new HashMap<String, String>();
            params.put("name", "Alif");
            params.put("domain", "http://itsalif.info");

            return params;
        }
    };
queue.add(postRequest);



    public void postRequest(String url,String gameName, String playstation4, String xboxOne, String switchc, String pc, final double rating )
    {
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest sr = new StringRequest(Request.Method.POST,"http://api.someservice.com/post/comment", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("gameName","MonsterHunter");
                params.put("playstation4", "Available");
                params.put("xboxOne", "Available");
                params.put("switch", "Available");
                params.put("rating",String.valueOf(rating));

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/json;charset=utf-8");
                return params;
            }
        };
        queue.add(sr);

    }

    public interface PostCommentResponseListener {
        public void requestStarted();
        public void requestCompleted();
        public void requestEndedWithError(VolleyError error);
    }
*/
}
