package bnn.io;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

public class SocketIO implements Serializable{

	private static final long serialVersionUID = 1L;

	static Logger logger = (Logger) LogManager.getLogger(SocketIO.class);
	
	ObjectOutputStream os;
	static ServerSocket server;
	static Socket s;
	ObjectInputStream is;
	
	public SocketIO(){
		
	}
	
	public static ServerSocket initSocket() throws IOException {
		logger.debug("Starting socket...");
		try {
			// Server will be started on 1700 port number
			s.close();
		    server = new ServerSocket(9999);
		    logger.debug("Socket ready on port:9898");
		    
		}catch(Exception e) {
			logger.error(e);
			s.close();	
			e.printStackTrace();
		}
		logger.debug("...socket start on localhost at port:9898");
		return server;
	}
	
	public void sendOut(Object objToSend) throws IOException {
		logger.debug("Sending object "+objToSend.toString()+"...");
		try {
			initSocket();
		    // Stream object for sending object 
		    os=new ObjectOutputStream(s.getOutputStream());
		    logger.debug("Socket outputStream");
			os.writeObject(objToSend);
			s.close();
			
		}catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
			s.close();
		}
		logger.debug("Onject sent.");
	}
	
	public Object readIn() throws IOException {
		Object readedObject = null;
		logger.debug("Reading object...");
		try {
			InetAddress host = InetAddress.getLocalHost();
			initSocket();
		    // Server listening for connection
		    s = server.accept();
		    logger.debug("Socket accept");
			Socket s=new Socket(host.getHostName(), 9898);
			is = new ObjectInputStream(s.getInputStream());
			    
			readedObject = is.readObject();
						    
			s.close();

			
		}catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
			s.close();
		}
		logger.debug("object readed "+readedObject.toString());
		return readedObject;
	}

	public void close() throws IOException {
		try {
			s.close();
		}catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
			s.close();
		}
		
	}
}
