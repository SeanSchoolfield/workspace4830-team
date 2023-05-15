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
@WebServlet("/InsertBookDetails")
public class InsertBookDetails extends HttpServlet {
    private static final long serialVersionUID = 1;

    String dns = "ec2-18-223-209-72.us-east-2.compute.amazonaws.com";


    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertBookDetails() {
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
        String bookTitle = request.getParameter("booktitle");
        String author = request.getParameter("author");
        String genre = request.getParameter("genre");
        String isbn = request.getParameter("isbn");
        String summary = request.getParameter("summary");
        String publisher = request.getParameter("publisher");
        String publishedYear = request.getParameter("publishedyear");

        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        String title = "Insert Book Details into database";
        String docType =
            "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
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

        sql = "INSERT INTO bookTable (Book_Title, Author, Genre, ISBN, Summary, Publisher, PublishedYear) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
        	 statement1 = connection.prepareStatement(sql);
        	  String theBookTitle = bookTitle;
              String authorName = author;
              String genreType = genre;
              String isbnNum = isbn;
              String bookSummary = summary;
              String Thepublisher = publisher;
              String ThepublishedYear = publishedYear;
            statement1.setString(1, bookTitle);
            statement1.setString(2, author);
            statement1.setString(3, genre);
            statement1.setString(4, isbn);
            statement1.setString(5, summary);
            statement1.setString(6, Thepublisher);
            statement1.setString(7, ThepublishedYear);
        } catch (SQLException e2) {
            e2.printStackTrace();
        }
        try {

            statement1.executeUpdate();
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        out.println("Thank you for registering the book details");
        out.println("</body></html>");
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}
