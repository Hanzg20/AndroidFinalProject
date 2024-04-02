//package algonquin.cst2335.androidfinalproject.sunLookup.Adapter;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.List;
//
//import algonquin.cst2335.androidfinalproject.R;
//import algonquin.cst2335.androidfinalproject.sunLookup.Dao.FavoriteLocation;
//
//public class FavoriteLocationAdapter extends RecyclerView.Adapter<FavoriteLocationAdapter.FavoriteLocationViewHolder> {
//
//    private List<FavoriteLocation> favoriteLocations;
//    private OnItemClickListener listener;
//
//    public FavoriteLocationAdapter(List<FavoriteLocation> favoriteLocations, OnItemClickListener listener) {
//        this.favoriteLocations = favoriteLocations;
//        this.listener = listener;
//    }
//
//    @NonNull
//    @Override
//    public FavoriteLocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_location, parent, false);
//        return new FavoriteLocationViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull FavoriteLocationViewHolder holder, int position) {
//        FavoriteLocation location = favoriteLocations.get(position);
//        holder.bind(location, listener);
//    }
//
//    @Override
//    public int getItemCount() {
//        return favoriteLocations.size();
//    }
//
//    public static class FavoriteLocationViewHolder extends RecyclerView.ViewHolder {
//        TextView textLocationName, textLatitude, textLongitude;
//        Button btnDelete, btnView;
//
//        public FavoriteLocationViewHolder(@NonNull View itemView) {
//            super(itemView);
//            textLocationName = itemView.findViewById(R.id.textLocationName);
//            textLatitude = itemView.findViewById(R.id.textLatitude);
//            textLongitude = itemView.findViewById(R.id.textLongitude);
//            btnDelete = itemView.findViewById(R.id.btnDelete);
//            btnView = itemView.findViewById(R.id.btnView);
//        }
//
//        public void bind(FavoriteLocation location, OnItemClickListener listener) {
//
//            textLatitude.setText(String.valueOf(location.getLatitude()));
//            textLongitude.setText(String.valueOf(location.getLongitude()));
//
//            // Set click listeners for delete and view buttons
//            btnDelete.setOnClickListener(v -> listener.onDeleteClick(location));
//            btnView.setOnClickListener(v -> listener.onViewClick(location));
//        }
//    }
//
//    // Interface for handling item click events
//    public interface OnItemClickListener {
//        void onDeleteClick(FavoriteLocation location);
//        void onViewClick(FavoriteLocation location);
//    }
//}
