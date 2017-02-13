package code.core;

import org.apache.pdfbox.multipdf.Splitter; 
import org.apache.pdfbox.pdmodel.PDDocument;

import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import java.awt.Rectangle;
import java.io.File;

import java.io.FileNotFoundException;


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import java.sql.Statement;
import java.util.List; 

import java.util.LinkedList;


/**
 * A class that can be used to split the pages of a PDF that contain a specific string, and then create new merged PDFs
 * @author Mandeep Kaler
 *
 */

public final class ManipulatePDF  {

	
	public ManipulatePDF()
	{
		
	}
	
	/**
	 * 
	 * @param toSplitPath Path of the input PDF to split
	 * @param outputPathPDF Path to save the new output PDFs
	 * @param inputPathDB	Path of the database input file
	 * @param outputPathDB	Path to save the output PDF
	 * @throws Exception
	 */
	public void init(File toSplit, String outputPathPDF, String inputPathDB, String outputPathDB) throws Exception
	{
		/**
		 * 
		 */
		
		List<Employee> emps = DBToList(inputPathDB);
		
		for(Employee emp: emps)
			splitSingle(toSplit, (outputPathPDF  + emp.getName() + "_" + emp.getLastName() + ".pdf").replaceAll(" ", "_")
					, emp );
		
		EmpsToSqlite(emps, outputPathDB);	
				
	}
	

	/**Splits for the PDF, searches for the key and merges it to a new PDF
	 * 
	 * @param toSplitPath Path of the PDF to split
	 * @param outputPathPDF Path to save the output PDFs
	 * @param emp Employee to split the PDF for
	 * @throws FileNotFoundException, IOException
	 */
	@SuppressWarnings("resource")
	private void splitSingle(File toSplit, String outputPathPDF, Employee emp) throws FileNotFoundException,
	IOException
	{
			File outputFile = new File(outputPathPDF);
			outputFile.createNewFile();
			
			PDFTextStripper stripper = new PDFTextStripper();
			
			//loading the input PDF
			PDDocument completeDoc = new PDDocument();
			completeDoc = PDDocument.load(toSplit);
			
			//Instantiating newDoc to save a new document dedicated to a single employee
			PDDocument newDoc = new PDDocument();
			
			/*
			InputStream is = SplitPDF.class.getResourceAsStream(toSplitPath);
			loading the file
			inputFile = new File()
			OutputStream os = new OutputStream();
			*/
			
			
			
			//Instantiating Splitter class
			Splitter splitter = new Splitter();
			
			//Setting the start page for the split
			splitter.setStartPage(1);
			
			//Setting the end page for the split
			splitter.setEndPage(completeDoc.getNumberOfPages());
			
			//rectangle to extract text from an area of the pdf page
			PDFTextStripperByArea stripAr = new PDFTextStripperByArea();
			
			
			
			//parameter sequence: x, y, width, height
			Rectangle recMese = new Rectangle(30, 280, 60, 15);
			//adding a region to the pdf
			stripAr.addRegion("mese", recMese);
			
			Rectangle recAnno = new Rectangle(90, 280, 50, 15);
			stripAr.addRegion("anno", recAnno);
			
			
			
			
			
			
			//Splitting the document and making a list out of it
			List<PDDocument> allPages = splitter.split(completeDoc);
			
			boolean verify = false;
			for(PDDocument pdDoc: allPages)
				if(stripper.getText(pdDoc).contains(emp.getCf()))
				{
					newDoc.addPage(pdDoc.getPage(0));
					
					//if "verify" is false it means the current page is the first page
					if(!verify)
					{
						//to print the coordinates of every single character on the screen
						//PDFTextStripper strip = new PrintTextLocations();
						//PrintTextLocations.GetPos(pdDoc);s
						
						stripAr.extractRegions(pdDoc.getPage(0));
						
						//extracting the month and adding it to the "mese" attribute
						if (stripAr.getTextForRegion("mese").trim().length() > 0)
							emp.setMese(stripAr.getTextForRegion("mese"));
						
						try
						{	//extracting the year and adding it to the "mese" attribute
							emp.setAnno(Integer.valueOf(stripAr.getTextForRegion("anno").trim()));
						}
						catch(NumberFormatException ex){}
						
						verify = true;
					}	
				}
			allPages.clear();
			//saving the PDDocument in the PDF 
			newDoc.save(outputFile);
			//closing the documents
			completeDoc.close();
			newDoc.close();
		
	}
	

	
	
	/**
	 * Imports a sqlite database into a List of employees
	 * @param inputPathDB path of the the database .sqlite file to import
	 * @return List of employees with data imported from the given .sqlite file
	 * @throws Exception
	 */
	private List<Employee> DBToList(String inputPathDB) throws Exception{
		Connection con = null;
		Statement stmt = null;
		List<Employee> emps = new LinkedList<Employee>();
		try
		{
			Class.forName("org.sqlite.JDBC");
		    con = DriverManager.getConnection("jdbc:sqlite:"+inputPathDB);
		    con.setAutoCommit(false);
		    System.out.println("Opened database successfully");
		    stmt = con.createStatement();
		  
		    ResultSet rs = stmt.executeQuery( "SELECT * FROM dipendenti;" );
		      while ( rs.next() ) {
		    	 Employee emp = new Employee();
		         emp.setName(rs.getString("nome"));
		         emp.setLastName(rs.getString("cognome"));
		         emp.setCf(rs.getString("cf"));
		         emps.add(emp);
		      }
		      rs.close();
		      stmt.close();
		      con.close();
		      System.out.println("The information from the database has been imported");
		}
		catch(Exception exSQL)
		{
			throw new Exception(exSQL.getMessage(), exSQL);
		}
		
		
		return emps;
	}
	
	/**
	 * Exports a list of employees to a sqlite database
	 * @param emps List of the employees to save in a .sqlite database
	 * @param outputPathDB Path to save the .sqlite database file
	 * @throws ClassNotFoundException 
	 */
	private void EmpsToSqlite(List<Employee> emps, String outputPathDB) throws ClassNotFoundException{
		 	Connection con = null;
		    Statement stmt = null;
		    try {
			      Class.forName("org.sqlite.JDBC");
			      File db = new File(outputPathDB);
			      
			      if(db.exists())
			      	db.delete();
	
			      con = DriverManager.getConnection("jdbc:sqlite:"+db);
			      con.setAutoCommit(false);
			      System.out.println("Opened database successfully");
	
			      stmt = con.createStatement();
				      
				  String sql = "CREATE TABLE BustaPaga" +
						  	"(Nome	TEXT    NOT NULL," + 
				              "Cognome Text    NOT NULL," + 
				              "CF	CHAR(16)	NOT NULL," + 
				              "Mese    TEXT," +
				              "Anno	INTEGER, "+
				             "Cifra	REAL)";
				 stmt.executeUpdate(sql);
				 System.out.println("Database has been created");
		      
			   
			      for(Employee emp: emps)
			      {
			    	 String temp = "INSERT INTO BustaPaga" + " VALUES (" + emp.toStringDB() + ");";
			    	 stmt.executeUpdate(temp);
			      }
			      
			      System.out.println("Database has been updated");
			      
			      stmt.close();
			      con.commit();
			      con.close();
		    }
		    catch(Exception ex) 
		    {
		    	ex.printStackTrace();
		    }
	}
	

}
