import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainMinHash {
	private static int numhashfuncs = 100;
	private static boolean  printMatrix = true;
	private static Map<Integer,String> docNameNumber;

	public static void main(String[] args) {
		
		/*
		 * USAGE: 	colocar em pathToFiles a pasta onde os ficheiros .txt estão alocados (por default é a pasta docs do projeto)
		 * 			colocar em agrupar o número que queremos agrupar os shingles em
		 * 			colocar em numRowsPerBand a quantidade de linhas por banda		
		 * 			colocar em factor o fator que queremos para calcular o número de buckets (factor * numDocs)
		 * 			colocar em minCollisions o número mínimo de colisões para calcular a distância de Jaccard de dois documentos
		 * 			DEBUG : printMatrix para mostrar o estado da matriz de signaturas nos diferentes módulos
		 * */	
		
		String pathToFiles = "C:/Users/mumox/workspace/MPEI/src/docs";
		int agrupar = 10; // Agrupar n a n
		int factor = 100000;
		int numRowsPerBand = 2;
		int minCollisions = 15;
		
		
		
		
		/********************************
		 *       Zona de código			*
		 * 								*
		 ********************************/
		
		ArrayList<ArrayList<String>> docs = new ArrayList<ArrayList<String>>();
		File folder = new File(pathToFiles);
		File[] listOfFiles = folder.listFiles();
		TxTReader ci;
		int count = 0;
		docNameNumber = new HashMap<Integer,String>();
		for (File f : listOfFiles) {
			if (f.isFile() && f.getName().endsWith(".txt")) {
				ci = new TxTReader(f.getAbsolutePath());
				docs.add(ci.getStrings());
				docNameNumber.put(count,f.getName());
				count++;
			}
		}
		
		// Print documents and given number
		for(int number : docNameNumber.keySet()){
			System.out.printf("Doc: %s -> number %d\n",docNameNumber.get(number),number);
		}
		Shingles sh;
		int numDocs = docs.size();
		int numBuckets = factor * numDocs;
		sh = new Shingles(docs, agrupar);
		MinHash mh = new MinHash(sh.getShingles());
		if(printMatrix){
			System.out.printf("MAIN matrix\n");
			printMatrix(mh.getSignaturesMatrix(), numhashfuncs,numDocs);
		}
		
		LSH lsh = new LSH(mh.getSignaturesMatrix(), numDocs,numBuckets,numRowsPerBand);
		// Get Documents by columns
		for (Pair p : lsh.getPairs_table().keySet()) {
			if (lsh.getPairs_table().get(p) >= minCollisions) {
				System.out.printf("Calculating Jaccard for pair %s docs %s and %s \n",p.toString(),docNameNumber.get(p.getDoc1()),docNameNumber.get(p.getDoc2()));
				calculateJaccard(getDocSig(p.getDoc1(), mh.getSignaturesMatrix()), getDocSig(p.getDoc2(), mh.getSignaturesMatrix()));
			}
		}


	}

	
	// Print a matrix of integers
	
	private static void printMatrix(int[][] matrix,int rows,int columns){
		for(int I = 0; I < rows ; I++){
			System.out.printf("I = %d \t\t", I);
			for(int J = 0; J < columns ; J++){
				System.out.printf("[%d]", matrix[I][J]);
			}
			System.out.println();
		}
	}
	
	
	// Calcula a coluna assinatura de um documento

	private static ArrayList<Integer> getDocSig(int documentNumber, int[][] sigMatrix) {
		ArrayList<Integer> docSig = new ArrayList<Integer>();
		for (int I = 0; I < numhashfuncs; I++) {
			docSig.add(sigMatrix[I][documentNumber]);
		}
		return docSig;
	}

	
	// Calcula a distância de Jaccard entre dois documentos
	
	private static void calculateJaccard(ArrayList<Integer> doc1_sig, ArrayList<Integer> doc2_sig) {
		int intersection = 0;
		for (int I = 0; I < doc1_sig.size(); I++) {
			if (doc1_sig.get(I) == doc2_sig.get(I))
				intersection++;
		}

		double jaccard = (100 * intersection / numhashfuncs);
		System.out.printf("Intersection = %d | Reunion = %d | Jacccard = %3.1f\n", intersection, numhashfuncs, jaccard);

	}

	
	// Getters

	public static int getNumhashfuncs() {
		return numhashfuncs;
	}

	public static boolean isPrintMatrix() {
		return printMatrix;
	}

	public static Map<Integer, String> getDocNameNumber() {
		return docNameNumber;
	}
	
}
