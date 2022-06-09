package dwiky.valentino.sigpertanianuser.responses;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class WrappedListResponse<T> {
    public WrappedListResponse() {
    }

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

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    @SerializedName("error") private boolean error;
    @SerializedName("pesan") private String pesan;
    @SerializedName("message") private String message;
    @SerializedName("data") private List<T> data = new ArrayList<>();
}
