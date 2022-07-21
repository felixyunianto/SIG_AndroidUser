package dwiky.valentino.sigpertanianuser.models;

import com.google.gson.annotations.SerializedName;

public class Chart {
    public String getNamakomoditas() {
        return namakomoditas;
    }

    public void setNamakomoditas(String namakomoditas) {
        this.namakomoditas = namakomoditas;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    @SerializedName("namakomoditas") public String namakomoditas;
    @SerializedName("total") public String total;
}