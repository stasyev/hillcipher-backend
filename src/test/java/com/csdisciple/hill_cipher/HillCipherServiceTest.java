package com.csdisciple.hill_cipher;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class HillCipherServiceTest {

    @Autowired
    private HillCipherService service;

    @Test
    public void testDecrypt(){
        int encryptedChar = service.encryptCharToNumber('a');

        assertEquals('z', service.decryptNumberToChar(26));
    }

    @Test
    public void encryptCharToNumber(){
        int encryptedChar = service.encryptCharToNumber('a');
        int encryptedCharTwo = service.encryptCharToNumber('a');
        assertEquals(encryptedChar, 1);
    }
}
