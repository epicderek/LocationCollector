package domain.apptesting.derek.loccollector.entity_relationship;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * An Item is a basic element in a stream.
 * This class is the base class of all type of personal data items in PrivacyStream.
 */

public class Entity
{

    /**
     * The timestamp when the Item is created.
     */

    public static final String TIME_CREATED = "time_created";

    private Map<String, Object> itemMap;

    public Entity() {
        this(new HashMap<String, Object>());
    }

    public Entity(Map<String, Object> itemMap)
    {
        this.itemMap = itemMap;
        this.setFieldValue(TIME_CREATED, System.currentTimeMillis());
    }

    public Map<String, Object> getItemMap() {
        return this.itemMap;
    }

    public JSONObject toJson() {
        return new JSONObject(this.itemMap);
    }

    public String toString() {
        return this.itemMap.toString();
    }

    /**
     * Get the value of a field in the entity.
     *
     * @param fieldName the name of the field
     * @param <TValue>  the type of field value
     * @return the field value
     */
    public <TValue> TValue getValueByField(String fieldName)
    {
        if (itemMap.containsKey(fieldName))
            return (TValue)itemMap.get(fieldName);
        else return null;
    }

    /**
     * Set a value to a field in the entity.
     * @param fieldName the name of field
     * @param value     the value of field
     * @param <TValue>  the type of field value
     */
    public <TValue> void setFieldValue(String fieldName, TValue value) {
        this.itemMap.put(fieldName, value);
    }

    /**
     * Test if current item contains a entity.
     * @param fieldName the field name to test
     * @return true if the item contains the field, otherwise false
     */
    public boolean containsField(String fieldName) {
        return this.itemMap.containsKey(fieldName);
    }

    /**
     * Include certain fields for output
     * the included fields will be available for output, while other fields will not.
     * @param fieldKeys the names of fields to include
     */
    public void includeFields(String... fieldKeys)
    {
        HashSet<String> fieldKeysToRemove = new HashSet<>();
        fieldKeysToRemove.addAll(this.itemMap.keySet());
        fieldKeysToRemove.removeAll(Arrays.asList(fieldKeys));
        this.itemMap.keySet().removeAll(fieldKeysToRemove);
    }

    /**
     * Exclude some fields from current item
     * the excluded fields will not be available for output
     *
     * @param fieldKeys the names of fields to exclude
     */
    public void excludeFields(String... fieldKeys)
    {
        HashSet<String> fieldKeysToRemove = new HashSet<>(Arrays.asList(fieldKeys));
        this.itemMap.keySet().removeAll(fieldKeysToRemove);
    }

    public boolean equals(Entity anotherEntity)
    {
        return this.itemMap.equals(anotherEntity.getItemMap());
    }

    public void createGraphNode()
    {

    }

    public void createEntity(Map<String, Object> itemMap)
    {
        this.itemMap = itemMap;
        this.setFieldValue(TIME_CREATED, System.currentTimeMillis());
    }

}
