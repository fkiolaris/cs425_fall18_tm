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
	private static final String latencyFile = "latencyTime.txt";

    public static void main(String[] args) {


        
        try {
            String hostname = args[0];
            int port = Integer.parseInt(args[1]);
			initializeFiles();
			for (int i=1; i<=USER; i++ ) new ClientThread(port, hostname, i).start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}       
    }
    
    public static void initializeFiles() throws IOException {
    	File file = new File(latencyFile);
    	DataOutputStream stream = new DataOutputStream(new FileOutputStream(file));
		stream.writeBytes("");
		stream.close();
    }
    
	 synchronized static void writeToFile(String time, int userID) throws IOException {			
		File file = new File(latencyFile);
		file.createNewFile();		
		DataOutputStream stream = new DataOutputStream(new FileOutputStream(file, true));
		stream.writeBytes("UserID:"+userID+" "+time+"\n");
		stream.close();
	}
	 

}