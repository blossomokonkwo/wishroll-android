package co.wishroll.viewmodel;

import androidx.lifecycle.ViewModel;

import co.wishroll.models.repository.MainRepository;
import co.wishroll.models.repository.local.SessionManagement;

import static co.wishroll.WishRollApplication.applicationGraph;

public class MainViewModel extends ViewModel {

    MainRepository mainRepository = applicationGraph.mainRepository();
    SessionManagement sessionManagement = applicationGraph.sessionManagement();







}




