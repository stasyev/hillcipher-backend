package com.csdisciple.hill_cipher;

import Jama.Matrix;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Locale;
import java.util.Random;

/*
 * Step one: convert the message to a 2D array
 * Step two: convert key to a 2D array
 * Multiply 2Dx2D = result2DArray mod 26 (%26)
 * Then convert number array to letters
 * Output encrypted result as String
 * */

/*
 * To Decrypt
 * Take an inverse of the key matrix and then mod 26
 * multiply the inverse matrix by the encrypted message
 * Then mod 26 the matrix to get decrypted message
 *
 * */
@Service
public class HillCipherService {
    String key, encryptedMessage;
    Matrix keyMatrix, messageMatrix, encryptedKeyMatrix;


    public String encrypt(String message) {
        int messageLength = message.length();
        String messageLowerCase = message.toLowerCase(Locale.ROOT);
        key = generateRandomKey(messageLength);
        keyMatrix = convertKeyToMatrix(key);
        messageMatrix = convertMessageToIntMatrix(messageLowerCase);
        encryptedKeyMatrix = keyMatrix.times(messageMatrix);
        encryptedKeyMatrix = mod26OfMatrix(encryptedKeyMatrix); // returning a matrix with zeros
        encryptedMessage = toString(encryptedKeyMatrix);


        // convert key to a messageLength x messageLength array
        // convert message to a 1 x message.length array
        // multiply them
        // mod  26 the result
        // convert array to a char array
        // return to user


        return encryptedMessage;
    }

    public int encryptCharToNumber(char letter) {
        return Integer.valueOf(letter - 'a' + 1);
    }

    public char decryptNumberToChar(double number) {
        return Character.valueOf((char) (number == 0 ? number + 1 : number + 'a' - 1));
    }

    // inverse of the encrypted key matrix mod 26 * the encrypted message vector
    public String decrypt(String message) {
        // get inverse keyMatrix mod 26
        // multiply by message
        Matrix matrix = keyMatrix;
        // 1/determinant(matrix) * adjugate(matrix) or transpose(matrix)
        matrix = matrix.inverse();
        matrix = mod26OfMatrix(matrix);
        matrix = matrix.times(encryptedKeyMatrix);


        return toString(matrix);
    }

    // 5 x 1 returns
//    public Matrix mod26Matrix(Matrix matrix) {
//        Matrix ans = new Matrix(matrix.getRowDimension(), matrix.getColumnDimension());
//        for (int row = 0; row < matrix.getRowDimension(); row++) {
//            ans.set(row,0, matrix.get(row, 0) % 26);
//        }
//        return ans;
//    }

    public Matrix mod26OfMatrix(Matrix matrix) {
        Matrix ans = new Matrix(matrix.getRowDimension(), matrix.getColumnDimension());
        for (int row = 0; row < matrix.getRowDimension(); row++) {
            for (int col = 0; col < matrix.getColumnDimension(); col++) {
                ans.set(row,col, matrix.get(row, col) % 26);
            }

        }
        return ans;
    }

    // generate random key based on message size EX: given message size is 3, key size is 3^2
    public String generateRandomKey(int messageLength) {
        StringBuilder sb = new StringBuilder();
        Random r = new Random();
        int keySize = (int) Math.pow(messageLength, 2);
        for (int i = 0; i < keySize; i++) {
            sb.append(decryptNumberToChar(r.nextInt(27 - 1) + 1));
        }
        return sb.toString();
    }

    public Matrix convertKeyToMatrix(String key) {
        int row = (int) Math.sqrt((int) key.length());
        int col = row;
        Matrix keyMatrix = new Matrix(row, col);
        int countStringIndex = 0;

        for (int i = 0; i < keyMatrix.getRowDimension(); i++) {
            for (int y = 0; y < keyMatrix.getColumnDimension(); y++) {
                // convert each char into int and save to matrix;
                keyMatrix.set(i, y, encryptCharToNumber(key.charAt(countStringIndex)));
                countStringIndex++; // keep going through the string;
            }
        }
        return keyMatrix;
    }

    public Matrix convertMessageToIntMatrix(String message) {
        Matrix matrix = new Matrix(message.length(), 1);
        for (int i = 0; i < matrix.getRowDimension(); i++) {
            matrix.set(i, 0, encryptCharToNumber(message.charAt(i)));
        }
        return matrix;
    }

    // we usually just want to toString a x by 1 matrix hence we are only concerned with the first column
    public String toString(Matrix matrix){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < matrix.getRowDimension(); i++){
            sb.append(decryptNumberToChar(matrix.get(i, 0)));
        }
        return sb.toString();
    }

}
