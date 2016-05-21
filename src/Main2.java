import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Main driver for text compression.
 * @author Zach Wingo
 *
 */
public class Main2 {

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		long originalSize = 0;
		try {
			// File to compress
			File file = new File("./src/WarAndPeace.txt");
			originalSize = file.length();
			// Reads entire file into string.
			String message = new Scanner(file).useDelimiter("\\A").next();

			// Pass the string to the coding tree to be encoded.
			CodingTree codeTree = new CodingTree(message);

			// Opens two files for writing codes and compressed text.
			FileOutputStream outputCodes = new FileOutputStream(new File("./src/codes1.txt"));
			FileOutputStream outputCompressedFile = new FileOutputStream(new File("./src/compressed1.txt"));
 
			// We can write the codes and close that file stream.
			outputCodes.write(codeTree.toString().getBytes());
			outputCodes.close();
			
			List<Byte> bytes = codeTree.getBytes();
			byte[] b = new byte[bytes.size()];
			
			// Put all bytes into primitive array. 
			for(int i = 0; i < bytes.size(); i++) {
				b[i]=bytes.get(i);
			}
			// Now we only have to write once.
			outputCompressedFile.write(b);
			outputCompressedFile.close();
			
			// Store code tree.
			Map<Character, String> codes = codeTree.getCodes();
			
			// Stores the codes as we get them from the tree.
			StringBuilder sb = new StringBuilder();
			int bytesWritten = 0;
			
			// We loop through the entire text one character at a time and look
			// up the character code as we go.  We append the code to the string
			// builder and when the length gets over 32 characters we write them
			// to the file as 16 bit binary integers. 
//			for(int i = 0; i < message.length(); i++) {
//				sb.append(codes.get(message.charAt(i)));
//					
//					while(sb.length() >= 32) {
//						
//						// We get 4 bytes before writing to file.
//						byte[] bytes = {// Hard-code 4 bytes.
//										Integer.valueOf(sb.substring(0,8), 2).byteValue(), 
//										Integer.valueOf(sb.substring(8,16), 2).byteValue(),
//										Integer.valueOf(sb.substring(16,24), 2).byteValue(),
//										Integer.valueOf(sb.substring(24,32), 2).byteValue()};
//						
//						outputCompressedFile.write(bytes);
//						sb.delete(0, 32);
//						bytesWritten+=4;
//					}
//			}
			
			// We have to write what's left in the string builder buffer.
//			if(sb.length() > 0){
//				outputCompressedFile.write(Integer.valueOf(sb.toString(), 2));
//				bytesWritten += 1;
//			}
			
			
			// Prints out compression info.
			System.out.println("Original Size: " + originalSize + " bytes");
			System.out.println("Compressed Size: " + bytesWritten + " bytes");
			System.out.println("Compression Factor: " + Math.round((bytesWritten*1.0/originalSize)*100) + "%");
			
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
		System.out.println("Run Time: " + (((System.currentTimeMillis() - start))/1000) + " seconds ");
	}
	

}
