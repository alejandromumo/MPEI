import java.util.ArrayList;

public class Shingles {
	private ArrayList<ArrayList<String>> docs_shingles;
	
	public Shingles(ArrayList<ArrayList<String>> documents, int n){
		docs_shingles = new ArrayList<ArrayList<String>>();
		for(ArrayList<String> s : documents){
			StringToShingles(s, n);
		}
		
	}
	
	
	// Returns shingles from String s, grouped n by n Strings.
	
	private void StringToShingles(ArrayList<String> s,int n){
		ArrayList<String> shingles = new ArrayList<String>();
		//System.out.printf("String %s\n", s);
		for(int I = 0; I < s.size() - n ; I++){
			String x = " ";
			for(int J = I; J < I + n;J++){
				x += " " + s.get(J);
			}
			shingles.add(x);
		}
		docs_shingles.add(shingles);
	}
	
	
	// TODO - Return shingles from Strings , grouped n by n chars
	
	@SuppressWarnings("unused")
	private void StringToShinglesChar(ArrayList<String> s,int n){
		ArrayList<String> shingles = new ArrayList<String>();
		System.out.printf("Strings s : %s \n", s.toString());
		for(int I = 0; I < s.size(); I++){
			String x = " ";
			for(int J = I; J < I + n;J++){
				x += " " + s.get(I).charAt(J);
			}
			shingles.add(x);
		}
		docs_shingles.add(shingles);
	}
	
	// Getters
	
	public ArrayList<ArrayList<String>> getShingles(){
		return this.docs_shingles;
	}
}
