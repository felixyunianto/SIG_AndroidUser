package dwiky.valentino.sigpertanianuser.webservices;

import dwiky.valentino.sigpertanianuser.models.Agriculture;
import dwiky.valentino.sigpertanianuser.models.District;
import dwiky.valentino.sigpertanianuser.models.SearchingChart;
import dwiky.valentino.sigpertanianuser.models.SubDistrict;
import dwiky.valentino.sigpertanianuser.responses.WrappedListResponse;
import dwiky.valentino.sigpertanianuser.responses.WrappedResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIServices {
    //Lahan Pertanian
    @GET("LahanPetaniUser")
    Call<WrappedListResponse<Agriculture>> fetchAgriculture(
            @Query("kecamatan") String kecamatan,
            @Query("desa") String desa
    );

    // District
    @GET("UserKecamatan")
    Call<WrappedListResponse<District>> fetchDistrict();

    @GET("UserKecamatan")
    Call<WrappedListResponse<District>> fetchSingleDistrict(
            @Query("kecamatan") String kecamatan
    );

    // Sub District
    @GET("Desa")
    Call<WrappedListResponse<SubDistrict>> fetchSubDistrict(
            @Query("id_kecamatan") String id_kecamatan
    );

    @GET("Chart/getChartComodity")
    Call<WrappedResponse<SearchingChart>> fetchChartComodity(
            @Query("kecamatan") String kecamatan,
            @Query("jenis_komoditas") String jenis_komoditas,
            @Query("jenis_statistik") String jenis_statistik,
            @Query("awal") String awal,
            @Query("akhir") String akhir
    );
}
