package CHATTING;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

@ServerEndpoint(value="/filesocket/{email}/{roomId}") //Ŭ���̾�Ʈ���� ������ ���� �ּ�
public class FileUploadSocket {
	private static Map<String, Session> clientsMap = Collections.synchronizedMap(new HashMap<String, Session>());
	private ConnectDB connDB = ConnectDB.getConnectDB();
	
	BufferedOutputStream bos;
	
	//Ŭ���̾�Ʈ�κ��� �޽����� �������� ��� ó�� ���
	@OnMessage
	public void onMessage(String message, Session session, @PathParam("email") String email, @PathParam("roomId") int roomId) throws IOException{
		System.out.println("[����] Ŭ���̾�Ʈ �޽��� ���� = session : " + session + " / email : " + email + " / roomId : " + roomId + " / message : " + message);
		String path = "C:\\Users\\hyoseung\\Documents\\dialog"+roomId+"\\";
		
		try {
			synchronized (clientsMap) {
				if (!message.equals("upload-file-end")) {
					//String fileName = message.substring(message.indexOf(":")+1);
		            System.out.println("[����] �̸� : "+message);
		            File file = new File(path + message);
		            try {
		                bos = new BufferedOutputStream(new FileOutputStream(file));
		            } catch (FileNotFoundException e) {
		                // TODO Auto-generated catch block
		                e.printStackTrace();
		            }
				} 
				else {
					System.out.println("[����] ���ε� ���� / bos ���� ");
					try {
		                bos.flush();
		                bos.close();
		            } catch (IOException e) {
		                e.printStackTrace();
		            }
				}
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@OnMessage
    public void processUpload(ByteBuffer message, boolean last, Session session, @PathParam("email") String email, @PathParam("roomId") int roomId) throws IOException{
        while(message.hasRemaining()){
            try {
                bos.write(message.get());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
   
        //��� �����ϴ°� �����ؾ��� /�̸��ߺ�
        //connDB.fileUpload(roomId, session.toString());
		clientsMap.get(session.toString()).getBasicRemote().sendText("�Ϸ�"); //Ŭ���̾�Ʈ���� ���� ���ε� �����ٰ� �˷���
    }

	//Ŭ���̾�Ʈ�� ���� �Ҷ�
	@OnOpen
	public void onOpen(Session session, @PathParam("email") String email, @PathParam("roomId") int roomId){
		
		System.out.println("[����] Ŭ���̾�Ʈ ���� ���� = session : " + session + " / email : " + email + " / roomId : " + roomId);
		clientsMap.put(session.toString(), session);
	}
	
	//Ŭ���̾�Ʈ�� ������ �������� ��
	@OnClose
	public void onClose(Session session, @PathParam("email") String email, @PathParam("roomId") int roomId){
		System.out.println("[����] Ŭ���̾�Ʈ ���� ���� = session : " + session + " / email : " + email + " / roomId : " + roomId);
	}
}
