package algonquin.cst2335.androidfinalproject.sunLookup;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import algonquin.cst2335.androidfinalproject.R;
import algonquin.cst2335.androidfinalproject.sunLookup.Dao.FavoriteLocation;

public class FavoriteLocationAdapter extends RecyclerView.Adapter<FavoriteLocationAdapter.ViewHolder> {

    private List<FavoriteLocation> favoriteLocations;
    private LayoutInflater inflater;

    public FavoriteLocationAdapter(Context context, List<FavoriteLocation> favoriteLocations) {
        this.inflater = LayoutInflater.from(context);
        this.favoriteLocations = favoriteLocations;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.favorite_location, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FavoriteLocation favoriteLocation = favoriteLocations.get(position);
        holder.bind(favoriteLocation);
    }

    @Override
    public int getItemCount() {
        return favoriteLocations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView latitudeTextView;
        TextView longitudeTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            latitudeTextView = itemView.findViewById(R.id.latitudeTextView);
            longitudeTextView = itemView.findViewById(R.id.longitudeTextView);
        }

        public void bind(FavoriteLocation favoriteLocation) {
            latitudeTextView.setText(String.valueOf(favoriteLocation.getLatitude()));
            longitudeTextView.setText(String.valueOf(favoriteLocation.getLongitude()));
        }
    }
}
