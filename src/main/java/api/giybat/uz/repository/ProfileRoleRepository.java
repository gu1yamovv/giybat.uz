package api.giybat.uz.repository;

import api.giybat.uz.entity.ProfileRoleEntity;
import api.giybat.uz.enums.ProfileRole;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.beans.Transient;
import java.util.List;

public interface ProfileRoleRepository extends CrudRepository<ProfileRoleEntity, Integer> {

    @Transactional
    @Modifying
    void deleteByProfileId(Integer profileId);


    @Query("Select  p.roles From ProfileRoleEntity p where p.profileId = ?1 ")
    List<ProfileRole> getAllRolesListByProfileId(Integer profileId);

}
