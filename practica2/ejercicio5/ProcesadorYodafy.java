
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

	// Constructor que tiene como parámetro una referencia al socket abierto en por otra clase
	public ProcesadorYodafy(Socket socketServicio) {
		this.socketServicio=socketServicio;
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

			if(peticion == "play"){
				inicializar();
				while(contar_completas <25){
					outPrinter.println(imprimirRosco());
					char letra = (char) (puntero +65);
					outPrinter.println(letra+ ": " + definiciones.get(puntero));
					
					peticion=inReader.readLine();
					
					if(peticion == respuestas.get(puntero)){
						contar_completas++;
						contestadas.set(puntero,1);
						outPrinter.println("CORRECTO!!!!!!!!!!");
					}
					else if( peticion != "pasapalabra"){
						contestadas.set(puntero,2);
						contar_completas++;
						outPrinter.println("OHHHHH ERRORR!! La respuesta correcta era " + respuestas.get(puntero));
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

	
	private void siguientePuntero(){
		boolean encontrado = false;
		while(!encontrado){
			puntero= (puntero+1)%25;
			if(contestadas.get(puntero) == 0){
				encontrado=true;
			}
		}

	}

	private String imprimirRosco(){
		/////esto imprime un triangulo
		//// creo que el rosco lo mas facil es hacerlo como un rombo
		int x = 4;
		int y = x*2;
		String rosco = new String();

        for(int contador= 0; contador<=x; contador++)
        {
            for(int espacios = x - 1; espacios >=contador; espacios-- )
            {
                rosco += " ";
            }
            for(int asteriscos= 0; asteriscos<= (1*contador + contador); asteriscos++)
            {
                rosco = rosco + "*";
            }
            //System.out.println();
 
		}
		return rosco;
	}
	private void inicializar(){

		definiciones = new ArrayList();
		respuestas = new ArrayList();
		contestadas = new ArrayList(25);
		String cadena;
		
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
		respuestas.add("oslo");

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
		respuestas.add("ritchie");

		definiciones.add("Segunda persona de singular del pretérito perfecto simple de indicativo del verbo zanjar");
		respuestas.add("zanjaste");

	}
}
