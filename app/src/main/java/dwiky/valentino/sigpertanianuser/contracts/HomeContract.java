package dwiky.valentino.sigpertanianuser.contracts;

import java.util.List;

import dwiky.valentino.sigpertanianuser.models.Agriculture;

public interface HomeContract {
    interface HomeView {
        void attachToMap(List<Agriculture> agricultures);
        void toast(String message);
        void loading (boolean loading);
    }

    interface HomePresenter{
        void getData();
        void destroy();
    }
}
