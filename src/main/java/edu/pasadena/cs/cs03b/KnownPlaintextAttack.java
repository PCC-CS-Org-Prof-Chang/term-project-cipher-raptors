package edu.pasadena.cs.cs03b;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KnownPlaintextAttack extends BruteForceAttack {
    private int[] AlphabetsOrder;
    private int[] NumericOrder;
    private String AlphabetsKey;
    private String NumericKey;

    public KnownPlaintextAttack(DoubleTranspositionCipher secondTransposition,
            ColumnarTranspositionCipher firstTransposition) {
        super(secondTransposition, firstTransposition); // Add constructor call to the superclass BruteForceAttack
    }

    public int getCount() {
        return this.count;
    }

    public void setAlphabetsKey(String AlphabetsKey) {
        this.AlphabetsKey = AlphabetsKey;
    }

    public void setNumericKey(String NumericKey) {
        this.NumericKey = NumericKey;
    }

    public String getAlphabetsKey() {
        return AlphabetsKey;
    }

    public String getNumericKey() {
        return NumericKey;
    }

    public int[] getAlphabetsOrder() {
        return AlphabetsOrder;
    }

    public int[] getNumericOrder() {
        return NumericOrder;
    }

    public void attackUsingOrder(int[] givenOrder, boolean isAlphabets) {
        int keyLength = givenOrder.length;
        char[] key = new char[keyLength];
        Arrays.fill(key, isAlphabets ? 'A' : '0');

        boolean success = false;
        while (!success) {
            String currentKey = new String(key);
            counter();
            success = isAlphabets ? secondTransposition.verify(currentKey) : firstTransposition.verify(currentKey);
            if (success) {
                System.out.println((isAlphabets ? "Alphabet" : "Numeric") + " key cracked successfully: " + currentKey);
                if (isAlphabets)
                    this.setAlphabetsKey(currentKey);
                else
                    this.setNumericKey(currentKey);
                break;
            }
            if (!incrementKey(key, givenOrder, isAlphabets)) {
                System.out
                        .println("Failed to crack " + (isAlphabets ? "Alphabet" : "Numeric") + " key with the order.");
                break;
            }
        }
    }

    private boolean incrementKey(char[] key, int[] givenOrder, boolean isAlphabets) {

        int[] iterationOrder = new int[givenOrder.length];
        for (int i = 0; i < givenOrder.length; i++) {
            iterationOrder[givenOrder[i]] = i;
        }
        for (int i = iterationOrder.length - 1; i >= 0; i--) {
            int idx = iterationOrder[i];
            if ((isAlphabets && key[idx] < 'Z') || (!isAlphabets && key[idx] < '9')) {
                key[idx]++;
                return true;
            } else {
                key[idx] = isAlphabets ? 'A' : '0';
                if (i == 0)
                    return false;
            }
        }
        return true; 
    }

    public void deduceColumnOrder(String horizontal, String vertical, int columns, String type) {
        horizontal = horizontal.replace('_', ' ');
        vertical = vertical.replace('_', ' ');

        while (horizontal.length() < vertical.length()) {
            horizontal += " ";
        }

        while (vertical.length() < horizontal.length()) {
            vertical += " ";
        }

        int rows = vertical.length() / columns;
        String[] verticalSegments = new String[columns];

        for (int i = 0; i < columns; i++) {
            verticalSegments[i] = vertical.substring(i * rows, i * rows + rows);
        }

        List<String[]> allMatrices = createAllMatrices(verticalSegments, columns, rows);
        for (String[] matrix : allMatrices) {
            if (compareMatrixWithHorizontal(matrix, horizontal, columns, rows)) {
                int[] order = new int[columns];
                for (int i = 0; i < columns; i++) {
                    for (int j = 0; j < columns; j++) {
                        if (matrix[i].equals(verticalSegments[j])) {
                            order[i] = j;
                            break;
                        }
                    }
                }
                if (type.equals("Alphabets"))
                    AlphabetsOrder = order;
                else if (type.equals("Numeric"))
                    NumericOrder = order;
                return;
            }
        }
        throw new IllegalStateException("Matching matrix not found.");
    }

    private List<String[]> createAllMatrices(String[] segments, int columns, int rows) {
        List<String[]> allMatrices = new ArrayList<>();
        permute(segments, 0, allMatrices);
        return allMatrices;
    }

    private void permute(String[] arr, int k, List<String[]> list) {
        for (int i = k; i < arr.length; i++) {
            String temp = arr[k];
            arr[k] = arr[i];
            arr[i] = temp;
            permute(arr, k + 1, list);
            temp = arr[k];
            arr[k] = arr[i];
            arr[i] = temp;
        }
        if (k == arr.length - 1) {
            list.add(arr.clone());
        }
    }

    private boolean compareMatrixWithHorizontal(String[] matrix, String horizontal, int columns, int rows) {
        StringBuilder sb = new StringBuilder();
        int rowsToCompare = rows - 1;
        for (int row = 0; row < rowsToCompare; row++) {
            for (int col = 0; col < columns; col++) {
                sb.append(matrix[col].charAt(row));
            }
        }
        String comparisonString = sb.toString();
        String trimmedHorizontal = horizontal.substring(0, Math.min(horizontal.length(), comparisonString.length()));
        return comparisonString.equals(trimmedHorizontal);
    }
}
