package reverse_server_multi_threaded;

import java.net.*;
import java.util.Scanner;

import reverse_server_multi_threaded.ClientFormatMessage.Body;
import reverse_server_multi_threaded.ClientFormatMessage.Header;

import java.io.*;
 
/**
 * This program demonstrates a simple TCP/IP socket client that reads input
 * from the user and prints echoed message from the server.
 */
public class ReverseClient{
	
	private static final int USER = 10;
//	private static int currentUsers = 1;
//	private static final int MAX_REQUESTS_PER_USER = 300;
 
    public static void main(String[] args) {
//        if (args.length < 2) return;
 
        String hostname = args[0];
        int port = Integer.parseInt(args[1]);
        
        for (int i=1; i<=USER; i++ ) new ClientThread(port, hostname, i).start();

    }
}