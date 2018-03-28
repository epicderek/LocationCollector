package domain.apptesting.derek.loccollector.db;

import static domain.apptesting.derek.loccollector.entity_relationship.LocConstants.*;
import domain.apptesting.derek.loccollector.entity_relationship.*;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * A class responsible for creating a competent sql database for the storage of information relevant to Place, that is the entity itself and its correlations.
 */
public class DBHandler extends SQLiteOpenHelper
{
    private static final String DATA_BASE_NAME = "location_data_base";
    private static final int DATA_BASE_VERSION = 1;

    public DBHandler(Context con)
    {
        super(con,DATA_BASE_NAME,null,DATA_BASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db)
    {
        String CREATE_LOC_TABLE = String.format("create table %s ( %s real unsigned, %s real unsigned, %s text, " +
                        "%s text, %s text, %s text, %s text, %s text, %s text, %s text, %s text, %s text, %s text, " +
                        "%s integer primary key autoincrement);",
                TABLE_LOC,KEY_LAT,KEY_LNG,KEY_PLACE_NAME,KEY_PLACE_TYPE,
                KEY_STREET_NUM,KEY_ROUTE,KEY_NEIGHBORHOOD,KEY_LOCALITY,KEY_ADMINISTRATIVE2,KEY_ADMINISTRATIVE1,
                KEY_COUNTRY,KEY_ZIP,KEY_STREET_ADDRESS,KEY_PLID);
        db.execSQL(CREATE_LOC_TABLE);
    }

    public void onOpen(SQLiteDatabase db)
    {
        super.onOpen(db);
        if(!db.isReadOnly())
            db.execSQL("PRAGMA foreign_keys = 1;");
    }

    public void onUpgrade(SQLiteDatabase db, int old, int ne)
    {
        db.execSQL("drop table if exists " + TABLE_LOC);
        onCreate(db);
    }

    /**
     * Browse the database to check for any existent Place identical to this Place within the tolerance of 0.0001 in both latitude and longitude.
     * @param lat The latitude of the Place.
     * @param lon The longitude of the Place.
     * @return A cursor that might contain the Place queried.
     */
    public Cursor dupSearch(double lat, double lon)
    {
        SQLiteDatabase db = getReadableDatabase();
        //The where statement in which the allowance of the difference in position is specified.
        String where = String.format("%s-(%s) <= ? and %s-(%s) <= ?",KEY_LAT,lat,KEY_LNG,lon);
        String[] allow = {String.valueOf(0.0001),String.valueOf(0.0001)};
        Cursor cur = db.query(TABLE_LOC,null,where,allow,null,null,null);
        return cur;
    }

    /**
     * Record this Place information in the sql database and return the loc_id of this Place. If the Place already exists in the database, the id of that Place is returned.
     * @param loc The Place to be recorded in the database.
     *@return The assigned place id of this place.
     */
    public long addPlace(Place loc)
    {
        Cursor cur = dupSearch((double)loc.getValueByField(KEY_LAT),(double)loc.getValueByField(KEY_LNG));
        //If the Place already exists in the database.
        if(cur.moveToNext())
            return cur.getLong(13);
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_LAT,(Double)loc.getValueByField(KEY_LAT));
        values.put(KEY_LNG,(Double)loc.getValueByField(KEY_LNG));
        values.put(KEY_PLACE_NAME,(String)loc.getValueByField(KEY_PLACE_NAME));
        values.put(KEY_PLACE_TYPE,(String)loc.getValueByField(KEY_PLACE_TYPE));
        values.put(KEY_STREET_NUM,(String)loc.getValueByField(KEY_STREET_NUM));
        values.put(KEY_ROUTE,(String)loc.getValueByField(KEY_ROUTE));
        values.put(KEY_NEIGHBORHOOD,(String)loc.getValueByField(KEY_NEIGHBORHOOD));
        values.put(KEY_LOCALITY,(String)loc.getValueByField(KEY_LOCALITY));
        values.put(KEY_ADMINISTRATIVE2,(String)loc.getValueByField(KEY_ADMINISTRATIVE2));
        values.put(KEY_ADMINISTRATIVE1,(String)loc.getValueByField(KEY_ADMINISTRATIVE1));
        values.put(KEY_COUNTRY,(String)loc.getValueByField(KEY_COUNTRY));
        values.put(KEY_ZIP,(String)loc.getValueByField(KEY_ZIP));
        values.put(KEY_STREET_ADDRESS,(String)loc.getValueByField(KEY_STREET_ADDRESS));
        loc.setFieldValue(KEY_PLID,db.insert(TABLE_LOC,null,values));
        return loc.getValueByField(KEY_PLID);
    }

