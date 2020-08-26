package co.wishroll.viewmodel;

import androidx.lifecycle.ViewModel;

import co.wishroll.models.repository.MainRepository;

import static co.wishroll.WishRollApplication.applicationGraph;

public class MainViewModel extends ViewModel {

    MainRepository mainRepository = applicationGraph.mainRepository();

    //loads profile picture


}
