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

@ServerEndpoint(value="/websocket/{email}/{roomId}") //Ŭ���̾�Ʈ���� ������ ���� �ּ�
public class WebSocket {
	//private static Set<Session> clients = Collections.synchronizedSet(new HashSet<Session>());
	private static Map<String, Session> clientsMap = Collections.synchronizedMap(new HashMap<String, Session>());
	private ConnectDB connDB = ConnectDB.getConnectDB();
	//Ŭ���̾�Ʈ�κ��� �޽����� �������� ��� ó�� ���
	@OnMessage
	public void onMessage(String message, Session session, @PathParam("email") String email, @PathParam("roomId") int roomId) throws IOException{
		System.out.println("Ŭ���̾�Ʈ �޽��� ���� = session : " + session + " / email : " + email + " / roomId : " + roomId + " / message : " + message);
		
		try {
			synchronized (clientsMap){
				if(connDB.insertDialog(roomId, email, message))
					System.out.println("�޽��� DB ���� ����");
				else
					System.out.println("�޽��� DB ���� ����");
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
	
	//Ŭ���̾�Ʈ�� ���� �Ҷ�
	@OnOpen
	public void onOpen(Session session, @PathParam("email") String email, @PathParam("roomId") int roomId){
		
		System.out.println("Ŭ���̾�Ʈ ���� ���� = session : " + session + " / email : " + email + " / roomId : " + roomId);
		if(connDB.addClient(session.toString(), email, roomId)){
			System.out.println("Ŭ���̾�Ʈ �߰� �Ϸ�!");
		}
		else{
			System.out.println("Ŭ���̾�Ʈ �߰� ����!");
		}
		clientsMap.put(session.toString(), session);
	}
	
	//Ŭ���̾�Ʈ�� ������ �������� ��
	@OnClose
	public void onClose(Session session, @PathParam("email") String email, @PathParam("roomId") int roomId){
		System.out.println("Ŭ���̾�Ʈ ���� ���� = session : " + session + " / email : " + email + " / roomId : " + roomId);
		if(connDB.deleteClient(email, roomId))
			System.out.println("Ŭ���̾�Ʈ ���� �Ϸ�!");
		else
			System.out.println("Ŭ���̾�Ʈ ���� ����!");
		
		if(connDB.insertLastReadDialogId(email, roomId))
			System.out.println("Last Dialog ID ���� �Ϸ�!");
		else
			System.out.println("Last Dialog ID ���� ����!");

		clientsMap.remove(session.toString());
	}
}
