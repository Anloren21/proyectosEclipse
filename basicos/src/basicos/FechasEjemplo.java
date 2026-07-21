package basicos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class FechasEjemplo {
	public static void main(String[] args) {
		LocalDateTime ahora = LocalDateTime.now();
		
		System.out.println(ahora);
		
		LocalDate fechaNacimiento = LocalDate.of(1996, 04, 13);
		
		System.out.println(fechaNacimiento);
		
		LocalDate hoy = LocalDate.now();
		
		int anyos = Period.between(fechaNacimiento, hoy).getYears();
		
		System.out.println(anyos);
		
		int dias = 5;
		
		System.out.println(dias);
		
		//Programa de suscripción
		LocalDate diaSuscripcion = LocalDate.of(2026, 01, 31);
		
		//LocalDate diaSiguienteCuota = diaSuscripcion.plusMonths(1);
		
		LocalDate diaSiguienteCuota;
		//System.out.println(diaSiguienteCuota);
		
		//Aquñi tendríamos las fechas en que se pasaría la suscripción
		for(int mes = 1; mes <= 12; mes++) {
			diaSiguienteCuota = diaSuscripcion.plusMonths(mes-1);
			System.out.println(diaSiguienteCuota);			
		}
		
		//Conversiones
		DateTimeFormatter fechaEspanyola = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		String fechaTexto = "21/07/2026";
		
		LocalDate localDate = LocalDate.parse(fechaTexto, fechaEspanyola);
	
		System.out.println(localDate.getDayOfWeek());
		System.out.println(localDate.getDayOfMonth());
		System.out.println(localDate.getMonth());
		System.out.println(localDate.getYear());
		
		System.out.println(localDate.format(fechaEspanyola));
	}
}
