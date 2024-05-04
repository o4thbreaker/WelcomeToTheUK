package com.example.welcometotheuk;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

import Data.DatabaseHandler;
import Model.VisitedPlaces;

public class LondonPlacesActivity extends AppCompatActivity {

    private boolean isVisited = false;
    private boolean isAddedToDB = false;
    private int placeId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_london_places);
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

        hotelsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LondonPlacesActivity.this, LondonHotelsActivity.class);
                startActivity(intent);
                finish();
            }
        });

        tripsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LondonPlacesActivity.this, LondonTripsActivity.class);
                startActivity(intent);
                finish();
            }
        });

        flightsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LondonPlacesActivity.this, LondonFlightsActivity.class);
                startActivity(intent);
                finish();
            }
        });

        backArrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LondonPlacesActivity.this, ChooseCityActivity.class);
                startActivity(intent);
                finish();
            }
        });

        markVisitedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<VisitedPlaces> placesList = databaseHandler.getAllPlaces();
                Log.d("id", "placeId: " + placeId);

                for (VisitedPlaces place : placesList)
                {
                    Log.d("VisitedPlaces info: ", " ID: " + place.getId() + " , City: " + place.getCity()
                            + " , Name: " + place.getName() + " , isVisited: " + place.getIsVisited());
                }

                if (!isVisited)
                {
                    visitedCircleImage.setImageResource(R.drawable.visited_place_icon);

                    if (!isAddedToDB)
                    {
                        VisitedPlaces placeToAdd = new VisitedPlaces("London", "WestminsterAbbey", 1);
                        Log.d("info: ", "!isVisited + !isAddedToDB");
                        placeId = placeToAdd.getId(); // sets to 0 for some reason
                        Log.d("id", "placeID: " + placeId);
                        databaseHandler.addPlace(placeToAdd);
                        isAddedToDB = true;
                    }
                    else
                    {
                        Log.d("info: ", "!isVisited + isAddedToDB");
                        VisitedPlaces place = databaseHandler.getPlace(placeId);
                        place.setVisited(1);
                        databaseHandler.updatePlace(place);
                    }

                    isVisited = true;
                }
                else
                {
                    Log.d("info: ", "isVisited");
                    visitedCircleImage.setImageResource(R.drawable.unvisited_place_icon);

                    //VisitedPlaces place = databaseHandler.getPlace(placeId);
                    //place.setVisited(0);

                    //Log.d("VisitedPlaces info: ", " ID: " + place.getId() + " , City: " + place.getCity()
                    //        + " , Name: " + place.getName() + " , isVisited: " + place.getIsVisited());
                    //databaseHandler.updatePlace(place);

                    isVisited = false;
                }
            }
        });

    }
}