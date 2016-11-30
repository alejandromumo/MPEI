import java.util.BitSet;

public class mainBloomFilter {
	// TODO : Better tests
	public static void main(String[] args) {
		
		
		/*
		 * Usage: 	colocar em k o número de hash functions a serem utilizadas
		 * 			colocar em size o tamanho do bloom filter
		 * 
		 * 
		 * */

		
		/********************************
		 *       Zona de código			*
		 * 								*
		 ********************************/ 
	
		  	int size = 1000;
		  	int k = 5;
			BitSet array = new BitSet(size);
			String[] Members = { "Batata" };
			String[] Test = { "Batata", "Feijao", "Tomate", "tabata" };
			BloomFilter bloom = new BloomFilter(k, size);
			parameters[] hashFuncs = bloom.init(size, k);
			int length = Members.length;
			for (int I = 0; I < length; I++) {
				String memberToAdd = Members[I];
				array = bloom.insert(array, hashFuncs, k, memberToAdd);
			}
			int lengthTest = Test.length;
			int belongs;
			for (int J = 0; J < lengthTest; J++) {
				String memberToTest = Test[J];
				belongs = bloom.belongs(array, memberToTest, hashFuncs);
				if (belongs == 1)
					System.out.printf("Test key %s - Probably exists\n", memberToTest);
				else
					System.out.printf("Test key %s - Doesn't exist\n", memberToTest);
			}
		

	}

}
