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
	
	private static final String THROUGHPUT_FILE = "throughtputs";	
	
    public static void main(String[] args) { 
        int port = Integer.parseInt(args[0]);
 
        
        try (ServerSocket serverSocket = new ServerSocket(port)) {
 
        	initializeFile();
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
    
    public static void initializeFile() throws IOException {
    	
    	//Get a list of Files in my current Dir
    	File[] dirFiles = new File(".").listFiles();
    	//Search Through the list
    	for (int i=0; i<dirFiles.length; i++)
    	             //If the Files starts with the word "phase"
    	    if (dirFiles[i].getName().startsWith(THROUGHPUT_FILE, 0))
    	                          //Delete This file
    	        new File(dirFiles[i].getName()).delete();
    }
    
    synchronized static void writeToFile(int requests, int fileID) throws IOException {			
		File file = new File(THROUGHPUT_FILE + fileID);
		file.createNewFile();		
		DataOutputStream stream = new DataOutputStream(new FileOutputStream(file, true));
		stream.writeBytes(requests+"\n");
		stream.close();
	}
    
	 
}