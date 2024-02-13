package edu.pasadena.cs.cs03b;

import java.util.Random;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Cipher {
    protected String numericKey;
    protected String alphabetsKey;
    protected int keyLength = 5;
    protected int count = 0;
    private String fileContent = " ";
    private String BruteForceCrackTimes = " ";
    private String KnownPlaintextCrackTimes = " ";
    
    public Cipher() {
        this.numericKey = "";
        this.alphabetsKey = "";
    }

    public void setBruteForceCrackTimes(String BruteForceCrackTimes){
        this.BruteForceCrackTimes = BruteForceCrackTimes;
    }
    public void setKnownPlaintextCrackTimes(String KnownPlaintextCrackTimes){
        this.KnownPlaintextCrackTimes = KnownPlaintextCrackTimes;
    }

    public String getBruteForceCrackTimes(){
        return BruteForceCrackTimes;
    }

    public String getKnownPlaintextCrackTimes(){
        return KnownPlaintextCrackTimes;
    }

    protected void setNumericKey(String numericKey) {
        this.numericKey = numericKey;
    }

    protected void setAlphabetsKey(String alphabetsKey) {
        this.alphabetsKey = alphabetsKey;
    }

    protected String getNumericKey() {
        return numericKey;
    }

    protected String getAlphabetsKey() {
        return alphabetsKey;
    }

    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }

    public String getFileContent() {
        return fileContent;
    }

    protected void counter() {
        count++;
    }

    public void setKeyLength(int keyLength) {
        this.keyLength = keyLength;
    }

    public int getKeyLength() {
        return keyLength;
    }

    public static String loadFile(String filename) {
        StringBuilder content = new StringBuilder();
        
        String filePath = "src/main/java/edu/pasadena/cs/cs03b/" + filename; 
        
       
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("File does not exist: " + filePath);
            return ""; 
        }
        
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                content.append(scanner.nextLine()).append("\n");
            }
        } catch (FileNotFoundException e) {
            
            System.out.println("File not found: " + e.getMessage());
        }
        return content.toString();
    }
    Boolean verify(String decryptFiles) {
        String original = this.fileContent.trim();
        String decrypted = decryptFiles.trim();

        Boolean result = true;
        if (original.equals(decrypted)) {
            System.out.println("Matched. The decoded file is the same as the original message.\n");
        } else {
            System.out.println("The decoded file is different from the original message.");
            result = false;
        }
        return result;
    }

    public void generateRandomNumericKey(int keyLength) {
        Integer[] numbers = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
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

    public static void printMatrix(char[][] encryption, String keyWord) {
        char[][] matrix = encryption;

        int rows = matrix.length;
        int cols = matrix[0].length;

        System.out.print("+");
        for (int i = 0; i < cols; i++) {
            System.out.print("-----+");
        }
        System.out.println();

        System.out.print("|");
        for (int i = 0; i < keyWord.length(); i++) {
            System.out.printf("  %c  |", keyWord.charAt(i));
        }
        System.out.println();

        System.out.print("+");
        for (int i = 0; i < cols; i++) {
            System.out.print("-----+");
        }
        System.out.println();

        for (int i = 0; i < rows; i++) {
            System.out.print("|");
            for (int j = 0; j < cols; j++) {

                System.out.printf("  %c  |", matrix[i][j] == '\u0000' ? ' ' : matrix[i][j]);
            }
            System.out.println();

            System.out.print("+");
            for (int j = 0; j < cols; j++) {
                System.out.print("-----+");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ColumnarTranspositionCipher FirstTransposition = new ColumnarTranspositionCipher();
        DoubleTranspositionCipher SecondTransposition = new DoubleTranspositionCipher();
        Cipher cipher = new Cipher();
        /*
         * 
         * System.out.
         * println("Enter the filename of the message to load (e.g., message1.txt):");
         * String filename = scanner.nextLine();
         */
        String file = "Text1.txt";
        cipher.setFileContent(loadFile(file));
        System.out.println("Contents of " + file + ":\n");
        System.out.println("Read the file and output the original text：\n");
        System.out.println(cipher.getFileContent());

        boolean exit = false;
        while (!exit) {
            System.out.println("Main interface:");
            System.out.println("1. Encryption");
            System.out.println("2. Decryption");
            System.out.println("3. Brute force cracking of forgotten passwords");
            System.out.println("4. Known-plaintext attack");
            System.out.println("5. Check the number of cracks");
            System.out.println("6. Exit");
            
            System.out.print("Please select an action (1-6): \n");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the leftover newline

            switch (choice) {
                case 1:

                    System.out.println("\nYou chose encryption.");
                    do {
                        if (cipher.getKeyLength() < 5 || cipher.getKeyLength() > 10) {
                            System.out.println("Invalid message. Enter a message with at least 5 characters:");
                        }
                        System.out.println("Enter the desired key length (5-10):");
                        cipher.setKeyLength(scanner.nextInt());
                    } while (cipher.getKeyLength() < 5 || cipher.getKeyLength() > 10);
                    scanner.nextLine(); // Consume the leftover newline

                    cipher.generateRandomNumericKey(cipher.getKeyLength());

                    FirstTransposition = new ColumnarTranspositionCipher(cipher.getNumericKey());
                    FirstTransposition.encrypt(cipher.getFileContent());

                    boolean backToMainMenu = false;
                    while (!backToMainMenu) {
                        System.out.println("\nNumeric encryption completed. Please select the following options：\n");
                        System.out.println("1. Show encrypted information");
                        System.out.println("2. Secondary encryption");
                        System.out.println("3. Return to the previous menu");
                        System.out.print("Please select an action：");

                        int subChoice = scanner.nextInt();
                        scanner.nextLine(); // Consume the leftover newline
                        System.out.println();
                        switch (subChoice) {
                            case 1:
                                System.out.println("\nGenerated numeric key: " + cipher.getNumericKey() + ".");
                                System.out.println("\nEncryption：");

                                
                                


                                if (FirstTransposition.getMatrix() != null && FirstTransposition.getMatrix().length > 0 && FirstTransposition.getMatrix()[0].length > 0) {
                                    printMatrix(FirstTransposition.getMatrix(), cipher.getNumericKey());
                                } else {
                                    System.out.println("The encryption matrix for the first transposition is empty or not initialized properly.");
                                }
                                //printMatrix(FirstTransposition.getMatrix(), cipher.getNumericKey());
                                System.out.println("\nFirst Encrypted Text: " + FirstTransposition.getEncryptedText());
                                break;
                            case 2:

                                cipher.generateRandomAlphabetsKey(cipher.getNumericKey());
                                // System.out.println("Generated key: " + cipher.getAlphabetsKey());
                                SecondTransposition = new DoubleTranspositionCipher(cipher.getAlphabetsKey());
                                SecondTransposition.encrypt(FirstTransposition.getEncryptedText());
                                boolean returnToPreviousMenu = false;
                                while (!returnToPreviousMenu) {
                                    System.out.println(
                                            "\nAlphabet encryption completed. Please select the following options：\n");
                                    System.out.println("1. Show encrypted information");
                                    System.out.println("2. Return to previous menu");
                                    System.out.print("Please enter options: ");
                                    subChoice = scanner.nextInt();
                                    scanner.nextLine();
                                    switch (subChoice) {
                                        case 1:
                                            System.out.println(
                                                    "\nGenerated Alphabet key: " + cipher.getAlphabetsKey() + ".");
                                            System.out.println("\nEncryption：");
                                            printMatrix(SecondTransposition.getMatrix(), cipher.getAlphabetsKey());
                                            System.out.println("\nSecond Encrypted Text: "
                                                    + SecondTransposition.getEncryptedText());
                                            break;
                                        case 2:
                                            returnToPreviousMenu = true;
                                            System.out.println("\n***Your Alphabets Key is: " + cipher.getAlphabetsKey()
                                                    + ", and your numeric key is: " + cipher.getNumericKey()
                                                    + ". Please keep it properly.***");
                                            break;
                                        default:
                                            System.out.println("Invalid option, please re-enter. ");
                                            break;
                                    }
                                }
                                break;
                            case 3:
                                backToMainMenu = true;
                                break;
                            default:
                                System.out.println("Invalid option, please re-enter. ");
                                break;
                        }

                    }
                    // System.out.println(SecondTransposition.encryptedText.length());
                    // System.out.println(FirstTransposition.encryptedText.length());
                    break;
                case 2: // Decryption
                    System.out.println("\nYou choose to decrypt. Please enter the Alphabet key for decryption.\n");
                    Boolean result = true;
                    do {
                        if (!result) {
                            System.out.println("Please re-enter.");
                        }
                        // String key = scanner.nextLine();
                        result = SecondTransposition.verify(cipher.getAlphabetsKey());
                    } while (!result);
                    if (FirstTransposition.match(SecondTransposition.getDecryptedText())) {
                        System.out.println("The first part is decoded correctly.\n");
                        System.out.println("Please enter the Numeric key for further decryption.\n");
                        do {
                            if (!result) {
                                System.out.println("Please re-enter: ");
                            }
                            // String key = scanner.nextLine();
                            result = FirstTransposition.verify(cipher.getNumericKey());
                        } while (!result);

                    }else{
                        System.out.println("The first part is not decoded correctly.\n");
                        break;
                    }
                    if (cipher.verify(FirstTransposition.getDecryptedText())) {
                        System.out.println("Decoding successful, the following is the message:\n");
                        System.out.println(FirstTransposition.getDecryptedText() + "\n");
                    } else {
                        System.out.println("Decoding error, return to the main interface.\n");
                    }
                    break;
                case 3: // Brute force cracking of forgotten passwords
                    int attempt1 = 0;
                    int attempt2 = 0;
                    System.out.println("You chose brute force cracking of your forgotten password.");
                    BruteForceAttack bruteForce = new BruteForceAttack(SecondTransposition, FirstTransposition);
                    bruteForce.Attack("Alphabets");
                    System.out.println("\nAlphabets key cracked successfully: " + bruteForce.getAlphabetsKey() + "\n");
                    attempt1 = bruteForce.getCount();
                    System.out.println("Number of attacks: " + attempt1 + " times.\n");
                    if (FirstTransposition.match(SecondTransposition.getDecryptedText())) {
                        System.out.println("The first part is decoded correctly.\n");
                    }else{
                        System.out.println("The first part is not decoded correctly.\n");
                        break;
                    }
                    System.out.println("Do you want to continue cracking numeric key? (1. Yes  2. No)");
                    int continueCracking = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    if (continueCracking == 1) {
                        bruteForce.Attack("Numeric");
                        System.out.println("\nNumeric key cracked successfully: " + bruteForce.getNumericKey() + "\n");
                        attempt2 = bruteForce.getCount();
                        System.out.println("Number of attacks: " + attempt2 + " times.\n");
                        if (cipher.verify(FirstTransposition.getDecryptedText())) {
                            System.out.println("Decoding successful, The original message is:\n");
                            System.out.println(FirstTransposition.getDecryptedText());
                        } else {
                            System.out.println("Decoding error, return to the main interface.\n");
                        }
                    }
                    cipher.setBruteForceCrackTimes("\nBrute Force Crack total attempts: " + (attempt1 + attempt2) + " times. Alphabets: "
                    + attempt1 + " times, Numeric: " + attempt2 + " times.");
                    System.out.println(cipher.getBruteForceCrackTimes() + "\n");
                    break;
                case 4: // Known-plaintext attack
                    int choose = 0;
                    int attempt3 = 0;
                    int attempt4 = 0;
                    System.out.println("You chose Known-plaintext attack.");
                    String horizontalText = FirstTransposition.getEncryptedText();
                    String verticalText = SecondTransposition.getEncryptedText();
                    KnownPlaintextAttack knownPlaintextAttack = new KnownPlaintextAttack(SecondTransposition,
                            FirstTransposition);
                    knownPlaintextAttack.deduceColumnOrder(horizontalText, verticalText, cipher.getKeyLength(),
                            "Alphabets");
                    System.out.println("According to Known-plaintext, crack the encryption sequence of Alphabets key.\n");
                    System.out.print("Deduced Column Order: ");
                    int[] alphabetsOrder = knownPlaintextAttack.getAlphabetsOrder();
                    for (int i = 0; i < alphabetsOrder.length; i++) {
                        System.out.print(alphabetsOrder[i] + " ");
                    }
                    System.out.print("\n\nDo you want to decipher the secret key in this order? (1. Yes  2. No): \n");
                    choose = scanner.nextInt();
                    scanner.nextLine();
                    if (choose == 1) {
                        knownPlaintextAttack.attackUsingOrder(alphabetsOrder, true);
                        attempt3 = knownPlaintextAttack.getCount();
                        System.out.println("\nNumber of attacks: " + attempt3 + " times.\n");
                        if (FirstTransposition.match(SecondTransposition.getDecryptedText())) {
                            System.out.println("The first part is decoded correctly.\n");
                        }else{
                            System.out.println("The first part is not decoded correctly.\n");
                            break;
                        }
                        System.out.print("Do you want to continue to crack the numeric key? (1. Yes  2. No):");
                            choose = scanner.nextInt();
                            scanner.nextLine();
                            if (choose == 1) {
                                horizontalText = cipher.getFileContent();
                                verticalText = SecondTransposition.getDecryptedText();
                                knownPlaintextAttack.deduceColumnOrder(horizontalText, verticalText, cipher.getKeyLength(),
                                    "Numeric");
                                System.out.println("\nAccording to Known-plaintext, crack the encryption sequence of Numeric key.\n");
                                System.out.print("Deduced Column Order: ");
                                int[] numericOrder = knownPlaintextAttack.getNumericOrder();
                                for (int i = 0; i < numericOrder.length; i++) {
                                    System.out.print(numericOrder[i] + " ");
                                }
                                System.out.print("\n\nDo you want to decipher the secret in this order? (1. Yes  2. No): \n");
                                choose = scanner.nextInt();
                                scanner.nextLine();
                                if (choose == 1) {
                                    knownPlaintextAttack.attackUsingOrder(numericOrder, false);
                                    attempt4 = knownPlaintextAttack.getCount();
                                    System.out.println("\nNumber of attacks: " + attempt4 + " times.\n");
                                    if (cipher.verify(FirstTransposition.getDecryptedText())) {
                                        System.out.println("The second part is decoded correctly.\n");
                                        System.out.println("The original text is:.\n");
                                        System.out.println(FirstTransposition.getDecryptedText() + "\n");
                                    }else{
                                        System.out.println("The second part is not decoded correctly.\n");
                                    }
                                }
                            }
                    }
                    cipher.setKnownPlaintextCrackTimes("Known-plaintext Crack total attempts: " + (attempt3 + attempt4) + " times. Alphabets: " + attempt3 + " times, Numeric: " + attempt4 + " times.");
                    System.out.println(cipher.getKnownPlaintextCrackTimes() + "\n");
                    break;
                case 5:
                    System.out.println("You have chosen to check the number of cracks.");
                    System.out.println(cipher.getBruteForceCrackTimes());
                    System.out.println(cipher.getKnownPlaintextCrackTimes());
                    System.out.println();
                    break;
                case 6:
                    System.out.println("You have chosen to exit.");
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option, please re-enter.");
                    break;
            }
        }
        scanner.close();
    }

    public static int dummy() {

        // TOOD: add your logic here

        return 1;
    }
}
