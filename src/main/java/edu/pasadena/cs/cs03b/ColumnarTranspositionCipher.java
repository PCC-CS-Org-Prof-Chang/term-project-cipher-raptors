package edu.pasadena.cs.cs03b;

import java.util.Arrays;
import java.util.Comparator;

public class ColumnarTranspositionCipher extends Cipher {

    // private String text = "";
    protected StringBuilder encryptedText = new StringBuilder();
    protected StringBuilder decryptedText = new StringBuilder();
    protected char[][] matrix = null;

    public ColumnarTranspositionCipher() {
    }

    public ColumnarTranspositionCipher(String numericKey) {
        this.numericKey = numericKey;
    }

    public String getEncryptedText() {
        return this.encryptedText.toString();
    }

    public String getDecryptedText() {
        return this.decryptedText.toString();
    }

    public char[][] getMatrix() {
        return this.matrix;
    }

    @Override
    protected Boolean verify(String numericKey) {
        Boolean result = true;
        if (this.numericKey.equals(numericKey)) {
            System.out.println(numericKey + " is correct.\n");
            decrypt(numericKey);
        } else {
            System.out.println(numericKey + " is incorrect.\n");
            result = false;
        }
        return result;
    }

    public Boolean match(String SecondDecryptedText) {
        Boolean result = true;
        if (this.getEncryptedText().equals(SecondDecryptedText)) {
            System.out.print("Matched. ");
        } else {
            System.out.print("Not Matched. ");
            result = false;
        }
        return result;
    }

    @Override
    public void encrypt(String fileContent) {
        // Remove spaces and convert text to upper case for consistency
        fileContent = fileContent.replaceAll("\\s", "_");

        // The length of the numeric key will determine the number of columns
        int cols = numericKey.length();
        // Calculate the number of rows based on the text length
        int rows = (int) Math.ceil((double) fileContent.length() / cols);

        // Initialize the matrix for the Columnar Transposition Cipher
        matrix = new char[rows][cols];

        // Fill the matrix with the text characters
        for (int i = 0, k = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = (k < fileContent.length()) ? fileContent.charAt(k++) : '_';
            }
        }

        // The key sequence will be used to determine the order of the columns
        int[] keySequence = new int[cols];
        for (int i = 0; i < cols; i++) {
            keySequence[i] = numericKey.charAt(i) - '0'; // Convert char digit to integer
            // System.out.println(keySequence[i]);
        }

        // Create a StringBuilder to hold the encrypted text

        // Read the columns based on the order defined by the key sequence
        Arrays.sort(keySequence); // Sort the key sequence to get the order of columns
        for (int k = 0; k < cols; k++) {
            int col = numericKey.indexOf(Integer.toString(keySequence[k]));
            for (int row = 0; row < rows; row++) {
                this.encryptedText.append(matrix[row][col]);
            }
        }

    }

    // Helper method to get the order of columns based on the key
    private Integer[] getOrderOfKey() {
        // Assuming key is made up of unique numbers for simplicity
        Integer[] order = new Integer[numericKey.length()];
        for (int i = 0; i < numericKey.length(); i++) {
            order[i] = numericKey.charAt(i) - '0';
        }
        Arrays.sort(order);
        return order;
    }

    public void decrypt(String numericKey) {

        this.decryptedText.setLength(0); // Reset the StringBuilder

        int cols = numericKey.length();
        int rows = this.encryptedText.length() / cols;
        int extraChars = this.encryptedText.length() % cols;

        // Initialize the matrix
        char[][] matrix = new char[rows + (extraChars > 0 ? 1 : 0)][cols];

        // Create an array to store the original positions of the digits in numericKey
        Integer[] keyPositions = new Integer[cols];
        for (int i = 0; i < cols; i++) {
            keyPositions[i] = i; // Initialize with the original position
        }

        // Sort the keyPositions array based on the values of the numericKey
        Arrays.sort(keyPositions, Comparator.comparingInt(i -> numericKey.charAt(i)));

        // Calculate column lengths
        int[] colLengths = new int[cols];
        for (int i = 0; i < cols; i++) {
            colLengths[i] = rows; // Base length for all columns
            if (i < extraChars) {
                colLengths[i]++; // Columns corresponding to the first 'extraChars' digits have an extra
                                 // character
            }
        }

        // Populate the decryption matrix
        int charIndex = 0;
        for (int pos : keyPositions) {
            for (int row = 0; row < colLengths[pos]; row++) {
                if (charIndex < this.encryptedText.length()) {
                    matrix[row][pos] = this.encryptedText.charAt(charIndex++);
                }
            }
        }

        // Read the matrix row by row to construct the decrypted message
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.decryptedText.append(matrix[i][j]);
            }
        }

        // Handle the last row if there are extra characters
        if (extraChars > 0) {
            for (int i = 0; i < extraChars; i++) {
                this.decryptedText.append(matrix[rows][i]);
            }
        }

        // Replace underscores with spaces to get the original text
        String result = this.decryptedText.toString().replace('_', ' ');
        this.decryptedText.setLength(0); // Clear the StringBuilder
        this.decryptedText.append(result); // Append the final result
    }

}
