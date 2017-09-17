package com.example.theo.fishbowlapp.MySLQiteHelpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

public class MySQLiteHelperGameSettings extends SQLiteOpenHelper {

    private static final String TABLE_GAME_SETTINGS = "game_settings";  // GameSettings table name

    /* game_settings Table Columns names */
    private static final String KEY_ID = "id";
    private static final String KEY_TEAMS_NUM = "teams_num";
    private static final String KEY_WORDS_PER_PL = "words_per_pl";
    private static final String KEY_SECONDS = "seconds";

    private static int flag = 0;

    private static final String[] COLUMNS = {KEY_ID, KEY_TEAMS_NUM, KEY_WORDS_PER_PL, KEY_SECONDS};

    /* Database Version */
    private static final int DATABASE_VERSION = 1;

    /* Database Name */
    private static final String DATABASE_NAME = "FishBowlDB";

    public MySQLiteHelperGameSettings(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /* SQL statement to create game_settings table */
        String CREATE_GAME_SETTINGS_TABLE = "CREATE TABLE game_settings ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "teams_num INTEGER, "+
                "words_per_pl INTEGER, "+
                "seconds INTEGER )";

        /* create game_settings table */
        db.execSQL(CREATE_GAME_SETTINGS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /* Drop older game_settings table if existed */
        db.execSQL("DROP TABLE IF EXISTS game_settings");

        /* create fresh game_settings table */
        this.onCreate(db);
    }

    @Override
    protected void finalize() throws Throwable {
        this.close();
        super.finalize();
    }

    public void dropTable(){
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DROP TABLE IF EXISTS game_settings");
        onCreate(db);
    }


    public void addGameSettings(GameSettings game_settings){
        //for logging
        Log.d("addGameSettings", game_settings.toString());

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_TEAMS_NUM, game_settings.getTeamsNum()); // get game_settings name
        values.put(KEY_WORDS_PER_PL, game_settings.getWordsPerPl()); // get game_settings color
        values.put(KEY_SECONDS, game_settings.getSeconds()); // get game_settings points

        // 3. insert
        db.insert(TABLE_GAME_SETTINGS, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    public GameSettings getGameSettings(int id){

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(TABLE_GAME_SETTINGS, // a. table
                        COLUMNS, // b. column names
                        " id = ?", // c. selections
                        new String[] { String.valueOf(id) }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // 4. build game_settings object
        GameSettings game_settings = new GameSettings();
        game_settings.setId(Integer.parseInt(cursor.getString(0)));
        game_settings.setTeamsNum(Integer.parseInt(cursor.getString(1)));
        game_settings.setWordsPerPl(Integer.parseInt(cursor.getString(2)));
        game_settings.setSeconds(Integer.parseInt(cursor.getString(3)));

        cursor.close();
        //log
        Log.d("getAllGameSettings("+id+")", game_settings.toString());

        // 5. return game_settings
        return game_settings;
    }

    public List<GameSettings> getAllGameSettings() {
        List<GameSettings> game_settings = new LinkedList<GameSettings>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_GAME_SETTINGS;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build game_settings and add it to list
        GameSettings gm = null;
        if (cursor.moveToFirst()) {
            do {
                gm = new GameSettings();
                gm.setId(Integer.parseInt(cursor.getString(0)));
                gm.setTeamsNum(Integer.parseInt(cursor.getString(1)));
                gm.setWordsPerPl(Integer.parseInt(cursor.getString(2)));
                gm.setSeconds(Integer.parseInt(cursor.getString(3)));

                // Add game_settings to game_settings
                game_settings.add(gm);
            } while (cursor.moveToNext());
        }

        cursor.close();
        Log.d("getAllGameSettings()", game_settings.toString());

        // return game_settings
        return game_settings;
    }

    /*
    public int updateTeam(GameSettings game_settings) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("name", game_settings.getName()); // get name
        values.put("color", game_settings.getColor()); // get color
        values.put("points", game_settings.getPoints()); // get points

        // 3. updating row
        int i = db.update(TABLE_GAME_SETTINGS, //table
                values, // column/value
                KEY_ID+" = ?", // selections
                new String[] { String.valueOf(game_settings.getId()) }); //selection args

        // 4. close
        db.close();
        return i;
    }

    public void deleteTeam(GameSettings game_settings) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_GAME_SETTINGS, //table name
                KEY_ID+" = ?",  // selections
                new String[] { String.valueOf(game_settings.getId()) }); //selections args

        // 3. close
        db.close();

        //log
        Log.d("deleteTeam", game_settings.toString());
    }*/

}