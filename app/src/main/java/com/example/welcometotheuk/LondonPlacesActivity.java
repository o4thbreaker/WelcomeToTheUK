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

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.config.Configuration;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay;
import org.osmdroid.wms.BuildConfig;

import java.util.ArrayList;
import java.util.List;

import Data.DatabaseHandler;
import Model.VisitedPlaces;

public class LondonPlacesActivity extends AppCompatActivity {

    private DatabaseHandler databaseHandler = null;
    private String placeName = "";
    private VisitedPlaces place = null;
    private MapView map = null;
    private Marker currentMarker = null;
    private RotationGestureOverlay mRotationGestureOverlay = null;

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

        // ACTIVITES BUTTONS
        final ImageView placesButton = findViewById(R.id.PlacesButton);
        final ImageView hotelsButton = findViewById(R.id.HotelsButton);
        final ImageView tripsButton = findViewById(R.id.TripsButton);
        final ImageView flightsButton = findViewById(R.id.FlightsButton);

        // BACK BUTTON
        final ImageView backArrowButton = findViewById(R.id.backArrow);

        // VISITED BUTTONS
        final LinearLayout markVisitedButton = findViewById(R.id.markVisitedButton);
        final ImageView visitedCircleImage = findViewById(R.id.visitedCircle);

        // HEADLINER BUTTON
        final Spinner spinner = findViewById(R.id.place_spinner);

        // DATABASE
        databaseHandler = new DatabaseHandler(this);

        // TODO: из-за моих кривых рук сначала ДОЛГОТА потом ШИРОТА (лень исправлять)
        AddPlaceToDB(databaseHandler, "Westminster Abbey",-0.1288624, 51.4993832);
        AddPlaceToDB(databaseHandler, "London Eye", -0.1220941, 51.5031897);
        AddPlaceToDB(databaseHandler, "Big Ben", -0.1272003, 51.5007325);

        // MAP
        ConfigureMap();

        placesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHandler.deleteAll();
                Log.d("info: ", "database cleared");

                LogDatabaseInfo();
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

                        IMapController mapController = map.getController();
                        mapController.setZoom(20.0);
                        GeoPoint point = new GeoPoint(place.getLatitude(), place.getLongitude());
                        deleteCurrentMarker();
                        setMarker(point);
                        mapController.setCenter(point);
                    }
                    else
                    {
                        visitedCircleImage.setImageResource(R.drawable.unvisited_place_icon);

                        IMapController mapController = map.getController();
                        mapController.setZoom(20.0);
                        GeoPoint point = new GeoPoint(place.getLatitude(), place.getLongitude());
                        deleteCurrentMarker();
                        setMarker(point);
                        mapController.setCenter(point);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        HandleAdapter(spinner);

        markVisitedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (place != null)
                {
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

                LogDatabaseInfo();
            }
        });
    }

    private void AddPlaceToDB(DatabaseHandler databaseHandler, String placeName, Double longitude, Double latitude)
    {
        VisitedPlaces placeToAdd = new VisitedPlaces("London", placeName, 0, longitude, latitude);

        try {
            databaseHandler.getPlaceByName(placeName);
        } catch (CursorIndexOutOfBoundsException e) {
            databaseHandler.addPlace(placeToAdd);
        }

    }

    private void LogDatabaseInfo()
    {
        List<VisitedPlaces> placesList = databaseHandler.getAllPlaces();

        for (VisitedPlaces place : placesList)
        {
            Log.d("VisitedPlaces info: ", " ID: " + place.getId() + " , City: " + place.getCity()
                    + " , Name: " + place.getName() + " , isVisited: " + place.getIsVisited() +
                    " , Longitude: " + place.getLongitude() + " , Latitude: " + place.getLatitude());
        }
    }

    private void HandleAdapter(Spinner spinner)
    {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Westminster Abbey");
        arrayList.add("London Eye");
        arrayList.add("Big Ben");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item, arrayList);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spinner.setAdapter(adapter);
    }

    private void ConfigureMap()
    {
        map = findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);

        Configuration.getInstance().setUserAgentValue(BuildConfig.LIBRARY_PACKAGE_NAME);
        Configuration.getInstance().setOsmdroidBasePath(getFilesDir());

        IMapController mapController = map.getController();
        mapController.setZoom(20.0);
        GeoPoint startPoint = new GeoPoint(51.4993832, -0.1288624);
        mapController.setCenter(startPoint);

        setMarker(startPoint);

        mRotationGestureOverlay = new RotationGestureOverlay(map);
        mRotationGestureOverlay.setEnabled(true);
        map.setMultiTouchControls(true);
        map.getOverlays().add(this.mRotationGestureOverlay);
    }

    private void setMarker(GeoPoint geoPoint)
    {
        Marker marker = new Marker(map);
        marker.setPosition(geoPoint);
        //marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER);
        marker.setIcon(getResources().getDrawable(R.drawable.map_place_icon, null));
        marker.setTitle("Start point");
        map.getOverlays().add(marker);
        currentMarker = marker;
    }

    private void deleteCurrentMarker()
    {
        if (currentMarker != null)
        {
            map.getOverlays().remove(currentMarker);
        }
    }
}