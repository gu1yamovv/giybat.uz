package api.giybat.uz.service;

import api.giybat.uz.dto.sms.SmsAuthDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SmsSendService {

    @Autowired
    private RestTemplate restTemplate;
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
        SmsAuthDTO smsAuthDTO = new SmsAuthDTO();
        smsAuthDTO.setEmail(accountLogin);
        smsAuthDTO.setPassword(accountPassword);
        restTemplate.postForObject(smsUrl + "/auth/login", null, String.class);
        return null;
    }

}
