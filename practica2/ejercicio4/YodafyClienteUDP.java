//
// YodafyServidorIterativo
// (CC) jjramos, 2012
//
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class YodafyClienteUDP {

	public static void main(String[] args) {
		
		byte []buferEnvio;
		InetAddress direccion;
		// Nombre del host donde se ejecuta el servidor:
		String host="localhost";
		// Puerto en el que espera el servidor:
		int port=8989;
		
		// Socket para la conexión UDP
		DatagramSocket socket=null;
		DatagramPacket paquete;
		try {		
			
			// Si queremos enviar una cadena de caracteres por un paquete UDP, hay que pasarla primero
			// a un array de bytes:
			buferEnvio="Al monte del volcán debes ir sin demora".getBytes();
			
			//creamos el paquete con la direccion y el puerto al que queremos mandar y lo mandamos con
			//send()
			socket= new DatagramSocket();
			direccion = InetAddress.getByName(host);
			paquete = new DatagramPacket(buferEnvio, buferEnvio.length,direccion,port);
			socket.send(paquete);
			


			//Esperamos con receive la recepcion del paquete con la respuesta
			socket.receive(paquete);
			
			String contesta= new String(paquete.getData(),0,paquete.getData().length);
			// MOstremos la cadena de caracteres recibidos:
			System.out.println("Recibido: ");
			System.out.print(contesta);
			
			
			// Una vez terminado el servicio, cerramos el socket 
			//////////////////////////////////////////////////////
			socket.close();
			//////////////////////////////////////////////////////
			
			// Excepciones:
		} catch (UnknownHostException e) {
			System.err.println("Error: Nombre de host no encontrado.");
		} catch (IOException e) {
			System.err.println("Error de entrada/salida al abrir el socket.");
		}
	}
}
