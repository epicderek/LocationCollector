package domain.apptesting.derek.loccollector.utils;

import org.json.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Common utilities for Java Script Object Notation processing, of conversion to native map representation of JSON Object and native list representation of JSON Array,
 * of a JSON Writer which constructs JSON Objects and JSON Arrays from native data types, string, primitive value, and list.
 * Convenient methods regarding JSON Files are also included such as the parsing of an irregular json file that is not a JSON Object, and certain constructions of JSON Files.
 */
public class JsonUtils
{

    /**
     * Parse a json string in the format of a json object into a native map entity.
     * @param json The string which specifies a json object.
     * @return A map representation of this json object.
     * @throws JSONException
     */
    public static Map<String,Object> parseJsonString(String json) throws JSONException
    {
        Map<String,Object> output = new HashMap<>();
        JSONObject obj = new JSONObject(json);
        return parseJsonObject(obj);
    }

    /**
     * Parse a a json object into a native map entity.
     * @param obj The json object to be parsed.
     * @return A map representation of this json object.
     * @throws JSONException
     */
    public static Map<String,Object> parseJsonObject(JSONObject obj) throws JSONException
    {
        Map<String,Object> output = new HashMap<>();
        Iterator<String> ite = obj.keys();
        while(ite.hasNext())
        {
            String key = ite.next();
            Object item = obj.get(key);
            if(item instanceof JSONObject)
                output.put(key,parseJsonObject((JSONObject)item));
            else if (item instanceof JSONArray)
                output.put(key,parseJsonArray((JSONArray)item));
            else
                output.put(key,item);
        }
        return output;
    }

    /**
     * Parse a json array into the native equivalents supported data types of json values, ex. json object to map, json array to list, double to double, etc.
     * @param arr The json array to be parsed.
     * @return A native list representation of the contents of this json array.
     * @throws JSONException
     */
    public static List<Object> parseJsonArray(JSONArray arr) throws JSONException
    {
        List<Object> vals = new ArrayList<>();
        for(int i=0; i<arr.length(); i++)
        {
            Object ent = arr.get(i);
            if(ent instanceof JSONObject)
                vals.add(parseJsonObject((JSONObject)ent));
            else if(ent instanceof JSONArray)
                vals.add(parseJsonArray((JSONArray)ent));
            else
                vals.add(ent);
        }
        return vals;
    }

    /**
     * A helper method that parses an irregular JSON File of parallel none-nested JSON Objects into individual JSON Objects,
     * which are subsequently returned. This file is correctly formatted in lines, upon which one line can only contain
     * a "{" or "}."
     * @param text The irregular JSON File to be parsed.
     * @return An array of valid JSON Objects.
     * @throws IOException
     */
    public static JSONArray parseIrreJsonFile(File text) throws IOException, JSONException
    {
        BufferedReader reader = new BufferedReader(new FileReader(text));
        int counter1 = 0, counter2 = 0;
        List<String> jsons = new LinkedList<String>();
        StringBuilder builder = new StringBuilder();
        String line;
        Pattern test = Pattern.compile("[\\w{}\\[\\]]");
        while((line=reader.readLine())!=null)
        {
            Matcher mat = test.matcher(line);
            if(!mat.find())
                continue;
            if(line.contains("{"))
                counter1++;
            else if(line.contains("}"))
                counter2++;
            builder.append(line);
            if(counter1==counter2)
            {
                jsons.add(builder.toString());
                builder = new StringBuilder();
                counter1 = 0;
                counter2 = 0;
            }
        }
        reader.close();
        JSONArray objs = new JSONArray();
        for(String holder: jsons)
            objs.put(new JSONObject(holder));
        return objs;
    }

}
