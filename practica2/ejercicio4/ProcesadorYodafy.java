//
// YodafyServidorIterativo
// (CC) jjramos, 2012
//
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.util.Random;


//
// Nota: si esta clase extendiera la clase Thread, y el procesamiento lo hiciera el método "run()",
// ¡Podríamos realizar un procesado concurrente! 
//
public class ProcesadorYodafy {
	//socket y paquete para el envio UDP
	private DatagramPacket paquete;
	private DatagramSocket socketServicio;
	// Para que la respuesta sea siempre diferente, usamos un generador de números aleatorios.
	private Random random;
	
	// Constructor que tiene como parámetro una referencia al socket abierto en por otra clase
	public ProcesadorYodafy(DatagramPacket paque) {
		random=new Random();
		this.paquete=paque;
	}
	
	
	// Aquí es donde se realiza el procesamiento realmente:
	void procesa(){
		
		// Como máximo leeremos un bloque de 1024 bytes. Esto se puede modificar.
		byte [] datosRecibidos=new byte[1024];
		
		// Array de bytes para enviar la respuesta. Podemos reservar memoria cuando vayamos a enviarka:
		byte [] datosEnviar;
		
		
		try {
			//obtenemos los datos del paquete que nos pasa el servidor al creer un objeto de 
			//este tipo
			datosRecibidos=paquete.getData();
			
			// Yoda hace su magia:
			// Creamos un String a partir de un array de bytes de tamaño "bytesRecibidos":
			String peticion= new String(datosRecibidos,0,datosRecibidos.length);
			// Yoda reinterpreta el mensaje:
			String respuesta=yodaDo(peticion);
			// Convertimos el String de respuesta en una array de bytes:
			datosEnviar=respuesta.getBytes();
			
			//creamos un socket UDP y el paquete para mandarlo al mismo origen del paquete inicial
			socketServicio = new DatagramSocket();
			paquete = new DatagramPacket(datosEnviar,datosEnviar.length, paquete.getAddress(), paquete.getPort());
			socketServicio.send(paquete);
			// Enviamos la traducción de Yoda:
			////////////////////////////////////////////////////////
			// ... write ... datosEnviar... datosEnviar.length ... (Completar)
			//outputStream.write(datosEnviar,0,datosEnviar.length);
			////////////////////////////////////////////////////////
			socketServicio.close();
			
			
		} catch (IOException e) {
			System.err.println("Error al obtener los flujso de entrada/salida.");
		}

	}

	// Yoda interpreta una frase y la devuelve en su "dialecto":
	private String yodaDo(String peticion) {
		// Desordenamos las palabras:
		String[] s = peticion.split(" ");
		String resultado="";
		
		for(int i=0;i<s.length;i++){
			int j=random.nextInt(s.length);
			int k=random.nextInt(s.length);
			String tmp=s[j];
			
			s[j]=s[k];
			s[k]=tmp;
		}
		
		resultado=s[0];
		for(int i=1;i<s.length;i++){
		  resultado+=" "+s[i];
		}
		
		return resultado;
	}
}
