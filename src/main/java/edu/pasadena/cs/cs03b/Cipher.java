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
    public static void printnMatrix(char[][] encryption, String keyWord) {
        char[][] matrix = encryption;
        // 计算行数和列数
    int rows = matrix.length;
    int cols = matrix[0].length;
    
        // 打印列标题的顶部边框
        System.out.print("+");
        for (int i = 0; i < cols; i++) {
            System.out.print("-----+"); // 增加单元格宽度
        }
        System.out.println();
    
        // 打印密钥单词作为列标题
        System.out.print("|");
        for (int i = 0; i < keyWord.length(); i++) {
            System.out.printf("  %c  |", keyWord.charAt(i));
        }
        System.out.println();
    
        // 打印列标题下的边框
        System.out.print("+");
        for (int i = 0; i < cols; i++) {
            System.out.print("-----+"); // 增加单元格宽度
        }
        System.out.println();
    
        // 打印矩阵内容和每行的边框
        for (int i = 0; i < rows; i++) {
            System.out.print("|");
            for (int j = 0; j < cols; j++) {
                // 输出矩阵元素，如果是空字符，代表空格，打印空格
                System.out.printf("  %c  |", matrix[i][j] == '\u0000' ? ' ' : matrix[i][j]);
            }
            System.out.println();
    
            // 打印每一行下的边框
            System.out.print("+");
            for (int j = 0; j < cols; j++) {
                System.out.print("-----+"); // 增加单元格宽度
            }
            System.out.println();
        }
}
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ColumnarTranspositionCipher FirstTransposition = new ColumnarTranspositionCipher();
        DoubleTranspositionCipher SecondTransposition = new DoubleTranspositionCipher();
        /* 
        
        System.out.println("Enter the filename of the message to load (e.g., message1.txt):");
        String filename = scanner.nextLine();
        */
        String file = "Text1.txt";
        String fileContent = loadFile(file);
        System.out.println("Contents of " + file + ":\n");
        System.out.println("Read the file and output the original text：\n");
        System.out.println(fileContent);
        
        
        Cipher cipher = new Cipher();
        int keyLength = 5;
        boolean exit = false;
        while (!exit) {
            System.out.println("Main interface:");
            System.out.println("1. Encryption");
            System.out.println("2. Decryption");
            System.out.println("3. Brute force cracking of forgotten passwords");
            System.out.println("4. Guess the password");
            System.out.println("5. Exit");
            System.out.print("Please select an action (1-5): ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the leftover newline

            switch (choice) {
                case 1: // 加密
                
                    System.out.println("\nYou chose encryption.");
                    do {
                        if(keyLength < 5 || keyLength > 10){
                            System.out.println("Invalid message. Enter a message with at least 5 characters:");
                        }
                        System.out.println("Enter the desired key length (5-10):");
                    keyLength = scanner.nextInt();
                    } while (keyLength < 5 || keyLength > 10);
                    scanner.nextLine(); // Consume the leftover newline
            
                    cipher.generateRandomNumericKey(keyLength);
                    
                    FirstTransposition = new ColumnarTranspositionCipher(cipher.getNumericKey());
                    FirstTransposition.encrypt(fileContent);
                    
                    
                    
                    boolean backToMainMenu = false;
                    while (!backToMainMenu) {
                        System.out.println("\nNumeric encryption completed. Please select the following options：\n");
                        System.out.println("1. Show encrypted information");
                        System.out.println("2. Secondary encryption");
                        System.out.println("3. Return to the previous menu");
                        System.out.print("Please select an action：");

                        
                        int subChoice = scanner.nextInt();
                        scanner.nextLine(); // Consume the leftover newline
                
                        switch (subChoice) {
                            case 1: // 显示加密信息
                                System.out.println("\nGenerated numeric key: " + cipher.getNumericKey() + ".");
                                System.out.println("\nEncryption：");
                                printnMatrix(FirstTransposition.getMatrix(), cipher.getNumericKey());
                                System.out.println("\nFirst Encrypted Text: " + FirstTransposition.getEncryptedText());
                                System.out.println("数字加密完成。\n");
                                break;
                            case 2: // 二次加密
                            
                                cipher.generateRandomAlphabetsKey(cipher.getNumericKey());
                                //System.out.println("字符码：");
                                System.out.println("Generated key: " + cipher.getAlphabetsKey());
                            //String encrypt = FirstTransposition.getEncryptedText();
                            SecondTransposition = new DoubleTranspositionCipher(cipher.getAlphabetsKey());
                            SecondTransposition.encrypt(FirstTransposition.getEncryptedText());
                            boolean returnToPreviousMenu = false;
                            while (!returnToPreviousMenu) {
                                System.out.println("\nAlphabet encryption completed. Please select the following options：\n");
                                System.out.println("1. Show encrypted information");
                                System.out.println("2. Return to previous menu");
                                System.out.print("Please enter options: ");
                        
                                subChoice = scanner.nextInt();
                                scanner.nextLine(); // 清除换行符
                        
                                switch (subChoice) {
                                    case 1: // 显示解密信息
                                        System.out.println("\nGenerated Alphabet key: " + cipher.getAlphabetsKey() + ".");
                                        System.out.println("\nEncryption：");
                                        printnMatrix(SecondTransposition.getMatrix(), cipher.getAlphabetsKey());
                                        System.out.println("\nSecond Encrypted Text: " + SecondTransposition.getEncryptedText());
                                        System.out.println("\nAlphabet encryption is completed.\n");
                                        break;
                                    case 2: // 返回到主界面
                                        returnToPreviousMenu = true;
                                        break;
                                    default:
                                        System.out.println("Invalid option, please re-enter. ");
                                        break;
                                }
                            }
                            
                                break;
                            case 3: // 返回上级菜单
                                backToMainMenu = true;
                                break;
                            default:
                                System.out.println("Invalid option, please re-enter. ");
                                break;
                        }

                    }
                    
                    
                    
                    break;
                case 2: // 解密
                    System.out.println("You choose to decrypt.\n");
                    // 调用解密方法
                    break;
                case 3: // 遗忘密码暴力破解
                    System.out.println("You chose brute force cracking of your forgotten password.");
                    // 调用暴力破解方法
                    break;
                case 4: // 猜测密码
                    System.out.println("You chose to guess your password.");
                    // 调用猜测密码方法
                    break;
                case 5: // 退出
                    System.out.println("You have chosen to exit.");
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option, please re-enter.");
                    break;
            }
        }
        
       
        System.out.println();
        SecondTransposition.decrypt();
        String decrypt2 = SecondTransposition.getDecryptedText();
        System.out.println("第一次解密：");
        System.out.println(decrypt2);
        //System.out.println(decrypt2.length());
        //System.out.println(encrypt.length());
        if(decrypt2.equals(FirstTransposition.getEncryptedText()) ){
            FirstTransposition.decrypt();
        } else {
            System.out.println("The two decrypted messages are different");
        }
        System.out.println();
        System.out.println("第二次解密，变回原文：");
        System.out.println(FirstTransposition.getDecryptedText());
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







