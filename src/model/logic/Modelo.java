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

import javax.swing.JFrame;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;

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
	private Grafo<Double, String, Double> grafoJ= new Grafo<Double,String,Double>(m);
	private Grafo<Double, String, Double> grafoP= new Grafo<Double,String,Double>(m);

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
			JSONObject vertice = new JSONObject();

			idJ.put("ID", id);
			idJ.put("DIRECCION", nodo);

			vertice.put("VERTICE",idJ);
			vertices.add(vertice);
		}


		Iterator<Arco<String, Double>> iterArcos = grafo.arcos().iterator();

		while(iterArcos.hasNext()) 
		{
			Arco<String, Double> arco = iterArcos.next();

			JSONObject idJ = new JSONObject();
			JSONObject arcoS = new JSONObject();

			idJ.put("PRIMER_VERTICE", arco.darPrimerVertice());
			idJ.put("ULTIMO_VERTICE", arco.darUltimoVertice());
			idJ.put("INFORMACION_ARCO", arco.darInfo().toString());	


			arcoS.put("ARCO",idJ);
			arcos.add(arcoS);
		}

		obj.put("Vertices", vertices);
		obj.put("Arcos", arcos);

		FileWriter fw = new FileWriter(new File("./data/comparendos.geojson"), false);
		fw.write(obj.toJSONString());
		fw.close();
	}

	public void loadGrafo (String grafoFile)
	{
		JSONParser parser = new JSONParser();

		try {     
			Object obj = parser.parse(new FileReader(grafoFile));

			JSONObject jsonObject =  (JSONObject) obj;
			JSONArray jsArray = (JSONArray) jsonObject.get("Vertices");
			JSONArray jsArrayA = (JSONArray) jsonObject.get("Arcos");

			for(Object o: jsArray) {
				JSONObject comp = (JSONObject) o;	
				JSONObject vertices =  (JSONObject) comp.get("VERTICE");

				Vertice<Double,String> vertice = new Vertice<Double, String>(Double.parseDouble(String.valueOf(vertices.get("ID"))),String.valueOf(vertices.get("DIRECCION")));
				grafoJ.addVertex(vertice.darKey(), vertice.darinfo());
			}

			for(Object o: jsArrayA) 
			{
				JSONObject comp = (JSONObject) o;	
				JSONObject arcos =  (JSONObject) comp.get("ARCO");

				Arco<Double,Double> arco = new Arco<Double,Double>(Double.parseDouble(String.valueOf(arcos.get("PRIMER_VERTICE"))),Double.parseDouble(String.valueOf(arcos.get("ULTIMO_VERTICE"))),Double.parseDouble(String.valueOf(arcos.get("INFORMACION_ARCO"))));
				try {
					grafoJ.addEdge(arco.darPrimerVertice(), arco.darUltimoVertice(), arco.darInfo());
				} catch (Exception e) {
					System.out.println("Error carga arco");
				}
			}

			System.out.println("numero de vertices: " + grafoJ.V());
			System.out.println("numero de arcos: " + grafoJ.E());

		} catch (FileNotFoundException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e){
			e.printStackTrace();
		}
	}
	
	public void loadGrafoPolicia (String grafoFile)
	{
		JSONParser parser = new JSONParser();

		try {     
			
			Object obj = parser.parse(new FileReader(grafoFile));

			JSONObject jsonObject =  (JSONObject) obj;
			JSONArray jsArray = (JSONArray) jsonObject.get("features");

			for(Object o: jsArray) {
				JSONObject comp = (JSONObject) o;	
				JSONObject properties =  (JSONObject) comp.get("properties");
				JSONObject geometry =  (JSONObject) comp.get("geometry");
				JSONArray coordinates = (JSONArray) geometry.get("coordinates");
				String coordenadas = String.valueOf(coordinates);
				coordenadas = coordenadas.replaceAll("\\[","");
				coordenadas = coordenadas.replaceAll("\\]","");
				
				Vertice<Double,String> vertice = new Vertice<Double,String>(Double.parseDouble(String.valueOf(properties.get("OBJECTID"))), coordenadas);
				vertice.setLaYLo();
				grafoP.addVertex(vertice.darKey(), vertice.darinfo());
			
				System.out.println("OBJECTID_ESTACION: " + vertice.darKey().intValue()+" LATITUD: " + vertice.darLatitud()+" LONGITUD: "+ vertice.darLongitud());
			}
			System.out.println("Numero de estaciones: " + grafoP.V());
		} catch (FileNotFoundException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e){
			e.printStackTrace();
		}
	}
}


