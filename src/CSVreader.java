import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
public class CSVreader {
	private String path;
	private ArrayList<String> strings;
	public CSVreader(String path,int col){
		this.path = path;
		csvToArray(this.path,col);
	}

    private void csvToArray(String csvpath,int col) {

        String csvFile = csvpath; // mudar para path do ficheiro
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        ArrayList<String> result = new ArrayList<String>() ;
        int count = 0;
        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] country = line.split(cvsSplitBy);
                if(!country[col].equals("NULL") && count!=0) result.add(country[col].toLowerCase());
                count++;
                //System.out.println("City [code= " + country[6] + "]");
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

    public ArrayList<String> getStrings(){
    	return this.strings;
    }
}
