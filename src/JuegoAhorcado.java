import java.io.File;
import java.util.Scanner;

class Oportunidades{
	int oportunidades = 0;
	
	public void iniciarOportunidades() {
		this.oportunidades = 10;
	}
	public void reducriOp() {
		if(this.oportunidades > 0) {
			this.oportunidades--;
		}
	}
	public int obtenerOportunidades() {
		return this.oportunidades;
	}
}//class oportunidades

class PilaLetras{
	private char[] datos;
	private int tope;
	
	public PilaLetras(int capacidad) {
		datos = new char [capacidad];
		tope = -1;
	}
	public boolean vacio() {
		return tope == -1;
	}
	public boolean lleno() {
		return tope == datos.length-1;
	}
	public void push(char cima) {
		if(!lleno()) {
			datos[++tope] = cima;
		}
	}
	public char pop() {
		if(!vacio()) {
			return datos[tope--];
		}
		return '\0';
	}
	public void mostrarPila() {
		System.out.println("Letras ingresadas: ");
		for (int i = tope; i >= 0; i--) {
			System.out.println(datos[i] + "");
		}
		System.out.println("\n");
	}
}//class pila
class ManipulacionDeArchivos{
	public int verificarArchivo() {
		int contador = 0;
		
		try {
			File f = new File("Palabras.txt");
			if (!f.exists()) f.createNewFile();
			
			Scanner sc = new Scanner(f);
			while(sc.hasNext()) {
				sc.next();
				contador++;
			}//while
			sc.close();
		}catch(Exception e) {
			System.out.println("Error al leer el archivo.");
		}
		return contador;
		//final de la clase 
	}//class manipulacion del archivo
	
}//class manipulacion de archivos 
public class JuegoAhorcado {

	public static void main(String[] args) {
		

	}

}
