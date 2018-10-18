package reverse_server_multi_threaded;

/**
 * This program demonstrates a simple TCP/IP socket client that reads input
 * from the user and prints echoed message from the server.
 */
public class ReverseClient{
	
	private static final int USER = 10;

    public static void main(String[] args) {

        String hostname = args[0];
        int port = Integer.parseInt(args[1]);
        
        for (int i=1; i<=USER; i++ ) new ClientThread(port, hostname, i).start();

    }
}