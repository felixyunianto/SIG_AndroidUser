package dwiky.valentino.sigpertanianuser.contracts;

import java.util.List;
import dwiky.valentino.sigpertanianuser.models.District;

public interface ComodityContract {
    interface ComodityView {
        void attachSpinnerDistrict(List<District> districts);
        void toast(String message);
        void loading (boolean loading);
        void attachPolygon(List<District> polygon);
    }

    interface ComodityPresenter{
//        void getData(String kecamatan, String desa);
        void fetchDistrict();
        void destroy();
    }
}
