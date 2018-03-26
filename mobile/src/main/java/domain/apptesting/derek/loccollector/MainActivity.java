package domain.apptesting.derek.loccollector;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.*;

import domain.apptesting.derek.loccollector.utils.JsonUtils;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try
        {
            test();
        }catch(JSONException ex)
        {
            throw new RuntimeException(ex);
        }
    }

    public void test() throws JSONException
    {
        String json = "{\"Derek\":{\"age\":10,\"id\":[1,2,3,{\"life\":\"what\"}]},\"Matthew\":5}";
        JSONObject obj = new JSONObject(json);
        JSONArray arr = obj.getJSONObject("Derek").getJSONArray("id");
        for(int i=0; i<arr.length(); i++)
        {
            if(i!=3)
                Log.e("idNum",arr.getString(0));
            else
                Log.e("idObj",arr.getJSONObject(3).toString());
        }
        Log.e("Get?","Haha!!!");
        Log.e("To be", JsonUtils.parseJsonString(json).toString());
    }
}
