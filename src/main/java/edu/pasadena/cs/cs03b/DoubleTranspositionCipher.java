package edu.pasadena.cs.cs03b;

import java.util.Arrays;


public class DoubleTranspositionCipher extends ColumnarTranspositionCipher {

    public DoubleTranspositionCipher(String alphabetsKey) {
        super(alphabetsKey);
    }

    
    @Override
    public void encrypt(String fileContent) {
        fileContent = fileContent.replaceAll("\\s", "_");

        int cols = numericKey.length();
        int rows = (int) Math.ceil((double) fileContent.length() / cols);

        char[][] matrix = new char[rows][cols];

        for (int i = 0, k = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = (k < fileContent.length()) ? fileContent.charAt(k++) : '_';
            }
        }

        // Convert alphabetic key to positions ('A' = 0, 'B' = 1, ..., 'Z' = 25)
        int[] keyPositions = new int[cols];
        for (int i = 0; i < cols; i++) {
            keyPositions[i] = numericKey.toUpperCase().charAt(i) - 'A';
        }

        // Sort the key positions to get the order of columns
        Integer[] sortedIndices = new Integer[cols];
        for (int i = 0; i < cols; i++) {
            sortedIndices[i] = i;
        }
        Arrays.sort(sortedIndices, (a, b) -> Integer.compare(keyPositions[a], keyPositions[b]));

        // Read the columns based on the sorted order
        encryptedText.setLength(0); // Clear the previous encrypted text
        for (int k : sortedIndices) {
            for (int row = 0; row < rows; row++) {
                encryptedText.append(matrix[row][k]);
            }
        }
    }
    @Override
    public void decrypt() {
        // First decryption (reversing the second transposition)
        super.decrypt();
        for(int i = 0; i < this.decryptedText.length(); i++){
            if(decryptedText.charAt(i) == ' '){
                decryptedText.setCharAt(i, '_');
            }
        }
        
    }
}
