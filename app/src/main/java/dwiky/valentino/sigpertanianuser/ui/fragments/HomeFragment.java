package dwiky.valentino.sigpertanianuser.ui.fragments;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
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
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import dwiky.valentino.sigpertanianuser.R;
import dwiky.valentino.sigpertanianuser.adapters.ComodityAdapter;
import dwiky.valentino.sigpertanianuser.adapters.CustomInfoWindowAdapter;
import dwiky.valentino.sigpertanianuser.contracts.HomeContract;
import dwiky.valentino.sigpertanianuser.databinding.FragmentHomeBinding;
import dwiky.valentino.sigpertanianuser.models.Agriculture;
import dwiky.valentino.sigpertanianuser.models.Chart;
import dwiky.valentino.sigpertanianuser.models.Comodity;
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

    boolean comodityFilter = false;
    District selectedDitrictComodity;
    String selectedComodity;
    String selectedStatistic;


    List<Agriculture> list;
    String coordinateString;

    PieChart pieChart;

    boolean filterAgriculture = false;

    ComodityAdapter adapter;

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

        intentAgriculturePopUp();
        intentComodityPopUp();

        attachToSpinnerComodity();

        binding.etAwal.setOnClickListener(view -> {
            openStartDatePickerDialog(binding.etAwal);
        });

        binding.etAkhir.setOnClickListener(view -> {
            openStartDatePickerDialog(binding.etAkhir);
        });

        filterComodity();

        pieChart = binding.pieChart;

