import java.util.ArrayList;
import java.util.Random;

public class MinHash extends MainMinHash {
		private ArrayList<ArrayList<String>> ShingleSet;
		private int[][] signaturesMatrix;
		private parameters[] hashFuncs;
		private int numhashfuncs;
		
		/* Constructor
		 *  MinHash(Set<String> InputSet1, int k)
		 *  	- InputSet1 => o Set a ser convertido numa matriz de signatures (matriz de bits).
		 *  Esta classe irá gerar as hashfunctions que de seguida irão ser usadas no bloomfilter e a matriz de signaturas
		 *  	- getHashFuncs() 		=> hashfuncs
		 * 		- getSignaturesMatrix() => Matriz de signaturas dos documentos 
		 * 
		 */
		
		public MinHash(ArrayList<ArrayList<String>> InputSet1){
			this.ShingleSet = InputSet1;
			this.numhashfuncs = super.getNumhashfuncs();
			hashFuncs = new parameters[numhashfuncs];
			signaturesMatrix = new int[numhashfuncs][InputSet1.size()];
			for(int L = 0; L < numhashfuncs; L++){
				for(int C = 0; C < InputSet1.size() ; C++)
					signaturesMatrix[L][C] = -1;
			}
			parameters p;
			// Inicializa k hashFuncs baseadas no size do ShingleSet1
			for(int I = 0 ; I < this.numhashfuncs ; I++){
				p = new parameters(0,0,0,0,0);
				initHashFunc(100000,p);
				hashFuncs[I] = p;
			}
			
			// Generate Matrix
			int docnum = 0;
			for(ArrayList<String> Shingles: this.ShingleSet){
				setToBoolMatrix(Shingles, numhashfuncs,docnum);
				docnum++;
			}
			
		}
		
		
		// Converts a set into a boolean matrix of signatures
		
		private void setToBoolMatrix(ArrayList<String> SetShingles,int numhashfuncs, int docAnalysing){
			int setlen = SetShingles.size();
			// Matriz numhashfuncs x len(set)
			ArrayList<Integer> hashcodes = new ArrayList<Integer>();
			int min ;
			String s = "";
			int hashCodeForGivenShingle;
			for(int I = 0; I < numhashfuncs; I++){
				parameters p = hashFuncs[I];
				for(int J = 0; J < setlen ; J++){
					// Verificamos um shingle de cada vez (p.e. "O bom")
					s = SetShingles.get(J);
					// Calculamos os hashcodes para o determinado Shingle Hi("O bom") = 1
					hashCodeForGivenShingle = hashCode(p,s);
					// Ficámos com o mínimo valor desses hashcodes, min é o hashcode da string
					min = hashCodeForGivenShingle;
					hashcodes.add(min);
					if(signaturesMatrix[I][docAnalysing] == -1)
						signaturesMatrix[I][docAnalysing] = min;
					else if(min < signaturesMatrix[I][docAnalysing])
						signaturesMatrix[I][docAnalysing] = min;
				}
				hashcodes.clear();
			}
		}
		
		
		// Receives a list and returns it's min. value
		
		@SuppressWarnings("unused")
		private static int minFromList(ArrayList<Integer> list){
			int min = list.get(0);
			for(int I = 1; I < list.size(); I++){
				int elemento = list.get(I);
				if(elemento < min)
					min = elemento;
			}
			return min;
		}
		
		
		// Receives a hashfunction and a key and returns a hashcode (int)
		
		private static int hashCode(parameters p, String key) {
			int length = key.length();
			int hk;
			hk = key.charAt(0);
			for (int J = 1; J < length; J++) {
				hk = (p.getC() * hk + key.charAt(J)) % p.getP();
			}
			hk = (((p.getA() * hk + p.getB()) % p.getP()) % p.getM()) + 1;
			if(hk < 0)
				hk = Math.abs(hk);

			return hk;
		}
		
		
		// Initializes a hash function using random integer and a prime number
		
		private static void initHashFunc(int m, parameters p) {
			int ff = 1000;
			int pp = ff * Math.max(m + 1, 76);
			if (pp % 2 == 0)
				pp = pp + 1;
			while (!isPrime(pp)) {
				pp += 2;
			}
			Random r = new Random();
			int upperBound = pp - 1;
			p.setA(r.nextInt(upperBound - 1) + 1);
			p.setB(r.nextInt(upperBound));
			p.setC(r.nextInt(upperBound - 1) + 1);
			p.setP(pp);
			p.setM(m);
		}
		
		
		// Returns true if given integer n is prime. Otherwise returns false
		
		private static boolean isPrime(int n) {
			for (int i = 2; i <= n / 2; i++) {
				if (n % i == 0)
					return false;
			}
			return true;
		}

		
		
		// Getters
		

		public int[][] getSignaturesMatrix() {
			return signaturesMatrix;
		}

		public parameters[] getHashFuncs() {
			return hashFuncs;
		}
		
		
		
}
