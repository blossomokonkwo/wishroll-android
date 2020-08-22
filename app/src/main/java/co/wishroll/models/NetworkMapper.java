package co.wishroll.models;

import co.wishroll.models.domainmodels.User;
import co.wishroll.models.repository.datamodels.UserModel;

public class NetworkMapper implements EntityMapper{

    @Override
    public User fromNetToDomain(UserModel userModel) {
        return null;
    }

    @Override
    public UserModel fromDomainToNet(User user) {
        return null;
    }
}
