package extrudeEx;

import java.util.ArrayList;
import java.util.Collections;

public class Extruder {
	private ArrayList<Face> new_Faces = new ArrayList<Face>();
	private ArrayList<Vertex> O_Vertices = new ArrayList<Vertex>();
	private ArrayList<Face> O_Faces = new ArrayList<Face>();
	private ArrayList<Boundary> boundaries;

	private double EXTRUDE_FACTOR = 0;
	private int lastID_V = 0;
	private int lastID_F = 0;

	public Extruder(ArrayList<Vertex> O_vertices, ArrayList<Face> O_faces, double factor) {
		this.O_Vertices = O_vertices;
		this.O_Faces = O_faces;
		EXTRUDE_FACTOR = factor;
		lastID_V = O_Vertices.size() + 1;
		lastID_F = O_Faces.size() + 1;
	}

	public void Extrude() {
		boundaries = new ArrayList<Boundary>();
		Face new_Face;
		Collections.reverse(O_Vertices);
		for(int j = 0; j<O_Vertices.size(); j++) {
			if(j+1 < O_Vertices.size()) {
				boundaries.add(new Boundary(O_Vertices.get(j), O_Vertices.get(j+1)));
			}else {
				boundaries.add(new Boundary(O_Vertices.get(j), O_Vertices.get(0)));
			}
		}
		for(int i = 0; i<O_Faces.size(); i++) {
			Face f = O_Faces.get(i);
			new_Face = new Face(lastID_F);
			lastID_F += 1;
			Vertex normal = f.GetNormal();
			
			double [][] Transform = {{1,0,0,normal.GetX()*EXTRUDE_FACTOR},
					{0,1,0,normal.GetY()*EXTRUDE_FACTOR},
					{0,0,1,normal.GetZ()*EXTRUDE_FACTOR},
					{0,0,0,1}};
			for(int j = 0; j<boundaries.size(); j++) {
				Vertex A = boundaries.get(j).GetA();
				Vertex B = boundaries.get(j).GetB();
				
				double [][] vertexMatrixA = {{A.GetX()},
						{A.GetY()},
						{A.GetZ()},
						{1}};
				double [][] vertexMatrixB = {{B.GetX()},
						{B.GetY()},
						{B.GetZ()},
						{1}};
				
				double [][] newCoordAd = multiplyMatrices(Transform, vertexMatrixA, 4, 4, 1);
				double [][] newCoordBd = multiplyMatrices(Transform, vertexMatrixB, 4, 4, 1);
				
				Vertex newA = new Vertex(lastID_V, newCoordAd[0][0], newCoordAd[1][0], newCoordAd[2][0]);
				newA = checkID(newA);
				lastID_V += 1;
				Vertex newB = new Vertex(lastID_V, newCoordBd[0][0], newCoordBd[1][0], newCoordBd[2][0]);
				newB = checkID(newB);
				
				O_Vertices.add(newA);
				new_Face.AddVertex(newA);
				
				Face triangleInf = new Face(lastID_F);
				lastID_F += 1;
				Face triangleSup = new Face(lastID_F);
				lastID_F += 1;

				triangleInf.AddVertex(A);
				triangleInf.AddVertex(B);
				triangleInf.AddVertex(newB);
				triangleSup.AddVertex(newB);
				triangleSup.AddVertex(newA);
				triangleSup.AddVertex(A);

				new_Faces.add(triangleInf);
				new_Faces.add(triangleSup);
			}
			new_Faces.add(new_Face);
		}
	}

	public double[][] multiplyMatrices(double[][] firstMatrix, double[][] secondMatrix, int r1, int c1, int c2) {
		double[][] product = new double[r1][c2];
		for(int i = 0; i < r1; i++) {
			for (int j = 0; j < c2; j++) {
				for (int k = 0; k < c1; k++) {
					product[i][j] += firstMatrix[i][k] * secondMatrix[k][j];
				}
			}
		}

		return product;
	}
	
	public Vertex checkID (Vertex v) {
		for(int i = 0; i<O_Vertices.size(); i++) {
			if(O_Vertices.get(i).equals(v)) {
				return O_Vertices.get(i);
			}
		}
		return v;
	}
	
	public ArrayList<Vertex> GetNewVertices() {
		return O_Vertices;
	}
	public ArrayList<Face> GetNewFaces(){
		return new_Faces;
	}
}

