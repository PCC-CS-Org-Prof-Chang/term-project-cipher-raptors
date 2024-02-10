package edu.pasadena.cs.cs03b;


import java.util.Random;

import java.io.File;

import java.io.FileNotFoundException;

import java.util.Scanner;

public class Cipher {
    protected String numericKey;
    protected String alphabetsKey;

    public Cipher() {
        this.numericKey = "";
        this.alphabetsKey = "";
    }
   

    public String getNumericKey() {
        return numericKey;
    }
    public String getAlphabetsKey() {
        return alphabetsKey;
    }
   
    public static String loadFile(String filename) {
        StringBuilder content = new StringBuilder(); 
        String filePath = "/workspaces/term-project-cipher-raptors/src/main/java/edu/pasadena/cs/cs03b/" + filename;
        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                content.append(scanner.nextLine() + "\n"); 
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
        return content.toString();
    }

    public void generateRandomNumericKey(int keyLength) {
        Integer[] numbers = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}; 
        Random random = new Random();

        StringBuilder keyBuilder = new StringBuilder();
        int count = 0; 

        while (count < keyLength) {
            int index = random.nextInt(10); 
            if (numbers[index] != null) { 
                keyBuilder.append(numbers[index]);
                numbers[index] = null; 
                count++; 
            }
        }

        this.numericKey = keyBuilder.toString();
    }
    
    public void generateRandomAlphabetsKey(String numericKey) {
        Character[] alphabets = new Character[26];
        for (int i = 0; i < alphabets.length; i++) {
            alphabets[i] = (char) ('A' + i);
        }
        Random random = new Random();
        StringBuilder keyBuilder = new StringBuilder();
        int count = 0; 

        while (count < numericKey.length()) {
            int index = random.nextInt(26); 
            if (alphabets[index] != null) { 
                keyBuilder.append(alphabets[index]);
                alphabets[index] = null; 
                count++; 
            }
        }

        this.alphabetsKey = keyBuilder.toString();
    }



    public void encrypt(String fileContent) {
        
    }

    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        /* 
        
        System.out.println("Enter the filename of the message to load (e.g., message1.txt):");
        String filename = scanner.nextLine();
        */
        String file = "Text1.txt";
        String fileContent = loadFile(file);
        System.out.println("Contents of " + file + ":");
        System.out.println("原文：");
        System.out.println(fileContent);
        
        Cipher cipher = new Cipher();
        int keyLength = 5;
        do {
            System.out.println("Invalid message. Enter a message with at least 5 characters:");
            System.out.println("Enter the desired key length (5-10):");
        keyLength = scanner.nextInt();
        } while (keyLength < 5 || keyLength > 10);
        cipher.generateRandomNumericKey(keyLength);
        System.out.println("数字码：");
        System.out.println("Generated key: " + cipher.getNumericKey());
        ColumnarTranspositionCipher Transposition = new ColumnarTranspositionCipher(cipher.getNumericKey());
        Transposition.encrypt(fileContent);
        cipher.generateRandomAlphabetsKey(cipher.getNumericKey());
        System.out.println("字符码：");
        System.out.println("Generated key: " + cipher.getAlphabetsKey());
        
        String encrypt = Transposition.getEncryptedText();
        DoubleTranspositionCipher SecondTransposition = new DoubleTranspositionCipher(cipher.getAlphabetsKey());
        SecondTransposition.encrypt(Transposition.getEncryptedText());
        String secondEncrypt = SecondTransposition.getEncryptedText();
        System.out.println("第一次加密：");
        System.out.println(encrypt);
        System.out.println();
        System.out.println("第二次加密：");
        System.out.println(secondEncrypt);
        System.out.println();
        SecondTransposition.decrypt();
        String decrypt2 = SecondTransposition.getDecryptedText();
        System.out.println("第一次解密：");
        System.out.println(decrypt2);
        //System.out.println(decrypt2.length());
        //System.out.println(encrypt.length());
        if(decrypt2.equals(encrypt) ){
            Transposition.decrypt();
        } else {
            System.out.println("The two decrypted messages are different");
        }
        System.out.println();
        System.out.println("第二次解密，变回原文：");
        System.out.println(Transposition.getDecryptedText());
/* 
        int count1 = 0;
        for(int i = 0; i < (int) Math.ceil((double) fileContent.length() / keyLength); i++){
            for(int j = 0; j < keyLength; j++){
                if (count1 < fileContent.length()) { // Check to avoid StringIndexOutOfBoundsException
                    System.out.print(fileContent.charAt(count1++) + " ");
                }
            }
            System.out.println();
        }
        */
        //System.out.println(encrypt);
        //System.out.println();
        //Transposition.decrypt();
        //System.out.println();
        
        //String decrypt = Transposition.getDecryptedText();
        //System.out.println(decrypt);
        
      
        scanner.close();
        /* 
        Cipher cipher = new ColumnarTranspositionCipher();
        String message = cipher.loadMessage(filename);
        System.out.println("Loaded message: " + message);

        System.out.println("Enter the key length (5-10):");
        int keyLength = scanner.nextInt();
        while (keyLength < 5 || keyLength > 10) {
            System.out.println("Invalid key length. Enter a length between 5 and 10:");
            keyLength = scanner.nextInt();
        }
        scanner.nextLine(); // Consume the leftover newline

        String key = cipher.generateRandomKey(keyLength);
        System.out.println("Generated key: " + key);

        String encryptedMessage = cipher.encrypt(message);
        System.out.println("Encrypted message: " + encryptedMessage);

        String decryptedMessage = cipher.decrypt(encryptedMessage);
        System.out.println("Decrypted message: " + decryptedMessage);
        */
    }
    
	public static int dummy() {
		
		
		// TOOD: add your logic here

		return 1;
	}
}







