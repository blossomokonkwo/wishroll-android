package co.wishroll;

import javax.inject.Singleton;

import co.wishroll.models.networking.RetrofitInstance;
import co.wishroll.models.repository.AuthRepository;
import co.wishroll.models.repository.SearchRepository;
import co.wishroll.models.repository.PostRepository;
import co.wishroll.models.repository.UserRepository;
import co.wishroll.models.repository.local.SessionManagement;
import dagger.Component;

@Singleton
@Component(modules = RetrofitInstance.class)
public interface ApplicationGraph {

     AuthRepository authRepository();

     SessionManagement sessionManagement();

     SearchRepository searchRepository();

     UserRepository userRepository();

     PostRepository postRepository();










}
