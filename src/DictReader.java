import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class DictReader {

	private ArrayList<String> result;
	public DictReader(){
		String txtFile = "C:/Users/mumox/workspace/MPEI/src/docs/Apresentacao/BloomFilter/diccionario_PT.txt"; // mudar para path do ficheiro
		String newFile = "C:/Users/mumox/workspace/MPEI/src/docs/Apresentacao/BloomFilter/diccionario_PTF.txt"; // mudar para path do ficheiro
		BufferedReader br = null;
		BufferedWriter bw = null;
		String line = "";
		this.result = new ArrayList<String>();

		try {

			br = new BufferedReader(new FileReader(txtFile));
			while ((line = br.readLine()) != null) {
				this.result.add(line.replaceAll("(\\p{Ll})(\\p{Lu})","$1 $2"));
			}
			bw = new BufferedWriter(new FileWriter(newFile,true));
			System.out.printf("result = %s\n", result.toString());
			for(String r : result){
				bw.write(r + "\r\n");
			}
			bw.close();
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public  ArrayList<String> getResult() {
		return this.result;
	}

}
