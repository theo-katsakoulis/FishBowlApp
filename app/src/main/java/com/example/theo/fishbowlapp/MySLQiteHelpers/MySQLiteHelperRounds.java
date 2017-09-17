package com.example.theo.fishbowlapp.MySLQiteHelpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

public class MySQLiteHelperRounds extends SQLiteOpenHelper {

    private static final String TABLE_ROUNDS = "rounds";  // Rounds table name

    /* round Table Columns names */
    private static final String KEY_ID = "id";
    private static final String KEY_ROUND_NUM = "round_num";
    private static final String KEY_TEAM_PLAYING = "team_playing";

    private static final String[] COLUMNS = {KEY_ID, KEY_ROUND_NUM, KEY_TEAM_PLAYING};

    /* Database Version */
    private static final int DATABASE_VERSION = 1;

    /* Database Name */
    private static final String DATABASE_NAME = "FishBowlDB4";

    public MySQLiteHelperRounds(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /* SQL statement to create round table */
        String CREATE_ROUNDS_TABLE = "CREATE TABLE rounds ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "round_num INTEGER, "+
                "team_playing INTEGER )";

        /* create round table */
        db.execSQL(CREATE_ROUNDS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /* Drop older round table if existed */
        db.execSQL("DROP TABLE IF EXISTS rounds");

        /* create fresh round table */
        this.onCreate(db);
    }

    @Override
    protected void finalize() throws Throwable {
        this.close();
        super.finalize();
    }

    public void dropTable(){
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DROP TABLE IF EXISTS rounds");
        onCreate(db);
    }


    public void addRound(Rounds round){
        //for logging
        Log.d("addRound", round.toString());

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_ROUND_NUM, round.getRoundNum()); // get round name
        values.put(KEY_TEAM_PLAYING, round.getTeamPlaying()); // get round color

        // 3. insert
        db.insert(TABLE_ROUNDS, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    public Rounds getRound(int id){

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(TABLE_ROUNDS, // a. table
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

        // 4. build round object
        Rounds round = new Rounds();
        round.setId(Integer.parseInt(cursor.getString(0)));
        round.setRoundNum(Integer.parseInt(cursor.getString(1)));
        round.setTeamPlaying(Integer.parseInt(cursor.getString(2)));

        cursor.close();
        //log
        Log.d("getRounds("+id+")", round.toString());

        // 5. return round
        return round;
    }

    public List<Rounds> getAllRounds() {
        List<Rounds> round = new LinkedList<Rounds>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_ROUNDS;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build round and add it to list
        Rounds gm = null;
        if (cursor.moveToFirst()) {
            do {
                gm = new Rounds();
                gm.setId(Integer.parseInt(cursor.getString(0)));
                gm.setRoundNum(Integer.parseInt(cursor.getString(1)));
                gm.setTeamPlaying(Integer.parseInt(cursor.getString(2)));

                // Add round to round
                round.add(gm);
            } while (cursor.moveToNext());
        }
        cursor.close();

        Log.d("getAllRounds()", round.toString());

        // return round
        return round;
    }

    public int updateRound(Rounds round) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("round_num", round.getRoundNum()); // get name
        values.put("team_playing", round.getTeamPlaying()); // get color

        // 3. updating row
        int i = db.update(TABLE_ROUNDS, //table
                values, // column/value
                KEY_ID+" = ?", // selections
                new String[] { String.valueOf(round.getId()) }); //selection args

        // 4. close
        db.close();
        return i;
    }

    /*
    public void deleteTeam(Rounds round) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_GAME_SETTINGS, //table name
                KEY_ID+" = ?",  // selections
                new String[] { String.valueOf(round.getId()) }); //selections args

        // 3. close
        db.close();

        //log
        Log.d("deleteTeam", round.toString());
    }*/

}