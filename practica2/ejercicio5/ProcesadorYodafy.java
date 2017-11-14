
//
// YodafyServidorIterativo
// (CC) jjramos, 2012
//
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;
import java.util.ArrayList;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
//
// Nota: si esta clase extendiera la clase Thread, y el procesamiento lo hiciera el método "run()",
// ¡Podríamos realizar un procesado concurrente!
//
public class ProcesadorYodafy extends Thread {
	// Referencia a un socket para enviar/recibir las peticiones/respuestas
	private Socket socketServicio;
	// stream de lectura (por aquí se recibe lo que envía el cliente)
	//private InputStream inputStream;
	// stream de escritura (por aquí se envía los datos al cliente)
	//private OutputStream outputStream;


	private boolean completo;
	private int puntero =0;
	private ArrayList<String> definiciones;
	private int contar_completas=0;
	private ArrayList<Integer> contestadas;
	private ArrayList<String> respuestas;
	private int acertadas=0;

	// Constructor que tiene como parámetro una referencia al socket abierto en por otra clase
	public ProcesadorYodafy(Socket socketServicio) {
		this.socketServicio=socketServicio;
		definiciones = new ArrayList<String>();
		respuestas = new ArrayList<String>();
		contestadas = new ArrayList<Integer>();
		for(int i=0 ; i< 25 ;i = i+1){
			contestadas.add(0);
		}

		definiciones.add("No murado, no cercado o no cerrado");
		respuestas.add("abierto");

		definiciones.add("Herramienta formada por una barra metálica con la punta en espiral, para hacer agujeros en material duro");
		respuestas.add("broca");

		definiciones.add("Cosa apreciable que se adquiere a poca costa");
		respuestas.add("chollo");

		definiciones.add("Conjunto de dientes, muelas y colmillos que tiene en la boca una persona o un animal");
		respuestas.add("dentadura");

		definiciones.add("Hacer uso por primera vez de algo");
		respuestas.add("estrenar");

		definiciones.add("Tratamiento de las enfermedades o lesiones por medio de elementos mecánicos, como el masaje o la gimnasia");
		respuestas.add("fisioterapia");

		definiciones.add("Gran porción de mar que se interna en la tierra entre dos cabos");
		respuestas.add("golfo");

		definiciones.add("Restablecer el grado de humedad normal de la piel u otros tejidos");
		respuestas.add("hidratar");

		definiciones.add("País europeo con capital en Dublín");
		respuestas.add("irlanda");

		definiciones.add("Grupo musical de funk que en 1996 publicó el álbum Travelling without Moving:");
		respuestas.add("jamiroquai");

		definiciones.add("Viento procedente del este");
		respuestas.add("levante");

		definiciones.add("Persona que, por su humor tétrico, manifiesta aversión al trato humano");
		respuestas.add("misantropo");

		definiciones.add("Natural del país europeo con capital en Oslo");
		respuestas.add("noruego");

		definiciones.add("Contiene la ñ, parte de los árboles y matas que, cortada y hecha trozos, se emplea como combustible");
		respuestas.add("leña");

		definiciones.add("Anticuado, inadecuado a las circunstancias actuales");
		respuestas.add("obsoleto");

		definiciones.add("Se dice de un niño de muy corta edad:");
		respuestas.add("pequeño");

		definiciones.add("Contiene la Q, sitio poblado de árboles y matas");
		respuestas.add("bosque");

		definiciones.add("Espacio cercado en que combaten los boxeadores");
		respuestas.add("ring");

		definiciones.add("Falta de ruido");
		respuestas.add("silencio");

		definiciones.add("Silicato de magnesia que, en forma de polvo, se utiliza para la higiene y en la industria cosmética");
		respuestas.add("talco");

		definiciones.add("Apoderarse de una propiedad o de un derecho que legítimamente pertenece a otro, por lo general con violencia");
		respuestas.add("usurpar");

		definiciones.add(" Día que antecede inmediatamente a otro determinado, especialmente si es fiesta");
		respuestas.add("vispera");

		definiciones.add("Contiene la X, hacer flexible algo, darle flexibilidad");
		respuestas.add("flexibilizar");

		definiciones.add("Contiene la Y.  Nombre propio del realizador que dirigió la película Revolver en 2005");
		respuestas.add("guy");

		definiciones.add("Segunda persona de singular del pretérito perfecto simple de indicativo del verbo zanjar");
		respuestas.add("zanjaste");
	}