    /**
     * Retrieve a Place from the database by the locational id.
     * @param id The id of the location to be retrieved.
     * @return The place correlated with this locational id. Null if no profile of such person with such id exists.
     */
    public Place getPlaceById(long id)
    {
        SQLiteDatabase db = getReadableDatabase();
        String where = String.format("%s = ?",KEY_PLID);
        String[] cons = {String.valueOf(id)};
        Cursor cur = db.query(TABLE_LOC,null,where,cons,null,null,null);
        Object[] que = new Object[14];
        if(cur.moveToNext())
        {
            //The longitude and latitude.
            que[0] = cur.getDouble(0);
            que[1] = cur.getDouble(1);
            //The rest of the information in string format.
            for(int i=2; i<13; i++)
                que[i] = cur.getString(i);
            que[13] = cur.getLong(13);
            return processPlace(que);
        }
        return null;
    }

    /**
     * Process a Place retrieved from the database.
     * @param vals The values returned by the query. All information must be present. The sequence must be as it is stored in the database.
     * @return The Place equivalent to the information extracted from the database.
     */
    private static Place processPlace(Object[] vals)
    {
        Map<String,Object> fvals = new HashMap<String,Object>();
        fvals.put(KEY_LAT,vals[0]);
        fvals.put(KEY_LNG,vals[1]);
        fvals.put(KEY_PLACE_NAME,vals[2]);
        fvals.put(KEY_PLACE_TYPE,vals[3]);
        fvals.put(KEY_STREET_NUM,vals[4]);
        fvals.put(KEY_ROUTE,vals[5]);
        fvals.put(KEY_NEIGHBORHOOD,vals[6]);
        fvals.put(KEY_LOCALITY,vals[7]);
        fvals.put(KEY_ADMINISTRATIVE2,vals[8]);
        fvals.put(KEY_ADMINISTRATIVE1,vals[9]);
        fvals.put(KEY_COUNTRY,vals[10]);
        fvals.put(KEY_ZIP,vals[11]);
        fvals.put(KEY_STREET_ADDRESS,vals[12]);
        fvals.put(KEY_PLID,vals[13]);
        return new Place(fvals);
    }

    /**
     * A helper method that fills an array in the order accepted by the processing of place retrieved from the database
     * in conjunction with another method that completes this process by the initialization of the Place object.
     * The cursor given in the argument must be in a position of a valid row in the location table.
     * @param cur The cursor whose current position is a valid row in the location table.
     * @return The array of objects of field values which can then be passed to the other helper method to
     * accomplish the reconstruction of this place object.
     */
    private Object[] fillFromRetrieval(Cursor cur)
    {
        Object[] que = new Object[14];
        que[0] = cur.getDouble(0);
        que[1] = cur.getDouble(1);
        //The rest of the information in string format.
        for(int i=2; i<13; i++)
            que[i] = cur.getString(i);
        que[13] = cur.getLong(13);
        return que;
    }


    /**
     * Retrieve and reconstruct all places under the given place name.
     * @param name The place name to be inquired.
     * @return The list of places under this place name.
     */
    public List<Place> getPlacesByName(String name)
    {
        SQLiteDatabase db = getReadableDatabase();
        List<Place> plcs = new LinkedList<>();
        String where = String.format("%s = ?",KEY_PLACE_NAME);
        String[] vals = {name};
        Cursor cur = db.query(TABLE_LOC,null,where,vals,null,null,null);
        while(cur.moveToNext())
            plcs.add(processPlace(fillFromRetrieval(cur)));
        return plcs;
    }

    /**
     * Get all the existent places in the location table of this database.
     * @return All places currently contaiend in the database.
     */
    public List<Place> getAllPlaces()
    {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cur = db.rawQuery("select * from " + TABLE_LOC,null);
        List<Place> pls = new LinkedList<>();
        Object[] que = new Object[14];
        while(cur.moveToNext())
            pls.add(processPlace(fillFromRetrieval(cur)));
        return pls;
    }


}
