import java.io.*;
import java.util.*;

public class rFiles {
	public static void main(String arg[]) {
		
		char[] chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".toCharArray();
		Random random = new Random();
		File file;
		int i;
		int nFiles = 1000;
		int nChars = 10000;
		try {

			for (i=0; i < nFiles; i++) { // nº de ficheiros 
				StringBuilder sb = new StringBuilder();
				for (int j = 0; j < nChars; j++) { // Sempre se pode aumentar 
				    char c = chars[random.nextInt(chars.length)];
				    sb.append(c);
				    if(random.nextBoolean())
				    	sb.append(" ");
				}
				String output = sb.toString();
				file = new File("C:/Users/mumox/workspace/MPEI/src/docs/Apresentacao/"+i+".txt");
				file.createNewFile();
				FileWriter fw = new FileWriter(file.getPath());
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(output);
				bw.close();
				System.out.print("Great success (remove me after)\n");
			}
		} catch (Exception ex) {
			System.err.println("Error");
		}

	}

}






