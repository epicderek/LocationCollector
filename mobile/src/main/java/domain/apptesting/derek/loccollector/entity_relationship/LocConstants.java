package domain.apptesting.derek.loccollector.entity_relationship;

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
    public static final String KEY_STREET_NUM = "street number";
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
    public static final String TABLE_LOC = "location_table";
}
