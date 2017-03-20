//package bookstore;

import java.util.Scanner;
import java.sql.*;


// javac -cp ./pos*.jar frontend.java && java -cp .:./pos*.jar frontend;

/**
 * Created by Dennis on 2017-03-17.
 */
public class frontend {
    public static void main(String[] args)
    {

        // gui stuff
        int selection = 0;
        System.out.println("MENU");
        System.out.println("Option 1: approve a book");
        System.out.println("Option 2: XXX");
        System.out.println("Option 3: XXX");
        System.out.println("Option 4: XXX");
        System.out.println("Option 5: XXX");
        System.out.println("Option 6: QUIT");
        while (selection != 6)
        {
            Scanner sc = new Scanner(System.in);
            selection = sc.nextInt();
            System.out.printf("You selected option %d\n", selection);
            switch (selection)
            {
                case 1:
                    approveBook();
                    continue;
                case 2:
                    option2();
                    continue;
                case 3:
                    option3();
                    continue;
                case 4:
                    option4();
                    continue;
                case 5:
                    option5();
                    continue;
                default:
                    System.out.println("Invalid selection, try again...");
            }
        }

        System.out.println("Exiting...");
    }

    private static void approveBook()
    {
        Connection c = null;
        Statement stmt = null;
        int selection;
        try {
            System.out.println("got here 1");
            Class.forName("org.postgresql.Driver");
            System.out.println("got here 2");
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cs421", "cs421g02", "Team2pass");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            while(true)
            {
                stmt = c.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM BOOK;");
                while (rs.next())
                {
                    int bid = rs.getInt("bid");
                    boolean statusFlag = rs.getBoolean("statusflag");
                    String title = rs.getString("title");
                    System.out.println("bid: " + bid + "\tstatusFlag: " + statusFlag + "\ttitle: " + title);
                }
                rs.close();

                System.out.print("Please enter the bid of the book you want to approve (enter 0 to quit): ");
                Scanner sc = new Scanner(System.in);
                selection = sc.nextInt();
                if (selection == 0)
                {
                    c.close();
                    stmt.close();
                    return;
                }
                stmt.executeUpdate("UPDATE BOOK SET STATUSFLAG = true WHERE bid=" + selection + ";");
                stmt.close();
            }
        }
        catch(Exception e)
        {
            System.err.println(e.getClass().getName()+": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Command executed succesffully!");

    }

    private static void option2()
    {

    }

    private static void option3()
    {

    }

    private static void option4()
    {

    }

    private static void option5()
    {

    }
}