//        setupPieChart();
//        attachToChart();

        binding.btnClosePopUpChart.setOnClickListener(view -> {
            binding.popUpChart.setVisibility(View.GONE);
            binding.btnPopup.setVisibility(View.VISIBLE);
        });


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

        if(coord != null){
            PolygonOptions polygonOptions = new PolygonOptions();
            polygonOptions.strokeColor(Color.RED);
            polygonOptions.fillColor(Color.parseColor("#4DFF0000"));

            JSONArray jsonArrayPoly = new JSONArray(coord);
            for (int i = 0; i < jsonArrayPoly.length(); i++) {
                JSONObject jsonObj = jsonArrayPoly.getJSONObject(i);
                polygonOptions.add(new LatLng(jsonObj.getDouble("latitude"), jsonObj.getDouble("longitude")));
            }
            Polygon polygon = map.addPolygon(polygonOptions);
            if(comodityFilter){
                polygon.setClickable(true);map.setOnPolygonClickListener(new GoogleMap.OnPolygonClickListener() {
                    @Override
                    public void onPolygonClick(@NonNull Polygon polygon) {
                        binding.btnPopup.setVisibility(View.GONE);
                        binding.popUpChart.setVisibility(View.VISIBLE);
                    }
                });

            }

            openPopup(false);


            map.animateCamera(CameraUpdateFactory.newLatLngZoom(getCentroid(coord), 11));

        }

        if(agricultures!= null && agricultures.size() > 0){
            for (int i = 0; i < agricultures.size(); i++){
                createMarker(
                        Double.parseDouble(agricultures.get(i).getLatitude()),
                        Double.parseDouble(agricultures.get(i).getLongitude()),
                        "Lahan Pertanian",
                        agricultures.get(i));
            }
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

        binding.districtSpinnerComodity.setAdapter(spinnerAdapter);
        binding.districtSpinner.setAdapter(spinnerAdapter);

        binding.districtSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Object item = adapterView.getItemAtPosition(i);
                if(item instanceof District){
                    District district = (District) item;
                    selectedDistrict = district;
                    binding.inSubDistrictSpinner.setEnabled(true);
                    binding.subDistrictSpinner.setEnabled(true);

                    presenter.fetchSubDistrict(district.getId());
                }
            }
        });

        binding.districtSpinnerComodity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Object item = adapterView.getItemAtPosition(i);
                if(item instanceof District){
                    District district = (District) item;
                    selectedDitrictComodity = district;
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


    protected Marker createMarker(double latitude, double longitude, String title, Agriculture agriculture) {
        map.setInfoWindowAdapter(new CustomInfoWindowAdapter(requireActivity()));

        return map.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .anchor(0.5f, 0.5f)

                .title(title)
                .snippet("Pemilik Lahan : " + agriculture.getNamapemilik() + "\n" +
                        "Luas : " + agriculture.getLuas() + "\n" +
                        "Meter : " + agriculture.getMeter() + " m2\n" +
                        "Desa : " + agriculture.getDesa() + "\n" +
                        "Kecamatan : " + agriculture.getKecamatan()));
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
    public void attachPolygon(List<District> polygon) throws JSONException {
        Gson gson = new Gson();
        String coord = gson.toJson(polygon.get(0).getKoordinat());
        coordinateString = coord;

        if(comodityFilter){
            attachMarkerAgriculture(null, coord);
        }

    }

    @Override
    public void attachToChart(List<Chart> chart, List<Comodity> comodities) {
        adapter = new ComodityAdapter(getContext(), comodities);
        binding.rvComodity.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvComodity.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        setupPieChart();

        ArrayList<PieEntry> entries = new ArrayList<>();
        for (int i = 0; i < chart.size(); i++){
            entries.add(new PieEntry(Float.parseFloat(chart.get(i).total), chart.get(i).namakomoditas));
        }
        ArrayList<Integer> colors = new ArrayList<>();
        for(int color : ColorTemplate.MATERIAL_COLORS){
            colors.add(color);
        }

        for(int color : ColorTemplate.VORDIPLOM_COLORS){
            colors.add(color);
        }

        PieDataSet pieDataSet = new PieDataSet(entries, "Entries");
        pieDataSet.setColors(colors);

        PieData pieData = new PieData(pieDataSet);
        pieData.setDrawValues(true);
        pieData.setValueFormatter(new PercentFormatter(pieChart));
        pieData.setValueTextSize(10f);
        pieData.setValueTextColor(Color.BLACK);

        pieChart.setData(pieData);
        pieChart.invalidate();
    }

    public void attachToSpinnerComodity() {
        ArrayAdapter comodityAdapter = new ArrayAdapter(getActivity(), R.layout.spinner_item, getResources().getStringArray(R.array.comodity));
        binding.comoditySpinnerComodity.setAdapter(comodityAdapter);

        binding.comoditySpinnerComodity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedComodity = adapterView.getItemAtPosition(i).toString();
            }
        });

        ArrayAdapter statisticAdapter = new ArrayAdapter(getActivity(), R.layout.spinner_item, getResources().getStringArray(R.array.statistic));
        binding.statisticSpinnerComodity.setAdapter(statisticAdapter);

        binding.statisticSpinnerComodity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedStatistic = adapterView.getItemAtPosition(i).toString();
            }
        });


    }

    public void setupPieChart() {
        pieChart.setDrawHoleEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(12);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.getDescription().setEnabled(false);

        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setTextSize(8);
        l.setDrawInside(false);
        l.setEnabled(true);

    }
