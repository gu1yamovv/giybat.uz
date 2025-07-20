package api.giybat.uz.repository;

import api.giybat.uz.entity.ProfileEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProfileRepository extends CrudRepository<ProfileEntity,Integer >{

    Optional<ProfileEntity> findByUsernameVisibleTrue(String username);

}
