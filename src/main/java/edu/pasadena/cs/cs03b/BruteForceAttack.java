package edu.pasadena.cs.cs03b;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BruteForceAttack extends Cipher{
    protected final int MIN_LENGTH = 5;
    protected final int MAX_LENGTH = 10;
    protected String AlphabetsKey;
    protected String NumericKey;
    protected DoubleTranspositionCipher secondTransposition;
    protected ColumnarTranspositionCipher firstTransposition;
    public BruteForceAttack(DoubleTranspositionCipher secondTransposition, ColumnarTranspositionCipher firstTransposition) {
        this.secondTransposition = secondTransposition;
        this.firstTransposition = firstTransposition;
    }
    public String getAlphabetsKey() {
        return AlphabetsKey;
    }
    public String getNumericKey() {
        return NumericKey;
    }
    public void setCounter(int num){
        this.count = num;
    }
    public int getCount(){
        return this.count;
    }
    public void Attack(String type) {
        boolean found = false;
        for (int i = MIN_LENGTH; i <= MAX_LENGTH && !found; i++) {
            if(type.equals("Alphabets"))
            found = generateAndTryAlpKeys(i);
            else if(type.equals("Numeric"))
            found = generateAndTryNumKeys(i);
        }

        if (found) {
            System.out.println("Brute force attack success!");
        } else {
            System.out.println("Brute force attack failed.");
        }
    }
    
    public boolean generateAndTryAlpKeys(int length) {
        Queue<String> queue = new LinkedList<>();

        
        for (char c = 'A'; c <= 'Z'; c++) {
            queue.add(String.valueOf(c));
        }

        
        while (!queue.isEmpty()) {
            String current = queue.poll();
            counter();
            
            if (current.length() == length) {
                if (secondTransposition.verify(current)) {
                  
                    //System.out.println("Successful key: " + current);
                    this.AlphabetsKey = current;
                    return true;
                }
            } else {
               
                for (char c = 'A'; c <= 'Z'; c++) {
                    
                    queue.add(current + c);
                    
                }
            }
        }
        
        return false; 
    }
    

    public boolean generateAndTryNumKeys(int length) {
        Queue<String> queue = new LinkedList<>();

        // Initialize the queue with single-digit keys
        for (char c = '0'; c <= '9'; c++) {
            queue.add(String.valueOf(c));
        }

        // Iterate over the queue until it's empty
        while (!queue.isEmpty()) {
            String current = queue.poll();
            counter();
            // If the current key reaches the target length, try it
            if (current.length() == length) {
                if (firstTransposition.verify(current)) {
                    //System.out.println("Successful numeric key: " + current);
                    this.NumericKey = current;
                    return true; // Key found
                }
            } else {
                // Otherwise, append the next digit to the current key and enqueue
                for (char c = '0'; c <= '9'; c++) {
                    queue.add(current + c);
                }
            }
        }
        
        return false; // No key found
    }
    
}

