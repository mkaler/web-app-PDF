package code.servlets;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DeleteUser
 */
public class DeleteUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection con = null;
	    Statement stmt = null;
		try
		{
			
		
			String cf = request.getParameter("toDel");
			System.out.println(cf);
			
			File db = new File(getServletContext().getInitParameter("dbInPath"));
			
			if(!db.exists())
				throw new IOException();
		    
	    	 Class.forName("org.sqlite.JDBC");
	    	 con = DriverManager.getConnection("jdbc:sqlite:"+getServletContext().getInitParameter("dbInPath"));
	    	 con.setAutoCommit(false);
	    	 stmt = con.createStatement();
	    	 String sql = "DELETE FROM dipendenti " +
	                   "WHERE cf = " + "'"+cf+"'";
	    	 stmt.executeUpdate(sql);
	    	 con.commit();
	    	 stmt.close();
	    	 request.getRequestDispatcher("/WEB-INF/jsps/suc.jsp").forward(request, response);
	    	 
		}
		catch(Exception ex)
		{
			request.getRequestDispatcher("/WEB-INF/jsps/fail.jsp").forward(request, response);
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
