package com.csdisciple.hill_cipher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/cipher")
class MainController {
    @Autowired
    private HillCipherService hillCipherService;

    @GetMapping(value = "/encrypt", produces = "application/json")

    public @ResponseBody ResponseTransfer getEncryptedMessage(@RequestParam String message) {
        return new ResponseTransfer(hillCipherService.encrypt(message));

    }

    @GetMapping(value = "/decrypt", produces = "application/json")

    public @ResponseBody ResponseTransfer getEncryptedMessageNumber(@RequestParam String message) {
        return new ResponseTransfer(hillCipherService.decrypt(message));
    }

}
