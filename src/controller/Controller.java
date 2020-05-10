package controller;

import java.io.FileReader;



import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.awt.List;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import model.data_structures.Queue;
import model.logic.Comparendos;
import model.logic.Modelo;
import view.View;

public class Controller {

	/*
	 * 
	 */


	/* Instancia del Modelo*/
	private Modelo modelo;

	/* Instancia de la Vista*/
	private View view;

	private Comparendos comparendo;

	public static final String arcos="./data/bogota_arcos.txt";
	public static final String vertices="./data/bogota_vertices.txt";
	public static final String jsonAV="./data/grafo.geojson";
	public static final String POLICIA="./data/estacionpolicia.geojson";
	/**
	 * Crear la vista y el modelo del proyecto
	 * @param capacidad tamaNo inicial del arreglo
	 */
	public Controller()
	{
		//listaComparendos = new ArbolRojoNegro<String,Comparendos>();
		view = new View();
		modelo = new Modelo();
	}

	@SuppressWarnings("null")
	public void run() 
	{
		Scanner lector = new Scanner(System.in);
		boolean fin = false;


		while( !fin ){
			view.printMenu();

			int option = lector.nextInt();
			switch(option){

			case 0:
				modelo = new Modelo(); 
				try {
					modelo.cargarGrafo(vertices, arcos);
					System.out.println("numero de arcos "+modelo.numeroArcos());
					System.out.println("numero de vertices "+modelo.numeroVertices());
					modelo.guardarGrafo();
				} catch (Exception e) 
				{
					System.out.println("no carga");
				}
				break;

			case 1:

				try {
					modelo.loadGrafo(jsonAV);
				} catch (Exception e) 
				{
					System.out.println("no carga");
				}
				break;
			case 2:

				try {
					modelo.loadGrafoPolicia(POLICIA);
				} catch (Exception e) 
				{
					System.out.println("no carga");
				}
				break;

			default: 
				System.out.println("--------- \n Opcion Invalida !! \n---------");
				break;

			}
		}
	}
}	