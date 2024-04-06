package algonquin.cst2335.androidfinalproject.sunLookup.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


@Dao
public interface FavoriteLocationDao {
    @Insert
    void insert(FavoriteLocation Location);

    /**
     * @param favoriteLocation
     */
    @Update
    void update(FavoriteLocation favoriteLocation);

    @Delete
    void delete(FavoriteLocation favoriteLocation);

    @Query("SELECT * FROM favorite_locations")
    List<FavoriteLocation> getAllFavoriteLocations();

    @Query("SELECT * FROM favorite_locations WHERE id = :id")
    FavoriteLocation getFavoritelocationById(int id);


    @Query("SELECT * FROM favorite_locations WHERE latitude = :latitude AND longitude = :longitude")
    FavoriteLocation getFavoriteLocationByLatLng(double latitude, double longitude);


    /**
     * Retrieves a FavoriteLocation entity from the database by its latitude and longitude.
     *
     * @param latitude  The latitude of the FavoriteLocation entity
     * @param longitude The longitude of the FavoriteLocation entity
     * @return The FavoriteLocation entity with the specified latitude and longitude, if found; otherwise null
     */
    @Query("SELECT * FROM favorite_locations WHERE latitude = :latitude AND longitude = :longitude")
    FavoriteLocation getFavoritelocationByLatLng(double latitude, double longitude);

    @Insert
    void addAll(List<FavoriteLocation> allFavoriteLocations);

}