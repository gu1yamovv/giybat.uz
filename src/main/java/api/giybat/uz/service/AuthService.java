package api.giybat.uz.service;

import api.giybat.uz.dto.AuthDTO;
import api.giybat.uz.dto.ProfileDTO;
import api.giybat.uz.dto.RegistrationDTO;
import api.giybat.uz.entity.ProfileEntity;
import api.giybat.uz.entity.ProfileRoleEntity;
import api.giybat.uz.enums.GeneralStatus;
import api.giybat.uz.enums.ProfileRole;
import api.giybat.uz.exps.AppBadException;
import api.giybat.uz.repository.ProfileRepository;
import api.giybat.uz.repository.ProfileRoleRepository;
import api.giybat.uz.util.JwtUtil;
import io.jsonwebtoken.JwtException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private ProfileRoleRepository profileRoleRepository;
    @Autowired
    private ProfileRoleService profileRoleService;
    @Autowired
    private EmailSendingService emailSendingService;
    @Autowired
    private ProfileService profileService;


    @Transactional
    public String registration(RegistrationDTO dto) {
        Optional<ProfileEntity> optional = profileRepository.findByUsernameAndVisibleTrue(dto.getUsername());
        if (optional.isPresent()) {
            ProfileEntity profile = optional.get();
            if (profile.getStatus().equals(GeneralStatus.IN_REGISTRATION)) {
                profileRoleService.deleteRoles(profile.getId());
                profileRepository.delete(profile);

            } else {
                throw new AppBadException("Username is already exits");
            }
        }
        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setUsername(dto.getUsername());
        entity.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        entity.setStatus(GeneralStatus.IN_REGISTRATION);
        entity.setVisible(true);
        entity.setCreatedDate(LocalDateTime.now());
        profileRepository.save(entity);
        profileRoleService.create(entity.getId(), ProfileRole.ROLE_USER);
        emailSendingService.sendRegistrationEmail(dto.getUsername(), entity.getId());
        return "Successfully registered";
    }

    public String regVerification(String token) {
        Integer profileId = JwtUtil.decodeRegVerToken(token);

        try {
            ProfileEntity profile = profileService.getById(profileId);
            if (profile.getStatus().equals(GeneralStatus.IN_REGISTRATION)) {
                profileRepository.changeStatus(profileId, GeneralStatus.ACTIVE);
                return "Verification finished";
            }

        } catch (JwtException e) {

        }

        throw new AppBadException("Verification Failed");
    }

    public ProfileDTO login(AuthDTO dto) {
        Optional<ProfileEntity> optional = profileRepository.findByUsernameAndVisibleTrue(dto.getUsername());
        if (optional.isEmpty()) {
            throw new AppBadException("Username or password is wrong");
        }
        ProfileEntity profile = optional.get();
        if (!bCryptPasswordEncoder.matches(dto.getPassword(), profile.getPassword())) {
            throw new AppBadException("Username or password is wrong");
        }
        if (!profile.getStatus().equals(GeneralStatus.ACTIVE)) {
            throw new AppBadException("Status is wrong");
        }

        ProfileDTO response = new ProfileDTO();
        response.setName(profile.getName());
        response.setUsername(profile.getUsername());
        response.setRoleList(profileRoleRepository.getAllRolesListByProfileId(profile.getId()));
        response.setJwtToken(JwtUtil.encode(profile.getId(), response.getRoleList()));

        return response;
    }


}
