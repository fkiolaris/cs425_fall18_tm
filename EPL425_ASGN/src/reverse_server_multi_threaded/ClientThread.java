package reverse_server_multi_threaded;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientThread extends Thread  {
	private String serverID;
	private int clientID;
	private int port;
	private String clientIP;
	private int repetitionID;
	private static final int MAX_REQUESTS_PER_USER = 300;

	public ClientThread(int port, String serverID, int clientID, int repetitionID) {
		this.clientID = clientID;
		this.serverID = serverID;
		this.port = port;
		this.clientIP = getClientIp();
		this.repetitionID = repetitionID;
	}
	
	private static String getClientIp(){

        String remoteAddr = "";

        InetAddress inetAddress = null;
		try {
			inetAddress = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		remoteAddr = inetAddress.getHostAddress();
        return remoteAddr;
    }

	public void run() {
		
        try (Socket socket = new Socket(serverID, port)) {
       	 
            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);
           
            String text;
            int currentRequest = 0;
            do {                
                ClientFormatMessage clientFormatMessage = ClientFormatMessage.getInstance("Hello", clientID, clientIP, null, port, repetitionID);  
                text = ClientFormatMessage.objectToString(clientFormatMessage);
                               
                long startTime = System.currentTimeMillis();	//start request time
                writer.println(text);
 
                InputStream input = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
 
                String response = reader.readLine();
                long elapsedTime = System.currentTimeMillis() - startTime;
                ReverseClient.writeToFile(elapsedTime+"", clientID, repetitionID);
 
                System.out.println("Total elapsed http request/response time in milliseconds: " + elapsedTime);
                System.out.println("Server Response:"+response);
 
            } while (currentRequest++ < MAX_REQUESTS_PER_USER);
                                
            ClientFormatMessage clientFormatMessage = ClientFormatMessage.getInstance("BYE", clientID, clientIP, null, port, repetitionID);
            text = ClientFormatMessage.objectToString(clientFormatMessage);
            writer.println(text);
            
            System.out.println("Connection terminated..");
            socket.close();
 
        } catch (UnknownHostException ex) {	 
            System.out.println("Server not found: " + ex.getMessage());
 
        } catch (IOException ex) {	 
            System.out.println("I/O error: " + ex.getMessage());
        }
	}
	
}
