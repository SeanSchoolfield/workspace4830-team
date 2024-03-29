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
// Fetch data
/**
 * Servlet implementation class demo3
 */
@WebServlet("/GetBookDetails")
public class GetBookDetails extends HttpServlet {
    private static final long serialVersionUID = 1 ;

    String dns = "ec2-18-223-209-72.us-east-2.compute.amazonaws.com";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetBookDetails() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        String sql;
        Connection connection = null;
        Statement statement = null;
        PreparedStatement statement1 = null;
        ResultSet rs = null;
        PreparedStatement preparedStatement = null;
        String keyword = request.getParameter("keyword");
        response.setContentType("text/html");
        int field = 0;

        PrintWriter out = response.getWriter();
        String title = "Fetch Book Details";
        String docType =
            "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";

    
        out.println(docType + //
            "<html>\n" + //
        	"<style>\n" + //
            "body{\n" + //
            "background: rgb(63,152,251);\n"
            + "background: linear-gradient(0deg, rgba(63,152,251,1) 29%, rgba(252,70,223,0.938813025210084) 92%);\n" + //
            "}\n"
            + "table{"
            + "margin-left:auto;"
            + "margin-right:auto;"
            + "</style>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body>\n" + //
            "<h1 align = \"center\">" + title + "</h1>\n");


        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your MySQL JDBC Driver?");
            e.printStackTrace();
            return;
        }

        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + dns + ":3306/bookDATABASE", "Bookfinder", "1234");
        } catch (SQLException e2) {
            // TODO Auto-generated catch block
            System.out.println("Connection Failed!:\n" + e2.getMessage());
        }
        System.out.println("SUCCESS!!!! You made it, take control     your database now!");
        System.out.println("Creating statement...");

        sql = "SELECT * FROM bookTable WHERE Book_Title LIKE ? OR Author LIKE ? OR Genre LIKE ? OR ISBN LIKE ?";
        try {
            statement1 = connection.prepareStatement(sql);
            String wildcard = "%" + keyword + "%";
            statement1.setString(1, wildcard);
            statement1.setString(2, wildcard);
            statement1.setString(3, wildcard);
            statement1.setString(4, wildcard);

            rs = statement1.executeQuery();

            if (!rs.next()) {
            	out.println("<div style='text-align:center; font-size:30px;'>Book not found!</div>");
            } else {
                out.println("<table border=1 width=50% height=30%>");
                out.println("<tr><th>BookTitle</th><th>AuthorName</th><th>Genre</th><th>ISBN</th><th>Summary</th></tr>");

                do {
                    //Retrieve by column name
                    String bookTitle = rs.getString("Book_Title");
                    String author = rs.getString("Author");
                    String genre = rs.getString("Genre");
                    String ISBN = rs.getString("ISBN");
                    String summary = rs.getString("Summary");
                    String publisher = rs.getString("Publisher");
                    String publishedYear = rs.getString("PublishedYear");
                    out.println("<tr><td>" + bookTitle + "</td><td>" + author + "</td><td>" + genre + "</td><td>" + ISBN + "</td><td>" + summary + "</td><td>" + publisher + "</td><td>" + publishedYear + "</td></tr>");
                } while (rs.next());

                out.println("</table>");
            }
            out.println("<br><button onclick=\"document.location='/final-project/Homepage.html'\">Return</button>");
            out.println("</body></html>");

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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
