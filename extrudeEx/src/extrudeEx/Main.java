package extrudeEx;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Main {

	public static void main(String[] args) throws IOException {
		JTextField file = new JTextField();
		JTextField factor = new JTextField();
		ArrayList<Vertex> O_vertices;
		ArrayList<Face> O_faces;
		ArrayList<Vertex> new_Vertices = null;
		ArrayList<Face> new_Faces = null;
		Object[] message = {
		    "OBJ File:", file,
		    "Extrusion factor:", factor
		};
		int option = JOptionPane.showConfirmDialog(null, message, "Extrude algorithm", JOptionPane.OK_CANCEL_OPTION);
		if (option == JOptionPane.OK_OPTION) {
			OBJParsers obj = new OBJParsers(file.getText());
			boolean numeric = factor.getText().matches("-?\\d+(\\.\\d+)?");
			if(numeric) {
				obj.Read();
				O_vertices = new ArrayList<Vertex>(obj.GetVertices().values());
				O_faces = new ArrayList<Face>(obj.GetFaces().values());
				Extruder e = new Extruder(O_vertices, O_faces, Double.parseDouble(factor.getText()));
				e.Extrude();
				new_Vertices = e.GetNewVertices();
				new_Faces = e.GetNewFaces();
			}
		    
		FileWriter fw = new FileWriter("resultExt.obj");
		
		for(int i = 0; i<new_Vertices.size(); i++) {
			System.out.println(new_Vertices.get(i).toString());
			fw.write("\n" + new_Vertices.get(i).toString());
		}
		
		for(int i = 0; i<new_Faces.size(); i++) {
			System.out.println(new_Faces.get(i).toString());
			fw.write("\n" + new_Faces.get(i).toString());
		}
		fw.close();
	}

	}
}
