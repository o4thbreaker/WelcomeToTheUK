package com.example.welcometotheuk;

import android.content.Intent;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

import Data.DatabaseHandler;
import Model.VisitedPlaces;

public class LondonPlacesActivity extends AppCompatActivity {

    private boolean isVisited = false;
    private boolean isAddedToDB = false;
    private int placeId = -1;

    private String placeIdName = "";
    private String placeName = "";
    private VisitedPlaces place = null;

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

        final Spinner spinner = findViewById(R.id.place_spinner);

        DatabaseHandler databaseHandler = new DatabaseHandler(this);

        placesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHandler.deleteAll();
                List<VisitedPlaces> placesList = databaseHandler.getAllPlaces();

                for (VisitedPlaces place : placesList)
                {
                    Log.d("VisitedPlaces info: ", " ID: " + place.getId() + " , City: " + place.getCity()
                            + " , Name: " + place.getName() + " , isVisited: " + place.getIsVisited());
                }

                Log.d("info: ", "done");
            }
        });


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

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                placeName = parent.getItemAtPosition(position).toString();
                //Toast.makeText(LondonPlacesActivity.this, "Selected item: " + item, Toast.LENGTH_SHORT).show();

                try {
                    place = databaseHandler.getPlaceByName(placeName);
                } catch (CursorIndexOutOfBoundsException e) {
                    visitedCircleImage.setImageResource(R.drawable.unvisited_place_icon);
                    place = null;
                }


                if (place != null)
                {
                    if (place.getIsVisited() == 1)
                    {
                        visitedCircleImage.setImageResource(R.drawable.visited_place_icon);
                        isVisited = true;
                    }
                    else
                    {
                        visitedCircleImage.setImageResource(R.drawable.unvisited_place_icon);
                        isVisited = false;
                    }
                }
                /*else
                    isAddedToDB = false;*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Westminster Abbey");
        arrayList.add("London Eye");
        arrayList.add("Big Ben");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item, arrayList);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinner.setAdapter(adapter);

        markVisitedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<VisitedPlaces> placesList = databaseHandler.getAllPlaces();
                Log.d("id", "placeName: " + placeName);

                for (VisitedPlaces place : placesList)
                {
                    Log.d("VisitedPlaces info: ", " ID: " + place.getId() + " , City: " + place.getCity()
                            + " , Name: " + place.getName() + " , isVisited: " + place.getIsVisited());
                }

                if (place == null)
                {
                    VisitedPlaces placeToAdd = new VisitedPlaces("London", placeName, 1);
                    //placeIdName = placeToAdd.getName();
                    databaseHandler.addPlace(placeToAdd);
                    place = placeToAdd;

                    visitedCircleImage.setImageResource(R.drawable.visited_place_icon);
                    place.setVisited(1);
                    //isAddedToDB = true;
                }
                else
                {
                    //VisitedPlaces place = databaseHandler.getPlaceByName(placeName);
                    if (place.getIsVisited() == 1)
                    {
                        visitedCircleImage.setImageResource(R.drawable.unvisited_place_icon);
                        place.setVisited(0);
                    }
                    else
                    {
                        visitedCircleImage.setImageResource(R.drawable.visited_place_icon);
                        place.setVisited(1);
                    }

                    databaseHandler.updatePlace(place);
                }
            }

            void SetVisited(VisitedPlaces place)
            {
                place.setVisited(1);
                visitedCircleImage.setImageResource(R.drawable.visited_place_icon);

                /*if (place.getIsVisited() == 1)
                {
                    visitedCircleImage.setImageResource(R.drawable.visited_place_icon);
                }
                else
                {
                    Log.d("info: ", "isVisited");
                    visitedCircleImage.setImageResource(R.drawable.unvisited_place_icon);
                    VisitedPlaces place = databaseHandler.getPlaceByName(placeName);
                    place.setVisited(0);

                   *//* Log.d("VisitedPlaces info: ", " ID: " + place.getId() + " , City: " + place.getCity()
                            + " , Name: " + place.getName() + " , isVisited: " + place.getIsVisited());*//*

                    databaseHandler.updatePlace(place);
                }*/
            }
        });

    }
}