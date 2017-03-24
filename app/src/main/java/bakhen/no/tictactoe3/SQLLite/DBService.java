package bakhen.no.tictactoe3.SQLLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBService extends SQLiteOpenHelper {

    private SQLiteDatabase database;
    public static final String DB_NAME = "sqlitedatabase.db";
    public static final int DATABASE_VERSION = 1;
    private String TABLE_PLAYER = "PLAYER";
    private String FIRST_COLUMN_ID = "ID";
    private String SECOND_COLUMN_USERNAME = "USERNAME";
    private String THIRD_COLUMN_DEFEATS = "DEFEATS";
    private String FOURTH_COLUMN_WINS = "WINS";

    public DBService(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists " + TABLE_PLAYER + " ("
                + FIRST_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + SECOND_COLUMN_USERNAME + " VARCHAR, "
                + THIRD_COLUMN_DEFEATS + " INTEGER, "
                + FOURTH_COLUMN_WINS + " INTEGER);");

        //Just creating som default data in the database for testing purposes
        db.execSQL("INSERT INTO " + TABLE_PLAYER
                + " VALUES(1,'Henrik',3,4);");

        db.execSQL("INSERT INTO " + TABLE_PLAYER
                + " VALUES(2,'Eirik',2,6);");

        db.execSQL("INSERT INTO " + TABLE_PLAYER
                + " VALUES(3,'Ane',4,4);");

        db.execSQL("INSERT INTO " + TABLE_PLAYER
                + " VALUES(4,'HenrikTicTacToeMaster',3,4);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYER + ";");
        onCreate(db);
    }

    public void insertPlayerIntoDatabase(Player player) {
        database = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SECOND_COLUMN_USERNAME, player.getUserName());
        contentValues.put(THIRD_COLUMN_DEFEATS, player.getLosses());
        contentValues.put(FOURTH_COLUMN_WINS, player.getWins());
        database.insert(TABLE_PLAYER, null, contentValues);
        database.close();
    }

    public void updatePlayerInDatabase(Player player) {
        database = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SECOND_COLUMN_USERNAME, player.getUserName());
        contentValues.put(THIRD_COLUMN_DEFEATS, player.getLosses());
        contentValues.put(FOURTH_COLUMN_WINS, player.getWins());
        database.update(TABLE_PLAYER, contentValues, FIRST_COLUMN_ID + " = ?", new String[]{player.getID()});
        database.close();
    }

    public void deletePlayer(Player player) {
        database = this.getReadableDatabase();
        database.delete(TABLE_PLAYER, FIRST_COLUMN_ID + " = ?", new String[]{player.getID()});
        database.close();
    }

    public ArrayList<Player> getAllPlayers() {
        database = this.getReadableDatabase();
        Cursor cursor = database.query(TABLE_PLAYER, null, null, null, null, null, null, null);
        ArrayList<Player> players = new ArrayList<>();
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                Player player = new Player(null,"",0,0);
                player.setID(cursor.getString(0));
                player.setUserName(cursor.getString(1));
                player.setLosses(cursor.getInt(2));
                player.setWins(cursor.getInt(3));
                players.add(player);
            }
        }
        cursor.close();
        database.close();
        return players;
    }

    public Player getPlayer(String username){
        database = this.getReadableDatabase();
        Cursor cursor = database.query(TABLE_PLAYER, null, null, null, null, null, null, null);
        if (cursor.getCount() > 0) {
            for(int i =0; i<cursor.getCount(); i++){
                cursor.moveToNext();
                if(cursor.getString(1).equals(username)){
                    Player player = new Player(null,"",0,0);
                    player.setID(cursor.getString(0));
                    player.setUserName(cursor.getString(1));
                    player.setLosses(cursor.getInt(2));
                    player.setWins(cursor.getInt(3));
                    return player;
                }
            }
        }
        return null;
    }


}
