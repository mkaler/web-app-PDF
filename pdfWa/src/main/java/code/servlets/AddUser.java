package code.servlets;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import code.core.Employee;
/**
 * Servlet implementation class AddUser
 */
public class AddUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Connection con = null;
	    Statement stmt = null;
		try
		{
			
		
			String name = request.getParameter("fname");
			String lName = request.getParameter("lname");
			String cf = request.getParameter("cf");
			
			File db = new File(getServletContext().getInitParameter("dbInPath"));
			
			if(!db.exists())
				throw new IOException();
		    
	    	 Class.forName("org.sqlite.JDBC");
	    	 con = DriverManager.getConnection("jdbc:sqlite:"+getServletContext().getInitParameter("dbInPath"));
	    	 con.setAutoCommit(false);
	    	 stmt = con.createStatement();
	    	 ResultSet rs = stmt.executeQuery( "SELECT * FROM dipendenti;" );
		      while ( rs.next() ) {
		    	 Employee emp = new Employee();
		         emp.setName(rs.getString("nome"));
		         emp.setLastName(rs.getString("cognome"));
		         emp.setCf(rs.getString("cf"));
		         System.out.println(emp.toString());
		      }
	    	 rs.close();
	    	 
	    	 String str = "INSERT INTO dipendenti" + " VALUES ('"+name+"','"+lName+"','"+cf+"')";
	    	 
	    	
	    	
	    	stmt.executeUpdate(str);
	    	con.commit();
	    	
	    	request.getRequestDispatcher("/WEB-INF/jsps/suc.jsp").forward(request, response);
	    	
	    }
	    catch(Exception ex)
	    {
	    	ex.printStackTrace();
	    	request.getRequestDispatcher("/WEB-INF/jsps/fail.jsp").forward(request, response);
	    }
		finally
		{
			try
			{	if(stmt != null)
					stmt.close();
				if(con != null)
				stmt.close();
			}
			catch(Exception ex){}
				
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
