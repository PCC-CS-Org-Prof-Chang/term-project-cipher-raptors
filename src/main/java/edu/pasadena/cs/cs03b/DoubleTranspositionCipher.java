package edu.pasadena.cs.cs03b;

import java.util.Arrays;

public class DoubleTranspositionCipher extends ColumnarTranspositionCipher {

    public DoubleTranspositionCipher() {
    }

    public DoubleTranspositionCipher(String alphabetsKey) {
        this.alphabetsKey = alphabetsKey;
    }

    @Override
    protected Boolean verify(String AlphabetsKey) {
        Boolean result = true;
        if (this.alphabetsKey.equals(AlphabetsKey)) {
            System.out.println("Alphabet Key is correct.\n");
            decrypt(AlphabetsKey);
        } else {
            System.out.println("Alphabet Key is incorrect.\n");

            result = false;
        }
        return result;
    }

    @Override
    public void encrypt(String Firstencryption) {
        int cols = alphabetsKey.length();
        int rows = (int) Math.ceil((double) Firstencryption.length() / cols);

        this.matrix = new char[rows][cols];

        for (int i = 0, k = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (k < Firstencryption.length()) {
                    matrix[i][j] = Firstencryption.charAt(k++);
                } else {
                    matrix[i][j] = '_'; // 仅在需要时添加填充字符
                }
            }
        }

        int[] keyPositions = new int[cols];
        for (int i = 0; i < cols; i++) {
            keyPositions[i] = alphabetsKey.charAt(i) - 'A';
        }

        Integer[] sortedIndices = new Integer[cols];
        for (int i = 0; i < cols; i++) {
            sortedIndices[i] = i;
        }
        Arrays.sort(sortedIndices, (a, b) -> Integer.compare(keyPositions[a], keyPositions[b]));

        encryptedText.setLength(0);
        for (int k : sortedIndices) {
            for (int row = 0; row < rows; row++) {
                encryptedText.append(matrix[row][k]);
            }
        }
    }

    @Override
    public void decrypt(String alphabetsKey) {
        // First decryption (reversing the second transposition)
        super.decrypt(alphabetsKey);
        for (int i = 0; i < this.decryptedText.length(); i++) {
            if (decryptedText.charAt(i) == ' ') {
                decryptedText.setCharAt(i, '_');
            }
        }

    }
}
