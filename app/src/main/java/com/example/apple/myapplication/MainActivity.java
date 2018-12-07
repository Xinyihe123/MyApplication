package com.example.apple.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    //API key with ninghan2@illinoie.edu, 100 calls initially. (tested)
    private String APIKey = "36b1a4-a52bb5";

    //Second key with ivan.nzhong@gmail.com
    private String APIKey1 = "ba85c3-d60fd9";

    /** Default logging tag for messages from the main activity. */
    private static final String TAG = "Lab12:Main";


    /** Request queue for our network requests. */
    private static RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set up a queue for our Volley requests
        requestQueue = Volley.newRequestQueue(this);

        // Load the main layout for our activity
        setContentView(R.layout.activity_main);

        //Configure the button for search departure and arrival
        configureSearchDepartArr();

        //configure the button for search aircraft information
        configureSearchAircraft();

        //configure the button for search geography information
        configureGeographyInfo();
    }

    /**
     * A method that configures the button linked search departure and arrival information
     */
    private void configureSearchDepartArr() {
        Button searchDepartArriv = findViewById(R.id.searchDeparetureArrival);
        searchDepartArriv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SearchMenu.class));
            }
        });
    }

    /**
     * A method that configures the button linked to search aircraft information
     */
    private void configureSearchAircraft() {
        Button searchAircraft = findViewById(R.id.searchAircraft);
        searchAircraft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SearchAircraft.class));
            }
        });
    }

    /**
     * A method that configures the button linked to search geography information
     */
    private void configureGeographyInfo() {
        Button searchGeography = findViewById(R.id.searchGeography);
        searchGeography.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SearchGeography.class));
            }
        });
    }




}
