import java.util.ArrayList;

public class Shingles {
	private ArrayList<ArrayList<String>> docs_shingles;
	
	//Construtor
	
	public Shingles(ArrayList<ArrayList<String>> documents, int n, boolean group_by_words){
		docs_shingles = new ArrayList<ArrayList<String>>();
		for(ArrayList<String> s : documents){
			if(group_by_words) StringToShingles(s, n);
			else StringToShinglesChar(s, n);
		}
		
	}
	
	
	// Create shingles from String s, grouped n by n words.
	
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
	
	
	// Create shingles from String s, grouped n by n chars
	
	private void StringToShinglesChar(ArrayList<String> s,int n){
		ArrayList<String> shingles = new ArrayList<String>();
		String toda = "";
		for(String sh : s){
			toda+=sh + " ";
		}
		for(int I = 0; I < toda.length() - n; I++){
			String x = "";
			for(int J = I; J < I + n;J++){
				x += toda.charAt(J);
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
