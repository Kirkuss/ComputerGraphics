package extrudeEx;

public class Vertex {
	int index; // -1 for representing a vector
	double x, y, z;
	
	public Vertex(int index, double x, double y, double z) {
		this.index = index;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public int GetIndex() { return index;}
	public double GetX() { return x;}
	public double GetY() { return y;}
	public double GetZ() { return z;}
	
	public boolean equals(Vertex v) {
		if(this.x == v.GetX() && this.y == v.GetY() && this.z == v.GetZ()) {
			return true;
		}
		return false;
	}
	
	public String toString() {
		return "v " + x + " " + y + " " + z + "";
	}
}
