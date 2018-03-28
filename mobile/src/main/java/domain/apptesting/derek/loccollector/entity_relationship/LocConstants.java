package domain.apptesting.derek.loccollector.entity_relationship;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * The relevant constants associated with the description of the location itself or its storage in the database.
 */
public class LocConstants
{
    /*
    All relevant information of a Place.
     */
    public static final String KEY_LAT = "latitude";
    public static final String KEY_LNG = "longitude";
    public static final String KEY_PLACE_NAME = "place_name";
    public static final String KEY_PLACE_TYPE = "place_type";
    public static final String KEY_STREET_NUM = "street_number";
    public static final String KEY_ROUTE = "route";
    public static final String KEY_NEIGHBORHOOD = "neighborhood";
    public static final String KEY_LOCALITY = "locality";
    public static final String KEY_ADMINISTRATIVE2 = "county";
    public static final String KEY_ADMINISTRATIVE1 = "state";
    public static final String KEY_COUNTRY = "country";
    public static final String KEY_ZIP = "zip";
    public static final String KEY_STREET_ADDRESS = "street_address";
    public static final String KEY_PLID = "plid";
    public static final Set<String> PLACE_KEYS = new HashSet<>();

     /*
    Storage in the database. The column names are same as the field names above.
     */

    /**
     * Table of locations.
     */
    public static final String TABLE_LOC = "location";
    /**
     * The table that contains the time correlated with each location. The specific information is shown in the column names of this table.
     */
    public static final String TABLE_LOC_STAY = "location_stay";
    /*
    The columns of the location stay table, omitted the place id. They are also considered to be added to the Place fields.
     */
    public static final String KEY_START_TIME = "start_time";
    public static final String KEY_END_TIME = "end_time";
    public static final String KEY_DURATION = "duration";
    public static final String KEY_STAY_ID = "stay_id";

    static
    {
        Collections.addAll(PLACE_KEYS,KEY_LAT,KEY_LNG,KEY_PLACE_NAME,KEY_PLACE_TYPE,KEY_STREET_NUM,KEY_ROUTE,KEY_NEIGHBORHOOD,KEY_LOCALITY,KEY_ADMINISTRATIVE2,KEY_ADMINISTRATIVE1,KEY_COUNTRY,KEY_ZIP,KEY_STREET_ADDRESS,KEY_PLID,KEY_START_TIME,KEY_END_TIME,KEY_DURATION,KEY_STAY_ID);
    }


}