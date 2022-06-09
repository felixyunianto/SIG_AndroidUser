package dwiky.valentino.sigpertanianuser.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class District implements Parcelable {
    protected District(Parcel in) {
        id = in.readString();
        kecamatan = in.readString();
        koordinat = in.readString();
    }

    @SerializedName("id") private String id;
    @SerializedName("kecamatan") private String kecamatan;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKecamatan() {
        return kecamatan;
    }

    public void setKecamatan(String kecamatan) {
        this.kecamatan = kecamatan;
    }

    public String getKoordinat() {
        return koordinat;
    }

    public void setKoordinat(String koordinat) {
        this.koordinat = koordinat;
    }

    @SerializedName("koordinat") private String koordinat;

    public static final Creator<District> CREATOR = new Creator<District>() {
        @Override
        public District createFromParcel(Parcel in) {
            return new District(in);
        }

        @Override
        public District[] newArray(int size) {
            return new District[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.getId());
        parcel.writeString(this.getKecamatan());
        parcel.writeString(this.getKoordinat());
    }

    @Override
    public String toString() {
        return kecamatan;
    }
}
