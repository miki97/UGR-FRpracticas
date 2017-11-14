/////
// Aplicacion para el usuario del juego pasapalabra basado en TCP
// Desarrollado por:
//		Miguel Ángel López Robles
// 		Jaime Frías Funes
////////////
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientePasapalabra {

	public static void main(String[] args) {
		
		// Nombre del host donde se ejecuta el servidor:
		String host="localhost";
		// Puerto en el que espera el servidor:
		int port=8989;
		
		// Socket para la conexión TCP
		Socket socketServicio=null;
		//scanner para tomar las respuestas de la terminal
		Scanner capt = new Scanner(System.in);

		try {
			// Creamos un socket que se conecte a "host" y "port":
			//////////////////////////////////////////////////////
			socketServicio= new Socket(host, port);
			//////////////////////////////////////////////////////			
			
			//obtener los flujos en modo texto
			BufferedReader inReader = new BufferedReader
			(new InputStreamReader(socketServicio.getInputStream()));
			PrintWriter outPrinter = new PrintWriter(socketServicio.getOutputStream(),true);

			// enviamos el codigo play para indicar que deseamos jugar y que se inicie el servicio
			outPrinter.println("play");
			//////////////////////////////////////////////////////
			outPrinter.flush();
			//////////////////////////////////////////////////////
			// recibimos el mensaje de respuesta del servidor indicando que atiende nuestra peticion
			String recibido = inReader.readLine();
			//System.out.println(recibido);
			//el mensaje recibido debe ser comenzemos
			if(recibido.equals("comenzemos")){
				System.out.println("COMIENZA EL JUEGO");
				while(!recibido.equals("FIN DEL JUEGO")){
					//imprimir rosco que lo formaremos leyendo lineas del flujo de datos
					String rosco="";
					for(int i = 0 ; i <=14 ; i++){
						rosco += inReader.readLine();
						rosco+= "\n";
					}
					System.out.println(rosco);

					//imprimir pregunta
					System.out.println(inReader.readLine());

					//captar y enviar respuesta
					String respuesta = capt.nextLine();
					System.out.println(respuesta);
					outPrinter.println(respuesta);

					//imprimir resultado
					recibido = inReader.readLine();
					System.out.println(recibido);

				}



			}
		
			// Por ultimo el recibimos el rosco por ultima vez y el resultado final de este:
			System.out.println("El resultado del rosco es: ");
			String rosco="";
			for(int i = 0 ; i <=14 ; i++){
				rosco += inReader.readLine();
				rosco+= "\n";
			}
			System.out.println(rosco);
			//for(int i=0;i<bytesLeidos;i++){
			System.out.println(inReader.readLine());
			//}
			
			// Una vez terminado el servicio, cerramos el socket
			socketServicio.close();
			//////////////////////////////////////////////////////
			
			// Excepciones:
		} catch (UnknownHostException e) {
			System.err.println("Error: Nombre de host no encontrado.");
		} catch (IOException e) {
			System.err.println("Error de entrada/salida al abrir el socket.");
		}
	}
}
