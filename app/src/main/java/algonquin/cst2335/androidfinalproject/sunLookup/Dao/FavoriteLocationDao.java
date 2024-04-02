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
    void insert(FavoriteLocation favoriteLocation);

    /**
     * @param favoriteLocation
     */
    @Update
    void update(FavoriteLocation favoriteLocation);

    @Delete
    void delete(FavoriteLocation favoriteLocation);

    @Query("SELECT * FROM favorite_locations")
    List<FavoriteLocation> getAllFavoriteRecipes();

    @Query("SELECT * FROM favorite_locations WHERE id = :id")
    FavoriteLocation getFavoritelocationById(int id);


    @Query("SELECT * FROM favorite_locations WHERE latitude = :lat AND longitude = :lon")
    FavoriteLocation getFavoriteLocationByLatLng(double lat, double lon);

    FavoriteLocation getFavoritelocationByLatLng(double latitude,
                                                 double longitude);
}