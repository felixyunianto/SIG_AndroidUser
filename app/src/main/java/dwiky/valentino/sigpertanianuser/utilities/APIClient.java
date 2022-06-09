package dwiky.valentino.sigpertanianuser.utilities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import dwiky.valentino.sigpertanianuser.webservices.APIServices;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    private static Retrofit retrofit = null;
    private static Retrofit retrofit1 = null;
    public static OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS).build();

    public APIServices apiServices(){
        return getClient().create(APIServices.class);
    }

    public APIServices apiServicesAuth(){
        return getClientAuth().create(APIServices.class);
    }


    public static Retrofit getClient(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constans.API_ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }

        return retrofit;
    }

    public static Retrofit getClientAuth(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        if (retrofit1 == null){
            retrofit1 = new Retrofit.Builder()
                    .baseUrl(Constans.API_ENDPOINT_AUTH)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }

        return retrofit1;
    }
}
