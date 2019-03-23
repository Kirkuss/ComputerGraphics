package extrudeEx;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

public class OBJParsers {
	
	private String PATH;
	private Hashtable<Integer, Vertex> vertices = new Hashtable<Integer, Vertex>();
	private Hashtable<Integer, Face> faces = new Hashtable<Integer, Face>();
	
	public OBJParsers(String path) {
		this.PATH = path;
	}
	
	public void Read() {
		
		BufferedReader br = null;
		FileReader fr = null;
		
		try {
			fr = new FileReader(PATH);
			br = new BufferedReader(fr);
			
			String line, str_x = "", str_y = "", str_z = "";
			String face_v = "";
			
			ArrayList<String> face_vs = new ArrayList<String>();
			
			int v_id = 0, f_id = 0;
			int spaces = 0;
			
			while ((line = br.readLine()) != null) {
				spaces = 0;
				str_x = "";
				str_y = "";
				str_z = "";
				if(line.charAt(0) == 'v') v_id += 1;
				else if(line.charAt(0) == 'f') {
					f_id += 1;
					face_vs.clear();
				}
				for(int i = 0; i<line.length(); i++) {
					if(line.charAt(0) == 'v' && line.charAt(1) == ' ') {
						if(line.charAt(i) == ' ') {
							spaces += 1;
						}else if(spaces != 0 && line.charAt(i) != ' ') {
							switch(spaces) {
							case 1:
								str_x += line.charAt(i);
								break;
							case 2:
								str_y += line.charAt(i);
								break;
							case 3:
								str_z += line.charAt(i);
								break;
							}
						}
					}else if(line.charAt(0) == 'f' && line.charAt(1) == ' ') {
						if(line.charAt(i) == ' ') {
							spaces += 1;
						}else if(spaces != 0 && line.charAt(i) != ' ') {
							face_v += line.charAt(i);
							face_vs.add(face_v);
						}
						if(spaces > 1 && line.charAt(i) == ' ') {
							face_v = "";
						}
					}
				}
				switch(line.charAt(0)) {
				case 'v': 
					double x = 0, y = 0, z = 0;
					if (!str_x.equals("") || !str_y.equals("") || !str_z.equals("")) {
						try {
							x = Double.parseDouble(str_x);
							y = Double.parseDouble(str_y);
							z = Double.parseDouble(str_z);
						}catch (Exception e) {
							e.printStackTrace();
						}
						Vertex vert = new Vertex(v_id, x, y, z);
						vertices.put(v_id, vert);
					}
					break;
				case 'f':
					Face f = new Face(f_id);
					for(int i = 0; i<face_vs.size(); i++) {
						Vertex faceVertex = vertices.get(Integer.parseInt(face_vs.get(i)));
						f.AddVertex(faceVertex);
						faces.put(f_id, f);
					}
					break;
				}
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) br.close();
				if (fr != null) fr.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public Hashtable<Integer, Vertex> GetVertices(){ return vertices; }
	public Hashtable<Integer, Face> GetFaces(){ return faces;}
}
