package reverse_server_multi_threaded;

import java.io.*;
import java.net.*;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This thread is responsible to handle client connection.
 */
public class ServerThread extends Thread {
	private Socket socket;
	private static int counter = 0;
	private static final int TIMER_MILISECONDS = 1000;
	
	public ServerThread(Socket socket) {
		this.socket = socket;
	}

	public void run() {
		try {
			InputStream input = socket.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));

			OutputStream output = socket.getOutputStream();
			PrintWriter writer = new PrintWriter(output, true);

			String requestMessage;

			do {				
				requestMessage = reader.readLine();																				
			
				ClientFormatMessage clientFormatMessage = ClientFormatMessage.stringToObject(requestMessage);
				if (clientFormatMessage.getBody().getMsgBody().equals("BYE")) {
					System.out.println(
							"Connection with client " + clientFormatMessage.getHeader().getClientID() + " terminated..");
					break;
				}else {
					startTimer(clientFormatMessage.getHeader().getRepetitionID());
				}
				ClientFormatMessage serverFormatMessage = ClientFormatMessage.getInstance("WELCOME", clientFormatMessage.getHeader().getClientID(), 
						clientFormatMessage.getHeader().getClientIP(), ClientFormatMessage.calculatePayload(), clientFormatMessage.getHeader().getPort(),
						clientFormatMessage.getHeader().getRepetitionID());			

				System.out.println("Client Request:" + requestMessage);
				writer.println(ClientFormatMessage.objectToString(serverFormatMessage));

			} while (true);

			socket.close();
		} catch (IOException ex) {
			System.out.println("Server exception: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
	
    synchronized static void startTimer(int repetitionID) {
		counter++;
    	if (counter == 1) {
	    	Timer timer = new Timer();    
	    	timer.schedule(new TimerTask() {
	    	  @Override
	    	  public void run() {
	    		  try {
					ReverseServer.writeToFile(counter, repetitionID);
					counter = 0;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}    		  
	    	  }
	    	}, TIMER_MILISECONDS);
    	}    	
    }
}