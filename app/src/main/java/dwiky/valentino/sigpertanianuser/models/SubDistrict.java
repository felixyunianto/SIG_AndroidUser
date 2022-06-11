package dwiky.valentino.sigpertanianuser.models;

import com.google.gson.annotations.SerializedName;

public class SubDistrict  {
    @SerializedName("id") private String id;
    @SerializedName("desa") private String desa;
    @SerializedName("kecamatan") private String kecamatan;
    @SerializedName("wilayah_id") private String wilayah_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDesa() {
        return desa;
    }

    public void setDesa(String desa) {
        this.desa = desa;
    }

    public String getKecamatan() {
        return kecamatan;
    }

    public void setKecamatan(String kecamatan) {
        this.kecamatan = kecamatan;
    }

    public String getWilayah_id() {
        return wilayah_id;
    }

    public void setWilayah_id(String wilayah_id) {
        this.wilayah_id = wilayah_id;
    }

    @Override
    public String toString() {
        return this.desa;
    }
}
