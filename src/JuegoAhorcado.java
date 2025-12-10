import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
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
	public int getOportunidades() {
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
	}
	//metodos manipulacion de archivos
	public void ingresarPalabras() {
		Scanner sc = new Scanner(System.in);
		boolean cont = false;
		String op = "";
		
		while(!cont) {
			
			try {
				FileWriter fw = new FileWriter("Palabras.txt", true);
				System.out.println("Ingresa una palabra: ");
				String nueva = sc.nextLine();
				
				fw.write("\n"+ nueva);
				fw.close();
				System.out.println("Se agrego de forma correcta la palabra: ");
				
				boolean salida = false;
				while(!salida) {
					System.out.println("Deseas ingresar otra palabra?");
					System.out.println("1.- Si");
					System.out.println("2.- No");
					
					op = sc.nextLine();
					
					if(op.equals("1")|| op.equals("2")) {
						salida = true;
					} else {
						System.out.println("Opcion invalida");
					}
				}
				
				cont = op.equals("2");
				
			}catch (Exception e) {
				System.out.println("Error al escribir en el archivo.");
			}
		}
	}//ingresar palabras
	
	public void borrarPalabras() {
		try {
			FileWriter fw = new FileWriter("Palabras.txt");
			fw.write("");
			fw.close();
			
		}catch(Exception e){
			System.out.println("Error al borrar el archivo.");
		}
	}//borrar palabras
	
	public boolean hangman() {
		return verificarArchivo()<0;
	}//hangman
	
	public void ordenShellSort(String[] palabras) {
		int n = palabras.length;
		int intervalo = n/2;
		
		while(intervalo>0) {
			for (int i = intervalo; i < n; i++) {
				String val = palabras[i];
				int j = i;
				
				while(j >= intervalo && palabras[j - intervalo].compareTo(val)>0 ) {
					palabras[j] = palabras[j-intervalo];
					j -= intervalo;
				}
				palabras[j] = val;	
			}
			intervalo /= 2;
		}
		
		borrarPalabras();
		
		try {
			FileWriter fw = new FileWriter("Palabras.txt", true);
			for(String p : palabras) {
				fw.write("\n" + p);
			}
			fw.close();
		}catch(Exception e) {
			
			System.out.println("Error ordenando el archivo.");
		}
		
	}//metodo shellsort
	
}//class de manipulacion de archivos 

class juegoAhorcado{
	
	Oportunidades op = new Oportunidades();
	ManipulacionDeArchivos ma = new ManipulacionDeArchivos();
	Scanner sc = new Scanner(System.in);
	
	PilaLetras pila = new PilaLetras(200);
	
	public ArrayList<String> cargarPalabras(){
		
		while(true) {
			ArrayList<String> lista = new ArrayList<>();
			
			try {
				File f = new File("Palabras.txt");
				if(!f.exists()) f.createNewFile();
				
				Scanner lector = new Scanner(f);
				
				while(lector.hasNext()) {
					lista.add(lector.next().toUpperCase());
				}
				
				lector.close();
				
			}catch(Exception e) {
				
				System.out.println("Error al cargar palabras");
			}
			
			if(lista.size() == 0) {
				System.out.println("El archivo esta vacio, ingresa palabras.");
				ma.ingresarPalabras();
			} else {
				return lista;
			}
		}
	}//cargar palabras array list
	
	public String elegirPalabra(ArrayList<String> lista) {
		Random r = new Random();
		return lista.get(r.nextInt(lista.size()));
	}//elegir palabra array list
	
	public void inicioAhorcado(String palabra) {
	
		System.out.println("=====Este es el juego del ahorcado=====");
		System.out.println("La palabra tiene " + palabra.length()+ "letras, podras adivinarla?");
		
		ArrayList<String> abecedario = new ArrayList<>(Arrays.asList("a", "b", "c","d","e","f","g","h","i","j","k","l","m","n","ñ",
                "o","p","q","r","s","t","u","v","w","x","y","z"));
		
		op.iniciarOportunidades();
		pila = new PilaLetras(200);
		ArrayList<String> letrasIng = new ArrayList<>();
		
		while(!seAdivinoLaPalabra(palabra, letrasIng)) {
			
			System.out.println("==========");
			System.out.println("Solo tienes "+ op.getOportunidades()+ "oportunidades mas");
			System.out.println("Letras disponibles: ");
			System.out.println(abecedario);
			
			String letra = "";
			boolean valido = false;
			
			while(!valido) {
				System.out.println("Ingresa una letra: ");
				letra = sc.nextLine().toLowerCase();
				
				if(letrasIng.contains(letra)) {
					System.out.println("Ya ingresaste esa letra.");
				} else if(letra.length() != 1){
					System.out.println("Ingresa solo una letra. ");
					} else if(!letra.matches("[a-zñ]")) {
						System.out.println("Asegurate de solo ingresar letras");
						}else {
							valido = true;
						}
						}
			letrasIng.add(letra);
			
			//guardar en pila
			pila.push(letra.toUpperCase().charAt(0));
			
			if(!busquedaLineal(palabra, letra.toUpperCase())) {
				System.out.println("La letra no se encuentra en la palabra.");
				op.reducriOp();
			}
			obtenerPalabraAdivinada(palabra, letrasIng);
			obtenerLetrasDisponibles(letrasIng, abecedario);
			
			if(op.getOportunidades()== 0)
				break;
		}
		
	}//metodo inicio ahorcado
	
	public boolean seAdivinoLaPalabra(String palabra, ArrayList<String> letrasIng) {
		for(char c : palabra.toLowerCase().toCharArray()) {
			if(!letrasIng.contains(String.valueOf(c))) {
				if(op.getOportunidades() > 0)
					return false;
			}
		}
		
		if(op.getOportunidades() == 0) {
			System.out.println("\nNo adivinaste la palabra, perdiste.");
			System.out.println("La palabra correcta era: "+palabra);
		} else {
			System.out.println("\nFelicidades, ganaste!!");
		}
		System.out.println("\nHistorial de letras ingresadas: ");
		pila.mostrarPila();
		
		boolean salida = false;
		while(!salida) {
			System.out.println("Deseas jugar otra vez?");
			System.out.println("1.- Si");
			System.out.println("2.- No");
			
			String op2 = sc.nextLine();
			
			if(op2.equals("1") || op2.equals("2")) {
				salida = true;
				if(op2.equals("2")) {
					System.out.println("Gracias por jugar!!");
					System.exit(0);
				}
				
			}else {
				System.out.println("Opcion invalida.");
			}
		}
		return true;
	}//metodo se adivino la palabra
	
	public void obtenerPalabraAdivinada(String palabra, ArrayList<String> letrasIng) {
		
		StringBuilder secreta = new StringBuilder();
		
		for(char c : palabra.toLowerCase().toCharArray()) {
			if(letrasIng.contains(String.valueOf(c))) {
				secreta.append(" ").append(c);
			} else {
				secreta.append(" _");
			}
		}
		
		System.out.println(secreta.toString());
	}//obtener palabra adivinada
	
	public ArrayList<String> obtenerLetrasDisponibles(ArrayList<String> letrasIng, ArrayList<String> abecedario){
		
		for (int i = 0; i < abecedario.size(); i++) {
			
			if(letrasIng.contains(abecedario.get(i))) {
				abecedario.set(i, "");
			}
		}
		return abecedario;
	}//metodo obtener letras disponibles
	
	
}//class ahorcado






public class JuegoAhorcado {

	public static void main(String[] args) {
		

	}

}
