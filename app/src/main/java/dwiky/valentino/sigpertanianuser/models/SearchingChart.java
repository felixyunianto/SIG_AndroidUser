package dwiky.valentino.sigpertanianuser.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchingChart {


    public List<Chart> getChart() {
        return chart;
    }

    public void setChart(List<Chart> chart) {
        this.chart = chart;
    }

    public List<Comodity> getComodities() {
        return comodities;
    }

    public void setComodities(List<Comodity> comodities) {
        this.comodities = comodities;
    }

    @SerializedName("chart") public List<Chart> chart;
    @SerializedName("comodities") public List<Comodity> comodities;

}
