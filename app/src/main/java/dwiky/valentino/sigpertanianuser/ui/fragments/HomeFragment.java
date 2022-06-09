package dwiky.valentino.sigpertanianuser.ui.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import dwiky.valentino.sigpertanianuser.R;
import dwiky.valentino.sigpertanianuser.contracts.HomeContract;
import dwiky.valentino.sigpertanianuser.databinding.FragmentHomeBinding;
import dwiky.valentino.sigpertanianuser.models.Agriculture;
import dwiky.valentino.sigpertanianuser.presenters.HomePresenter;

public class HomeFragment extends Fragment implements OnMapReadyCallback, HomeContract.HomeView {
    SupportMapFragment supportMapFragment;
    GoogleMap map;
    HomePresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Initialize view
        View view=inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize map fragment
        supportMapFragment=(SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.map);

        // Async map
        supportMapFragment.getMapAsync(this);

        presenter = new HomePresenter(this);
        // Return view
        return view;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;


//        try {
//            createPolygonFromJson("[\n" +
//                    "                {\n" +
//                    "                    \"latitude\": -7.186122621321199,\n" +
//                    "                    \"longitude\": 108.89525766288834\n" +
//                    "                },\n" +
//                    "                {\n" +
//                    "                    \"latitude\": -7.173859921515611,\n" +
//                    "                    \"longitude\": 108.89491434015687\n" +
//                    "                },\n" +
//                    "                {\n" +
//                    "                    \"latitude\": -7.157509141331825,\n" +
//                    "                    \"longitude\": 108.8822113990922\n" +
//                    "                },\n" +
//                    "                {\n" +
//                    "                    \"latitude\": -7.1650033217329385,\n" +
//                    "                    \"longitude\": 108.87534494446265\n" +
//                    "                },\n" +
//                    "                {\n" +
//                    "                    \"latitude\": -7.159212374963735,\n" +
//                    "                    \"longitude\": 108.8622986806665\n" +
//                    "                },\n" +
//                    "                {\n" +
//                    "                    \"latitude\": -7.135025862064919,\n" +
//                    "                    \"longitude\": 108.85714883969433\n" +
//                    "                },\n" +
//                    "                {\n" +
//                    "                    \"latitude\": -7.120377070569528,\n" +
//                    "                    \"longitude\": 108.83757944400011\n" +
//                    "                },\n" +
//                    "                {\n" +
//                    "                    \"latitude\": -7.10879397393055,\n" +
//                    "                    \"longitude\": 108.78814097066734\n" +
//                    "                },\n" +
//                    "                {\n" +
//                    "                    \"latitude\": -7.112200797396469,\n" +
//                    "                    \"longitude\": 108.74728556562151\n" +
//                    "                },\n" +
//                    "                {\n" +
//                    "                    \"latitude\": -7.151036795556418,\n" +
//                    "                    \"longitude\": 108.69304057404806\n" +
//                    "                },\n" +
//                    "                {\n" +
//                    "                    \"latitude\": -7.192253847171426,\n" +
//                    "                    \"longitude\": 108.72290965168659\n" +
//                    "                },\n" +
//                    "                {\n" +
//                    "                    \"latitude\": -7.213371879827306,\n" +
//                    "                    \"longitude\": 108.79088755251917\n" +
//                    "                },\n" +
//                    "                {\n" +
//                    "                    \"latitude\": -7.205878496982071,\n" +
//                    "                    \"longitude\": 108.84787912594444\n" +
//                    "                },\n" +
//                    "                {\n" +
//                    "                    \"latitude\": -7.183397605496707,\n" +
//                    "                    \"longitude\": 108.88427133548106\n" +
//                    "                },\n" +
//                    "                {\n" +
//                    "                    \"latitude\": -7.186122621321199,\n" +
//                    "                    \"longitude\": 108.89525766288834\n" +
//                    "                }\n" +
//                    "            ]");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-6.892344,109.026947), 11));
    }

    @Override
    public void attachToMap(List<Agriculture> agricultures) {
        System.out.println("ATTACH JALAN");
        for (int i = 0; i < agricultures.size(); i++){
            createMarker(Double.parseDouble(agricultures.get(i).getLatitude()), Double.parseDouble(agricultures.get(i).getLongitude()), agricultures.get(i).getNamapemilik(), "TEST");
        }
    }



    protected Marker createMarker(double latitude, double longitude, String title, String snippet) {

        return map.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .anchor(0.5f, 0.5f)
                .title(title)
                .snippet(snippet));
    }

    @Override
    public void toast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void loading(boolean loading) {
        System.out.println("LOADING " + loading);
    }

//    void createPolygonFromJson(String json) throws JSONException {
//        // De-serialize the JSON string into an array of city objects
//        PolygonOptions polygonOptions = new PolygonOptions();
//        polygonOptions.strokeColor(Color.RED);
//        polygonOptions.fillColor(Color.parseColor("#4DFF0000"));
//        JSONArray jsonArrayPoly = new JSONArray(json);
//        for (int i = 0; i < jsonArrayPoly.length(); i++) {
//            JSONObject jsonObj = jsonArrayPoly.getJSONObject(i);
//            polygonOptions.add(new LatLng(jsonObj.getDouble("latitude"), jsonObj.getDouble("longitude")));
//        }
//        Polygon polygon = map.addPolygon(polygonOptions);
//
////        JSONArray array = new JSONArray(json);
////        System.out.println("ARRAY "+ array);
////        for (int i = 0; i < array.length(); i++) {
////            JSONObject row = array.getJSONObject(i);
////        }
//
//    }

    public void getData(){
        System.out.println("GET DATA JALAN");
        presenter.getData();
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.destroy();
    }
}