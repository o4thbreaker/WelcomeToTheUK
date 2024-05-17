package com.example.welcometotheuk;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.welcometotheuk.Retrofit.IRetrofitInterface;
import com.example.welcometotheuk.Retrofit.RetrofitBuilder;
import com.google.gson.JsonObject;
import retrofit2.Call;

import java.text.DecimalFormat;
import java.util.List;

import Data.DatabaseHandler;
import Model.VisitedPlaces;
import Utils.Util;
import retrofit2.Callback;
import retrofit2.Response;

public class LondonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_london);
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

        // CURRENCY CONVERTER BUTTON
        final EditText convertFrom = findViewById(R.id.GBPCurrencyValue);
        final TextView convertToValue = findViewById(R.id.convertToValue);
        final Spinner convertToSpinner = findViewById(R.id.convertToSpinner);

        String[] dropdownList = {"RUB", "USD", "EUR", "UAH", "GEL"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_currency_item, dropdownList);
        convertToSpinner.setAdapter(adapter);

        placesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LondonActivity.this, LondonPlacesActivity.class);
                startActivity(intent);
                finish();
            }
        });

        hotelsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LondonActivity.this, LondonHotelsActivity.class);
                startActivity(intent);
                finish();
            }
        });

        tripsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LondonActivity.this, LondonTripsActivity.class);
                startActivity(intent);
                finish();
            }
        });

        flightsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LondonActivity.this, LondonFlightsActivity.class);
                startActivity(intent);
                finish();
            }
        });

        backArrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LondonActivity.this, ChooseCityActivity.class);
                startActivity(intent);
                finish();
            }
        });

        convertToSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                IRetrofitInterface retrofitInterface = RetrofitBuilder.getRetrofitInstance().create(IRetrofitInterface.class);
                Call<JsonObject> call = retrofitInterface.getExchangeCurrency("GBP");
                call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        JsonObject res = response.body();
                        JsonObject rates = res.getAsJsonObject("conversion_rates");

                        Double currency = Double.valueOf(convertFrom.getText().toString());
                        Double multiplier = Double.valueOf(rates.get(convertToSpinner.getSelectedItem().toString()).toString());

                        Double result = currency * multiplier;

                        convertToValue.setText(String.valueOf(result));
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}