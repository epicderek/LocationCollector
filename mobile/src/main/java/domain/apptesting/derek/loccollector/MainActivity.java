package domain.apptesting.derek.loccollector;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.util.List;

import domain.apptesting.derek.loccollector.db.DBHandler;
import domain.apptesting.derek.loccollector.entity_relationship.LocationStay;

public class MainActivity extends AppCompatActivity {
    private static final int GPS_PERMISSION = 0;
    /**
     * The google location service provider.
     */
    private FusedLocationProviderClient client;
    /**
     * The call back for the location service client.
     */
    private LocationCallback callback;
    /**
     * The locaiton request of this activity.
     */
    private LocationRequest req;
    /**
     * The database that stores the location information.
     */
    private DBHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DBHandler(this);
        //Ask for gps permission.
        if (ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, GPS_PERMISSION);
        else {
            client = LocationServices.getFusedLocationProviderClient(this);
            //Initializing call back behavior for database storage.
            callback = new LocationCallback() {
                public void onLocationResult(LocationResult result) {
                    if (result == null)
                        Toast.makeText(MainActivity.this, "Null Location Result", Toast.LENGTH_SHORT).show();
                    else {
                        Location currentLoc = result.getLocations().get(0);
                        db.updateStay(currentLoc.getTime(), currentLoc.getLatitude(), currentLoc.getLongitude());
                        List<LocationStay> stays = db.getCurrentStays();
                        Toast.makeText(MainActivity.this,stays.get(stays.size()-1).toString(),Toast.LENGTH_SHORT).show();
                    }
                }
            };
            req = LocationRequest.create();
            req.setInterval(10000);
            req.setFastestInterval(1000);
            req.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            client.requestLocationUpdates(req, callback, null);
        }
    }

    @SuppressLint("MissingPermission")
    public void onRequestPermissionsResult(int id, String[] perms, int[] results) {
        switch (id) {
            case GPS_PERMISSION: {
                client = LocationServices.getFusedLocationProviderClient(this);
                //Initializing call back behavior for database storage.
                callback = new LocationCallback() {
                    public void onLocationResult(LocationResult result) {
                        if (result == null)
                            Toast.makeText(MainActivity.this, "Null Location Result", Toast.LENGTH_SHORT).show();
                        else {
                            Location currentLoc = result.getLocations().get(0);
                            db.updateStay(currentLoc.getTime(), currentLoc.getLatitude(), currentLoc.getLongitude());
                            List<LocationStay> stays = db.getCurrentStays();
                            Toast.makeText(MainActivity.this,stays.get(stays.size()-1).toString(),Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                req = LocationRequest.create();
                req.setInterval(10000);
                req.setFastestInterval(1000);
                req.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                client.requestLocationUpdates(req, callback, null);
            }
        }

    }

    /**
     * Terminate the location processing.
     */
    protected void onPause() {
        super.onPause();
        //client.removeLocationUpdates(callback);
    }

}
