import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class TxTReader {

	private ArrayList<String> strings;

	public TxTReader(String path) {
		txtToList(path);
	}

	public ArrayList<String> getStrings() {
		return strings;
	}

	private void txtToList(String path) {

		String txtFile = path; // mudar para path do ficheiro
		BufferedReader br = null;
		String line = "";
		ArrayList<String> result = new ArrayList<String>();

		try {

			br = new BufferedReader(new FileReader(txtFile));
			while ((line = br.readLine()) != null) {
				
				String[] separados = line.split(" ");
				for(String s : separados){
					if(!s.equals(" ") && !s.equals("\n")){
						result.add(s.toLowerCase());
					}
				}
			}
			this.strings = result;
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
}
