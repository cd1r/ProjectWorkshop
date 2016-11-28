package CHATTING;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.omg.CORBA_2_3.portable.InputStream;

/**
 * Servlet implementation class FileDownloadServlet
 */
@WebServlet("/file_download")
public class FileDownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileDownloadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		ConnectDB connDB = ConnectDB.getConnectDB();
		
		String path = "C:\\Users\\hyoseung\\Documents\\";
		//String path = "http://localhost:8080/참고.txt";
		String fileInfo = "";
		String fileName = "";
		String fileId = request.getParameter("file_id");
	
		fileInfo = connDB.downFileInfo(fileId);
		
		if(fileInfo==null){
			System.out.println("파일 없음");
			return;
		}
		
		fileInfo = fileInfo.replace("/", "\\");
		fileName = fileInfo.substring(fileInfo.indexOf("\\")+1);
		path += fileInfo;
		
		System.out.println("[파일 다운로드] 저장된경로: "+path+"  "+fileName);

		String file2 = URLEncoder.encode(fileName, "utf-8");
		
		java.io.File file = new java.io.File(path);
		byte b[] = new byte[(int)file.length()];
		
		response.setHeader("Content-Disposition", "attachment; filename=" + file2);
		response.setContentType("application/octet-stream");
		
		if(file.isFile()){
			 BufferedInputStream fin = new BufferedInputStream(new FileInputStream(file));
			 BufferedOutputStream outs = new BufferedOutputStream(response.getOutputStream());
			 int read = 0;
			 while((read = fin.read(b)) != -1){
			  outs.write(b, 0, read);
			 }
			 outs.close();
			 fin.close();
		}
		/*File file = new File(path);
		FileInputStream filein = new FileInputStream(file);
		ServletOutputStream op = response.getOutputStream();
		
		byte[] outputByte = new byte[4096];
		while(filein.read(outputByte, 0, 4096)!=-1){
			op.write(outputByte, 0, 4096);
		}
		
		filein.close();
		op.flush();
		op.close();*/
		
/*		int length = 0;
		ServletOutputStream op = response.getOutputStream();
		ServletContext context = getServletConfig().getServletContext();
		String mimetype = context.getMimeType(fileName);
		
		response.setContentType((mimetype != null) ? mimetype : "application/octec-stream");
		
		System.out.println("[] mimetype: "+mimetype);
		response.setHeader ("Content-Disposition", "attachment; filename=\""+fileName+"\"");
		
		byte[] b = new byte[4096];
		DataInputStream in = new DataInputStream(new FileInputStream(file));
		
		while((in!=null) && ((length = in.read(b))!=-1)){
			op.write(b,0,length);
		}
		
		in.close();
		op.flush();
		op.close();*/
/*		try{
		
			File file = null;
			FileInputStream in = null;
			ServletOutputStream fileout = null;
			
			try{
				file = new File(path, fileName);
				in = new FileInputStream(path);
			}catch(Exception e){}
			
			String client = request.getHeader("User-Agent");
			response.reset(); 
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Description", "JSP Generated Data");
			
			if(client.indexOf("MSIE") != -1){
				System.out.println("msie");
                response.setHeader ("Content-Disposition", "attachment; filename="+new String(fileName.getBytes("KSC5601"),"ISO8859_1"));
 
            }else{
            	System.out.println("else");
                // 한글 파일명 처리
                fileName = new String(fileName.getBytes("utf-8"),"iso-8859-1");
 
                response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
                response.setHeader("Content-Type", "application/octet-stream; charset=utf-8");
            }  
             
            response.setHeader ("Content-Length", ""+file.length() );
            fileout = response.getOutputStream();
            byte b[] = new byte[(int)file.length()];
            int leng=0;
            
            while((leng = in.read(b)) > 0){
            	fileout.write(b,0,leng);
            }*/
  /*          
			//File file = new File(path);
			File file = new File(root);
			byte b[] = new byte[4096];
			
			//page의 contentType을 동적으로 바꾸기 위해 초기화 시킴
			response.reset(); 
			response.setContentType("application/octet-stream");
			
			FileInputStream in = new FileInputStream(path);
			ServletOutputStream fileout = response.getOutputStream();

			
			String encoding = new String(fileName.getBytes("UTF-8"),  "8859_1");
			//파일 링크를 클릭했을 때 다운로드 저장 화면이 출력되게 처리하는 부분
			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName +"\"");
			response.setHeader("Content-Type", "application/octet-stream; charset=utf-8");
	
			int numRead;
			while((numRead = in.read(b, 0, b.length)) != -1){
				fileout.write(b, 0, numRead);
			}
			
			System.out.println("닫는 중");
			fileout.flush();
			fileout.close();
			in.close();
		}catch(Exception e){}*/
		

/*
		response.setContentType("text/html;charset=utf-8");
		PrintWriter pwrite = response.getWriter();
		pwrite.write("true");
		pwrite.close();*/
		
	}

}
