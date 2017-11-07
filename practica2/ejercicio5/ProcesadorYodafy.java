
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
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
//
// Nota: si esta clase extendiera la clase Thread, y el procesamiento lo hiciera el método "run()",
// ¡Podríamos realizar un procesado concurrente!
//
public class ProcesadorYodafy {
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
	private ArraList<String> respuestas;

	// Constructor que tiene como parámetro una referencia al socket abierto en por otra clase
	public ProcesadorYodafy(Socket socketServicio) {
		this.socketServicio=socketServicio;
	}


	// Aquí es donde se realiza el procesamiento realmente:
	void procesa(){

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
			// Lee la frase a Yodaficar:
			////////////////////////////////////////////////////////
			// read ... datosRecibidos.. (Completar)
			//bytesRecibidos = inputStream.read(datosRecibidos);
			String peticion =inReader.readLine();
			////////////////////////////////////////////////////////

			if(peticion == "play"){
				inicializar();
				while(contar_completa <25){
					outPrinter.println(imprimirRosco());
					char letra = (char) (puntero +65);
					outPrinter.println(letra+ ": " + definiciones[puntero]);
					
					peticion=inReader.readLine();
					
					if(peticion == respuestas[puntero]){
						contar_completas++;
						contestadas[puntero] = 1;
						outPrinter.println("CORRECTO!!!!!!!!!!");
					}
					else if( peticion != "pasapalabra"){
						contestadas[puntero]=2;
						contar_completas++;
						outPrinter.println("OHHHHH ERRORR!! La respuesta correcta era " + respuestas[puntero]);
					}	
					siguientePuntero();

			  }

			}

			outPrinter.println("fin del juegoo");
			////////////////////////////////////////////////////////



		} catch (IOException e) {
			System.err.println("Error al obtener los flujso de entrada/salida.");
		}

	}

	
	private siguientePuntero(){
		boolean encontrado = false;
		while(!encontrado){
			puntero= (puntero+1)%25;
			if(contestada[puntero] == 0){
				encontrado=true;
			}
		}

	}

	private String imprimirRosco(){
		/////esto imprime un triangulo
		//// creo que el rosco lo mas facil es hacerlo como un rombo
		int x = 4;
        int y = x*2;
        for(int contador= 0; contador<=x; contador++)
        {
            for(int espacios = x - 1; espacios >=contador; espacios-- )
            {
                System.out.print(" ");
            }
            for(int asteriscos= 0; asteriscos<= (1*contador + contador); asteriscos++)
            {
                System.out.print("*");
            }
            System.out.println();
 
        }
	}
	private inicializar(){
		definiciones = new ArrayList();
		respuestas = new ArraList();
		contestadas = new ArraList(25,0);
		definiciones.push_back("No murado, no cercado o no cerrado");
		respuestas.push_back("abierto");

		definiciones.push_back("Herramienta formada por una barra metálica con la punta en espiral, para hacer agujeros en material duro");
		respuestas.push_back("broca");

		definiciones.push_back("Cosa apreciable que se adquiere a poca costa");
		respuestas.push_back("chollo");

		definiciones.push_back("Conjunto de dientes, muelas y colmillos que tiene en la boca una persona o un animal");
		respuestas.push_back("dentadura");

		definiciones.push_back("Hacer uso por primera vez de algo");
		respuestas.push_back("estrenar");

		definiciones.push_back("Tratamiento de las enfermedades o lesiones por medio de elementos mecánicos, como el masaje o la gimnasia");
		respuestas.push_back("fisioterapia");

		definiciones.push_back("Gran porción de mar que se interna en la tierra entre dos cabos");
		respuestas.push_back("golfo");

		definiciones.push_back("Restablecer el grado de humedad normal de la piel u otros tejidos");
		respuestas.push_back("hidratar");

		definiciones.push_back("País europeo con capital en Dublín");
		respuestas.push_back("irlanda");

		definiciones.push_back("Grupo musical de funk que en 1996 publicó el álbum Travelling without Moving:");
		respuestas.push_back("jamiroquai");

		definiciones.push_back("Viento procedente del este");
		respuestas.push_back("levante");

		definiciones.push_back("Persona que, por su humor tétrico, manifiesta aversión al trato humano");
		respuestas.push_back("misantropo");

		definiciones.push_back("Natural del país europeo con capital en Oslo");
		respuestas.push_back("oslo");

		definiciones.push_back("Contiene la ñ, parte de los árboles y matas que, cortada y hecha trozos, se emplea como combustible");
		respuestas.push_back("leña");

		definiciones.push_back("Anticuado, inadecuado a las circunstancias actuales");
		respuestas.push_back("obsoleto");

		definiciones.push_back("Se dice de un niño de muy corta edad:");
		respuestas.push_back("pequeño");

		definiciones.push_back("Contiene la Q, sitio poblado de árboles y matas");
		respuestas.push_back("bosque");

		definiciones.push_back("Espacio cercado en que combaten los boxeadores");
		respuestas.push_back("ring");

		definiciones.push_back("Falta de ruido");
		respuestas.push_back("silencio");

		definiciones.push_back("Silicato de magnesia que, en forma de polvo, se utiliza para la higiene y en la industria cosmética");
		respuestas.push_back("talco");

		definiciones.push_back("Apoderarse de una propiedad o de un derecho que legítimamente pertenece a otro, por lo general con violencia");
		respuestas.push_back("usurpar");

		definiciones.push_back(" Día que antecede inmediatamente a otro determinado, especialmente si es fiesta");
		respuestas.push_back("vispera");

		definiciones.push_back("Contiene la X, hacer flexible algo, darle flexibilidad");
		respuestas.push_back("flexibilizar");

		definiciones.push_back("Contiene la Y.  Nombre propio del realizador que dirigió la película Revolver en 2005");
		respuestas.push_back("ritchie");

		definiciones.push_back("Segunda persona de singular del pretérito perfecto simple de indicativo del verbo zanjar");
		respuestas.push_back("zanjaste");

	}
}
