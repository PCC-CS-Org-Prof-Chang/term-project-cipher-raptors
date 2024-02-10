package edu.pasadena.cs.cs03b;

import java.util.Random;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Cipher {
    protected String key;

    public Cipher() {
        this.key = "";
    }
    public void setKey(String newKey) {
        this.key = newKey;
    }
    public String getKey() {
        return key;
    }
   
    public static String loadFile(String filename) {
        StringBuilder content = new StringBuilder(); // 修改这里，创建一个空的StringBuilder
        String filePath = "/workspaces/term-project-cipher-raptors/src/main/java/edu/pasadena/cs/cs03b/" + filename;
        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                content.append(scanner.nextLine() + "\n"); // 添加换行符，保持原有的段落格式
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
        return content.toString();
    }

    public String generateRandomKey(int keyLength) {
        Integer[] numbers = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}; // 使用Integer以支持null值
        Random random = new Random();

        StringBuilder keyBuilder = new StringBuilder();
        int count = 0; // 已生成数字的计数器

        while (count < keyLength) {
            int index = random.nextInt(10); // 生成0-9之间的随机索引
            if (numbers[index] != null) { // 检查该索引处的数字是否已被使用
                keyBuilder.append(numbers[index]);
                numbers[index] = null; // 将使用过的数字设置为null
                count++; // 增加已生成数字的计数
            }
        }

        this.key = keyBuilder.toString();
        return this.key;
    }
    

    public void encrypt(String fileContent, String numericKey) {
        
    }

    public void decrypt(String numericKey) {
       
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
        
        System.out.println(fileContent);
        
        Cipher cipher = new Cipher();
        int keyLength = 5;
        do {
            System.out.println("Invalid message. Enter a message with at least 5 characters:");
            System.out.println("Enter the desired key length (5-10):");
        keyLength = scanner.nextInt();
        } while (keyLength < 5 || keyLength > 10);
        String numericKey = cipher.generateRandomKey(keyLength);
        System.out.println("Generated key: " + cipher.getKey());
        ColumnarTranspositionCipher Transposition = new ColumnarTranspositionCipher();
        Transposition.encrypt(fileContent, numericKey);
        
        String encrypt = Transposition.getEncryptedText();
    
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
        Transposition.decrypt(numericKey);
        System.out.println();
        
        String decrypt = Transposition.getDecryptedText();
        System.out.println(decrypt);
        
      
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







