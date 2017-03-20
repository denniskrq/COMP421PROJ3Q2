import java.util.Scanner;
import java.sql.*;

public class frontend {
    public static void main(String[] args) {
        while (true) {
            // gui stuff
            int selection = 0;
            System.out.println("\nMENU");
            System.out.println("Option 1: manage book approval");
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
                manageBookStatus();
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

    private static void manageBookStatus()
    {
        Scanner scan = new Scanner(System.in);
        int selection;
        
        while(true)
        {
            try {
                Class.forName("org.postgresql.Driver");
                Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cs421", "cs421g02", "Team2pass");
                System.out.println("Opened database successfully");
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
                    System.out.println("Opened database successfully");
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
                    System.exit(0);
                }
            }
        }   
    }

    private static void option2() {

    }

    private static void option3() {

    }

    private static void option4() {

    }

    private static void option5() {

    }
}
