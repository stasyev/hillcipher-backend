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
        int encryptedChar = service.encryptCharToNumber('g');

        assertEquals('g', service.decryptNumberToChar(encryptedChar));
    }

    @Test
    public void encryptCharToNumber(){
        int encryptedChar = service.encryptCharToNumber('d');
        assertEquals(4, encryptedChar);
    }

    @Test
    public void getRandomKey(){
        int keyLength = 3;
        String randomKey = service.generateRandomKey(keyLength);
        assertEquals(Math.pow(keyLength, 2), randomKey.length());
    }

    @Test
    public void convertKeyToIntMatrix(){
        int keyLength = 3;
        String randomKey = service.generateRandomKey(keyLength);
        double[][] matrix = service.convertKeyToIntMatrix(randomKey);

        assertEquals(keyLength, matrix.length);


    }

    @Test
    public void convertMessageToIntMatrix(){
        int keyLength = 3;
        String randomKey = service.generateRandomKey(keyLength);
        double[][] matrix = service.convertMessageToIntMatrix(randomKey);

        assertEquals(keyLength*keyLength, matrix[0].length);
    }
}
