package com.example.welcometotheuk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import Data.DatabaseHandler;
import Model.VisitedPlaces;

public class LondonHotelsActivity extends AppCompatActivity {

    private boolean isVisited = false;
    private boolean isAddedToDB = false;
    private int placeId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_london_hotels);
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

        final LinearLayout markVisitedButton = findViewById(R.id.markVisitedButton);
        final ImageView visitedCircleImage = findViewById(R.id.visitedCircle);

        DatabaseHandler databaseHandler = new DatabaseHandler(this);

        placesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LondonHotelsActivity.this, LondonPlacesActivity.class);
                startActivity(intent);
                finish();
            }
        });

        hotelsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LondonHotelsActivity.this, LondonHotelsActivity.class);
                startActivity(intent);
                finish();
            }
        });

        tripsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LondonHotelsActivity.this, LondonTripsActivity.class);
                startActivity(intent);
                finish();
            }
        });

        flightsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LondonHotelsActivity.this, LondonFlightsActivity.class);
                startActivity(intent);
                finish();
            }
        });

        backArrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LondonHotelsActivity.this, ChooseCityActivity.class);
                startActivity(intent);
                finish();
            }
        });

        markVisitedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isVisited)
                {
                    visitedCircleImage.setImageResource(R.drawable.visited_place_icon);

                    if (!isAddedToDB)
                    {
                        VisitedPlaces placeToAdd = new VisitedPlaces("London", "SmartCamden", 1, -0.1420144, 51.5368398);
                        placeId = placeToAdd.getId();
                        databaseHandler.addPlace(placeToAdd);
                        isAddedToDB = true;
                    }
                    else
                    {
                        VisitedPlaces place = databaseHandler.getPlaceById(placeId);
                        place.setVisited(1);
                        databaseHandler.updatePlace(place);
                    }

                    isVisited = true;
                }
                else
                {
                    visitedCircleImage.setImageResource(R.drawable.unvisited_place_icon);

                    VisitedPlaces place = databaseHandler.getPlaceById(placeId);
                    place.setVisited(0);
                    databaseHandler.updatePlace(place);

                    isVisited = false;
                }
            }
        });

    }
}