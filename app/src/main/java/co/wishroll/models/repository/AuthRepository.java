package co.wishroll.models.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;

import javax.inject.Inject;
import javax.inject.Singleton;

import co.wishroll.models.networking.RetrofitInstance;
import co.wishroll.models.networking.WishRollApi;
import co.wishroll.models.repository.datamodels.AuthResponse;
import co.wishroll.models.repository.datamodels.LoginRequest;
import co.wishroll.models.repository.datamodels.UserModel;
import co.wishroll.models.repository.local.SessionManagement;
import co.wishroll.utilities.AuthResource;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

import static co.wishroll.WishRollApplication.applicationGraph;

@Singleton
public class AuthRepository {
    //network calls related to registration


    public WishRollApi wishRollApi;
    SessionManagement sessionManagement = applicationGraph.sessionManagement();




    @Inject
    public AuthRepository() {
        Retrofit retrofitInstance = RetrofitInstance.getRetrofitInstance();
        wishRollApi = retrofitInstance.create(WishRollApi.class);

    }




    public LiveData<AuthResource<AuthResponse>> loginUser(final LoginRequest loginRequest){
        final LiveData<AuthResource<AuthResponse>> source = LiveDataReactiveStreams.fromPublisher(
                wishRollApi.loginUser(loginRequest)
                        .onErrorReturn(new Function<Throwable, AuthResponse>() {
                            @Override
                            public AuthResponse apply(Throwable throwable) throws Exception {
                                AuthResponse errorResponse = new AuthResponse();
                                UserModel userModel = new UserModel();
                                userModel.setId(0);
                                sessionManagement.printEverything("nasty location but we're inside the authrepository class, in an error clause");
                                errorResponse.setUserModel(userModel);
                                return errorResponse;
                            }
                        })
                        .onBackpressureLatest()
                        .map(new Function<AuthResponse, AuthResource<AuthResponse>>() {
                            @Override
                            public AuthResource<AuthResponse> apply(AuthResponse authResponse) throws Exception {
                                if (authResponse.getUserModel().getId() == 0) {
                                     sessionManagement.printEverything("annother really nasty location but we're also in an error clause, in the map addon");
                                    return AuthResource.error("Please enter the correct credentials", null);
                                } else {
                                    sessionManagement.printEverything("Hey!! we're in a success area! these the credentials that should be saved");
                                    return AuthResource.authenticated(authResponse);

                                }
                            }
                        })
                        .subscribeOn(Schedulers.io()));


        return source;
    }


}
