package api.giybat.uz.repository;

import api.giybat.uz.entity.ProfileRoleEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;

import java.beans.Transient;

public interface ProfileRoleRepository extends CrudRepository<ProfileRoleEntity, Integer> {

    @Transactional
    @Modifying
    void deleteByProfileId(Integer profileId);




}
