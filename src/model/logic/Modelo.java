package model.logic;

import java.awt.List;




import java.io.FileNotFoundException;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import model.data_structures.Array;
import model.data_structures.Queue;
import model.data_structures.SeparateChainingHashST;


/**
 * Definicion del modelo del mundo
 *
 */
public class Modelo {

	int numeroElementos = 0;

	int m = 1500;

	private SeparateChainingHashST<String, Array<Comparendos>> hashSectoresSC = new SeparateChainingHashST<String, Array<Comparendos>>(m);

	private Queue<Comparendos> policia = new Queue();

	public void loadComparendos (String comparendosFile)
	{
		JSONParser parser = new JSONParser();

		try {     
			Object obj = parser.parse(new FileReader(comparendosFile));

			JSONObject jsonObject =  (JSONObject) obj;
			JSONArray jsArray = (JSONArray) jsonObject.get("features");

			for(Object o: jsArray) {
				JSONObject comp = (JSONObject) o;	
				JSONObject properties =  (JSONObject) comp.get("properties");
				JSONObject geometry =  (JSONObject) comp.get("geometry");
				JSONArray coordinates = (JSONArray) geometry.get("coordinates");
				Comparendos comparendo = new Comparendos(String.valueOf(comp.get("type")), Integer.parseInt(String.valueOf(properties.get("OBJECTID"))), String.valueOf(properties.get("FECHA_HORA")),String.valueOf(properties.get("MEDIO_DETECCION")), String.valueOf(properties.get("CLASE_VEHICULO")), String.valueOf(properties.get("TIPO_SERVICIO")), String.valueOf(properties.get("INFRACCION")), String.valueOf(properties.get("DES_INFRACCION")), String.valueOf(properties.get("LOCALIDAD")),String.valueOf(properties.get("MUNICIPIO")), String.valueOf(geometry.get("type")), String.valueOf(coordinates),0,0,0);
				String objectId = Integer.toString(comparendo.getOBJECTID());


				String Key = comparendo.getFECHA_HORA()+comparendo.getCLASE_VEHI()+comparendo.getINFRACCION();

				String coordenadas = comparendo.getFECHA_HORA();
				String[] parts = coordenadas.split("-");

				String part1 = parts[0];
				String part2 = parts[1];
				String part3 = parts[2];

				String[] diaP = part3.split("T");		
				int anio = Integer.parseInt(part1);		
				int mes = Integer.parseInt(part2);
				int dia = Integer.parseInt(diaP[0]);
				comparendo.setDIA(dia);
				comparendo.setMes(mes);
				comparendo.setAnio(anio);

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e){
			e.printStackTrace();
		}
	}



}


