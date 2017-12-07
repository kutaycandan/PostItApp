package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This class just listens and when client send request to join creates new thread for communication.
 * @author kutay
 *
 */
public class TCPServer extends Thread {
	
	ServerSocket welcomeSocket;
	Socket connectionSocket;
	BinarySemaphore mutex;
	public TCPServer() {
		mutex = new BinarySemaphore(true);
	}
	
	public void run() {
		try {
			//InetAddress addr = InetAddress.getByName("192.168.4.86");
			welcomeSocket = new ServerSocket(6789);
			
			//System.out.println(welcomeSocket.getLocalSocketAddress());
			while(true) {
				
				connectionSocket = welcomeSocket.accept();
				System.out.println("Servera yeni biri girdi.");
				
				Thread t =new ServerThread(connectionSocket,mutex);
				t.start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
}
