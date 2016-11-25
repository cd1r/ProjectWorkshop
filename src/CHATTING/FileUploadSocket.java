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

@ServerEndpoint(value="/filesocket/{email}/{roomId}/{fileName}") //Ŭ���̾�Ʈ���� ������ ���� �ּ�
public class FileUploadSocket {
	private static Map<String, Session> clientsMap = Collections.synchronizedMap(new HashMap<String, Session>());
	private ConnectDB connDB = ConnectDB.getConnectDB();
	
	BufferedOutputStream bos;
	
	//Ŭ���̾�Ʈ�κ��� �޽����� �������� ��� ó�� ���
	@OnMessage
	public void onMessage(String message, Session session, @PathParam("email") String email, @PathParam("roomId") int roomId, @PathParam("fileName") String fileName) throws IOException{
		System.out.println("[����] Ŭ���̾�Ʈ �޽��� ���� = session : " + session + " / email : " + email + " / roomId : " + roomId + " / fileName : "+ fileName +" / message : " + message);
		//String path = "C:\\Users\\Seyoon\\Documents\\SockeFileDir\\dialog"+roomId+"\\";
		String path = "C:\\Users\\hyoseung\\Documents\\dialog"+roomId+"\\";
		
		try {
			synchronized (clientsMap) {
				if(message.equals("upload-file-end")){
					System.out.println("[����] ���ε� ���� / bos ���� ");
					try {
		                bos.flush();
		                bos.close();
		            } catch (IOException e) {
		                e.printStackTrace();
		            }
				}
				else if(message.contains("upload-file-db")){
					System.out.println("db�� �ֱ���  : "+message);
					String fileType = fileName.substring(fileName.indexOf(".")+1).toLowerCase();
					boolean result = connDB.fileUpload(roomId, email, fileName, fileType);
					if(result)
						clientsMap.get(session.toString()).getBasicRemote().sendText("�Ϸ�");
				}
				else{ //�����̸�
					File file = new File(path + message);
		            try {
		                bos = new BufferedOutputStream(new FileOutputStream(file));
		            } catch (FileNotFoundException e) {
		                // TODO Auto-generated catch block
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
    public void processUpload(ByteBuffer message, boolean last, Session session, @PathParam("email") String email, @PathParam("roomId") int roomId, @PathParam("fileName") String fileName) throws IOException{
        System.out.println("[����] Ŭ���̾�Ʈ ���� ���ε� ��");
		while(message.hasRemaining()){
            try {
                bos.write(message.get());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                clientsMap.get(session.toString()).getBasicRemote().sendText("���� ���۽���");
            }
        }
        
        //String fileType = fileName.substring(fileName.indexOf(".")+1);
        //��� �����ϴ°� �����ؾ��� /�̸��ߺ�
        //boolean result = connDB.fileUpload(roomId, email, fileName, fileType);
        //if(result)
        clientsMap.get(session.toString()).getBasicRemote().sendText("���ϿϷ�"); //Ŭ���̾�Ʈ���� ���� ���ε� �����ٰ� �˷���
        
    }

	//Ŭ���̾�Ʈ�� ���� �Ҷ�
	@OnOpen
	public void onOpen(Session session, @PathParam("email") String email, @PathParam("roomId") int roomId, @PathParam("fileName") String fileName){	
		System.out.println("[����] Ŭ���̾�Ʈ ���� ���� = session : " + session + " / email : " + email + " / roomId : " + roomId+ " / fileName : "+ fileName);
		clientsMap.put(session.toString(), session);
	}
	
	//Ŭ���̾�Ʈ�� ������ �������� ��
	@OnClose
	public void onClose(Session session, @PathParam("email") String email, @PathParam("roomId") int roomId, @PathParam("fileName") String fileName){
		System.out.println("[����] Ŭ���̾�Ʈ ���� ���� = session : " + session + " / email : " + email + " / roomId : " + roomId+ " / fileName : "+ fileName);
	}
}

