package com.csdisciple.hill_cipher;

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
    int[][] keyMatrix, messageMatrix, encryptedKeyMatrix;

    public static int[][] invert(int a[][]) {

        int n = a.length;

        int x[][] = new int[n][n];

        int b[][] = new int[n][n];

        int index[] = new int[n];

        for (int i = 0; i < n; ++i)

            b[i][i] = 1;


        // Transform the matrix into an upper triangle

        gaussian(a, index);


        // Update the matrix b[i][j] with the ratios stored

        for (int i = 0; i < n - 1; ++i)

            for (int j = i + 1; j < n; ++j)

                for (int k = 0; k < n; ++k)

                    b[index[j]][k]

                            -= a[index[j]][i] * b[index[i]][k];


        // Perform backward substitutions

        for (int i = 0; i < n; ++i) {

            x[n - 1][i] = b[index[n - 1]][i] / a[index[n - 1]][n - 1];

            for (int j = n - 2; j >= 0; --j) {

                x[j][i] = b[index[j]][i];

                for (int k = j + 1; k < n; ++k) {

                    x[j][i] -= a[index[j]][k] * x[k][i];

                }

                x[j][i] /= a[index[j]][j];

            }

        }

        return x;

    }

    public static void gaussian(int a[][], int index[]) {

        int n = index.length;

        int c[] = new int[n];


        // Initialize the index

        for (int i = 0; i < n; ++i)

            index[i] = i;


        // Find the rescaling factors, one from each row

        for (int i = 0; i < n; ++i) {

            int c1 = 0;

            for (int j = 0; j < n; ++j) {

                int c0 = Math.abs(a[i][j]);

                if (c0 > c1) c1 = c0;

            }

            c[i] = c1;

        }


        // Search the pivoting element from each column

        int k = 0;

        for (int j = 0; j < n - 1; ++j) {

            int pi1 = 0;

            for (int i = j; i < n; ++i) {

                int pi0 = Math.abs(a[index[i]][j]);

                pi0 /= c[index[i]];

                if (pi0 > pi1) {

                    pi1 = pi0;

                    k = i;

                }

            }


            // Interchange rows according to the pivoting order

            int itmp = index[j];

            index[j] = index[k];

            index[k] = itmp;

            for (int i = j + 1; i < n; ++i) {

                int pj = a[index[i]][j] / a[index[j]][j];


                // Record pivoting ratios below the diagonal

                a[index[i]][j] = pj;


                // Modify other elements accordingly

                for (int l = j + 1; l < n; ++l)

                    a[index[i]][l] -= pj * a[index[j]][l];

            }

        }

    }

    public String encrypt(String message) {
        int messageLength = message.length();
        String messageLowerCase = message.toLowerCase(Locale.ROOT);
        key = generateRandomKey(messageLength);
        keyMatrix = convertKeyToIntMatrix(key);
        messageMatrix = convertMessageToIntMatrix(messageLowerCase);
        encryptedKeyMatrix = multiplyMatrices(keyMatrix, messageMatrix);
        encryptedKeyMatrix = mod26Matrix(encryptedKeyMatrix); // returning a matrix with zeros
        encryptedMessage = convertMatrixToString(encryptedKeyMatrix);


        // convert key to a messageLength x messageLength array
        // convert message to a 1 x message.length array
        // multiply them
        // mod  26 the result
        // convert array to a char array
        // return to user


        return encryptedMessage;
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
        return Integer.valueOf(letter - 'a' + 1);
    }

    public char decryptNumberToChar(int number) {
        return Character.valueOf((char) (number == 0 ? number + 1 : number + 'a' - 1));
    }

    // inverse of the encrypted key matrix mod 26 * the encrypted message vector
    public String decrypt(String message) {
        // get inverse keyMatrix mod 26
        // multiply by message
        int[][] matrix = invert(keyMatrix);
        matrix = mod26Matrix(matrix);
        matrix = multiplyMatrices(matrix, encryptedKeyMatrix);

        return convertMatrixToString(matrix);
    }

    public int[][] mod26Matrix(int[][] matrix) {
        int[][] ans = new int[matrix.length][1];
        for (int row = 0; row < matrix.length; row++) {
            ans[row][0] = matrix[row][0] % 26;
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

    public int[][] convertKeyToIntMatrix(String key) {
        int row = (int) Math.sqrt((int) key.length());
        int col = row;
        int[][] keyMatrix = new int[row][col];
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

    public int[][] convertMessageToIntMatrix(String message) {
        int[][] matrix = new int[message.length()][1];
        for (int i = 0; i < matrix.length; i++) {
            matrix[i][0] = encryptCharToNumber(message.charAt(i));
        }
        return matrix;
    }

    // multiply a 3x3 by 3x1
    // this method does two things which is not legal based on SOLID principles
    // REFACTOR
    private int[][] multiplyMatrices(int[][] keyMatrix, int[][] messageMatrix) {
        // multiply all col of every row by all rows and then sum, then mod 26 of sum
        int[][] ans = new int[messageMatrix.length][1];
        for (int row = 0; row < keyMatrix.length; row++) {
            for (int col = 0; col < messageMatrix[0].length; col++) {
                ans[row][0] += keyMatrix[row][col] * messageMatrix[row][0];
            }
        }

        return ans;
    }


// Method to carry out the partial-pivoting Gaussian

// elimination.  Here index[] stores pivoting order.

    // x by 1 matrix
    private String convertMatrixToString(int[][] matrix) {
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < matrix.length; row++) {
            sb.append(decryptNumberToChar(matrix[row][0]));
        }
        return sb.toString();
    }

}
