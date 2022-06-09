package dwiky.valentino.sigpertanianuser.webservices;

import dwiky.valentino.sigpertanianuser.models.Agriculture;
import dwiky.valentino.sigpertanianuser.responses.WrappedListResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIServices {
    //Lahan Pertanian
    @GET("LahanPetaniUser")
    Call<WrappedListResponse<Agriculture>> fetchAgriculture();
}
