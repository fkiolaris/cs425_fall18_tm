package reverse_server_single_threaded;

import java.net.*;
import java.util.Scanner;
import java.io.*;
 
/**
 * This program demonstrates a simple TCP/IP socket client that reads input
 * from the user and prints echoed message from the server.
 */
public class ReverseClient {
 
    public static void main(String[] args) {
        //if (args.length < 2) return;
 
        String hostname = "localhost";
        		//args[0];
        int port = 3000;
        		//Integer.parseInt(args[1]);
        
        
        try (Socket socket = new Socket(hostname, port)) {
 
            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);
 
            Scanner scan = new Scanner(System.in);
            String text;
 
            do {
            	System.out.print("Enter text: ");
                text = scan.nextLine();
 
                writer.println(text);
 
                InputStream input = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
 
                String time = reader.readLine();
 
                System.out.println(time);
 
            } while (!text.equals("bye"));
 
            scan.close();
            socket.close();
 
        } catch (UnknownHostException ex) {
 
            System.out.println("Server not found: " + ex.getMessage());
 
        } catch (IOException ex) {
 
            System.out.println("I/O error: " + ex.getMessage());
        }
    }
}