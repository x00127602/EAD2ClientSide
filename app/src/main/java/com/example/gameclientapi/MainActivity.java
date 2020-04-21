package com.example.gameclientapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.util.Log;
import com.google.gson.*;
import com.android.volley.*;
import com.android.volley.toolbox.*;
import com.google.gson.reflect.TypeToken;

import android.os.Bundle;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    // uri of RESTful service on Azure, note: https, cleartext support disabled by default
    private String SERVICE_URI = "https://gameserverapirachellouise.azurewebsites.net/api/GameServerItems";          // or https
    private String TAG = "helloworldvolleyclient";
    private RequestQueue requestQueue;

    Button btn_get;
    EditText editText_get_byID;

    Button btn_post;
    EditText editText_set_gameName, editText_set_ps4, editText_set_xb1, editText_set_switch, editText_set_pc, editText_set_rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        btn_get = (Button) findViewById(R.id.btn_get_byID);
        editText_get_byID = (EditText) findViewById(R.id.et_get_byID);

        btn_get.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {
                                           MyVolleyRequest.getInstance(MainActivity.this, new iVolley() {
                                               @Override
                                               public void onResponse(String response) {
                                                   Toast.makeText(MainActivity.this, "" + response, Toast.LENGTH_SHORT).show();
                                               }
                                           })
                                                   .getRequest("https://gameserverapirachellouise.azurewebsites.net/api/GameServerItems/" + editText_get_byID.getText());
                                       }
                                   }
        );

        btn_post = (Button) findViewById(R.id.btn_post);
        editText_set_gameName= (EditText) findViewById(R.id.et_set_gameName);
        editText_set_ps4= (EditText) findViewById(R.id.et_set_ps4);
        editText_set_xb1= (EditText) findViewById(R.id.et_set_xb1);
        editText_set_switch= (EditText) findViewById(R.id.et_set_switch);
        editText_set_pc= (EditText) findViewById(R.id.et_set_pc);
        editText_set_rating= (EditText) findViewById(R.id.et_set_rating);


        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double value = Double.parseDouble(editText_set_rating.getText().toString());
                String data = "{" +

                        //Working Perfectly
                        "\"gameName\"" + ":" + "\"" +  editText_set_gameName.getText().toString() + "\"" + "," +
                        "\"playstation4\"" + ":" + "\"" +  editText_set_ps4.getText().toString() + "\"" + "," +
                        "\"xboxOne\"" + ":" + "\"" +  editText_set_xb1.getText().toString() + "\"" + "," +
                        "\"switch\"" + ":" + "\"" +  editText_set_switch.getText().toString() + "\"" + "," +
                        "\"pc\"" + ":" + "\"" +  editText_set_pc.getText().toString() + "\"" + "," +
                        "\"rating\"" + ":" +  value + "}";

                        /*Attempt 1
                        This does not
                        "\"gameName\"" + "\"" + editText_set_gameName.getText().toString() + "\"," +
                        "\"playstation4\"" + "\"" + editText_set_ps4.getText().toString() + "\"," +
                        "\"xboxOne\"" + "\"" + editText_set_xb1.getText().toString() + "\"," +
                        "\"switch\"" + "\"" + editText_set_switch.getText().toString() + "\"," +
                        "\"pc\"" + "\"" + editText_set_pc.getText().toString() + "\"," +
                        "\"rating\"" + "\"" + editText_set_rating.getText() + "\"" +

                         */
                        /*Attempt 2
                        This does not work either alot of breaking down thestring to find out
                        "\"gameName\"" + "\"" + editText_set_gameName.getText().toString() + "\","+
                        "\"playstation4\"" + "\"" + editText_set_gameName.getText().toString() + "\","+
                        "\"xboxOne\"" + "\"" + editText_set_gameName.getText().toString() + "\","+
                        "\"switch\"" + "\"" + editText_set_gameName.getText().toString() + "\","+
                        "\"pc\"" + "\"" + editText_set_pc.getText().toString() + "\","+
                        "\"rating\":2.2}";
                        */
                        //"}";

                //Attempt 3
                //This works but just puts strings in as the variables
                        /*
                        "\"gameName\":\"string\"," +
                        "\"playstation4\":\"string\"," +
                        "\"xboxOne\":\"string\"," +
                        "\"switch\":\"string\"," +
                        "\"pc\":\"string\"," +
                        "\"rating\":2.2}";
                         */

                //Attempt4
//                        //This works but we need to get rating right now
//                        "\"gameName\"" + ":" + "\"" +  editText_set_gameName.getText().toString() + "\"" + "," +
//                        "\"playstation4\"" + ":" + "\"" +  editText_set_ps4.getText().toString() + "\"" + "," +
//                        "\"xboxOne\"" + ":" + "\"" +  editText_set_xb1.getText().toString() + "\"" + "," +
//                        "\"switch\"" + ":" + "\"" +  editText_set_switch.getText().toString() + "\"" + "," +
//                        "\"pc\"" + ":" + "\"" +  editText_set_pc.getText().toString() + "\"" + "," +
//                        "\"rating\":2.2}";



                Submit(data);
            }
        });


        // floating action button, call the service
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callService(view);
            }
        });
    }

    private void Submit(String data)
    {
        final String savedata= data;
        String URL="https://gameserverapirachellouise.azurewebsites.net/api/GameServerItems/";

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject objres=new JSONObject(response);
                    Toast.makeText(getApplicationContext(),objres.toString(),Toast.LENGTH_LONG).show();



                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),"Server Error",Toast.LENGTH_LONG).show();

                }
                //Log.i("VOLLEY", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

                //Log.v("VOLLEY", error.toString());
            }
        })


        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                // Removed this line if you dont need it or Use application/json
                params.put("Content-Type", "application/json");
                return params;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return savedata == null ? null : savedata.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    //Log.v("Unsupported Encoding while trying to get the bytes", data);
                    return null;
                }
            }

        };


        requestQueue.add(stringRequest);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return super.onOptionsItemSelected(item);
    }

    // call RESTful service using volley and display results
    public void callService(View v) {
        // get TextView for displaying result
        final TextView outputTextView = (TextView) findViewById(R.id.outputTextView);

        try {
            // make a string request (JSON request an alternative)
            RequestQueue queue = Volley.newRequestQueue(this);
            Log.d(TAG, "Making request");
            try {
                StringRequest strObjRequest = new StringRequest(Request.Method.GET, SERVICE_URI,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // parse resulting string containing JSON to Greeting object
                                Greeting[] greeting = new Gson().fromJson(response, Greeting[].class);
                                outputTextView.setText(greeting.toString());
                                Log.d(TAG, "Displaying data" + greeting.toString());
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                outputTextView.setText(error.toString());
                                Log.d(TAG, "Error" + error.toString());
                            }
                        });
                queue.add(strObjRequest);           // can have multiple in a queue, and can cancel
            } catch (Exception e1) {
                Log.d(TAG, e1.toString());
                outputTextView.setText(e1.toString());
            }
        } catch (Exception e2) {
            Log.d(TAG, e2.toString());
            outputTextView.setText(e2.toString());
        }
    }

}