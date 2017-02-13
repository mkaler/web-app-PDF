package code.servlets;

import java.io.File;
import java.io.IOException;

import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import code.core.ManipulatePDF;

/**
 * Servlet implementation class GotPDF
 */
public class GotPDF extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	   private String filePath = "src/uploaded/";
	   private File file;
	   private String dbOutPath = "src/output/database.sqlite";
	   private String dbInPath = "src/main/resources/inputFiles/InfoDipendenti.sqlite";
	   private String PDFOutPath = "src/output/";

	   public void init( ){
	      // Get the file location where it would be stored.
	      //filePath =  getServletContext().getInitParameter("PDFtpSplit"); 
	   }
	    public GotPDF() {
	        super();
	        // TODO Auto-generated constructor stub
	    }
	
		/**
		 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
		 */
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
			uploadPDF(request, response);
			
			request.getRequestDispatcher("/WEB-INF/jsps/acceptFile.jsp").forward(request, response);
			
			splitPDF(file);
		}
		
	
		/**
		 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
		 */
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
			doGet(request, response);
		}
		
		private void uploadPDF(HttpServletRequest request, HttpServletResponse response){
			String message = "The file has been uploaded";
			
		      DiskFileItemFactory factory = new DiskFileItemFactory();
		   
		      //factory.setRepository(new File("src/results/"));
	
		      // Create a new file upload handler
		      ServletFileUpload upload = new ServletFileUpload(factory);
	
		      try{ 
		      // Parse the request to get file items.
		      List<FileItem> fileItems = upload.parseRequest(request);
			
		      // Process the uploaded file items
		      Iterator<FileItem> i = fileItems.iterator();
		      
		      while ( i.hasNext () ) 
		      {
		         FileItem fi = (FileItem)i.next();
		         
		         if ( !fi.isFormField () )	
		         {
		            String fileName = fi.getName();
		            
		            
		            // Write the file
		            if( fileName.lastIndexOf("\\") >= 0 )
		            {
		               file = new File( filePath + 
		               fileName.substring( fileName.lastIndexOf("\\"))) ;
		            }
		            else
		            {
		               file = new File( filePath + 
		               fileName.substring(fileName.lastIndexOf("\\")+1)) ;
		            }
		            
		            fi.write( file ) ;
		            
		         }
		      }
		      
		   }catch(Exception ex) {
		       System.out.println(ex);
		       message = "Erorr";
		   }
		}
		private void splitPDF(File file){
			
			ManipulatePDF p = new ManipulatePDF();
			try {
				p.init(file, PDFOutPath, dbInPath, dbOutPath);
				System.out.println("Done");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	
}
