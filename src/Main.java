/*
 * Zachariah Wingo
 * TCSS 342
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Scanner;

/**
 * Main driver for text compression.
 * @author Zachariah Wingo
 *
 */
public class Main {

	public static void main(String[] args) {
		
		// The test method.
		//testCodingTree();
		
		long start = System.currentTimeMillis();
		long originalSize = 0;
		
		try {
			// File to compress.
			File inputFile = new File("./src/WarAndPeace.txt");
			
			// Compressed file.
			File outputFile = new File("./src/compressed.txt");
			
			// A second test file that is approximately 5.5MB
			//File inputFile = new File("./src/shaks12.txt");
			
			// Get's size of the file in bytes.
			originalSize = inputFile.length();
			
			
			// Reads entire file into string.
			String message = new Scanner(inputFile).useDelimiter("\\A").next();
			
			// Pass the string to the coding tree to be encoded.
			CodingTree codeTree = new CodingTree(message);

			// Opens two files for writing codes and compressed text.
			FileOutputStream outputCodes = new FileOutputStream(new File("./src/codes.txt"));
			FileOutputStream outputCompressedFile = new FileOutputStream(outputFile);
 
			// We can write the codes and close that file stream.
			outputCodes.write(codeTree.toString().getBytes());
			outputCodes.close();
			
			// Gets list of bytes from file.
			List<Byte> bytes = codeTree.getBytes();
			byte[] b = new byte[bytes.size()];
			int bytesWritten = 0;
			
			// Put all bytes into primitive array. 
			for(int i = 0; i < bytes.size(); i++) {
				b[i]=bytes.get(i);
				bytesWritten++;
			}
			
			// Now we only have to write once and close the file.
			outputCompressedFile.write(b);

			
			
			// Prints out compression info.
			System.out.println("Original Size: " + (originalSize/1024) + " kb");
			System.out.println("Compressed Size: " + (outputFile.length()/1024) + " kb");
			System.out.println("Compression Factor: " + Math.round((bytesWritten*1.0/originalSize)*100) + "%");
			outputCompressedFile.close();
			
		} catch (FileNotFoundException e) {
			System.out.println(e);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Display the runtime.
		System.out.println("Run Time: " + (((System.currentTimeMillis() - start)/1000.0)) + " seconds ");
	}
	
	
	private static void testCodingTree() {
		String str1 = "AG";
		String str2 = "YAG";
		String str3 = "ANNA HAS A BANANA IN A BANDANA";
		
		// Each of the two characters has a frequency count of 1
		// therefore we should get code output with one character
		// code as 0 and the other as 1.
		CodingTree ct1 = new CodingTree(str1);
		System.out.println(ct1.toString());
		
		// Each character count has a frequency count of 2 therefore
		// two characters get combined into a tree and the node that
		// binds them gets connected to the remaining 3rd character
		// leaving it as a lone node to the right or left. Thus giving
		// us one character as a 0 or 1, and the other two will be 11
		// or 10;
		CodingTree ct2 = new CodingTree(str2);
		System.out.println(ct2.toString());
		
		// Check the ability to look up a character code. Print out all codes
		// then do a lookup on 'A' and becaue it's the most common character
		// it should be 11 or 10
		CodingTree ct3 = new CodingTree(str3);
		System.out.println(ct3.getCodes().toString());
		System.out.println(ct3.getCodes().get('A'));

	}
	

}
