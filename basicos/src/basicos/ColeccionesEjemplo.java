package basicos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

public class ColeccionesEjemplo {
	public static void main(String[] args) {
		listas();
		conjuntos();
		mapas();
	}
	
	private static void mapas() {
		HashMap<String, String> diccionario = new HashMap<String, String>();
		
		diccionario.put("casa", "house");
		diccionario.put("perro", "dog");
		
		System.out.println(diccionario.get("perro"));
		
		for(String clave: diccionario.keySet()) {
			System.out.printf("%s: %s\n", clave, diccionario.get(clave));
		}
		
		for(String valor: diccionario.values()) {
			System.out.println(valor);
		}
		
		for(Entry<String, String> par: diccionario.entrySet()) {
			System.out.printf("%s: %s\n", par.getKey(), par.getValue());
		}
	}

	//	conjunto solo guarda valores únicos
	@SuppressWarnings("unlikely-arg-type")
	private static void conjuntos() {
		HashSet<String> coleccion = new HashSet<String>();
		
		coleccion.add("Uno");
		coleccion.add("Dos");
		coleccion.add("Dos");
		coleccion.add("Cuatro");
		coleccion.add("Cinco");
		
//		System.out.println(coleccion.get(2));
		
		System.out.println(coleccion);
		
		coleccion.remove(3);
//		coleccion.add(2,"Cuatro");
		
		System.out.println(coleccion);
		
		for(String dato:coleccion) {
			System.out.println(dato);
		}
	}
	
	private static void listas() {
		ArrayList<String> coleccion = new ArrayList<String>();
		
		coleccion.add("Uno");
		coleccion.add("Dos ");
		coleccion.add("Tres");
		coleccion.add("Cuatro");
		coleccion.add("Cinco");
		
		System.out.println(coleccion.get(2));
		
		coleccion.remove(3);
		coleccion.add(2,"Cuatro");
		
		System.out.println(coleccion);
		
		for(String dato:coleccion) {
			System.out.println(dato);
		}
	}
	
}
