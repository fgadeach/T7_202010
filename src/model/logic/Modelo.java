package model.logic;

import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import model.data_structures.Arco;
import model.data_structures.Array;
import model.data_structures.Grafo;
import model.data_structures.Queue;
import model.data_structures.SeparateChainingHashST;
import model.data_structures.Vertice;


/**
 * Definicion del modelo del mundo
 *
 */
public class Modelo {

	int m = 1500;

	private Grafo<String, String, Double> grafo= new Grafo<String,String,Double>(m);

	public void cargarGrafo (String vertices, String arcos) throws Exception
	{
		File vertice = new File(vertices);
		File arc = new File(vertices);

		try 
		{
			BufferedReader bufferedReader = new BufferedReader(new FileReader(vertice));
			BufferedReader bufferedReaderArcos = new BufferedReader(new FileReader(arcos));
			String linea;
			String linea2;
			String datos[];

			while((linea = bufferedReader.readLine())!=null)
			{
				datos = linea.split(",");
				String id = datos[0];
				String info = datos[1]+","+datos[2];

				if(id!=null) 
				{
					grafo.addVertex(id, info);
					String hola = grafo.getInfoVertex(id);
				}
			}

			while((linea2 = bufferedReaderArcos.readLine())!=null)
			{

				datos = linea2.split(" ");

				for (int i = 0; i < datos.length-1; i++) 
				{
					if(datos[i+1]!=null) 
					{
						String info = grafo.getInfoVertex(datos[i]);		
						String infoD = grafo.getInfoVertex(datos[i+1]);	

						String lector[] = info.split(",");
						double latitud = Double.parseDouble(lector[0]);

						double longitud = Double.parseDouble(lector[1]);

						String lectorD[] = infoD.split(",");
						double latitudD = Double.parseDouble(lectorD[0]);
						double longitudD = Double.parseDouble(lectorD[1]);

						Haversine harv = new Haversine();
						Double infoArc = harv.distance(latitud, longitud, latitudD, longitudD);
						grafo.addEdge(datos[i],datos[i+1], infoArc);


					}
				}
			}

			bufferedReader.close();
		} 
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int numeroArcos() 
	{

		return grafo.E();
	}
	public int numeroVertices() 
	{
		return grafo.V();
	}

	@SuppressWarnings({ "unchecked"})
	public void guardarGrafo() throws IOException 
	{
		JSONObject obj = new JSONObject();


		JSONArray vertices = new JSONArray();
		JSONArray arcos = new JSONArray();

		Iterator<String> iter = grafo.iterarVertices();
		while(iter.hasNext()) 
		{
			String id = iter.next();
			String nodo = grafo.getInfoVertex(id);
			JSONObject idJ = new JSONObject();

			idJ.put("ID_VERTICE", id);
			idJ.put("DIRECCION_VERTICE", nodo);
			
			vertices.add(idJ);
		}


		Iterator<Arco<String, Double>> iterArcos = grafo.arcos().iterator();

		while(iterArcos.hasNext()) 
		{
			Arco<String, Double> arco = iterArcos.next();

			JSONObject idJ = new JSONObject();
			
			idJ.put("PRIMER_VERTICE", arco.darPrimerVertice());
			idJ.put("ULTIMO_VERTICE", arco.darUltimoVertice());
			idJ.put("INFORMACION_ARCO", arco.darInfo().toString());		
			arcos.add(idJ);
		}

		obj.put("Vertices", vertices);
		obj.put("Arcos", arcos);

		FileWriter fw = new FileWriter(new File("./data/comparendos.geojson"), false);
		fw.write(obj.toJSONString());
		fw.close();
	}
}


