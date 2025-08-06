package api.giybat.uz.service;

import api.giybat.uz.entity.SmsHistoryEntity;
import api.giybat.uz.enums.AppLanguage;
import api.giybat.uz.enums.SmsType;
import api.giybat.uz.exps.AppBadException;
import api.giybat.uz.repository.SmsHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SmsHistoryService {
    @Autowired
    private SmsHistoryRepository smsHistoryRepository;
    @Autowired
    private ResourceBundleService bundleService;

    public void create(String phoneNumber, String message, String code, SmsType smsType) {
        SmsHistoryEntity entity = new SmsHistoryEntity();
        entity.setPhone(phoneNumber);
        entity.setMessage(message);
        entity.setCode(code);
        entity.setSmsType(smsType);
        entity.setAttemptCount(0);
        entity.setCreatedDate(LocalDateTime.now());
        smsHistoryRepository.save(entity);
    }


    public Long getSmsCount(String phone) {
        LocalDateTime now = LocalDateTime.now();
        return smsHistoryRepository.countByPhoneAndCreatedDateBetween(phone, now.minusMinutes(1), now);
    }

    public void check(String phoneNumber, String code, AppLanguage lang) {
        Optional<SmsHistoryEntity> optional = smsHistoryRepository.findTop1ByPhoneOrderByCreatedDateDesc(phoneNumber);
        if (optional.isEmpty()) {
            throw new AppBadException(bundleService.getMessage("verification.failed", lang));
        }
        // check code
        SmsHistoryEntity entity = optional.get();
        // attempt count
        if (entity.getAttemptCount() >= 3) {
            throw new AppBadException(bundleService.getMessage("verification.failed", lang));
        }
        if (!entity.getCode().equals(code)) {
            smsHistoryRepository.updateAttemptCount(entity.getId());
            throw new AppBadException(bundleService.getMessage("verification.failed", lang));
        }
        // check time
        LocalDateTime expDate = entity.getCreatedDate().plusMinutes(2);
        if (LocalDateTime.now().isAfter(expDate)) { // not valid
            throw new AppBadException(bundleService.getMessage("verification.failed", lang));
        }
    }


}
