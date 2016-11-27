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

@ServerEndpoint(value="/filesocket/{email}/{roomId}/{fileInfo}") //클라이언트에서 접속할 서버 주소
public class FileUploadSocket {
	private static Map<String, Session> clientsMap = Collections.synchronizedMap(new HashMap<String, Session>());
	private ConnectDB connDB = ConnectDB.getConnectDB();
	
	BufferedOutputStream bos;
	boolean check = true;
	long size = 0;
	long totalSize = 0;
	
	//클라이언트로부터 메시지가 도착했을 경우 처리 방법
	@OnMessage
	public void onMessage(String message, Session session, @PathParam("email") String email, @PathParam("roomId") int roomId, @PathParam("fileInfo") String fileInfo) throws IOException{
		System.out.println("[파일] 클라이언트 메시지 정보 = session : " + session + " / email : " + email + " / roomId : " + roomId + " / fileInfo : "+ fileInfo +" / message : " + message);
		//String path = "C:\\Users\\Seyoon\\Documents\\SockeFileDir\\dialog"+roomId+"\\";
		String path = "C:\\Users\\hyoseung\\Documents\\dialog"+roomId+"\\";
		
		String fileName = fileInfo.substring(0, fileInfo.indexOf("*"));
		String fileType = fileName.substring(fileName.indexOf(".")+1).toLowerCase();
		String fileSize = fileInfo.substring(fileInfo.indexOf("*")+1);
		
		try {
			synchronized (clientsMap) {
				if(message.equals("upload-file-end")){  // 3
					System.out.println("[파일] 업로드 성공 / bos 닫음 ");
					try {
		                bos.flush();
		                bos.close();
		            } catch (IOException e) {
		                e.printStackTrace();
		            }
				}
				else if(message.equals("upload-file-db")){ // 2
					System.out.println("db에 넣기전  : "+message+" "+fileName + "  "+fileType+"  "+fileSize);
					boolean result = connDB.fileUpload(roomId, email, fileName, fileType, Integer.parseInt(fileSize));
					if(result)
						clientsMap.get(session.toString()).getBasicRemote().sendText("완료");
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
		System.out.println("[파일] 클라이언트 파일 업로드 중  size="+size+"  totalsize="+totalSize);
		while(message.hasRemaining()){
            try {
                bos.write(message.get());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                clientsMap.get(session.toString()).getBasicRemote().sendText("파일 전송실패");
            }
        }
        if((size-totalSize)==0) clientsMap.get(session.toString()).getBasicRemote().sendText("파일완료"); //클라이언트에게 파일 업로드 끝났다고 알려줌
        
    }

	//클라이언트가 접속 할때
	@OnOpen
	public void onOpen(Session session, @PathParam("email") String email, @PathParam("roomId") int roomId, @PathParam("fileInfo") String fileInfo){	
		System.out.println("[파일] 클라이언트 접속 = session : " + session + " / email : " + email + " / roomId : " + roomId+ " / fileInfo : "+ fileInfo);
		clientsMap.put(session.toString(), session);
	}
	
	//클라이언트가 접속이 끊겨졌을 때
	@OnClose
	public void onClose(Session session, @PathParam("email") String email, @PathParam("roomId") int roomId, @PathParam("fileInfo") String fileInfo){
		System.out.println("[파일] 클라이언트 해제  = session : " + session + " / email : " + email + " / roomId : " + roomId+ " / fileInfo : "+ fileInfo);
	}
}
