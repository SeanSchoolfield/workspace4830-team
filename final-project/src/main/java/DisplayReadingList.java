import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DisplayReadingList")
public class DisplayReadingList extends HttpServlet {
    private static final long serialVersionUID = 1L;

    String dns = "ec2-18-223-209-72.us-east-2.compute.amazonaws.com";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String title = "Reading List";
        String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";

        out.println(docType + //
            "<html>\n" + //
        	"<style>\n" + //
            "body{\n" + //
            "background: rgb(63,152,251);\n"
            + "background: linear-gradient(0deg, rgba(63,152,251,1) 29%, rgba(252,70,223,0.938813025210084) 92%);\n" + //
            "}\n</style>\n" + //
            "<head><title>" + title + "</title></head>\n" + //
            "<body>\n" + //
            "<h1 align = \"center\">" + title + "</h1>\n");


        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your MySQL JDBC Driver?");
            e.printStackTrace();
            return;
        }

        try {
            conn = DriverManager.getConnection("jdbc:mysql://" + dns + ":3306/bookDATABASE", "Bookfinder", "1234");
        } catch (SQLException e2) {
            System.out.println("Connection Failed!:\n" + e2.getMessage());
            return;
        }

        String sql = "SELECT * FROM readingList";

        try {
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            out.println("<table border=1 width=50% height=30%>");
            out.println("<tr><th>BookTitle</th><th>AuthorName</th><th>Genre</th><th>ISBN</th><th>Summary</th><th>Publisher</th><th>PublishedYear</th><th>Rate</th><th>Reviews</th></tr>");
            while (rs.next()) {
                // Retrieve by column name
                String bookTitle = rs.getString("Book_Title");
                String author = rs.getString("Author");
                String genre = rs.getString("Genre");
                String ISBN = rs.getString("ISBN");
                String summary = rs.getString("Summary");
                String publisher = rs.getString("Publisher");
                String publishedYear = rs.getString("PublishedYear");
                String rate1 = rs.getString("Rate");
                String reviews = rs.getString("Reviews");
                out.println("<tr><td>" + bookTitle + "</td><td>" + author + "</td><td>" + genre + "</td><td>" + ISBN + "</td><td>" + summary + "</td><td>" + publisher + "</td><td>" + publishedYear + "</td><td>" + rate1 + "</td><td>" + reviews + "</td></tr>");
            }
            out.println("</table></body></html>");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.println("SQL Exception: " + e.getMessage());
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }
}
