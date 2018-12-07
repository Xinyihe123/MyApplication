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

public class SearchAircraft extends AppCompatActivity {

    //API key with ninghan2@illinoie.edu, 100 calls initially. (tested)
    private String APIKey = "36b1a4-a52bb5";

    //Second key with ivan.nzhong@gmail.com
    private String APIKey1 = "ba85c3-d60fd9";

    /** Default logging tag for messages from the main activity. */
    private static final String TAG = "Lab12:Main";

    String flightCarrier;

    String flightNumber;

    String year;

    String month;

    String day;

    //user input for flight information
    EditText flightCarrierInput;

    EditText flightNumberInput;

    EditText yearInput;

    EditText monthInput;

    EditText dayInput;

    Button submitButton;

    /** Request queue for our network requests. */
    private static RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set up a queue for our Volley requests
        requestQueue = Volley.newRequestQueue(this);

        // Load the main layout for our activity
        setContentView(R.layout.activity_search_aircraft);

        //set user input to flight input variables

        flightCarrierInput = findViewById(R.id.flightCarrierInput);

        flightNumberInput = findViewById(R.id.flightNumberInput);

        yearInput = findViewById(R.id.yearInput);

        monthInput = findViewById(R.id.monthInput);

        dayInput = findViewById(R.id.dayInput);

        //set submit button
        submitButton = findViewById(R.id.submitButton);

        //Set the text view of flight departure information
        final TextView aircraftInfo = findViewById(R.id.aircraftInfo);

        //set input to flight number variable by using a listener
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flightCarrier = flightCarrierInput.getText().toString();
                flightNumber = flightNumberInput.getText().toString();
                year = yearInput.getText().toString();
                month = monthInput.getText().toString();
                day = dayInput.getText().toString();
                Log.d(TAG, "Submit button clicked");
                //JSONObject arrival = null;
                startAPICall(aircraftInfo);
            }
        });

        //Configure the return home button -- Not Working yet!!!
        configurereturnHome();

    }



    /**
     * Make an API call.
     */
    void startAPICall(final TextView aircraftInfo) {
        try {
            //final String ret;
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    //"https://aviation-edge.com/v2/public/flights?key=[36b1a4-a52bb5]&flightIata=" + flightNumber
                    //"https://api.flightstats.com/flex/flightstatus/rest/v2/json/flight/status/AA/2211/arr/2018/12/8?appId=600c5e62&appKey=7b8ea8d4e5ccda50a5f3991e11e58f47&utc=false"
                    //"https://api.flightstats.com/flex/flightstatus/rest/v2/json/flight/status/AA/2211/dep/2018/12/8?appId=600c5e62&appKey=7b8ea8d4e5ccda50a5f3991e11e58f47&utc=false"
                    //"https://api.flightstats.com/flex/flightstatus/rest/v2/json/flight/status/"
                    //                            + flightCarrier + "/" + flightNumber + "/dep/" + year
                    //                            + "/" + month + "/" + day + "?appId=600c5e62&appKey=7b8ea8d4e5ccda50a5f3991e11e58f47&utc=false"
                    "https://api.flightstats.com/flex/flightstatus/rest/v2/json/flight/status/"
                            + flightCarrier + "/" + flightNumber + "/dep/" + year
                            + "/" + month + "/" + day + "?appId=600c5e62&appKey=7b8ea8d4e5ccda50a5f3991e11e58f47&utc=false",
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
                                aircraftInfo.setText(icaoCode);

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
