package dwiky.valentino.sigpertanianuser.contracts;

import org.json.JSONException;

import java.util.List;

import dwiky.valentino.sigpertanianuser.models.Agriculture;
import dwiky.valentino.sigpertanianuser.models.Chart;
import dwiky.valentino.sigpertanianuser.models.Comodity;
import dwiky.valentino.sigpertanianuser.models.District;
import dwiky.valentino.sigpertanianuser.models.SubDistrict;

public interface HomeContract {
    interface HomeView {
        void attachToMap(List<Agriculture> agricultures);
        void attachSpinnerDistrict(List<District> districts);
        void attachSpinnerSubDistrict(List<SubDistrict> subDistricts);
        void toast(String message);
        void loading (boolean loading);
        void attachPolygon(List<District> polygon) throws JSONException;
        void attachToChart(List<Chart> chart, List<Comodity> comodities);
    }

    interface HomePresenter{
        void getData(String kecamatan, String desa);
        void fetchDistrict();
        void fetchSingleDistrict(String kecamatan);
        void fetchSubDistrict(String id_kecamatan);
        void fetchChartComodity(String kecamatan, String jenis_komoditas, String jenis_statistik, String awal, String akhir);
        void destroy();
    }
}
