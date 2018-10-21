package reverse_server_multi_threaded;

import java.io.*;
import java.net.*;
import java.util.Timer;
import java.util.TimerTask;
 
/**
 * This program demonstrates a simple TCP/IP socket server that echoes every
 * message from the client in reversed form.
 * This server is multi-threaded.
 */
public class ReverseServer {
	
	private static final String THROUGHPUT_FILE = "throughtputs.txt";	
	
    public static void main(String[] args) { 
        int port = Integer.parseInt(args[0]);
 
        try (ServerSocket serverSocket = new ServerSocket(port)) {
 
        	initializeFiles();
            System.out.println("Server is listening on port " + port);
 
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");
                 
                new ServerThread(socket).start();
            }
 
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    public static void initializeFiles() throws IOException {
    	File file = new File(THROUGHPUT_FILE);
    	DataOutputStream stream = new DataOutputStream(new FileOutputStream(file));
		stream.writeBytes("");
		stream.close();
    }
    
    synchronized static void writeToFile(int requests) throws IOException {			
		File file = new File(THROUGHPUT_FILE);
		file.createNewFile();		
		DataOutputStream stream = new DataOutputStream(new FileOutputStream(file, true));
		stream.writeBytes(requests+"\n");
		stream.close();
	}
    
	 
}