package reverse_server_multi_threaded;

import java.io.*;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.net.*;
import java.util.Timer;
import java.util.TimerTask;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.MBeanServer;
import javax.management.ObjectName;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

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
    
    synchronized static void writeToFile(int requests, int fileID) throws Exception {			
		File file = new File(THROUGHPUT_FILE + fileID);
		file.createNewFile();		
		DataOutputStream stream = new DataOutputStream(new FileOutputStream(file, true));
		
		double cpuUsage = getProcessCpuLoad();		
		stream.writeBytes(requests+" " + cpuUsage +"  "+ getMemoryUsageUtilization()+"\n");
		stream.close();
	}
    
    public static double getProcessCpuLoad() throws Exception {

        MBeanServer mbs    = ManagementFactory.getPlatformMBeanServer();
        ObjectName name    = ObjectName.getInstance("java.lang:type=OperatingSystem");
        AttributeList list = mbs.getAttributes(name, new String[]{ "ProcessCpuLoad" });

        if (list.isEmpty())     return Double.NaN;

        Attribute att = (Attribute)list.get(0);
        Double value  = (Double)att.getValue();

        // usually takes a couple of seconds before we get real values
        if (value == -1.0)      return Double.NaN;
        // returns a percentage value with 1 decimal point precision
        return ((int)(value * 1000) / 10.0);
    }
    
    public static double getMemoryUsageUtilization() {
    	
    	long beforeUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
    	long afterUsedMem=Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory();
    	long actualMemUsed=afterUsedMem-beforeUsedMem;
//    	return actualMemUsed;
    	return ((int)(actualMemUsed * 1000) / 10.0);
    }
    
	 
}