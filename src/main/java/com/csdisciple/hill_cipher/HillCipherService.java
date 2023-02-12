package com.csdisciple.hill_cipher;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Locale;
import java.util.Random;

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
        String key = generateRandomKey(messageLength);
        double[][] keyMatrix = convertKeyToIntMatrix(key);
        double[][] messageMatrix = convertMessageToIntMatrix(message);
        multiplyMatrices(keyMatrix, messageMatrix);


        // convert key to a messageLength x messageLength array
        // convert message to a 1 x message.length array
        // multiply them
        // mod  26 the result
        // convert array to a char array
        // return to user;
        // INDArray matrixMultiply = eKey.mmul(eMessage);


        return multiplyMatrices(keyMatrix, messageMatrix);
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

    public char decryptNumberToChar(double number) {
        return number > 0 && number < 27 ? Character.valueOf((char) ((int) number + 'a' - 1)) : null;
    }

    // inverse of the encrypted key matrix mod 26 * the encrypted message matrix
    public String decrypt(String key, String message) {
        return "I have a boyfriend!";
    }

    // generate random key based on message size EX: given message size is 3, key size is 3^2
    public String generateRandomKey(int messageLength) {
        StringBuilder sb = new StringBuilder();
        Random r = new Random();
        int keySize = (int) Math.pow(messageLength, 2);
        for (int i = 0; i < keySize; i++) {
            sb.append(decryptNumberToChar(r.nextInt(26) + 1));
        }
        return sb.toString();
    }

    public double[][] convertKeyToIntMatrix(String key) {
        int row = (int) Math.sqrt((double) key.length());
        int col = row;
        double[][] keyMatrix = new double[row][col];
        int countStringIndex = 0;

        for (int i = 0; i < keyMatrix.length; i++) {
            for (int y = 0; y < keyMatrix[i].length; y++) {
                // convert each char into int and save to matrix;
                keyMatrix[i][y] = encryptCharToNumber(key.charAt(countStringIndex));
                countStringIndex++; // keep going through the string;
            }
        }
        return keyMatrix;
    }

    public double[][] convertMessageToIntMatrix(String message) {
        double[][] matrix = new double[message.length()][1];
        for (int i = 0; i < matrix.length; i++) {
            matrix[i][0] = encryptCharToNumber(message.charAt(i));
        }
        return matrix;
    }

    // multiply a 3x3 by 3x1
    private String multiplyMatrices(double[][] keyMatrix, double[][] messageMatrix) {
        // multiply all col of every row by all rows and then sum, then mod 26 of sum
        double[][] ans = new double[messageMatrix.length][1];
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < keyMatrix.length; row++) {
            for (int col = 0; col < messageMatrix[0].length; col++) {
                ans[row][0] += keyMatrix[row][col] * messageMatrix[row][0];
            }
            ans[row][0] = ans[row][0] % 26;
            sb.append(decryptNumberToChar(ans[row][0]));
        }


        return sb.toString();
    }
}
