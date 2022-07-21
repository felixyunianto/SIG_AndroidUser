package dwiky.valentino.sigpertanianuser.models;

import com.google.gson.annotations.SerializedName;

public class Comodity {
    public String getId_komoditas() {
        return id_komoditas;
    }

    public void setId_komoditas(String id_komoditas) {
        this.id_komoditas = id_komoditas;
    }

    public String getNamakomoditas() {
        return namakomoditas;
    }

    public void setNamakomoditas(String namakomoditas) {
        this.namakomoditas = namakomoditas;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getAwal() {
        return awal;
    }

    public void setAwal(String awal) {
        this.awal = awal;
    }

    public String getAkhir() {
        return akhir;
    }

    public void setAkhir(String akhir) {
        this.akhir = akhir;
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

    @SerializedName("id_komoditas") public String id_komoditas;
    @SerializedName("namakomoditas") public String namakomoditas;
    @SerializedName("jumlah") public String jumlah;
    @SerializedName("awal")  public String awal;
    @SerializedName("akhir") public String akhir;
    @SerializedName("desa") public String desa;
    @SerializedName("kecamatan") public String kecamatan;
}
