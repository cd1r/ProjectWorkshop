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

@ServerEndpoint(value="/filesocket/{email}/{roomId}/{fileInfo}") //Ŭ���̾�Ʈ���� ������ ���� �ּ�
public class FileUploadSocket {
	private static Map<String, Session> clientsMap = Collections.synchronizedMap(new HashMap<String, Session>());
	private ConnectDB connDB = ConnectDB.getConnectDB();
	
	BufferedOutputStream bos;
	boolean check = true;
	long size = 0;
	long totalSize = 0;
	
	//Ŭ���̾�Ʈ�κ��� �޽����� �������� ��� ó�� ���
	@OnMessage
	public void onMessage(String message, Session session, @PathParam("email") String email, @PathParam("roomId") int roomId, @PathParam("fileInfo") String fileInfo) throws IOException{
		System.out.println("[����] Ŭ���̾�Ʈ �޽��� ���� = session : " + session + " / email : " + email + " / roomId : " + roomId + " / fileInfo : "+ fileInfo +" / message : " + message);
		//String path = "C:\\Users\\Seyoon\\Documents\\SockeFileDir\\dialog"+roomId+"\\";
		String path = "C:\\Users\\hyoseung\\Documents\\dialog"+roomId+"\\";
		
		String fileName = fileInfo.substring(0, fileInfo.indexOf("*"));
		String fileType = fileName.substring(fileName.indexOf(".")+1).toLowerCase();
		String fileSize = fileInfo.substring(fileInfo.indexOf("*")+1);
		
		try {
			synchronized (clientsMap) {
				if(message.equals("upload-file-end")){  // 3
					System.out.println("[����] ���ε� ���� / bos ���� ");
					try {
		                bos.flush();
		                bos.close();
		            } catch (IOException e) {
		                e.printStackTrace();
		            }
				}
				else if(message.equals("upload-file-db")){ // 2
					System.out.println("db�� �ֱ���  : "+message+" "+fileName + "  "+fileType+"  "+fileSize);
					boolean result = connDB.fileUpload(roomId, email, fileName, fileType, Integer.parseInt(fileSize));
					if(result)
						clientsMap.get(session.toString()).getBasicRemote().sendText("�Ϸ�");
				}
				else{ //upload-file-start  1
					File file = new File(path + fileName);
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
    public void processUpload(ByteBuffer message, boolean last, Session session, @PathParam("email") String email, @PathParam("roomId") int roomId, @PathParam("fileInfo") String fileInfo) throws IOException{
        if(check == true){
        	totalSize = Long.parseLong(fileInfo.substring(fileInfo.indexOf("*")+1));
        	size = 0;
        	check = false; 	
        }
        size += message.limit();
		System.out.println("[����] Ŭ���̾�Ʈ ���� ���ε� ��  size="+size+"  totalsize="+totalSize);
		while(message.hasRemaining()){
            try {
                bos.write(message.get());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                clientsMap.get(session.toString()).getBasicRemote().sendText("���� ���۽���");
            }
        }
        if((size-totalSize)==0) clientsMap.get(session.toString()).getBasicRemote().sendText("���ϿϷ�"); //Ŭ���̾�Ʈ���� ���� ���ε� �����ٰ� �˷���
        
    }

	//Ŭ���̾�Ʈ�� ���� �Ҷ�
	@OnOpen
	public void onOpen(Session session, @PathParam("email") String email, @PathParam("roomId") int roomId, @PathParam("fileInfo") String fileInfo){	
		System.out.println("[����] Ŭ���̾�Ʈ ���� = session : " + session + " / email : " + email + " / roomId : " + roomId+ " / fileInfo : "+ fileInfo);
		clientsMap.put(session.toString(), session);
	}
	
	//Ŭ���̾�Ʈ�� ������ �������� ��
	@OnClose
	public void onClose(Session session, @PathParam("email") String email, @PathParam("roomId") int roomId, @PathParam("fileInfo") String fileInfo){
		System.out.println("[����] Ŭ���̾�Ʈ ����  = session : " + session + " / email : " + email + " / roomId : " + roomId+ " / fileInfo : "+ fileInfo);
	}
}
