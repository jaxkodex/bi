package bi.colegios.data.parser;

public enum CalificacionPrimaria {
	A (2),
	B (1),
	C (0);
	
	private int val;
	
	CalificacionPrimaria (int val) {
		this.val = val;
	}

	public int getVal() {
		return val;
	}

	public void setVal(int val) {
		this.val = val;
	}
}
