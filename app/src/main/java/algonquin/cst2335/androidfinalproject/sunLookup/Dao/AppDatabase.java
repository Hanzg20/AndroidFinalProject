package algonquin.cst2335.androidfinalproject.sunLookup.Dao;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {FavoriteLocation.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract FavoriteLocationDao favoriteLocationDao();

    // Singleton instance of the database
    private static AppDatabase instance;

    // Method to get the singleton instance of the database
    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "app_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}