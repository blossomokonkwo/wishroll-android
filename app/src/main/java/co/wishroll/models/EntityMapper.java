package co.wishroll.models;

import co.wishroll.models.domainmodels.User;
import co.wishroll.models.repository.datamodels.UserModel;

public interface EntityMapper {



     //User
     User fromNetToDomain(UserModel userModel);
     UserModel fromDomainToNet(User user);
}
