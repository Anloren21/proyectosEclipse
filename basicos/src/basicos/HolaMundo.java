package basicos;

public class HolaMundo {
	/**
	 * Método de entrada de la aplicación
	 * @param args argumentos recibidos por consola
	 */
	
	public static void main(String[] args) {
		/**
		 * Vamos a demostrar aquí las funcionalidades
		 * básicas del lenguaje
		 */
		
		System.out.println("Hola a todos"); //Muestra un mensaje en pantalla
		
		double d1 = 0.1;
		double d2 = 0.2;
		
		double suma = d1 + d2;
		
		System.out.println(suma);
		
		@SuppressWarnings("unused")
		 long l = 1234123456L;
		 
		 char c = 'A';
		 
		 int i = c;
		 
		 //int i = null; no se puede 
		 
		 System.out.println(i);
		 
		 
		 //Programa que muestra la tabla de carácteres desde el 32 al 127
		 for (int codigo = 32; codigo<=127; codigo++) {
			 char letra = (char) codigo;
			 System.out.println(letra + "=" + codigo);
		 }
		 
		 String nombre = "Angie"; //Tipo de referencia
		 
		 System.out.println("El nombre es " + nombre.toUpperCase());
	}
}
