package algonquin.cst2335.androidfinalproject.sunLookup;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import algonquin.cst2335.androidfinalproject.sunLookup.Dao.FavoriteLocation;

/**
 * ViewModel class to manage FavoriteLocation data for the LocationFragment.
 */
public class LocationViewModel extends ViewModel {

    /**
     * MutableLiveData to hold a list of FavoriteLocation objects.
     */
    public MutableLiveData<ArrayList<FavoriteLocation>> favoriteLocations = new MutableLiveData<>();
}