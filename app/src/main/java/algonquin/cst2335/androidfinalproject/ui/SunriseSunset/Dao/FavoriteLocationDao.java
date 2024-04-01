package algonquin.cst2335.androidfinalproject.ui.SunriseSunset.Dao;


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

    @Update
    void update(FavoriteLocation favoriteLocation);

    @Delete
    void delete(FavoriteLocation favoriteLocation);

    @Query("SELECT * FROM favorite_locations")
    List<FavoriteLocation> getAllFavoriteRecipes();

    @Query("SELECT * FROM favorite_locations WHERE id = :id")
    FavoriteLocation getFavoritelocationById(int id);
}
