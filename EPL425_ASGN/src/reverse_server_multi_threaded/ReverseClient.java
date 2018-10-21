package reverse_server_multi_threaded;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * This program demonstrates a simple TCP/IP socket client that reads input
 * from the user and prints echoed message from the server.
 */
public class ReverseClient{
	
	private static final int USER = 10;
	private static final String latencyFile = "latencyTime";

    public static void main(String[] args) {
        
        try {
            String hostname = args[0];
            int port = Integer.parseInt(args[1]);
            int repetitions = 1;
            		//Integer.parseInt(args[2]);
            
            for (int i=1; i<=repetitions; i++) initializeFiles(i);            
            runAllUsers(repetitions, port, hostname); 
           
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}       
    }
    
    synchronized static void runAllUsers(int repetitionID, int port, String hostname) {
    	for (int i=1; i<=USER; i++ ) new ClientThread(port, hostname, i, repetitionID).start();
    }
    
    public static void initializeFiles(int fileID) throws IOException {
    	File file = new File(latencyFile+fileID);
    	DataOutputStream stream = new DataOutputStream(new FileOutputStream(file));
		stream.writeBytes("");
		stream.close();
    }
    
	 synchronized static void writeToFile(String time, int userID, int fileID) throws IOException {			
		File file = new File(latencyFile+fileID);
		file.createNewFile();		
		DataOutputStream stream = new DataOutputStream(new FileOutputStream(file, true));
		stream.writeBytes("UserID:"+userID+" "+time+"\n");
		stream.close();
	}
	 

}