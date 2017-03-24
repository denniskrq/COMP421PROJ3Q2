
import java.util.Scanner;
import java.sql.*;

public class frontend {
    public static void main(String[] args) {
        while (true) {
            // gui stuff
            int selection = 0;
            System.out.println("\nMENU");
            System.out.println("Option 1: manage book approval");
            System.out.println("Option 2: add new admin");
            System.out.println("Option 3: review a book");
            System.out.println("Option 4: manage book categories");
            System.out.println("Option 5: remove all reviews for a book");
            System.out.println("Option 6: QUIT");

            Scanner sc = new Scanner(System.in);
            selection = sc.nextInt();
            System.out.printf("You selected option %d\n", selection);

            if (selection == 6)
                break;
            else if (selection == 1)
                manageBookStatus();
            else if (selection == 2)
                addAdmin();
            else if (selection == 3)
                createNewReview();
            else if (selection == 4)
                manageCategories();
            else if (selection == 5)
                reviewRemoval();
            else
                System.out.println("Invalid selection. Please try again.");
        }
        System.out.println("Exiting...");
        System.exit(0);
    }

    private static void manageBookStatus()
    {
        Scanner scan = new Scanner(System.in);
        int selection;
        
        while(true)
        {
            try {
                Class.forName("org.postgresql.Driver");
                Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cs421", "cs421g02", "Team2pass");
                System.out.println("Opened database successfully\n");
                Statement stmt = c.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM BOOK;");
                while (rs.next())
                {
                    int bid = rs.getInt("bid");
                    boolean statusFlag = rs.getBoolean("statusflag");
                    String title = rs.getString("title");
                    System.out.println("bid: " + bid + "\tstatusFlag: " + statusFlag + "\ttitle: " + title);
                }
                rs.close();
                stmt.close();
                c.close();
            }
            catch(Exception e)
            {
                System.err.println(e.getClass().getName()+": " + e.getMessage());
                System.exit(0);
            }
            System.out.print("enter bid of book whose status will be changed (enter 0 to quit): ");
            selection = scan.nextInt();
            if (selection == 0)
                return;
    
            else
            {
                try
                {
                    Class.forName("org.postgresql.Driver");
                    Connection c2 = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cs421", "cs421g02", "Team2pass");
                    System.out.println("Opened database successfully\n");
                    Statement stmt2 = c2.createStatement();
                    ResultSet rs2 = stmt2.executeQuery("select statusflag from book where bid=" + selection + ";");
                    while (rs2.next())
                    {
                        boolean statusFlag2 = rs2.getBoolean("statusflag");
                        boolean newStatus = !statusFlag2;
                        Statement stmt3 = c2.createStatement();
                        stmt3.executeUpdate("UPDATE BOOK SET STATUSFLAG =" + newStatus + " WHERE bid=" + selection + ";");
                        if (newStatus)
                        {
                            stmt3.executeUpdate("INSERT INTO bookapproval (email, bid) VALUES ('admin1@gmail.com', " + selection + ");");
                            System.out.println("Inserted new entry in bookApproval.");
                        }
                            
                        else
                        {
                            stmt3.executeUpdate("DELETE FROM bookapproval WHERE bid=" + selection + ";");
                            System.out.println("Removed entry from bookApproval.");
                        }
                        stmt3.close();
                    }
                    rs2.close();
                    stmt2.close();
                    c2.close();
                }
                catch(Exception e)
                {
                    System.err.println(e.getClass().getName()+": " + e.getMessage());
                    System.out.println("Operation failed!");
                }
            }
        }   
    }

    private static void addAdmin() 
    {
        Scanner scan = new Scanner(System.in);
        Statement stmt = null;
        Connection c = null;

        System.out.print("Please enter the new admin's email address: ");
        String email = scan.next();
        System.out.println("Please enter the admin's date of birth (YYYY-MM-DD): ");
        String birthday = scan.next();
        System.out.println("Please enter the admin's phone number (max 10 characters): ");
        String phoneNumber = scan.next();
        System.out.println("Please enter the admin's name (max 20 characters): ");
        String name = scan.next();
        System.out.println("Please enter the admin's desired password (max 50 characters): ");
        String password = scan.next();
        try
        {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cs421", "cs421g02", "Team2pass");
            System.out.println("Opened database successfully\n");
            stmt = c.createStatement();
            stmt.executeUpdate("INSERT INTO Admin (email, birthday, phoneNumber, name, password) VALUES ('"+email+"','"+birthday+"','"+phoneNumber+"','"+name+"','"+password+"');");
            stmt.close();
            c.close();
        }
        catch(Exception e)
        {
            System.err.println(e.getClass().getName()+": " + e.getMessage());
            System.out.println("Operation failed!");
        }
        System.out.println("Admin inserted succesfully!");
    }

