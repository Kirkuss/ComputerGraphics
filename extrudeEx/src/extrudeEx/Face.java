package extrudeEx;

import java.util.ArrayList;

public class Face {
	int index = 0;
	private ArrayList<Vertex> vertices = new ArrayList<Vertex>();
	
	public Face(int index) {
		this.index = index;
	}
	
	public void AddVertex(Vertex v) {
		vertices.add(v);
	}
	
	public void SetVertices(ArrayList<Vertex> vertices) {
		this.vertices = vertices;
	}
	
	public ArrayList<Vertex> GetVertices() { return vertices;}
	public int GetIndex() { return index; }
	
	public String toString() {
		String result = "";
		for(int i = 0; i<vertices.size(); i++) {
			result += vertices.get(i).GetIndex() + " ";
		}
		return "f " + result;
	}
	public Vertex GetNormal() {
		Vertex A = vertices.get(2);
		Vertex B = vertices.get(0);
		Vertex C = vertices.get(1);

		Vertex vectorAB = new Vertex(-1, B.GetX() - A.GetX(), B.GetY() - A.GetY(), B.GetZ() - A.GetZ());
		Vertex vectorAC = new Vertex(-1, C.GetX() - C.GetX(), C.GetY() - A.GetY(), C.GetZ() - A.GetZ());
		
		Vertex vectorNM = new Vertex(-1, (vectorAB.GetY() * vectorAC.GetY()) - (vectorAB.GetZ() * vectorAC.GetY()),
				(vectorAB.GetZ() * vectorAC.GetX()) - (vectorAB.GetX() * vectorAC.GetZ()),
				(vectorAB.GetX() * vectorAC.GetY()) - (vectorAB.GetY() * vectorAC.GetX()));
		
		double NMMod = Math.sqrt(Math.pow(vectorNM.GetX(),2) + Math.pow(vectorNM.GetY(),2) + Math.pow(vectorNM.GetZ(),2));
		Vertex vNormalized = new Vertex(-1, vectorNM.GetX()/NMMod, vectorNM.GetY()/NMMod, vectorNM.GetZ()/NMMod);
		
		System.out.println("\nNormal vector: \n" + vNormalized.toString() + "\n");
		
		return vNormalized;
	}
}