//
//    public void attachToChart () {
//        ArrayList<PieEntry> entries = new ArrayList<>();
//        entries.add(new PieEntry(0.3f, "Kesatu"));
//        entries.add(new PieEntry(0.2f, "Kedua"));
//        entries.add(new PieEntry(0.2f, "Ketiga"));
//        entries.add(new PieEntry(0.3f, "keempate"));
//        entries.add(new PieEntry(0.3f, "Kelima"));
//
//        ArrayList<Integer> colors = new ArrayList<>();
//        for(int color : ColorTemplate.MATERIAL_COLORS){
//            colors.add(color);
//        }
//
//        for(int color : ColorTemplate.VORDIPLOM_COLORS){
//            colors.add(color);
//        }
//
//        PieDataSet pieDataSet = new PieDataSet(entries, "Entries");
//        pieDataSet.setColors(colors);
//
//        PieData pieData = new PieData(pieDataSet);
//        pieData.setDrawValues(true);
//        pieData.setValueFormatter(new PercentFormatter(pieChart));
//        pieData.setValueTextSize(10f);
//        pieData.setValueTextColor(Color.BLACK);
//
//        pieChart.setData(pieData);
//        pieChart.invalidate();
//
//
//    }

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

        binding.btnClosePopUpComodity.setOnClickListener(view -> {
            binding.popUpComodity.setVisibility(View.GONE);

        });
    }

    public void getData(){
        presenter.getData(null, null);
        presenter.fetchDistrict();
    }



    public void filterData(){
        binding.btnFilter.setOnClickListener(view -> {
            comodityFilter = false;
            filterAgriculture = true;
            if(selectedDistrict == null){
                binding.errorKecamatan.setVisibility(View.VISIBLE);
            }else if(selectedSubDistrict == null){
                binding.errorKecamatan.setVisibility(View.VISIBLE);
            }else{
                String kecamatan = selectedDistrict.toString();
                String desa = selectedSubDistrict.toString();

                presenter.getData(kecamatan, desa);
                presenter.fetchSingleDistrict(kecamatan);
            }

        });
    }

    public void intentComodityPopUp() {
        binding.intentComodity.setOnClickListener(view -> {
            binding.popUp.setVisibility(View.GONE);
            binding.popUpComodity.setVisibility(View.VISIBLE);
        });
    }

    public void intentAgriculturePopUp() {
        binding.intentAgriculture.setOnClickListener(view -> {
            binding.popUpComodity.setVisibility(View.GONE);
            binding.popUp.setVisibility(View.VISIBLE);
        });
    }

    public void openStartDatePickerDialog(TextInputEditText editText){
        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                editText.setText(formatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    public void filterComodity() {
        binding.btnFilterComodity.setOnClickListener(view -> {
            comodityFilter = true;
            validKecamatanComodity();
            validComodity();
            validStatistic();
            validAwal();
            validAkhir();

            if(validKecamatanComodity() && validComodity() && validStatistic() && validAwal() && validAkhir()){
                String kecamatan = selectedDitrictComodity.toString();
                String jenis_komoditas = selectedComodity.toString();
                String jenis_statistik = selectedStatistic.toString();
                String awal = binding.etAwal.getText().toString().trim();
                String akhir = binding.etAkhir.getText().toString().trim();

                presenter.fetchSingleDistrict(kecamatan);
                presenter.fetchChartComodity(kecamatan, jenis_komoditas, jenis_statistik, awal, akhir);

                binding.chartKecamatan.setText(kecamatan);
                binding.chartAwal.setText(awal);
                binding.chartAkhir.setText(akhir);

                binding.popUpComodity.setVisibility(View.GONE);
                binding.btnPopup.setVisibility(View.VISIBLE);
            }

        });
    }

    public boolean validKecamatanComodity() {
        if(selectedDitrictComodity == null){
            binding.inDistrictSpinnerComodity.setHelperText("Form ini harus disi");
            binding.inDistrictSpinnerComodity.setHelperTextColor(
                    ColorStateList.valueOf(Color.RED)
            );

            return false;
        }

        return true;
    }

    public boolean validComodity() {
        if(selectedComodity == null){
            binding.inComoditySpinnerComodity.setHelperText("Form ini harus disi");
            binding.inComoditySpinnerComodity.setHelperTextColor(
                    ColorStateList.valueOf(Color.RED)
            );

            return false;
        }

        return true;
    }

    public boolean validStatistic() {
        if(selectedStatistic == null){
            binding.inStatisticSpinnerComodity.setHelperText("Form ini harus disi");
            binding.inStatisticSpinnerComodity.setHelperTextColor(
                    ColorStateList.valueOf(Color.RED)
            );

            return false;
        }

        return true;
    }

    public boolean validAwal() {
        if(binding.etAwal.getText().toString().trim().isEmpty()){
            binding.inEtAwal.setHelperText("Form ini harus disi");
            binding.inEtAwal.setHelperTextColor(
                    ColorStateList.valueOf(Color.RED)
            );

            return false;
        }

        return true;
    }

    public boolean validAkhir() {
        if(binding.etAkhir.getText().toString().trim().isEmpty()){
            binding.inEtAkhir.setHelperText("Form ini harus disi");
            binding.inEtAkhir.setHelperTextColor(
                    ColorStateList.valueOf(Color.RED)
            );

            return false;
        }

        return true;
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