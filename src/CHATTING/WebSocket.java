package CHATTING;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

@ServerEndpoint(value="/websocket/{email}/{roomId}") //클라이언트에서 접속할 서버 주소
public class WebSocket {
	//private static Set<Session> clients = Collections.synchronizedSet(new HashSet<Session>());
	private static Map<String, Session> clientsMap = Collections.synchronizedMap(new HashMap<String, Session>());
	private ConnectDB connDB = ConnectDB.getConnectDB();
	//클라이언트로부터 메시지가 도착했을 경우 처리 방법
	@OnMessage
	public void onMessage(String message, Session session, @PathParam("email") String email, @PathParam("roomId") int roomId) throws IOException{
		System.out.println("클라이언트 메시지 정보 = session : " + session + " / email : " + email + " / roomId : " + roomId + " / message : " + message);
		
		try {
			synchronized (clientsMap){
				if(connDB.insertDialog(roomId, email, message))
					System.out.println("메시지 DB 저장 성공");
				else
					System.out.println("메시지 DB 저장 실패");
				ArrayList<String> clientStr = new ArrayList<>();
				
				InputSource is = new InputSource(new StringReader(connDB.getReceiverClient(roomId)));
				
				Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);
				XPath xpath= XPathFactory.newInstance().newXPath();
				
				NodeList sessions = (NodeList)xpath.evaluate("//sessions/session", document, XPathConstants.NODESET);
				for(int idx=0; idx<sessions.getLength(); idx++){
					//System.out.println(sessions.item(idx).getTextContent());
					clientStr.add(sessions.item(idx).getTextContent());
				}
				
				for(int i=0; i<clientStr.size(); i++){
					if(!clientStr.get(i).equals(session.toString()))
					{
						System.out.println(clientStr.get(i) + " " + session.toString());
						clientsMap.get(clientStr.get(i)).getBasicRemote().sendText(message);
					}
				}
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//클라이언트가 접속 할때
	@OnOpen
	public void onOpen(Session session, @PathParam("email") String email, @PathParam("roomId") int roomId){
		
		System.out.println("클라이언트 접속 정보 = session : " + session + " / email : " + email + " / roomId : " + roomId);
		if(connDB.addClient(session.toString(), email, roomId)){
			System.out.println("클라이언트 추가 완료!");
		}
		else{
			System.out.println("클라이언트 추가 실패!");
		}
		clientsMap.put(session.toString(), session);
	}
	
	//클라이언트가 접속이 끊겨졌을 때
	@OnClose
	public void onClose(Session session, @PathParam("email") String email, @PathParam("roomId") int roomId){
		System.out.println("클라이언트 해제 정보 = session : " + session + " / email : " + email + " / roomId : " + roomId);
		if(connDB.deleteClient(email, roomId))
			System.out.println("클라이언트 제거 완료!");
		else
			System.out.println("클라이언트 제거 실패!");
		
		if(connDB.insertLastReadDialogId(email, roomId))
			System.out.println("Last Dialog ID 삽입 완료!");
		else
			System.out.println("Last Dialog ID 삽입 실패!");

		clientsMap.remove(session.toString());
	}
}
