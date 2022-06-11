package dwiky.valentino.sigpertanianuser.webservices;

import dwiky.valentino.sigpertanianuser.models.Agriculture;
import dwiky.valentino.sigpertanianuser.models.District;
import dwiky.valentino.sigpertanianuser.models.SubDistrict;
import dwiky.valentino.sigpertanianuser.responses.WrappedListResponse;
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
    Call<WrappedListResponse<SubDistrict>> fetchSubDistrict();
}
