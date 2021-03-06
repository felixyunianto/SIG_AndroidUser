package dwiky.valentino.sigpertanianuser.presenters;

import org.json.JSONException;

import dwiky.valentino.sigpertanianuser.contracts.HomeContract;
import dwiky.valentino.sigpertanianuser.models.Agriculture;
import dwiky.valentino.sigpertanianuser.models.District;
import dwiky.valentino.sigpertanianuser.models.SearchingChart;
import dwiky.valentino.sigpertanianuser.models.SubDistrict;
import dwiky.valentino.sigpertanianuser.responses.WrappedListResponse;
import dwiky.valentino.sigpertanianuser.responses.WrappedResponse;
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
    public void getData(String kecamatan, String desa) {
        view.loading(true);
        apiServices.fetchAgriculture( kecamatan, desa)
                .enqueue(new Callback<WrappedListResponse<Agriculture>>() {
                    @Override
                    public void onResponse(Call<WrappedListResponse<Agriculture>> call, Response<WrappedListResponse<Agriculture>> response) {
                        if(response.isSuccessful()){
                            WrappedListResponse body = response.body();
                            if(body != null && !body.isError()){
                                view.attachToMap(body.getData());
                                System.out.println("BODY " + body.getData());
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
    public void fetchDistrict() {
        view.loading(true);
        apiServices.fetchDistrict()
                .enqueue(new Callback<WrappedListResponse<District>>() {
                    @Override
                    public void onResponse(Call<WrappedListResponse<District>> call, Response<WrappedListResponse<District>> response) {
                        if(response.isSuccessful()){
                            WrappedListResponse body = response.body();
                            if(body != null){
                                view.attachSpinnerDistrict(body.getData());
                            }else{
                                view.toast("Error");
                            }
                        }
                        view.loading(false);
                    }

                    @Override
                    public void onFailure(Call<WrappedListResponse<District>> call, Throwable t) {
                        System.out.println("Terjadi kesalahan " + t.getMessage());
                        view.loading(false);
                    }
                });
    }

    @Override
    public void fetchSingleDistrict(String kecamatan) {
        apiServices.fetchSingleDistrict(kecamatan)
                .enqueue(new Callback<WrappedListResponse<District>>() {
                    @Override
                    public void onResponse(Call<WrappedListResponse<District>> call, Response<WrappedListResponse<District>> response) {
                        if (response.isSuccessful()){
                            WrappedListResponse body = response.body();
                            if(body != null) {
                                try {
                                    view.attachPolygon(body.getData());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<WrappedListResponse<District>> call, Throwable t) {
                        System.out.println("Terjadi kesalahan " + t.getMessage());
                        view.loading(false);
                    }
                });
    }

    @Override
    public void fetchSubDistrict(String id_kecamatan) {
        view.loading(true);
        apiServices.fetchSubDistrict(id_kecamatan)
                .enqueue(new Callback<WrappedListResponse<SubDistrict>>() {
                    @Override
                    public void onResponse(Call<WrappedListResponse<SubDistrict>> call, Response<WrappedListResponse<SubDistrict>> response) {
                        if(response.isSuccessful()){
                            WrappedListResponse body = response.body();
                            if(body != null){
                                view.attachSpinnerSubDistrict(body.getData());
                            }else{
                                view.toast("Error");
                            }
                        }
                        view.loading(false);
                    }

                    @Override
                    public void onFailure(Call<WrappedListResponse<SubDistrict>> call, Throwable t) {
                        System.out.println("Terjadi kesalahan " + t.getMessage());
                        view.loading(false);
                    }
                });
    }

    @Override
    public void fetchChartComodity(String kecamatan, String jenis_komoditas, String jenis_statistik, String awal, String akhir) {
        view.loading(true);
        apiServices.fetchChartComodity(kecamatan, jenis_komoditas, jenis_statistik, awal, akhir)
                .enqueue(new Callback<WrappedResponse<SearchingChart>>() {
                    @Override
                    public void onResponse(Call<WrappedResponse<SearchingChart>> call, Response<WrappedResponse<SearchingChart>> response) {
                        if(response.isSuccessful()){
                            WrappedResponse body = response.body();
                            SearchingChart data = (SearchingChart) body.getData();
                            if(body != null){
                                view.attachToChart(data.getChart(), data.getComodities());
                            }else{
                                view.toast("Error");
                            }
                        }
                        view.loading(false);
                    }

                    @Override
                    public void onFailure(Call<WrappedResponse<SearchingChart>> call, Throwable t) {
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
