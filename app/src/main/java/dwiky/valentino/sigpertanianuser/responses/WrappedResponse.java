package dwiky.valentino.sigpertanianuser.responses;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class WrappedResponse<T> {
    public WrappedResponse(){

    }

    @SerializedName("error") private boolean error;
    @SerializedName("pesan") private String pesan;
    @SerializedName("message") private String message;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @SerializedName("data") private T data = null;

}
