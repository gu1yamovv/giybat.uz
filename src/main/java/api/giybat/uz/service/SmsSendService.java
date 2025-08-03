package api.giybat.uz.service;

import api.giybat.uz.dto.sms.SmsAuthDTO;
import api.giybat.uz.dto.sms.SmsAuthResponseDTO;
import api.giybat.uz.entity.SmsProviderTokenHolderEntity;
import api.giybat.uz.repository.SmsProviderTokenHolderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SmsSendService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private SmsProviderTokenHolderRepository smsProviderTokenHolderRepository;

    @Value("${eskiz.url}")
    private String smsUrl;
    @Value("${eskiz.login}")
    private String accountLogin;
    @Value("${eskiz.password}")
    private String accountPassword;

    public String sendSms(String phoneNumber, String message) {
        return null;
    }

    public String getToken() {
        Optional<SmsProviderTokenHolderEntity> optional = smsProviderTokenHolderRepository.findTop1By();
        if (optional.isEmpty()) {// if token not exists
            String token = getTokenFromProvider();
            SmsProviderTokenHolderEntity entity = new SmsProviderTokenHolderEntity();
            entity.setToken(token);
            entity.setCreatedDate(LocalDateTime.now());
            entity.setExpiredDate(LocalDateTime.now().plusMonths(1));
            smsProviderTokenHolderRepository.save(entity);
            return token;
        }
        //if exists check
        SmsProviderTokenHolderEntity entity = optional.get();
        if (LocalDateTime.now().isBefore(entity.getExpiredDate())) {
            return entity.getToken();
        }
        // get new token and update
        String token = getTokenFromProvider();
        entity.setToken(token);
        entity.setCreatedDate(LocalDateTime.now());
        entity.setExpiredDate(LocalDateTime.now().plusMonths(1));
        smsProviderTokenHolderRepository.save(entity);
        return token;

    }

    public String getTokenFromProvider() {
        SmsAuthDTO smsAuthDTO = new SmsAuthDTO();
        smsAuthDTO.setEmail(accountLogin);
        smsAuthDTO.setPassword(accountPassword);


        try {
            System.out.println("--- new SmsSender new Token was taken ---");
            SmsAuthResponseDTO response = restTemplate.postForObject(smsUrl + "/auth/login", smsAuthDTO, SmsAuthResponseDTO.class);
            System.out.println(response.getData().getToken());
            return response.getData().getToken();
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }


    }

}
