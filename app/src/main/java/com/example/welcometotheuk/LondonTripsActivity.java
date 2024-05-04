package com.example.welcometotheuk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LondonTripsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_london_trips);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        final ImageView placesButton = findViewById(R.id.PlacesButton);
        final ImageView hotelsButton = findViewById(R.id.HotelsButton);
        final ImageView tripsButton = findViewById(R.id.TripsButton);
        final ImageView flightsButton = findViewById(R.id.FlightsButton);

        final ImageView backArrowButton = findViewById(R.id.backArrow);

        placesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LondonTripsActivity.this, LondonPlacesActivity.class);
                startActivity(intent);
                finish();
            }
        });

        hotelsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LondonTripsActivity.this, LondonHotelsActivity.class);
                startActivity(intent);
                finish();
            }
        });

        tripsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LondonTripsActivity.this, LondonTripsActivity.class);
                startActivity(intent);
                finish();
            }
        });

        flightsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LondonTripsActivity.this, LondonFlightsActivity.class);
                startActivity(intent);
                finish();
            }
        });

        backArrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LondonTripsActivity.this, ChooseCityActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}