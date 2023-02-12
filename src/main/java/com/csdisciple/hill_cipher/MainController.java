package com.csdisciple.hill_cipher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cipher")
public class MainController {
    @Autowired
    private HillCipherService calculationService;

    @GetMapping(value = "/encrypt", produces = "application/json")
    public String getEncryptedMessage(@RequestParam String message) {
        return calculationService.encrypt(message);

    }

    @GetMapping(value = "/decrypt", produces = "application/json")
    public String getEncryptedMessageNumber(@RequestParam String key, String message) {
        return calculationService.decrypt(key, message);
    }

}
