package code.servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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
	   private String dbOutPath = "src/output/database/database.sqlite";
	   private String dbInPath = "src/main/resources/inputFiles/InfoDipendenti.sqlite";
	   private String PDFOutPath = "src/output/pdf/";

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
			try
			{
				uploadPDF(request, response);
				
				splitPDF(file);
				
				makeZip(request, response);
				
			}
			catch(ServletException servletEx)
			{
				servletEx.printStackTrace();
				
			}
			catch(IOException IOEx)
			{
				IOEx.getMessage();
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
			
		}
		
	
		/**
		 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
		 */
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
			doGet(request, response);
		}
		
		private void uploadPDF(HttpServletRequest request, HttpServletResponse response) throws Exception{
			
			String message = "The file has been uploaded";
			
		      DiskFileItemFactory factory = new DiskFileItemFactory();
		   
		      //factory.setRepository(new File("src/results/"));
	
		      // Create a new file upload handler
		      ServletFileUpload upload = new ServletFileUpload(factory);
	
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
		               file = new File( filePath + fileName.substring(fileName.lastIndexOf("\\")+1)) ;
		            }
		            
		            fi.write( file );
		            
		         }
		      }
		}
		
		private void splitPDF(File file) throws Exception{
			
			ManipulatePDF p = new ManipulatePDF();
			p.init(file, PDFOutPath, dbInPath, dbOutPath);
			System.out.println("Done");
			
		}
		
		public void zipDir(String dir2zip, ZipOutputStream zos) throws IOException 
		{
		        //create a new File object based on the directory we have to zip File    
		        File zipDir = new File(dir2zip); 
		        //get a listing of the directory content 
		        String[] dirList = zipDir.list(); 
		        byte[] readBuffer = new byte[2156]; 
		        int bytesIn = 0; 
		        //loop through dirList, and zip the files 
		        for(int i=0; i<dirList.length; i++) 
		        { 
		            File f = new File(zipDir, dirList[i]); 
		            if(f.isDirectory()) 
		            { 
		                //if the File object is a directory, call this 
		                //function again to add its content recursively 
		                String filePath = f.getPath();
		            //  String filePath = f.getCanonicalPath();

		                zipDir(filePath, zos);
		                //loop again 
		                continue; 
		            } 
		            //if we reached here, the File object f was not  a directory 
		            //create a FileInputStream on top of f 
		            FileInputStream fis = new FileInputStream(f); 
		            // create a new zip entry 
		            ZipEntry anEntry = new ZipEntry(f.getName()); 
		            //place the zip entry in the ZipOutputStream object 
		            zos.putNextEntry(anEntry); 
		            //now write the content of the file to the ZipOutputStream 
		            while((bytesIn = fis.read(readBuffer)) != -1) 
		            { 
		                zos.write(readBuffer, 0, bytesIn); 
		            } 
		            //close the Stream 
		            fis.close(); 
		        }  
		    
		}
		public void makeZip(HttpServletRequest request, HttpServletResponse response) throws Exception{
			try 
		    { 
		        response.setContentType("application/zip");
		        response.setHeader("Content-Disposition", "attachment;filename=dividedPDFs.zip");

		        ZipOutputStream zos = new 
		               ZipOutputStream(response.getOutputStream()); 

		        zipDir(PDFOutPath, zos); 

		        zos.close();
		        //request.getRequestDispatcher("/WEB-INF/jsps/acceptFile.jsp").forward(request, response);
		    } 
		    catch(Exception e) 
		    { 
		       throw new Exception("Error while making the zip", e);
		    } 

			
		}
		
	
}
