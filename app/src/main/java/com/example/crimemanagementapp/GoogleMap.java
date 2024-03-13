package com.example.crimemanagementapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.maps.android.heatmaps.HeatmapTileProvider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GoogleMap extends AppCompatActivity implements OnMapReadyCallback {

    private final int FINE_PERMISSION_CODE = 1;
    private com.google.android.gms.maps.GoogleMap myMap;
    private SearchView mapSearchView;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    public LatLng userLocation;
    private HeatmapTileProvider mProvider;
    private List<LatLng> mHeatmapData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map);

        mapSearchView = findViewById(R.id.mapSearch);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();

        mapSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                String location = mapSearchView.getQuery().toString();
                List<Address> addressList = null;

                if (location != null){
                    Geocoder geocoder = new Geocoder(GoogleMap.this);

                    try {
                        addressList = geocoder.getFromLocationName(location, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Address address = addressList.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    myMap.addMarker(new MarkerOptions().position(latLng).title(location));
                    myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.mapNone) {
            myMap.setMapType(com.google.android.gms.maps.GoogleMap.MAP_TYPE_NONE);
        }

        if (id == R.id.mapNormal) {
            myMap.setMapType(com.google.android.gms.maps.GoogleMap.MAP_TYPE_NORMAL);
        }

        if (id == R.id.mapSatellite) {
            myMap.setMapType(com.google.android.gms.maps.GoogleMap.MAP_TYPE_SATELLITE);
        }

        if (id == R.id.mapHybrid) {
            myMap.setMapType(com.google.android.gms.maps.GoogleMap.MAP_TYPE_HYBRID);
        }

        if (id == R.id.mapTerrain) {
            myMap.setMapType(com.google.android.gms.maps.GoogleMap.MAP_TYPE_TERRAIN);
        }


        return super.onOptionsItemSelected(item);
    }

    private void getLastLocation() {

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_PERMISSION_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;

                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                    mapFragment.getMapAsync(GoogleMap.this);
                }
            }
        });
    }

    @Override
    public void onMapReady(@NonNull com.google.android.gms.maps.GoogleMap googleMap) {

        myMap = googleMap;

        // Initialize heatmap data
        mHeatmapData = generateHeatmapData();

        // Create a HeatmapTileProvider
        mProvider = new HeatmapTileProvider.Builder()
                .data(mHeatmapData)
                .build();

        // Add a tile overlay to the map
        myMap.addTileOverlay(new TileOverlayOptions().tileProvider(mProvider));

        LatLng sydney = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        MarkerOptions options = new MarkerOptions().position(sydney).title("Your Location");
        myMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        myMap.addMarker(options);
        myMap.getUiSettings().setZoomControlsEnabled(true);
        myMap.getUiSettings().setCompassEnabled(true);

        // Search for nearby police stations
        String policeStation = "police";
        String url = getUrl(currentLocation.getLatitude(), currentLocation.getLongitude(), policeStation);
        Object[] dataTransfer = new Object[2];
        dataTransfer[0] = myMap;
        dataTransfer[1] = url;
        // Create instance of GetNearbyPlacesData
        GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData(myMap, "AIzaSyAEeJy0X-NH5eXep12V9pr695Kj1ZRY2c8");


    }

    private List<LatLng> generateHeatmapData() {
        List<LatLng> heatmapData = new ArrayList<>();
        // Add your heatmap data points here
        heatmapData.add(new LatLng(37.775, -122.419));
        heatmapData.add(new LatLng(37.775, -122.418));
        heatmapData.add(new LatLng(37.775, -122.417));
        heatmapData.add(new LatLng(-0.303099,36.080027));
        heatmapData.add(new LatLng(-1.286389, 36.817223));
        heatmapData.add(new LatLng( -4.043477, 39.668206));
        heatmapData.add(new LatLng(-0.091702, 34.767956));
        heatmapData.add(new LatLng( 0.521459, 35.269788));
        heatmapData.add(new LatLng(-3.217000, 40.116753));
        heatmapData.add(new LatLng(-2.271573,40.902156));
        heatmapData.add(new LatLng (-0.717305, 36.431601));
        heatmapData.add(new LatLng(-4.280632, 39.593889));
        heatmapData.add(new LatLng (0.046582, 37.647392));
        // Add more data points as needed
        return heatmapData;
    }

    // Method to get URL for nearby places API request
    private String getUrl(double latitude, double longitude, String nearbyPlace) {
        StringBuilder googlePlacesUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlacesUrl.append("location=").append(latitude).append(",").append(longitude);
        googlePlacesUrl.append("&radius=").append(5000);
        googlePlacesUrl.append("&type=").append(nearbyPlace);
        googlePlacesUrl.append("&sensor=true");
        googlePlacesUrl.append("&key=").append("AIzaSyAEeJy0X-NH5eXep12V9pr695Kj1ZRY2c8");
        return googlePlacesUrl.toString();
    }

    public class CrimeDataFetcher {

        // Hypothetical method to fetch crime hotspots for Nakuru County
        public List<LatLng> getCrimeHotspotsForNakuruCounty() {
            List<LatLng> crimeHotspots = new ArrayList<>();

            // Hypothetical crime data for Nakuru County (latitude and longitude coordinates)
            double[][] crimeData = {
                    {0.3, 36.0667}, // Example hotspot 1
                    {0.35, 36.1},   // Example hotspot 2
                    // Add more crime hotspot coordinates as needed
            };

            // Add crime hotspot coordinates to the list
            for (double[] coordinates : crimeData) {
                LatLng hotspot = new LatLng(coordinates[0], coordinates[1]);
                crimeHotspots.add(hotspot);
                MarkerOptions markerOptions = new MarkerOptions()
                        .position(hotspot)
                        .title("Crime Hotspot")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)); // You can customize the marker icon
                myMap.addMarker(markerOptions);
            }

            return crimeHotspots;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == FINE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            } else {
                Toast.makeText(this, "Location permission is denied, please allow the permission", Toast.LENGTH_SHORT).show();
            }
        }
    }
}