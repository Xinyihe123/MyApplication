package com.example.apple.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class SearchMenu extends AppCompatActivity {

    //API key with ninghan2@illinoie.edu, 100 calls initially. (tested)
    private String APIKey = "36b1a4-a52bb5";

    /** Default logging tag for messages from the main activity. */
    private static final String TAG = "Lab12:Main";

    String flightNumber;

    EditText flightNumberInput;

    Button submitButton;

    /** Request queue for our network requests. */
    private static RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set up a queue for our Volley requests
        requestQueue = Volley.newRequestQueue(this);

        // Load the main layout for our activity
        setContentView(R.layout.activity_search_menu);

        //set user input to flightNumberInput variable
        flightNumberInput = findViewById(R.id.flightNumberInput);

        //set submit button
        submitButton = findViewById(R.id.submitButton);

        //Set the text view of flight departure information
        final TextView flightDepartInfo = findViewById(R.id.flightDepartInfo);

        //set input to flight number variable by using a listener
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flightNumber = flightNumberInput.getText().toString();
                Log.d(TAG, "Submit button clicked");
                //JSONObject arrival = null;
                startAPICall(flightDepartInfo);
            }
        });

        //Configure the return home button -- Not Working yet!!!
        configurereturnHome();

    }



    /**
     * Make an API call.
     */
    void startAPICall(final TextView flightDepartInfo) {
        try {
            //final String ret;
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    //"https://aviation-edge.com/v2/public/flights?key=[36b1a4-a52bb5]&flightIata=" + flightNumber
                    //"https://universities.hipolabs.com/search?name=" + flightNumber
                    "https://aviation-edge.com/v2/public/flights?key=36b1a4-a52bb5&flightIata=" + flightNumber,
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(final JSONObject response) {
                            Log.d(TAG, response.toString());
                            try {
                                JSONArray arr = new JSONArray(response);
                                //get departure info -- icaoCode
                                JSONObject departure = arr.getJSONObject(2);
                                String icaoCode = departure.getString("icaoCode");
                                flightDepartInfo.setText(icaoCode);

                            } catch (Exception e) {
                                Log.e("MYAPP", "unexpected JSON exception", e);
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(final VolleyError error) {
                    Log.w(TAG, error.toString());
                }
            });
            requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void configurereturnHome() {
        Button returnHome = findViewById(R.id.returnHome);
        returnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
