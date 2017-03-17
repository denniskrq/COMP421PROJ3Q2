package bookstore;

import java.util.Scanner;

/**
 * Created by Dennis on 2017-03-17.
 */
public class frontend {

    public static void main(String[] args)
    {
        int selection = 0;
        System.out.println("MENU");
        System.out.println("Option 1: XXX");
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
                    option1();
                    break;
                case 2:
                    option2();
                    break;
                case 3:
                    option3();
                    break;
                case 4:
                    option4();
                    break;
                case 5:
                    option5();
                    break;
                default:
                    System.out.println("Invalid selection, try again...");
                    continue;
            }
        }
        System.out.println("Exiting...");
    }

    private static void option1()
    {

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
