package com.csdisciple.hill_cipher;

import org.junit.jupiter.api.Test;
import org.la4j.Matrix;
import org.la4j.matrix.dense.Basic2DMatrix;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class HillCipherServiceTest {
// tests are just too simplistic, only asserting length
    // need to test for edge cases
        // make sure we only work with lower case, non null, 1-26, a-z
            // nothing else
    @Autowired
    private HillCipherService service;

    @Test
    public void testDecryptChar(){
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
        int[][] matrix = service.convertKeyToIntMatrix(randomKey);

        assertEquals(keyLength, matrix.length);


    }

    @Test
    public void convertMessageToIntMatrix(){
        int keyLength = 3;
        // generates a key of length keyLength * keyLength
        String randomKey = service.generateRandomKey(keyLength);
        int[][] matrix = service.convertMessageToIntMatrix(randomKey);

        assertEquals(randomKey.length(), matrix.length);
    }

    @Test
    public void encrypt(){
        String message = service.encrypt("hellomynameis");
        assertEquals(message.length(), "hellomynameis".length());
    }
    @Test
    public void decrypt(){
        String message = service.decrypt("hellomynameis");
        assertEquals(message.length(), "hellomynameis".length());
    }
//
//    @Test
//    public void mod26Matrix(){
//        Matrix a = new Basic2DMatrix(new int[][]{
//                { 100.0, 250.0, 39.0 },
//                { 48.0, 54.0, 65.0 },
//                { 77.0, 86.0, 95.0 }
//        });
//        String matrix = service.mod26Matrix(a).toString();
//        System.out.println(matrix);
//    }

}
