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

@ServerEndpoint(value="/filesocket/{email}/{roomId}/{fileName}") //클라이언트에서 접속할 서버 주소
public class FileUploadSocket {
	private static Map<String, Session> clientsMap = Collections.synchronizedMap(new HashMap<String, Session>());
	private ConnectDB connDB = ConnectDB.getConnectDB();
	
	BufferedOutputStream bos;
	
	//클라이언트로부터 메시지가 도착했을 경우 처리 방법
	@OnMessage
	public void onMessage(String message, Session session, @PathParam("email") String email, @PathParam("roomId") int roomId, @PathParam("fileName") String fileName) throws IOException{
		System.out.println("[파일] 클라이언트 메시지 정보 = session : " + session + " / email : " + email + " / roomId : " + roomId + " / fileName : "+ fileName +" / message : " + message);
		//String path = "C:\\Users\\Seyoon\\Documents\\SockeFileDir\\dialog"+roomId+"\\";
		String path = "C:\\Users\\hyoseung\\Documents\\dialog"+roomId+"\\";
		
		try {
			synchronized (clientsMap) {
				if(message.equals("upload-file-end")){
					System.out.println("[파일] 업로드 성공 / bos 닫음 ");
					try {
		                bos.flush();
		                bos.close();
		            } catch (IOException e) {
		                e.printStackTrace();
		            }
				}
				else if(message.contains("upload-file-db")){
					System.out.println("db에 넣기전  : "+message);
					String fileType = fileName.substring(fileName.indexOf(".")+1).toLowerCase();
					boolean result = connDB.fileUpload(roomId, email, fileName, fileType);
					if(result)
						clientsMap.get(session.toString()).getBasicRemote().sendText("완료");
				}
				else{ //파일이름
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
        System.out.println("[파일] 클라이언트 파일 업로드 중");
		while(message.hasRemaining()){
            try {
                bos.write(message.get());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                clientsMap.get(session.toString()).getBasicRemote().sendText("파일 전송실패");
            }
        }
        
        //String fileType = fileName.substring(fileName.indexOf(".")+1);
        //디비 저장하는거 구현해야함 /이름중복
        //boolean result = connDB.fileUpload(roomId, email, fileName, fileType);
        //if(result)
        clientsMap.get(session.toString()).getBasicRemote().sendText("파일완료"); //클라이언트에게 파일 업로드 끝났다고 알려줌
        
    }

	//클라이언트가 접속 할때
	@OnOpen
	public void onOpen(Session session, @PathParam("email") String email, @PathParam("roomId") int roomId, @PathParam("fileName") String fileName){	
		System.out.println("[파일] 클라이언트 접속 정보 = session : " + session + " / email : " + email + " / roomId : " + roomId+ " / fileName : "+ fileName);
		clientsMap.put(session.toString(), session);
	}
	
	//클라이언트가 접속이 끊겨졌을 때
	@OnClose
	public void onClose(Session session, @PathParam("email") String email, @PathParam("roomId") int roomId, @PathParam("fileName") String fileName){
		System.out.println("[파일] 클라이언트 해제 정보 = session : " + session + " / email : " + email + " / roomId : " + roomId+ " / fileName : "+ fileName);
	}
}

