package algonquin.cst2335.androidfinalproject.recipe;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {RecipeDetail.class}, version = 1)
public abstract class RecipeDatabase extends RoomDatabase {
    public abstract RecipeDetailDAO cmDAO();
}