    private static void createNewReview() {
        Scanner scan = new Scanner(System.in).useDelimiter("\n");
        boolean isValidPassword = false;
        
        try {
            Class.forName("org.postgresql.Driver");
            Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cs421", "cs421g02", "Team2pass");
            System.out.println("Opened database successfully\n");
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM BOOK;");
            while (rs.next())
            {
                int bid = rs.getInt("bid");
                String title = rs.getString("title");
                System.out.println("bid: " + bid + "\ttitle: " + title);
            }
            rs.close();
            stmt.close();
            c.close();
        }
        catch(Exception e)
        {
            System.err.println(e.getClass().getName()+": " + e.getMessage());
            System.exit(0);
        }
          
        System.out.print("Please enter the bid of the book you'd like to review: ");
        int bid = scan.nextInt();
        System.out.println("Please enter your email address: ");
        String email = scan.next();
        System.out.println("Please enter your password ");
        String password = scan.next();
        System.out.println("Please enter your rating (on five): ");
        int rating = scan.nextInt();
        System.out.println("Please enter description : ");
        String description = scan.next();
        
        try
        {
            Class.forName("org.postgresql.Driver");
            Connection c2 = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cs421", "cs421g02", "Team2pass");
            System.out.println("Opened database successfully\n");
            Statement stmt2 = c2.createStatement();
            ResultSet rs2 = stmt2.executeQuery("SELECT email FROM customer WHERE password=" + "'" + password + "' ;");
            while(rs2.next())
            {
                if(!rs2.getString("email").equals(email))
                {
                    System.out.println("Incorrect password");
                }
                else
                {
                    isValidPassword = true;
                }
            }
            rs2.close();
            stmt2.close();
            c2.close();
        }
        catch(Exception e)
        {
            System.err.println(e.getClass().getName()+": " + e.getMessage());
            System.out.println("Operation failed!");
        }
        
        if(isValidPassword)
        {
            try
            {
                Class.forName("org.postgresql.Driver");
                Connection c3 = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cs421", "cs421g02", "Team2pass");
                System.out.println("Opened database successfully\n");
                Statement stmt3 = c3.createStatement();
                stmt3.executeUpdate("INSERT INTO reviews (rating, description, bid, email) VALUES ('"+rating+"','"+description+"','"+bid+"','"+email+"');");
                stmt3.close();
                c3.close();

                System.out.println("Review added succesfully!");
            }
            catch(Exception e)
            {
                System.err.println(e.getClass().getName()+": " + e.getMessage());
                System.out.println("Operation failed!");
            }

            }
        }


