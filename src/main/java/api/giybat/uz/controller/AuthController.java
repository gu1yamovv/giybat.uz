package api.giybat.uz.controller;

import api.giybat.uz.dto.AuthDTO;
import api.giybat.uz.dto.ProfileDTO;
import api.giybat.uz.dto.RegistrationDTO;
import api.giybat.uz.dto.sms.SmsVerificationDTO;
import api.giybat.uz.enums.AppLanguage;
import api.giybat.uz.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

//    @PostMapping("/registration")
//    public ResponseEntity<String> registration(@Valid @RequestBody RegistrationDTO dto) {
//        authService.registration(dto);
//        return ResponseEntity.ok().body(authService.registration(dto));
//    }

    @PostMapping("/registration")
    public ResponseEntity<String> registration(@Valid @RequestBody RegistrationDTO dto,
                                               @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage lang) {
        String response = authService.registration(dto, lang);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/registration/email/verification/{token}")
    public ResponseEntity<String> emailVerification(@PathVariable("token") String token,
                                                    @RequestParam(value = "lang", defaultValue = "UZ") AppLanguage lang) {
        return ResponseEntity.ok().body(authService.registrationEmailVerification(token, lang));
    }


    @PostMapping("/registration/sms/verification")
    public ResponseEntity<ProfileDTO> smsVerification(@Valid @RequestBody SmsVerificationDTO dto,
                                                      @RequestParam(value = "lang", defaultValue = "UZ") AppLanguage lang) {
        return ResponseEntity.ok().body(authService.RegistrationSmsVerification(dto, lang));
    }


    @PostMapping("/login")
    public ResponseEntity<ProfileDTO> login(@Valid @RequestBody AuthDTO dto,
                                            @RequestHeader(value = "Accept-Language", defaultValue = "UZ") AppLanguage lang) {
        return ResponseEntity.ok().body(authService.login(dto, lang));
    }

    //resend


}
