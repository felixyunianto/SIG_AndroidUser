package dwiky.valentino.sigpertanianuser.presenters;

import dwiky.valentino.sigpertanianuser.contracts.HomeContract;
import dwiky.valentino.sigpertanianuser.models.Agriculture;
import dwiky.valentino.sigpertanianuser.responses.WrappedListResponse;
import dwiky.valentino.sigpertanianuser.utilities.APIClient;
import dwiky.valentino.sigpertanianuser.webservices.APIServices;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePresenter implements HomeContract.HomePresenter {
    HomeContract.HomeView view;
    APIServices apiServices = new APIClient().apiServices();

    public HomePresenter(HomeContract.HomeView v){
        this.view = v;
    }

    @Override
    public void getData() {
        view.loading(true);
        apiServices.fetchAgriculture()
                .enqueue(new Callback<WrappedListResponse<Agriculture>>() {
                    @Override
                    public void onResponse(Call<WrappedListResponse<Agriculture>> call, Response<WrappedListResponse<Agriculture>> response) {
                        if(response.isSuccessful()){
                            WrappedListResponse body = response.body();
                            if(body != null && !body.isError()){
                                view.attachToMap(body.getData());
                                view.toast(body.getMessage());
                            }
                        }
                        view.loading(false);
                    }

                    @Override
                    public void onFailure(Call<WrappedListResponse<Agriculture>> call, Throwable t) {
                        System.out.println("Terjadi kesalahan " + t.getMessage());
                        view.loading(false);
                    }
                });
    }

    @Override
    public void destroy() {
        view = null;
    }
}
