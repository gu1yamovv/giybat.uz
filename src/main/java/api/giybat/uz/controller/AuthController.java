package api.giybat.uz.controller;

import api.giybat.uz.dto.AuthDTO;
import api.giybat.uz.dto.ProfileDTO;
import api.giybat.uz.dto.RegistrationDTO;
import api.giybat.uz.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<String> registration(@Valid @RequestBody RegistrationDTO dto) {
        String response = authService.registration(dto);
        return ResponseEntity.ok().body(response);
    }



    @GetMapping("/registration/verification/{token}")
    public ResponseEntity<String> regVerification(@PathVariable ("token") String token) {
        return ResponseEntity.ok().body(authService.regVerification(token));
    }



    @PostMapping("/login")
    public ResponseEntity<ProfileDTO> login(@Valid @RequestBody AuthDTO dto) {
        return ResponseEntity.ok().body(authService.login(dto));
    }

    //resend



}
