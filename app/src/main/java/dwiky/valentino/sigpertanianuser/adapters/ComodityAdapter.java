package dwiky.valentino.sigpertanianuser.adapters;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dwiky.valentino.sigpertanianuser.R;
import dwiky.valentino.sigpertanianuser.models.Comodity;

public class ComodityAdapter extends RecyclerView.Adapter<ComodityAdapter.ViewHolder>
{
    private Context context;
    private List<Comodity> comodities;

    public ComodityAdapter(Context context, List<Comodity> comodities) {
        this.context = context;
        this.comodities = comodities;
    }


    @NonNull
    @Override
    public ComodityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.comodity_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ComodityAdapter.ViewHolder holder, int position) {
        Comodity comodity = comodities.get(position);
        holder.tvNamaKomoditas.setText(comodity.getNamakomoditas());
        holder.tvJumlah.setText(comodity.getJumlah());
        holder.tvAwal.setText(comodity.getAwal());
        holder.tvAkhir.setText(comodity.getAkhir());
        holder.tvDesa.setText(comodity.getDesa());
        holder.tvKecamatan.setText(comodity.getKecamatan());
    }

    @Override
    public int getItemCount() {
        return comodities.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNamaKomoditas, tvJumlah, tvAwal, tvAkhir, tvDesa, tvKecamatan;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNamaKomoditas = itemView.findViewById(R.id.chartNamaKomoditas);
            tvJumlah = itemView.findViewById(R.id.chartJumlah);
            tvAwal = itemView.findViewById(R.id.chartTglAwal);
            tvAkhir = itemView.findViewById(R.id.chartTglAkhir);
            tvDesa = itemView.findViewById(R.id.chartKomoditasDesa);
            tvKecamatan = itemView.findViewById(R.id.chartKomoditasKecamatan);
        }
    }
}
