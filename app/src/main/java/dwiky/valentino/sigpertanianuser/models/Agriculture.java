package dwiky.valentino.sigpertanianuser.models;

import com.google.gson.annotations.SerializedName;

public class Agriculture {
    @SerializedName("id_lahan") public String id_lahan;
    @SerializedName("namapemilik") public String namapemilik;
    @SerializedName("luas") public String luas;
    @SerializedName("meter") public String meter;
    @SerializedName("desa") public String desa;
    @SerializedName("kecamatan") public String kecamatan;
    @SerializedName("latitude") public String latitude;
    @SerializedName("longtitude") public String longitude;
    @SerializedName("foto") public String foto;

    public String getId_lahan() {
        return id_lahan;
    }

    public void setId_lahan(String id_lahan) {
        this.id_lahan = id_lahan;
    }

    public String getNamapemilik() {
        return namapemilik;
    }

    public void setNamapemilik(String namapemilik) {
        this.namapemilik = namapemilik;
    }

    public String getLuas() {
        return luas;
    }

    public void setLuas(String luas) {
        this.luas = luas;
    }

    public String getMeter() {
        return meter;
    }

    public void setMeter(String meter) {
        this.meter = meter;
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

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
