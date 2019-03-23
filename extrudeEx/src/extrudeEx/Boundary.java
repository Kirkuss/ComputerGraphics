package extrudeEx;

public class Boundary {
	private Vertex A;
	private Vertex B;
	
	public Boundary(Vertex A, Vertex B) {
		this.A = A;
		this.B = B;
	}
	
	public Vertex GetA() { return A;}
	public Vertex GetB() { return B;}
	
	public String toString() { return A + " -> " + B;}
}
