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

        //Configure the return home button -- Not Working yet!!!
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
                            getAircraftInfo(response);
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

    public void getAircraftInfo(final JSONObject response) {
        try {
            JSONObject appendix = response.getJSONObject("appendix");
            JSONArray equipments = appendix.getJSONArray("equipments");
            JSONObject equipentInfo = equipments.getJSONObject(0);

            //set name
            String planeName = equipentInfo.getString("name");
            final TextView planeN = findViewById(R.id.name);
            planeN.setText(planeName);

            //set turboProp
            String turboProp = equipentInfo.getString("turboProp");
            final TextView turboP = findViewById(R.id.turbo);
            turboP.setText(turboProp);

            //set jet
            String jet = equipentInfo.getString("jet");
            final TextView jetInfo = findViewById(R.id.jet);
            jetInfo.setText(jet);

            //set widebody
            String widebody = equipentInfo.getString("widebody");
            final TextView wideBody = findViewById(R.id.widebody);
            wideBody.setText(widebody);

            //set regional
            String regional = equipentInfo.getString("regional");
            final TextView regionalInfo = findViewById(R.id.reigonal);
            regionalInfo.setText(regional);

            //set tail number
            JSONArray flightStatue = response.getJSONArray("flightStatuses");
            JSONObject flightStatuses = flightStatue.getJSONObject(0);
            JSONObject flightEquipment = flightStatuses.getJSONObject("flightEquipment");
            String tailNumber = flightEquipment.getString("tailNumber");
            final TextView tailN = findViewById(R.id.tailNumber);
            tailN.setText(tailNumber);

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
