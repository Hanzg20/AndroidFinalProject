package algonquin.cst2335.androidfinalproject.sunLookup;


import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import algonquin.cst2335.androidfinalproject.sunLookup.Dao.FavoriteLocation;

public class LocationViewModel extends ViewModel {
    public MutableLiveData<ArrayList<FavoriteLocation>> favoriteLocations = new MutableLiveData<>();
}