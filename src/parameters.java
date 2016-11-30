public class parameters {
		int a, b, c, m;
		int p;

		public parameters(int a, int b, int c, int m, int p) {
			this.a = a;
			this.b = b;
			this.c = c;
			this.m = m;
			this.p = p;
		}

		public void setP(int p) {
			this.p = p;
		}

		public int getP() {
			return p;
		}

		public int getA() {
			return a;
		}

		public void setA(int a) {
			this.a = a;
		}

		public int getB() {
			return b;
		}

		public void setB(int b) {
			this.b = b;
		}

		public int getC() {
			return c;
		}

		public void setC(int c) {
			this.c = c;
		}

		public int getM() {
			return m;
		}

		public void setM(int m) {
			this.m = m;
		}

		@Override
		public String toString() {
			return String.format("Hash func parameters: %d,%d,%d,%d,%d", getA(), getB(), getC(), getM(), getP());
		}
		
		@Override
		public boolean equals(Object obj){
			if(obj == null)
				return false;
			parameters p = (parameters) obj;
			//TODO - confirm
			if(this.getA() == p.getA() || this.getB() == p.getB() || this.getC() == p.getC() || this.getP() == p.getP())
				return true;
			return false;
		}
	}