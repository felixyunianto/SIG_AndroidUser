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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.google.gson.Gson;

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
import dwiky.valentino.sigpertanianuser.models.Coordinate;
import dwiky.valentino.sigpertanianuser.models.District;
import dwiky.valentino.sigpertanianuser.models.SubDistrict;
import dwiky.valentino.sigpertanianuser.presenters.HomePresenter;

public class HomeFragment extends Fragment implements OnMapReadyCallback, HomeContract.HomeView {
    SupportMapFragment supportMapFragment;
    GoogleMap map;
    HomePresenter presenter;
    FragmentHomeBinding binding;

    SubDistrict selectedSubDistrict;
    District selectedDistrict;

    List<Agriculture> list;
    String coordinateString;

    boolean filterAgriculture = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Initialize view
        binding = FragmentHomeBinding.inflate(getLayoutInflater(), container, false);

        // Initialize map fragment
        supportMapFragment=(SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.map);

        // Async map
        supportMapFragment.getMapAsync(this);

        presenter = new HomePresenter(this);
        clickOpenPopup();
        clickClosePopup();

        filterData();
        // Return view
        return binding.getRoot();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-6.892344,109.026947), 11));
    }

    @Override
    public void attachToMap(List<Agriculture> agricultures) {
        list = agricultures;

        try{
            attachMarkerAgriculture(agricultures, coordinateString);
            coordinateString = null;
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public void attachMarkerAgriculture(List<Agriculture> agricultures, String coord) throws JSONException {
        map.clear();

        for (int i = 0; i < agricultures.size(); i++){
            createMarker(Double.parseDouble(agricultures.get(i).getLatitude()), Double.parseDouble(agricultures.get(i).getLongitude()), agricultures.get(i).getNamapemilik(), "TEST");
        }

        if(coord != null){
            PolygonOptions polygonOptions = new PolygonOptions();
            polygonOptions.strokeColor(Color.RED);
            polygonOptions.fillColor(Color.parseColor("#4DFF0000"));
            JSONArray jsonArrayPoly = new JSONArray(coord);
            for (int i = 0; i < jsonArrayPoly.length(); i++) {
                JSONObject jsonObj = jsonArrayPoly.getJSONObject(i);
                polygonOptions.add(new LatLng(jsonObj.getDouble("latitude"), jsonObj.getDouble("longitude")));
            }
            map.addPolygon(polygonOptions);
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(getCentroid(coord), 11));
        }
    }

    public static LatLng getCentroid(String json) throws JSONException {
        Gson gson = new Gson();
        JSONArray jsonArrayPoly = new JSONArray(json);
        double[] centroid = { 0.0, 0.0 };
        for (int i = 0; i < jsonArrayPoly.length(); i++) {
            JSONObject jsonObj = jsonArrayPoly.getJSONObject(i);
            centroid[0] += jsonObj.getDouble("latitude");
            centroid[1] += jsonObj.getDouble("longitude");
        }
        int totalPoints = jsonArrayPoly.length();
        centroid[0] = centroid[0] / totalPoints;
        centroid[1] = centroid[1] / totalPoints;

        return new LatLng(centroid[0], centroid[1]);
    }

    @Override
    public void attachSpinnerDistrict(List<District> districts) {
        ArrayAdapter spinnerAdapter = new ArrayAdapter(getActivity(), R.layout.spinner_item, districts);

        binding.districtSpinner.setAdapter(spinnerAdapter);

        binding.districtSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Object item = adapterView.getItemAtPosition(i);
                if(item instanceof District){
                    District district = (District) item;
                    selectedDistrict = district;
                }
            }
        });
    }

    @Override
    public void attachSpinnerSubDistrict(List<SubDistrict> subDistricts) {
        ArrayAdapter spinnerAdapter = new ArrayAdapter(getActivity(), R.layout.spinner_item, subDistricts);

        binding.subDistrictSpinner.setAdapter(spinnerAdapter);

        binding.subDistrictSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SubDistrict item = subDistricts.get(i);
                selectedSubDistrict = item;

            }
        });
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

    @Override
    public void attachPolygon(List<District> polygon) {
        Gson gson = new Gson();
        String coord = gson.toJson(polygon.get(0).getKoordinat());
        coordinateString = coord;
    }

    public void openPopup(boolean status){
        if(status){
            binding.popUp.setVisibility(View.VISIBLE);
            binding.btnPopup.setVisibility(View.GONE);
        }else {
            binding.popUp.setVisibility(View.GONE);
            binding.btnPopup.setVisibility(View.VISIBLE);
        }
    }

    public void clickOpenPopup(){
        binding.btnPopup.setOnClickListener(view -> {
            openPopup(true);
        });
    }

    public void clickClosePopup(){
        binding.btnClosePopUp.setOnClickListener(view -> {
            openPopup(false);
        });
    }

    public void getData(){
        presenter.getData(null, null);
        presenter.fetchDistrict();
        presenter.fetchSubDistrict();
    }

    public void filterData(){
        binding.btnFilter.setOnClickListener(view -> {
            filterAgriculture = true;
            String kecamatan = selectedDistrict.toString();
            String desa = selectedSubDistrict.toString();

            presenter.getData(kecamatan, desa);
            presenter.fetchSingleDistrict(kecamatan);
        });
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