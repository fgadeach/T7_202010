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

	public static final String ruta="./data/comparendos1.geojson";
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
		String n = null;
		String m = null;
		String o = null;
		String p = null;
		String q = null;

		int y =1;
		int s= 0;
		int r= 0;

		double l= 0;
		double z= 0;
		
		Integer i = 1;
		Object datoS = null;
		String respuesta = "";
		String tipo = null;


		while( !fin ){
			view.printMenu();

			int option = lector.nextInt();
			switch(option){
			
			case 0:
				modelo = new Modelo(); 
				modelo.loadComparendos(ruta);
							break;
			case 1:
				System.out.println("--------- \nDar numero de comparendos de mayor gravedad a buscar: ");
				s = lector.nextInt();
			
				break;
				
			case 2:
				System.out.println("--------- \nDar mes: ");
				s = lector.nextInt();
				System.out.println("--------- \nDar dia: ");
				m = lector.next();
				try {
				
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			
			case 3:
				System.out.println("--------- \nDar limite bajo: ");
				n = lector.next();
				System.out.println("--------- \nDar limite alto: ");
				m = lector.next();
				try {
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;

			case 4:
				System.out.println("--------- \nDar numero de comparendos mas cercanos a buscar: ");
				s = lector.nextInt();
		
				break;
				
			case 5:
				System.out.println("--------- \nDar medio de detección: ");
				n = lector.next();
				System.out.println("--------- \nDar clase vehiculo: ");
				m = lector.next();
				System.out.println("--------- \nDar tipo de servicio: ");
				o = lector.next();
				System.out.println("--------- \nDar localidad: ");
				p = lector.next();
				
				
				break;
				
			case 6:
				System.out.println("--------- \nDar Latitud menor: ");
				n = lector.next();
				System.out.println("--------- \nDar latitud mayor: ");
				m = lector.next();
				System.out.println("--------- \nDar clase vehiculo: ");
				o = lector.next();

			
				break;
			
			case 7:
				System.out.println("--------- \nDar d dias: ");
				s = lector.nextInt();
				
			
				break;
				
			case 8:
				System.out.println("--------- \nDar d dias: ");
				s = lector.nextInt();
				
				
				break;
				
			case 9:
				System.out.println("--------- \nTiempo actual policia: ");				
			
				System.out.println("--------- \nTiempo solucion: ");
			
				break;


			default: 
				System.out.println("--------- \n Opcion Invalida !! \n---------");
				break;

			}
		}
	}
}	