package edu.pasadena.cs.cs03b;

import java.util.Arrays;

public class DoubleTranspositionCipher extends ColumnarTranspositionCipher {

    private String secondKey;

    public DoubleTranspositionCipher(String text, String secondKey) {
        //super(text, secondKey); // Set the first key
        this.secondKey = secondKey; // Store the second key
    }

    //@Override
    //public void encrypt() {
        // First transposition with the first key
        //String firstTransposition = super.encrypt();
        
        // Temporarily swap keys for the second transposition
        //String originalKey = this.key;
        //this.key = secondKey;
        
        // Second transposition with the second key
        //String secondTransposition = super.encrypt();
        
        // Swap back the keys
        //this.key = originalKey;
        
        //return secondTransposition;
    //}

    @Override
    public void decrypt(String numericKey) {
        // Temporarily swap keys to decrypt with the second key first
        String originalKey = this.key;
        this.key = secondKey;
        
        // Reverse the second transposition
        //String firstReversal = super.decrypt();
        
        // Swap back the keys to decrypt with the first key
        this.key = originalKey;
        
        // Reverse the first transposition
       // return super.decrypt();
    }
    
    // Since the getOrderOfKey method is private in the parent class, you may need to override it
    // if it needs to be accessed in this class, or make it protected in the parent class.
}