package edu.pasadena.cs.cs03b;

import java.util.ArrayList;
import java.util.List;

public class KnownPlaintextAttack extends BruteForceAttack {
    private int[] AlphabetsOrder;
    private int[] NumericOrder;

    public KnownPlaintextAttack(DoubleTranspositionCipher secondTransposition, ColumnarTranspositionCipher firstTransposition) {
        super(secondTransposition, firstTransposition); // Add constructor call to the superclass BruteForceAttack
    }

    public int[] getAlphabetsOrder() {
        return AlphabetsOrder;
    }

    public int[] getNumericOrder() {
        return NumericOrder;
    }

    public void deduceColumnOrder(String horizontal, String vertical, int columns, String type) {
      
        horizontal = horizontal.replace('_', ' ');
        vertical = vertical.replace('_', ' ');
        while(horizontal.length() < vertical.length()){
            horizontal += " ";
        }
            
        //System.out.println(horizontal.length());
        //System.out.println(vertical.length());

        int rows = vertical.length() / columns;
        String[] verticalSegments = new String[columns];

        // 划分纵向文本
        for (int i = 0; i < columns; i++) {
            verticalSegments[i] = vertical.substring(i * rows, i * rows + rows);
        }

        // 创建矩阵并比较
        List<String[]> allMatrices = createAllMatrices(verticalSegments, columns, rows);
        for (String[] matrix : allMatrices) {
            if (compareMatrixWithHorizontal(matrix, horizontal, columns, rows)) {
                // 确定列顺序
                int[] order = new int[columns];
                for (int i = 0; i < columns; i++) {
                    for (int j = 0; j < columns; j++) {
                        if (matrix[i].equals(verticalSegments[j])) {
                            order[i] = j;
                            break;
                        }
                    }
                }
                // 赋值给相应的字段
                if(type.equals("Alphabets"))
                    AlphabetsOrder = order;
                else if(type.equals("Numeric"))
                    NumericOrder = order;
                return; // 找到匹配后即退出方法
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

        // 构建的字符串与 horizontal 进行比较
        String comparisonString = sb.toString();

        // horizontal 也应该截取到相同的长度，以便比较
        String trimmedHorizontal = horizontal.substring(0, Math.min(horizontal.length(), comparisonString.length()));
        
        return comparisonString.equals(trimmedHorizontal);
    }
}
