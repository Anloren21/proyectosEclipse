package basicos;

import java.util.Scanner;

public class ConsolaEjemplo {
	public static void main(String[] args) {
		System.out.println("Ejemplo de consola");
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Dime tu nombre: ");
		
		String nombre = sc.nextLine();
		
		System.out.println("Hola " + nombre);
		
		//Calculadora por consola
		System.out.println("Dime op1: ");
		double op1 = Double.parseDouble(sc.nextLine());
		
		System.out.println("Dime op2: ");
		double op2 = Double.parseDouble(sc.nextLine());
		
		double suma = op1 + op2;
		
		System.out.println(suma);
		
		sc.close();
		
	}
}