import java.util.Random;
import java.util.BitSet;
public class BloomFilter {
	private int size, k;
	private BitSet array;

	/* Constructor BloomFilter(int k, int size)
	 * Parameters:
	 * 	- k 	: Number of hash functions to be used
	 * 	- size 	: Size of the bloom filter
	 * */
	
	public BloomFilter(int k, int size) {
		this.size = size;
		this.k = k;
		this.array = new BitSet(size);
	}

	
	// Initializes k hash functions and returns an array of them
	
	public parameters[] init(int n, int k) { 
		parameters[] arrayOfHashFuncs = new parameters[k];
		parameters p;
		for (int I = 0; I < k; I++) {
			p = new parameters(0, 0, 0, 0, 0);
			initHashFunc(n, p);
			arrayOfHashFuncs[I] = p;
		}
		return arrayOfHashFuncs;
	}

	
	// Receives a hashfunction and a key and returns a hashcode (int)
	
	private int hashCode(parameters p, String key) {
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
	
	
	// Inserts a key into Bloom's filter array

	public BitSet insert(BitSet array, parameters[] hashFuncs, int k, String key) {
		int hk;
		parameters p;
		for (int I = 0; I < k; I++) {
			p = hashFuncs[I];
			hk = hashCode(p, key);
			array.set(hk);
		}
		return array;
	}

	
	// Initializes a hash function using random integer and a prime number
	
	public void initHashFunc(int m, parameters p) {
		int ff = 1000;
		int pp = ff * Math.max(m + 1, 76); // Escolher melhor primo base
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

	
	/* Belongs(BitSet,String,parameters[]) 
	 * Parameters:
	 * 	- array		: Bloom filter's array
	 *  - key		: Key to be tested
	 *  - hashFuncs	: HashFuncs used in the bloom filter
	 * */
	
	public int belongs(BitSet array, String key, parameters[] hashFuncs) {
		int I;
		int length = hashFuncs.length;
		int hk;
		parameters p;
		for (I = 0; I < length; I++) {
			p = hashFuncs[I];
			hk = hashCode(p, key);
			if (array.get(hk) == false)
				return 0;
		}
		return 1;

	}

	// Returns true if given integer n is prime. Otherwise returns false
	
	static boolean isPrime(int n) {
		for (int i = 2; i <= n / 2; i++) {
			if (n % i == 0)
				return false;
		}
		return true;
	}
	
	
	// Getters
	
	public int getSize() {
		return size;
	}

	public int getK() {
		return k;
	}

	public BitSet getArray() {
		return array;
	}

}