	// Aquí es donde se realiza el procesamiento realmente:
	public void run(){

		// Como máximo leeremos un bloque de 1024 bytes. Esto se puede modificar.
		//byte [] datosRecibidos=new byte[1024];
		//int bytesRecibidos=0;


		// Array de bytes para enviar la respuesta. Podemos reservar memoria cuando vayamos a enviarka:
		//byte [] datosEnviar;


		try {
			// Obtiene los flujos de escritura/lectura
			//inputStream=socketServicio.getInputStream();
			//outputStream=socketServicio.getOutputStream();

			BufferedReader inReader = new BufferedReader
			(new InputStreamReader(socketServicio.getInputStream()));
			PrintWriter outPrinter = new PrintWriter(socketServicio.getOutputStream(),true);

			// read ... datosRecibidos.. (Completar)
			//bytesRecibidos = inputStream.read(datosRecibidos);
			String peticion =inReader.readLine();
			////////////////////////////////////////////////////////
			//System.out.println(peticion);

			if(peticion.equals("play")){
				outPrinter.println("comenzemos");

				while(contar_completas < 25){
					outPrinter.println(imprimirRosco());
					//para saltarnos la K
					char letra = convierteLetra(puntero);
					
					outPrinter.println(letra +": " + definiciones.get(puntero));
					
					peticion=inReader.readLine();
					
					if(peticion.equals(respuestas.get(puntero))){
						contar_completas++;
						acertadas++;
						contestadas.set(puntero,1);
						if(contar_completas == 25){
							outPrinter.println("FIN DEL JUEGO");
						}
						else{
							outPrinter.println("CORRECTO!!!!!!!!!!");
						}
					}
					else if( !peticion.equals("pasapalabra")){
						contestadas.set(puntero,2);
						contar_completas++;
						if(contar_completas == 25){
							outPrinter.println("FIN DEL JUEGO");
						}
						else{
							outPrinter.println("OHHHHH ERRORR!! La respuesta correcta era " + respuestas.get(puntero));
						}
						
					}
					else{
						outPrinter.println("CONTINUAMOS CON LA SIGUIENTE");
					}
					//System.out.println(contar_completas);
					siguientePuntero();

			  }

			}
			outPrinter.println(imprimirRosco());
			if(acertadas == 25){
				outPrinter.println("Enhorabuena has ganado y te llevas el bote");
			}
			else{
				outPrinter.println("Lo siento has perdido pero has acertado " + Integer.toString(acertadas));
			}
			
			////////////////////////////////////////////////////////



		} catch (IOException e) {
			System.err.println("Error al obtener los flujso de entrada/salida.");
		}

	}

	
	private void siguientePuntero(){
		boolean encontrado = false;
		while(contar_completas != 25 && !encontrado){
			puntero= (puntero+1)%25;
			if(contestadas.get(puntero) == 0){
				encontrado=true;
			}
		}

	}
	
	private char convierteLetra(int puntero){
		char letra;
		if(puntero < 10 || puntero > 13){
			letra = (char)(puntero +65);
		}
		else if(puntero == 13){
			letra = 'N';
		}
		else{
			letra = (char)(puntero +66);
		}
		return letra;
	}
	

	private String imprimirRosco(){
		String rosco ="";
		int n = 7;
		//Se hace con un preincremento ++j.
		for (int i = 0; i < n; ++i) {
			//Cada primer for, se hace un salto de línea.
			rosco += "\n";
			//Utilizamos dos "for" para lograr la forma.
			for (int j = 0; j < n-i-1; ++j) {                      
				rosco+= " ";           
			}
			for (int j = 0; j < 2*i+1; ++j){
				//Condición para imprimir solo los bordes.
				if (i==0){
					rosco += 'A';
				}
				else if(j==0) {
					rosco += convierteLetra(25-(i+1));	
				} else if(j==2*i){
					rosco += convierteLetra(i);
				}	
				else {
					rosco+= " ";
				}                
			}            
		}
		//Se utiliza otro grupo de for para lograr la forma de "rombo"
		int cont=n;
		for (int j = n-2; j >= 0; --j) {
            rosco += "\n";
            //Utilizamos dos "for" para lograr la forma.
            for (int i = 0; i < n-j-1; ++i) {                
				rosco+= " ";
            }
            for (int i = 0; i < 2*j+1; ++i) {
                //Condición para imprimir solo los bordes.
				if (j==0){
					rosco += 'N';
				}
				else if(i==0){
					rosco += convierteLetra(25-(cont+1));
				}
				else if(i==2*j) {
                    rosco += convierteLetra(cont);
                } else {
                    rosco+= " ";
                }                
			}
			cont++;
        }    
		rosco+= "\n";
		return rosco;
	}
}



