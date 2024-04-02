//package algonquin.cst2335.androidfinalproject.sunLookup.Adapter;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//import java.util.List;
//
//import algonquin.cst2335.androidfinalproject.R;
//import algonquin.cst2335.androidfinalproject.sunLookup.Model.SunriseSunsetData;
//
//public class SunriseSunsetAdapter extends RecyclerView.Adapter<SunriseSunsetAdapter.ViewHolder> {
//
//    private List<SunriseSunsetData> sunriseSunsetDataList;
//
//    public SunriseSunsetAdapter(List<SunriseSunsetData> sunriseSunsetDataList) {
//        this.sunriseSunsetDataList = sunriseSunsetDataList;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_sun_lookup, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        SunriseSunsetData data = sunriseSunsetDataList.get(position);
//        holder.dateTextView.setText(data.getDate());
//        holder.sunriseTextView.setText(data.getSunrise());
//        holder.sunsetTextView.setText(data.getSunset());
//    }
//
//    @Override
//    public int getItemCount() {
//        return sunriseSunsetDataList.size();
//    }
//
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        TextView dateTextView;
//        TextView sunriseTextView;
//        TextView sunsetTextView;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            dateTextView = itemView.findViewById(R.id.dateTextView);
//            sunriseTextView = itemView.findViewById(R.id.sunriseView);
//            sunsetTextView = itemView.findViewById(R.id.sunsetView);
//        }
//    }
//}
