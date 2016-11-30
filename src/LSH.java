import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

public class LSH extends MainMinHash {
	private int[][] signatureMatrix;
	private Hashtable<Integer, ArrayList<Integer>> buckets;
	private Hashtable<Pair, Integer> pairs_table;

	// Constructor
	// Given a signature Matrix sigMatrix , it hashes a document's signature
	// into a HashTable.
	// Parameters:
	// 	- sigMatrix 		: signature Matrix
	// 	- ndocs 			: number of documents being analysed
	// 	- numhashfuncs 	: number of hashfunctions used

	public LSH(int[][] sigMatrix, int nDocs,int numBuckets, int numRowPBand) {
		this.signatureMatrix = sigMatrix;
		if(super.isPrintMatrix()){
			System.out.println("LSH constructor. Given matrix : ");
			printMatrix(this.signatureMatrix, super.getNumhashfuncs(),nDocs);
		}
		this.buckets = new Hashtable<Integer, ArrayList<Integer>>(numBuckets);
		this.pairs_table = new Hashtable<Pair, Integer>();
		for (int k : this.buckets.keySet()) {
			System.out.printf("Key : %d\n", k);
		}
		parameters p = new parameters(0, 0, 0, 0, 0);
		initHashFunc(100000, p);
		int linhasPorBanda = numRowPBand;

		ArrayList<Integer> banda = new ArrayList<Integer>();
		int docAnalysing = 0;
		for (int J = 0; J < super.getNumhashfuncs(); J += linhasPorBanda) {
			banda.clear();
			if (docAnalysing > nDocs - 1)
				break;
			for (int K = J; K < J + linhasPorBanda; K++) {
				banda.add(this.signatureMatrix[K][docAnalysing]);
			}
			System.out.printf("Banda : %s\n", banda.toString());
			// Banda construída, pronta a ser calculado o seu hashcode

			int hashCode = hashCode(p, banda);
			ArrayList<Integer> tmp = this.buckets.get(hashCode);
			if (tmp == null)
				tmp = new ArrayList<Integer>();
			if (!tmp.contains(docAnalysing)) {
				tmp.add(docAnalysing);
				this.buckets.put(hashCode, tmp);
			}
			if (J == super.getNumhashfuncs() - linhasPorBanda) {
				docAnalysing++;
				J = -linhasPorBanda;
			}
		}
		for (int k : this.buckets.keySet()) {
			System.out.printf("Key : %d -> Value : %s\n", k, this.buckets.get(k));
		}
		checkPairs();
		System.out.printf("Candidate pairs: \n%s\n", this.pairs_table.toString());
	}

	
	// Check which documents are similar candidates
	
	private void checkPairs() {
		for (Integer k : this.buckets.keySet()) {
			ArrayList<Integer> tmp = this.buckets.get(k);
			if (this.buckets.get(k).size() > 1) {
				// System.out.printf("Collision at key %d\n", k);
				Pair p;
				for (int I = 0; I < tmp.size() - 1; I++) {
					// System.out.printf("Docs: %d and
					// %d\n",tmp.get(I),tmp.get(I+1));
					for (int J = I + 1; J < tmp.size(); J++) {
						p = new Pair(tmp.get(I), tmp.get(J));
						if (!pairs_table.containsKey(p))
							pairs_table.put(p, 1);
						else {
							pairs_table.put(p, pairs_table.get(p) + 1);
						}
					}
				}
			}
		}

	}

	
	// Print a matrix of integers
	
	private void printMatrix(int[][] matrix,int rows,int columns){
		for(int I = 0; I < rows ; I++){
			System.out.printf("I = %d \t\t", I);
			for(int J = 0; J < columns ; J++){
				System.out.printf("[%d]", matrix[I][J]);
			}
			System.out.println();
		}
	}
	
	
	// Hash Function methods

	// hashCode - Computes an integer hash code of a given key
	// Parameters:
	// - p : hashfunction's parameters
	// - key : key to be converted into a hashcode
	// Returns :
	// - hk : hashcode to given key

	private int hashCode(parameters p, ArrayList<Integer> key) {
		int length = key.size();
		int hk;
		hk = 7;
		for (int J = 0; J < length; J++) {
			hk = p.getC() * hk + key.get(J) % p.getP();
			hk = ((p.getA() * hk + p.getB() % p.getP()) % p.getM()) + 1;
		}
		if (hk < 0)
			hk = Math.abs(hk);

		return hk;
	}

	
	// InitHashFunc - Initializes parameters of a hash function
	// Parameters:
	// - m : size
	// - p : parameters of a hash function to be initialized

	private void initHashFunc(int m, parameters p) {
		int ff = 1000;
		int pp = ff * Math.max(m + 1, 76);
		if (pp % 2 == 0)
			pp = pp + 1;
		while (!isPrime(pp)) {
			pp += 2;
		}
		Random r = new Random();
		int upperBound = (int) pp - 1;
		p.setA(r.nextInt(upperBound - 1) + 1);
		p.setB(r.nextInt(upperBound));
		p.setC(r.nextInt(upperBound - 1) + 1);
		p.setP(pp);
		p.setM(m);
	}

	// isPrime - Verifies if a number is prime
	// Parameters:
	// - n : number to be tested
	// Returns :
	// - true if given n is prime,
	// false otherwise

	private boolean isPrime(int n) {
		for (int i = 2; i <= n / 2; i++) {
			if (n % i == 0)
				return false;
		}
		return true;
	}

	
	// Getters

	public int[][] getSignatureMatrix() {
		return signatureMatrix;
	}

	public Hashtable<Integer, ArrayList<Integer>> getHashTable() {
		return buckets;
	}
	
	public Hashtable<Pair, Integer> getPairs_table() {
		return pairs_table;
	}

}
