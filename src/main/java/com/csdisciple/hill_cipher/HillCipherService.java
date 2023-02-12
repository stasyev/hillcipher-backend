package com.csdisciple.hill_cipher;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Locale;

/*
 * Step one: convert the message to a 1D array
 * Step two: convert key to a 2D array
 * Multiply 2Dx1D = result1DArray mod 26 (%26)
 * Then convert number array to letters
 * Output encrypted result as String
 * */

/*
 * To Decrypt
 * Take an inverse of the key matrix and then mod 26
 * multiply the inverse matrix by the encrypted message
 * Then mod 26 the one dimensional matrix to get decrypted message
 *
 * */
@Service
public class HillCipherService {
    public String encrypt(String message) {
        int messageLength = message.length();
        String key = "GYBNQKURP";
        String messageNumberArray = encryptToNumber(message.trim().toLowerCase());


        INDArray eMessage = Nd4j.create(messageNumberArray);
       // INDArray matrixMultiply = eKey.mmul(eMessage);
        return message;
    }

    public String encryptToNumber(String message) {
        int[] array = new int[message.length()];
        for (int i = 0; i < message.length(); i++) {
            array[i] = encryptCharToNumber(message.toLowerCase(Locale.ROOT).charAt(i));
        }
        StringBuilder stringBuilder = new StringBuilder();
        Arrays.stream(array).forEach(n -> stringBuilder.append(n + " "));
        return stringBuilder.toString();
    }

    public int encryptCharToNumber(char letter) {
        return letter - 'a' + 1;
    }
    public char decryptNumberToChar(int number){
        return number > 0 && number < 27 ? Character.valueOf((char)(number + 'a' - 1)) : null;
    }

    // inverse of the encrypted key matrix mod 26 * the encrypted message matrix
    public String decrypt(String message) {
        return "I have a boyfriend!";
    }
}
