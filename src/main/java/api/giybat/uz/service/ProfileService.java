package api.giybat.uz.service;

import api.giybat.uz.entity.ProfileEntity;
import api.giybat.uz.exps.AppBadException;
import api.giybat.uz.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    public ProfileEntity getById(int id) {
//        Optional<ProfileEntity> optional = profileRepository.findByIdAndVisibleTrue(id);
//        if (optional.isEmpty()) {
//            throw new AppBadException("Profile not found");
//        }

        return profileRepository.findById(id).orElseThrow(() -> {
            throw new AppBadException("Profile Not Found");
        });


    }


}
