package reverse_server_multi_threaded;

import java.io.IOException;
import java.io.Serializable;
import java.util.Random;
import org.codehaus.jackson.map.ObjectMapper;

public class ClientFormatMessage implements Serializable {	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public Body getBody() {
		return body;
	}

	public void setBody(Body body) {
		this.body = body;
	}

	private Header header;
	private Body body;
	
	public static class Header implements Serializable{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;	
		
		public String getClientIP() {
			return clientIP;
		}
		public void setClientIP(String clientIP) {
			this.clientIP = clientIP;
		}
		public int getPort() {
			return port;
		}
		public void setPort(int port) {
			this.port = port;
		}
		public int getClientID() {
			return clientID;
		}
		public void setClientID(int clientID) {
			this.clientID = clientID;
		}
		
		public String getPayload() {
			return payload;
		}
		public void setPayload(String payload) {
			this.payload = payload;
		}

		public int getRepetitionID() {
			return repetitionID;
		}
		public void setRepetitionID(int repetitionID) {
			this.repetitionID = repetitionID;
		}
		
		private int repetitionID;
		private String clientIP;
		private int port;
		private int clientID;
		private String payload;
	}
	
	public static class Body implements Serializable{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public String getMsgBody() {
			return msgBody;
		}

		public void setMsgBody(String msgBody) {
			this.msgBody = msgBody;
		}

		private String msgBody;
	}
	
	public static String objectToString(ClientFormatMessage clientFormatMessage) {
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			String jsonString = mapper.writeValueAsString(clientFormatMessage);
			return jsonString;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;	
	}
	
	public static ClientFormatMessage stringToObject(String clientFormatMessageString) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			ClientFormatMessage clientFormatMessage = mapper.readValue(clientFormatMessageString, ClientFormatMessage.class);
			return clientFormatMessage;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static String calculatePayload() {
		int min = 300;
		int max = 2000;
		
		Random rand = new Random();
		String payload = Integer.toString((rand.nextInt(max - min + 1) + min));
		return payload;
	}
	
	public static ClientFormatMessage getInstance(String message, int clientID, String clientIP, String payload, int port, int repetitionID){
		Body body = new Body();
		Header header = new Header();
		ClientFormatMessage clientFormatMessage = new ClientFormatMessage();
		
		body.setMsgBody(message);
		header.setClientID(clientID);
		header.setClientIP(clientIP);
		header.setPayload(payload);
		header.setPort(port);
		header.setRepetitionID(repetitionID);		
		clientFormatMessage.setBody(body);
		clientFormatMessage.setHeader(header);
		
		return clientFormatMessage;
	}
	

}
