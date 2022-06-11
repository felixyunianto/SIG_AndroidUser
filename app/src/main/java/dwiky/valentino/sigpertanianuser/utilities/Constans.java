package dwiky.valentino.sigpertanianuser.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Constans {
    public static final String API_ENDPOINT = "http://192.168.1.123/SIGPertanianAndroid/api/";
    public static final String API_ENDPOINT_AUTH = "http://192.168.1.123/SIGPertanianAndroid/android/login/";

    public static final String converDateToSave(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String resultDate = null;
        try {
            Date parseDate = dateFormat.parse(date);
            dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            resultDate = dateFormat.format(parseDate);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return resultDate;
    }
}
