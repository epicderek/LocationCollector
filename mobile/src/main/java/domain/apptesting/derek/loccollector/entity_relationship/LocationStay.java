package domain.apptesting.derek.loccollector.entity_relationship;

import static domain.apptesting.derek.loccollector.entity_relationship.LocConstants.*;

/**
 * This class represents the relationship of a Place to a finite duration of time in which the place is occasioned by the user. This LocationStay
 * Object should only serve as a product from the database in that it ought only be retrieved and then applied.
 */
public class LocationStay extends Entity
{
    /**
     * Construct a LocationStay object retrieved from the database.
     * @param startTime The starting time of this stay.
     * @param endTime The ending time of this stay.
     * @param duration the duration of this stay.
     * @param loc_id The id of the location visited by the user.
     * @param stay_id The auto-generated id of this stay from the database.
     */
    public LocationStay(long startTime, long endTime, long duration, long loc_id, long stay_id)
    {
        setFieldValue(KEY_START_TIME,startTime);
        setFieldValue(KEY_END_TIME,endTime);
        setFieldValue(KEY_DURATION,duration);
        setFieldValue(KEY_PLID,loc_id);
        setFieldValue(KEY_STAY_ID,stay_id);
    }

    public String toString()
    {
        return String.format("%s Stay from %s to %s\nof %s\nat %s",
                getValueByField(KEY_STAY_ID),
                getValueByField(KEY_START_TIME),
                getValueByField(KEY_END_TIME),
                getValueByField(KEY_DURATION),
                getValueByField(KEY_PLID));
    }


}
