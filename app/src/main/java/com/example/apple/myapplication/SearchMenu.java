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

    //Set the text view of flight departure information
    //final TextView flightDepartInfo = findViewById(R.id.flightDepartInfo);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set up a queue for our Volley requests
        requestQueue = Volley.newRequestQueue(this);

        // Load the main layout for our activity
        setContentView(R.layout.activity_search_menu);

        //set user input to flight input variables

        flightCarrierInput = findViewById(R.id.flightCarrierInput);

        flightNumberInput = findViewById(R.id.flightNumberInput);

        yearInput = findViewById(R.id.yearInput);

        monthInput = findViewById(R.id.monthInput);

        dayInput = findViewById(R.id.dayInput);

        //set submit button
        submitButton = findViewById(R.id.submitButton);

        //Set the text view of flight departure information
        final TextView flightDepartInfo = findViewById(R.id.flightDepartInfo);

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
                startAPICall();
                //flightDepartInfo.setText("asdsadfasfdasef");
            }
        });
        //Configure the return home button
        configurereturnHome();
    }


    /**
     * Make an API call.
     */
    void startAPICall() {
        try {
            //final String ret;
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET,
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
                            getAirports(response);
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

    public void getAirports(final JSONObject response) {
        try {
            JSONObject appendix = response.getJSONObject("appendix");
            JSONArray airports = appendix.getJSONArray("airports");
            JSONObject departureInfo = airports.getJSONObject(0);

            //set departure airport full name
            String departureAirport = departureInfo.getString("name");
            final TextView flightDepartInfo = findViewById(R.id.flightDepartInfo);
            flightDepartInfo.setText(departureAirport);

            //set departure airport code
            String departureAirportC = departureInfo.getString("iata");
            final TextView departureAirportCode = findViewById(R.id.departAirportCode);
            departureAirportCode.setText(departureAirportC);

            //get Departure and Arrival Time
            JSONArray flightStatuses = response.getJSONArray("flightStatuses");
            JSONObject depArrTime = flightStatuses.getJSONObject(0);

            //set estimated departure time
            JSONObject departureDate = depArrTime.getJSONObject("departureDate");
            String dateLocal = departureDate.getString("dateLocal");
            String[] departString = dateLocal.split("T");
            String departTime = departString[1];
            departTime = departTime.substring(0,5);
            final TextView departureTime = findViewById(R.id.departTime);
            departureTime.setText(departTime);

            //get arrival airport information
            JSONObject arrivalInfo = airports.getJSONObject(1);

            //set arrival airport full name
            String arrivalAirport = arrivalInfo.getString("name");
            final TextView flightArrivalInfo = findViewById(R.id.arrivalInfo);
            flightArrivalInfo.setText(arrivalAirport);

            //set arrival airport code
            String arrivalAirportC = arrivalInfo.getString("iata");
            final TextView arrivalAirportCode = findViewById(R.id.arriveAirportCode);
            arrivalAirportCode.setText(arrivalAirportC);

            //set estimated arrival time
            JSONObject arrivalDate = depArrTime.getJSONObject("arrivalDate");
            String dateLocalA = arrivalDate.getString("dateLocal");
            String[] arrivalString = dateLocalA.split("T");
            String arrivalTime = arrivalString[1];
            arrivalTime = arrivalTime.substring(0,5);
            final TextView arriveTime = findViewById(R.id.arriveTime);
            arriveTime.setText(arrivalTime);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //return home page button configuration
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