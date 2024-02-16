package edu.pasadena.cs.cs03b;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.*;

public class TestDummy {

  @Test
  public void testDummy() {
    // action
    int iResult = Cipher.dummy();
    // assertion
    assertEquals(1, iResult);

    // TODO: add your own test cases
    // Test whether the read file is equal to the stored file content
    Cipher cipher = new Cipher();
    String FileContent = cipher.loadFile("src/main/java/edu/pasadena/cs/cs03b/Text1.txt");
    cipher.setFileContent(FileContent);
    assertEquals(cipher.getFileContent(), FileContent);

    // Test the length of password generation
    cipher.generateRandomNumericKey(5);
    assertEquals(cipher.getNumericKey().length(), 5);

    // Test the file after encrypting it once and then encrypting it twice and
    // then verifying whether the password is correct when decrypting it. If it is
    // correct,
    // decrypt it and see whether the decrypted file is equal to the file that was
    // encrypted once.
    ColumnarTranspositionCipher FirstTransposition = new ColumnarTranspositionCipher(cipher.getNumericKey());
    FirstTransposition.encrypt(cipher.getFileContent());
    cipher.generateRandomAlphabetsKey(cipher.getNumericKey());
    DoubleTranspositionCipher SecondTransposition = new DoubleTranspositionCipher(cipher.getAlphabetsKey());
    SecondTransposition.encrypt(FirstTransposition.getEncryptedText());
    SecondTransposition.verify(cipher.getAlphabetsKey());
    SecondTransposition.getDecryptedText();
    assertEquals(FirstTransposition.getEncryptedText(), SecondTransposition.getDecryptedText());

    // Test whether the file that matches the secondary encryption is the same as
    // the file
    // that was once encrypted after decryption。 If they are the same, verify the
    // password and then decrypt it.
    FirstTransposition.match(SecondTransposition.getDecryptedText());
    FirstTransposition.verify(cipher.getNumericKey());

    // Compare whether the twice decrypted files are the same as the original
    // content
    assertEquals(FirstTransposition.getDecryptedText(), cipher.getFileContent());
  }
}
