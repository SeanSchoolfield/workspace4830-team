import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Servlet implementation class demo3
 */
@WebServlet("/MyServletDB")
public class MyServletDB extends HttpServlet {
private static final long serialVersionUID = 1L;
	static String url = "jdbc:mysql://ec2-18-223-209-72.us-east-2.compute.amazonaws.com:3306/bookDATABASE";
    static String user = "Bookfinder";
    static String password = "1234";
    Connection connection = null;
    Statement statement = null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyServletDB() {
        super();
        // TODO Auto-generated constructor stub
    }
/**
 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse 
response)
 */
protected void doGet(HttpServletRequest request, HttpServletResponse 
response) throws ServletException, IOException {
// TODO Auto-generated method stub
 PrintWriter out = response.getWriter();
 try {
        Class.forName("com.mysql.jdbc.Driver");
    } catch (ClassNotFoundException e) {
        System.out.println("Where is your MySQL JDBC Driver?");
        e.printStackTrace();
        return;
    }
  // Provide your username and password in place of admin1 and root.
    try {
        connection = DriverManager.getConnection(url, user, password);
    } catch (SQLException e) {
        System.out.println("Connection Failed!:\n" + e.getMessage());
    }
    if (connection != null) {
        System.out.println("SUCCESS!!!! You made it, take control your database now!");
        System.out.println("Creating statement...");
        try {
statement = connection.createStatement();
} catch (SQLException e3) {
// TODO Auto-generated catch block
e3.printStackTrace();
}
        String sql;
        sql = "SELECT * FROM bookTable";
        PreparedStatement prepstate = null;
        ResultSet rs = null;
		try {
			prepstate = connection.prepareStatement(sql);
		} catch (SQLException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
        
try {
rs = prepstate.executeQuery(sql);
} catch (SQLException e2) {
// TODO Auto-generated catch block
e2.printStackTrace();
}
        //STEP 5: Extract data from result set
        try {
while (rs.next()) {
    //Retrieve by column name
String bookTitle = rs.getString("Book_Title");
String author = rs.getString("Author");
String genre = rs.getString("Genre");
String ISBN = rs.getString("ISBN");
String summary = rs.getString("Summary");
    
//Display values
out.println("Book_Title: " + bookTitle + ", ");
out.println("Author: " + author + ", ");
out.println("Genre: " + genre + ", ");
out.println("ISBN: " + ISBN + ", ");
out.println("Summary: " + summary);
}
} catch (SQLException e1) {
// TODO Auto-generated catch block
e1.printStackTrace();
}
        //STEP 6: Clean-up environment
        try {
rs.close();
} catch (SQLException e) {
// TODO Auto-generated catch block
e.printStackTrace();
}
        try {
statement.close();
} catch (SQLException e) {
// TODO Auto-generated catch block
e.printStackTrace();
}
        try {
connection.close();
} catch (SQLException e) {
// TODO Auto-generated catch block
e.printStackTrace();
}
    } 
   
    else {
        System.out.println("FAILURE! Failed to make connection!");
    }
}
/**
 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse 
response)
 */
protected void doPost(HttpServletRequest request, HttpServletResponse 
response) throws ServletException, IOException {
// TODO Auto-generated method stub
doGet(request, response);
}
}
