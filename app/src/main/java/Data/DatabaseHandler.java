package Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import Model.VisitedPlaces;
import Utils.Util;

public class DatabaseHandler extends SQLiteOpenHelper {

    public DatabaseHandler(Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_VISITEDPLACES_TABLE = "CREATE TABLE " + Util.TABLE_NAME + " ("
                + Util.KEY_ID + " INTEGER PRIMARY KEY, "
                + Util.KEY_CITY + " TEXT, "
                + Util.KEY_NAME + " TEXT, "
                + Util.KEY_ISVISITED + " INTEGER" + " )";

        db.execSQL(CREATE_VISITEDPLACES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Util.TABLE_NAME);
        onCreate(db);
    }

    public void addPlace(VisitedPlaces places) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Util.KEY_CITY, places.getCity());
        contentValues.put(Util.KEY_NAME, places.getName());
        contentValues.put(Util.KEY_ISVISITED, places.getIsVisited());

        db.insert(Util.TABLE_NAME, null, contentValues);
        db.close();
    }

    public VisitedPlaces getPlaceById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Util.TABLE_NAME, new String[] {Util.KEY_ID, Util.KEY_CITY, Util.KEY_NAME, Util.KEY_ISVISITED},
                Util.KEY_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        VisitedPlaces places = new VisitedPlaces(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), Integer.parseInt(cursor.getString(3)));
        return places;
    }

    public VisitedPlaces getPlaceByName(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Util.TABLE_NAME, new String[] {Util.KEY_ID, Util.KEY_CITY, Util.KEY_NAME, Util.KEY_ISVISITED},
                Util.KEY_NAME + "=?", new String[] {name}, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        VisitedPlaces places = new VisitedPlaces(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), Integer.parseInt(cursor.getString(3)));
        return places;
    }


    public List<VisitedPlaces> getAllPlaces()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        List<VisitedPlaces> placesList = new ArrayList<>();
        String selectAllPlaces = "Select * from " + Util.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectAllPlaces, null);

        if (cursor.moveToFirst())
        {
            do {
                VisitedPlaces places = new VisitedPlaces();
                places.setId(Integer.parseInt(cursor.getString(0)));
                places.setCity(cursor.getString(1));
                places.setName(cursor.getString(2));
                places.setVisited(Integer.parseInt(cursor.getString(3)));

                placesList.add(places);
            } while (cursor.moveToNext());
        }

        return placesList;
    }

    public int updatePlace(VisitedPlaces places)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Util.KEY_CITY, places.getCity());
        contentValues.put(Util.KEY_NAME, places.getName());
        contentValues.put(Util.KEY_ISVISITED, places.getIsVisited());

       return db.update(Util.TABLE_NAME, contentValues, Util.KEY_ID + "=?", new String[] {String.valueOf(places.getId())});
    }

}