    private static void manageCategories() {
        Scanner scan = new Scanner(System.in);
        
        try {
            Class.forName("org.postgresql.Driver");
            Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cs421", "cs421g02", "Team2pass");
            System.out.println("Opened database successfully\n");
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM BOOK;");
            while (rs.next())
            {
                int bid = rs.getInt("bid");
                String title = rs.getString("title");
                System.out.println("bid: " + bid + "\ttitle: " + title);
            }
            rs.close();
            stmt.close();
            c.close();
        }
        catch(Exception e)
        {
            System.err.println(e.getClass().getName()+": " + e.getMessage());
            System.exit(0);
        }
          
        System.out.print("Please enter the bid of the book for which you'd like to update the categories: ");
        int bid = scan.nextInt();

        try {
            Class.forName("org.postgresql.Driver");
            Connection c2 = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cs421", "cs421g02", "Team2pass");
            System.out.println("Opened database successfully\n");
            Statement stmt2 = c2.createStatement();
            ResultSet rs2 = stmt2.executeQuery("SELECT name FROM categories WHERE cid IN (SELECT cid FROM bookcategory WHERE bid = " + bid + ");");
            System.out.print("This book is currently in the following categories: ");
            while (rs2.next())
            {
                String name = rs2.getString("name");
                System.out.print("'" + name + "' ");
            }
            rs2.close();
            stmt2.close();
            c2.close();
        }
        catch(Exception e)
        {
            System.err.println(e.getClass().getName()+": " + e.getMessage());
            System.exit(0);
        }


        System.out.print("\n \nEnter 1 to add a category, 2 to delete a category, 3 to leave it unchanged: ");
        int option = scan.nextInt();

        if(option == 1 || option == 2)
        {

            System.out.print("\nEnter 1 to see category list, or 0 to proceed :");
            int list = scan.nextInt();
            if(list == 1){
                try 
                {
                    Class.forName("org.postgresql.Driver");
                    Connection c3 = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cs421", "cs421g02", "Team2pass");
                    System.out.println("Opened database successfully\n");
                    Statement stmt3 = c3.createStatement();
                    ResultSet rs3 = stmt3.executeQuery("SELECT * FROM Categories;");
                    while (rs3.next())
                    {
                        int cid = rs3.getInt("cid");
                        String name = rs3.getString("name");
                        System.out.println("cid: " + cid + "\tname: " + name);
                    }
                    rs3.close();
                    stmt3.close();
                    c3.close();
                }
                catch(Exception e)
                {
                    System.err.println(e.getClass().getName()+": " + e.getMessage());
                    System.exit(0);
                }
            }
          
        }

        if(option == 1)
        {
            int cid = 1;
            while(cid!=0)
            {
                System.out.print(" \nEnter category number you'd like to add (press 0 to exit): ");
                cid = scan.nextInt();
                if(cid!=0){
                    try
                    {
                        Class.forName("org.postgresql.Driver");
                        Connection c4 = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cs421", "cs421g02", "Team2pass");
                        System.out.println("Opened database successfully\n");
                        Statement stmt4 = c4.createStatement();
                        stmt4.executeUpdate("INSERT INTO bookcategory (cid, bid) VALUES ('" + cid + "','" + bid + "');");
                      
                        stmt4.close();
                        c4.close();

                        System.out.println("Category added successfully\n!");
                    }
                    catch(Exception e)
                    {
                        System.err.println(e.getClass().getName()+": " + e.getMessage());
                        System.out.println("Operation failed!");
                    }
                }
            }       
        }
        
        
        else if(option == 2)
        {
            System.out.print(" \nWhich category would you like to delete?");
            int cidDelete = scan.nextInt();
            try
            {
                Class.forName("org.postgresql.Driver");
                Connection c5 = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cs421", "cs421g02", "Team2pass");
                System.out.println("Opened database successfully\n");
                Statement stmt5 = c5.createStatement();
                stmt5.executeUpdate("DELETE FROM bookcategory WHERE cid = "+ cidDelete +" AND bid = " + bid +";");
                stmt5.close();
                c5.close();

                System.out.println("SUCCESS!");
            }
            catch(Exception e)
            {
                System.err.println(e.getClass().getName()+": " + e.getMessage());
                System.out.println("Operation failed!");
            }

        }
    }

    private static void reviewRemoval() {
        Scanner scan = new Scanner(System.in);

        try {
                Class.forName("org.postgresql.Driver");
                Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cs421", "cs421g02", "Team2pass");
                System.out.println("Opened database successfully\n");
                Statement stmt = c.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM BOOK;");
                while (rs.next())
                {
                    int bid = rs.getInt("bid");
                    boolean statusFlag = rs.getBoolean("statusflag");
                    String title = rs.getString("title");
                    System.out.println("bid: " + bid + "\ttitle: " + title);
                }
                rs.close();
                stmt.close();
                c.close();
            }
            catch(Exception e)
            {
                System.err.println(e.getClass().getName()+": " + e.getMessage());
                System.exit(0);
            }

        System.out.print("Select the book for which you'd like to remove all reviews:");
        int bid = scan.nextInt();

        System.out.println("This book has the following reviews: ");
        try {
            Class.forName("org.postgresql.Driver");
            Connection c2 = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cs421", "cs421g02", "Team2pass");
            System.out.println("Opened database successfully\n");
            Statement stmt2 = c2.createStatement();
            ResultSet rs2 = stmt2.executeQuery("SELECT * FROM reviews WHERE bid=" + bid + ";");
            while (rs2.next())
            {
                int rid = rs2.getInt("rid");
                int rating = rs2.getInt("rating");
                String description = rs2.getString("description");
                String author = rs2.getString("email");
                System.out.println("\nrid:" + rid + "\tauthor:" + author + "\trating: " + rating + "\ndescription: " + description);
            }
            rs2.close();
            stmt2.close();
            c2.close();
        }
        catch(Exception e)
        {
            System.err.println(e.getClass().getName()+": " + e.getMessage());
            System.exit(0);
        }

        System.out.println("Press 0 to delete all, or enter the id for the review you'd like to remove");
        int rid = scan.nextInt();

        try {
            Class.forName("org.postgresql.Driver");
            Connection c3 = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cs421", "cs421g02", "Team2pass");
            System.out.println("Opened database successfully\n");
            Statement stmt3 = c3.createStatement();
            
            if(rid==0)
                stmt3.executeUpdate("DELETE FROM reviews WHERE bid=" + bid + ";");

            else
                stmt3.executeUpdate("DELETE FROM reviews WHERE rid=" + rid + " AND bid=" + bid + ";");

            stmt3.close();
            c3.close();

            System.out.println("SUCCESS!");
        }
        catch(Exception e)
        {
            System.err.println(e.getClass().getName()+": " + e.getMessage());
            System.exit(0);
        }
          

    }
}
