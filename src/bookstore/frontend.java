import java.util.Scanner;
import java.sql.*;

    public class frontend {
    public static void main(String[] args)
    {
        while (true)
        {
            // gui stuff
            int selection = 0;
            System.out.println("\nMENU");
            System.out.println("Option 1: approve a book");
            System.out.println("Option 2: XXX");
            System.out.println("Option 3: XXX");
            System.out.println("Option 4: XXX");
            System.out.println("Option 5: XXX");
            System.out.println("Option 6: QUIT");

            Scanner sc = new Scanner(System.in);
            selection = sc.nextInt();
            System.out.printf("You selected option %d\n", selection);
            
            if (selection == 6)
                break;
            else if (selection == 1)
                approveBook();
            else if (selection == 2)
                option2();
            else if (selection == 3)
                option3();
            else if (selection == 4)
                option4();
            else if (selection == 5)
                option5();  
        }
        System.out.println("Exiting...");
        System.exit(0);
    }

    private static void approveBook()
    {
        Connection c = null;
        Statement stmt = null;
        int selection;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cs421", "cs421g02", "Team2pass");
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
                    System.out.println("\tbid: " + bid + "\t\tstatusFlag: " + statusFlag + "\t\ttitle: " + title);
                }
                rs.close();

                System.out.print("Please enter the bid of the book you want to approve (enter 0 to quit): ");
                Scanner sc = new Scanner(System.in);
                selection = sc.nextInt();
                if (selection == 0)
                {
                    stmt.close();
                    c.close();
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
