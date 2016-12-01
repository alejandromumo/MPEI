import java.util.ArrayList;

public class Test {

	public static void main(String[] args) {
		TxTReader t = new TxTReader("C:/Users/mumox/workspace/MPEI/src/docs/Apresentacao/AulaTest/ficheiro_0.txt");
		ArrayList<ArrayList<String>> docs = new ArrayList<>();
		docs.add(t.getStrings());
		Shingles sh = new Shingles(docs,5,false);

	}

}
