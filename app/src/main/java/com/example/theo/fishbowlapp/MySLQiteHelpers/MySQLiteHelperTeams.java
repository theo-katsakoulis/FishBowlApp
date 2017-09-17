package com.example.theo.fishbowlapp.MySLQiteHelpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

public class MySQLiteHelperTeams extends SQLiteOpenHelper {

    private static final String TABLE_TEAMS = "teams";  // Teams table name

    /* teams Table Columns names */
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_COLOR = "color";
    private static final String KEY_POINTS = "points";
    private static final String KEY_MISTAKES_ROUND = "mistakes_round";
    private static final String KEY_FOUND_ROUND = "found_round";
    private static final String KEY_MISTAKES_OVERALL = "mistakes_overall";
    private static final String KEY_FOUND_OVERALL = "found_overall";

    private static int flag = 0;

    private static final String[] COLUMNS = {KEY_ID, KEY_NAME, KEY_COLOR, KEY_POINTS, KEY_MISTAKES_ROUND, KEY_FOUND_ROUND, KEY_MISTAKES_OVERALL, KEY_FOUND_OVERALL};

    /* Database Version */
    private static final int DATABASE_VERSION = 1;

    /* Database Name */
    private static final String DATABASE_NAME = "FishBowlDB2";

    public MySQLiteHelperTeams(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /* SQL statement to create teams table */
        String CREATE_TEAMS_TABLE = "CREATE TABLE teams ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, "+
                "color TEXT, "+
                "points INTEGER, " +
                "mistakes_round INTEGER, " +
                "found_round INTEGER, " +
                "mistakes_overall INTEGER," +
                "found_overall INTEGER )";

        /* create teams table */
        db.execSQL(CREATE_TEAMS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /* Drop older teams table if existed */
        db.execSQL("DROP TABLE IF EXISTS teams");

        /* create fresh teams table */
        this.onCreate(db);
    }

    @Override
    protected void finalize() throws Throwable {
        this.close();
        super.finalize();
    }

    public void dropTable(){
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DROP TABLE IF EXISTS teams");
        onCreate(db);
    }


    public void addTeam(Teams team){
        //for logging
        Log.d("addTeam", team.toString());

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, team.getName()); // get team name
        values.put(KEY_COLOR, team.getColor()); // get team color
        values.put(KEY_POINTS, team.getPoints()); // get team points
        values.put(KEY_MISTAKES_ROUND, team.getMistakesRound());
        values.put(KEY_FOUND_ROUND, team.getFoundRound());
        values.put(KEY_MISTAKES_OVERALL, team.getMistakesOverall());
        values.put(KEY_FOUND_OVERALL, team.getFoundOverall());

        // 3. insert
        db.insert(TABLE_TEAMS, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    public Teams getTeamById(int id){

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(TABLE_TEAMS, // a. table
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

        // 4. build team object
        Teams team = new Teams();
        team.setId(Integer.parseInt(cursor.getString(0)));
        team.setName(cursor.getString(1));
        team.setColor(cursor.getString(2));
        team.setPoints(Integer.parseInt(cursor.getString(3)));
        team.setMistakesRound(Integer.parseInt(cursor.getString(4)));
        team.setFoundRound(Integer.parseInt(cursor.getString(5)));
        team.setMistakesOverall(Integer.parseInt(cursor.getString(6)));
        team.setFoundOverall(Integer.parseInt(cursor.getString(7)));

        cursor.close();
        //log
        Log.d("getTeam("+id+")", team.toString());

        // 5. return team
        return team;
    }

    public Teams getTeamByName(String team_name) {

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(TABLE_TEAMS, // a. table
                        COLUMNS, // b. column names
                        " name = ?", // c. selections
                        new String[]{(team_name)}, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // 4. build team object
        Teams team = new Teams();
        team.setId(Integer.parseInt(cursor.getString(0)));
        team.setName(cursor.getString(1));
        team.setColor(cursor.getString(2));
        team.setPoints(Integer.parseInt(cursor.getString(3)));
        team.setMistakesRound(Integer.parseInt(cursor.getString(4)));
        team.setFoundRound(Integer.parseInt(cursor.getString(5)));
        team.setMistakesOverall(Integer.parseInt(cursor.getString(6)));
        team.setFoundOverall(Integer.parseInt(cursor.getString(7)));

        cursor.close();
        //log
        Log.d("getTeam(" + team_name + ")", team.toString());

        // 5. return team
        return team;
    }

    public List<Teams> getAllTeams() {
        List<Teams> teams = new LinkedList<Teams>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_TEAMS;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build team and add it to list
        Teams team = null;
        if (cursor.moveToFirst()) {
            do {
                team = new Teams();
                team.setId(Integer.parseInt(cursor.getString(0)));
                team.setName(cursor.getString(1));
                team.setColor(cursor.getString(2));
                team.setPoints(Integer.parseInt(cursor.getString(3)));
                team.setMistakesRound(Integer.parseInt(cursor.getString(4)));
                team.setFoundRound(Integer.parseInt(cursor.getString(5)));
                team.setMistakesOverall(Integer.parseInt(cursor.getString(6)));
                team.setFoundOverall(Integer.parseInt(cursor.getString(7)));

                // Add team to teams
                teams.add(team);
            } while (cursor.moveToNext());
        }
        cursor.close();

        Log.d("getAllTeams()", teams.toString());

        // return teams
        return teams;
    }

    /* returns the number of words that are in the table */
    public int getTeamsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_TEAMS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        Log.d("getAllTeamsCount()", String.valueOf(cnt));
        return cnt;
    }

    public int updateTeam(Teams team) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("name", team.getName()); // get name
        values.put("color", team.getColor()); // get color
        values.put("points", team.getPoints()); // get points
        values.put("mistakes_round", team.getMistakesRound());
        values.put("found_round", team.getFoundRound());
        values.put("mistakes_overall", team.getMistakesOverall());
        values.put("found_overall", team.getFoundOverall());

        // 3. updating row
        int i = db.update(TABLE_TEAMS, //table
                values, // column/value
                KEY_ID+" = ?", // selections
                new String[] { String.valueOf(team.getId()) }); //selection args

        // 4. close
        db.close();
        return i;
    }
    
}