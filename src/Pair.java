
public class Pair{
	private int doc1,doc2;
	public Pair(int doc1,int doc2){
		this.doc1 = doc1;
		this.doc2 = doc2;
	}
	public int getDoc1() {
		return doc1;
	}
	public int getDoc2() {
		return doc2;
	}
	@Override
	public int hashCode() {
		String s = "";
		s = Integer.toString(this.getDoc1()) + "," + Integer.toString(this.getDoc2()) ;
		return s.hashCode();
	}
	@Override
	public String toString(){
		return String.format("(%d,%d) ", getDoc1(),getDoc2());
	}
	@Override
	public boolean equals(Object obj){
		if(obj == null)
			return false;
		Pair p = (Pair) obj;
		if(this.getDoc1() == p.getDoc1() && this.getDoc2() == p.getDoc2())
			return true;
		else
			return false;
		
	}

}
