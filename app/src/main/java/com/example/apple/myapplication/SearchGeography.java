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

public class SearchGeography extends AppCompatActivity {

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
        setContentView(R.layout.activity_search_geography);

        //set user input to flight input variables

        flightCarrierInput = findViewById(R.id.flightCarrierInput);

        flightNumberInput = findViewById(R.id.flightNumberInput);

        yearInput = findViewById(R.id.yearInput);

        monthInput = findViewById(R.id.monthInput);

        dayInput = findViewById(R.id.dayInput);

        //set submit button
        submitButton = findViewById(R.id.submitButton);

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
                startAPICall();
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
                            getGeography(response);
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

    public void getGeography(final JSONObject response) {
        try {
            JSONObject appendix = response.getJSONObject("appendix");
            JSONArray airports = appendix.getJSONArray("airports");
            JSONObject departureInfo = airports.getJSONObject(0);

            //set departure airport full name
            String departureAirport = departureInfo.getString("name");
            final TextView departAirportName = findViewById(R.id.departAirportName);
            departAirportName.setText(departureAirport);

            //set departure airport country
            String departCountryName = "Country: " + departureInfo.getString("countryName");
            final TextView departCountry = findViewById(R.id.departCountry);
            departCountry.setText(departCountryName);

            //set departure airport city
            String departCityName = "City: " + departureInfo.getString("city");
            final TextView departCity = findViewById(R.id.departCity);
            departCity.setText(departCityName);

            //set departure airport latitude
            double departLatitude = departureInfo.getDouble("latitude");
            String departureLatitude = "Latitude: " + String.valueOf(departLatitude);
            final TextView deLatitude = findViewById(R.id.departLatitude);
            deLatitude.setText(departureLatitude);

            //set departure airport longitude
            double departLongitude = departureInfo.getDouble("longitude");
            String departureLongitude = "Longitude: " + String.valueOf(departLongitude);
            final TextView deLongitude = findViewById(R.id.departLongitude);
            deLongitude.setText(departureLongitude);

            //set departure airport elevation
            double departElevation = departureInfo.getDouble("elevationFeet");
            String departureElevation = "Airport Elevation(In Feet): " + String.valueOf(departElevation);
            final TextView deElevation = findViewById(R.id.departElevation);
            deElevation.setText(departureElevation);


            JSONObject arrivalInfo = airports.getJSONObject(1);

            //set arrival airport full name
            String arrivalAirport = arrivalInfo.getString("name");
            final TextView arriveName = findViewById(R.id.arriveAirportName);
            arriveName.setText(arrivalAirport);

            //set arrival airport country
            String arriveCountryName = "Country: " + arrivalInfo.getString("countryName");
            final TextView arriveCountry = findViewById(R.id.arriveCountry);
            arriveCountry.setText(arriveCountryName);

            //set arrival airport city
            String arriveCityName = "City: " + arrivalInfo.getString("city");
            final TextView arriveCity = findViewById(R.id.arriveCity);
            arriveCity.setText(arriveCityName);

            //set arrival airport latitude
            double arriveLatitude = arrivalInfo.getDouble("latitude");
            String arrivalLatitude = "Latitude: " + String.valueOf(arriveLatitude);
            final TextView arrLatitude = findViewById(R.id.arriveLatitude);
            arrLatitude.setText(arrivalLatitude);

            //set arrival airport longitude
            double arriveLongitude = arrivalInfo.getDouble("longitude");
            String arrivalLongitude = "Longitude: " + String.valueOf(arriveLongitude);
            final TextView arrLongitude = findViewById(R.id.arriveLongitude);
            arrLongitude.setText(arrivalLongitude);

            //set arrival airport elevation
            double arriveElevation = arrivalInfo.getDouble("elevationFeet");
            String arrivalElevation = "Airport Elevation(In Feet): " + String.valueOf(arriveElevation);
            final TextView arrElevation = findViewById(R.id.arriveElevation);
            arrElevation.setText(arrivalElevation);

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
