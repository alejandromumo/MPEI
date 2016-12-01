import java.util.BitSet;

public class mainBloomFilter {
	// TODO : Better tests
	public static void main(String[] args) {
		
		
		/*
		 * Usage: 	colocar em k o número de hash functions a serem utilizadas
		 * 			colocar em size o tamanho do bloom filter
		 * 			colocar na pasta src/docs/Apresentacao/BloomFilter os ficheiros de membros e de elementos a testar.
		 * 			colocar nos campos MemberFileName e TestFileName os nomes dos ficheiros de Membros e de Teste respetivamente
		 * 
		 * */
	  	int size = 50000;
	  	int k = 100;
	  	String MemberFileName = "diccionario_PTF.txt";
	  	String TestFileName = "testBF.txt";
	  	
		
	  	
	  	
	  	
	  	
	  	
		/********************************
		 *       Zona de código			*
		 * 								*
		 ********************************/ 
	

			BitSet array = new BitSet(size);
			String workspace = System.getProperty("user.dir");
			TxTReader tr = new TxTReader(workspace + "/src/docs/Apresentacao/BloomFilter/" + MemberFileName);
			String[] Members = new String[tr.getStrings().size()];
			System.out.printf("Size of dict: %d \n", Members.length);
			tr.getStrings().toArray(Members);
			TxTReader testReader = new TxTReader(workspace + "/src/docs/Apresentacao/BloomFilter/" + TestFileName);
			String[] Test = new String[testReader.getStrings().size()];
			System.out.printf("Test size : %d\n", Test.length);
			testReader.getStrings().toArray(Test);
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
