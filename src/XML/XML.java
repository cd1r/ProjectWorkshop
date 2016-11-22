package XML;


//import javax.swing.text.Document;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
//import javax.xml.bind.Element;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


public class XML {
	DocumentBuilderFactory docFactory;
	DocumentBuilder docBuilder;
	Document doc;
	
	Element rootElement;
	Element rootElementChild;
	
	public XML(){
		try {
			docFactory = DocumentBuilderFactory.newInstance();
			docBuilder = docFactory.newDocumentBuilder();
			doc = docBuilder.newDocument();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	<root>
		<element_str>
			<name>
		</element_Str>
	</root>
	*/
	
	public void make_rootElement(String root){
		rootElement = doc.createElement(root);
		doc.appendChild(rootElement);
	}
	
	public void make_element(String element_str){
		rootElementChild = doc.createElement(element_str);
		rootElement.appendChild(rootElementChild);
	}
	
	public void make_child(String name, String text){
		Element e = doc.createElement(name);
		e.appendChild(doc.createTextNode(text));
		rootElementChild.appendChild(e);
	}
	
	//public DOMSource make_xml(){
	public String make_xml(){	
		DOMSource source = null;
		StringWriter sw = new StringWriter();
		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING, "euc-kr");
			source = new DOMSource(doc);
			
			/*StreamResult result = new StreamResult(System.out);
			transformer.transform(source, result);*/
			
			transformer.transform(source, new StreamResult(sw));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//return source;
		return sw.toString();
	}
	
	public Document stringToDocument(String xmlStr){
		try{
			doc = docBuilder.parse(new InputSource(new StringReader(xmlStr)));
			return doc;
		} catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
