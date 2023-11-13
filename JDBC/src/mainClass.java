import java.util.*;

public class mainClass {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Login log = new Login();
        System.out.println("WELCOME, Please Enter Your Credentials ");
        log.enterDetails();

        System.out.print("Choose from the Menu, what you want to perform- \n");

        int flag = 1;

        do {
            System.out.println(
                    "Press 1 : Add Thought \nPress 2 : Show All Thoughts \nPress 3 : Search Thoughts \nPress 4 : Delete Particular Record \nPress 5 : Delete All Records");
            System.out.println("Press 6 : Update Password");
            int num = sc.nextInt();
            switch (num) {
                case 1:
                    log.addThoughts();
                    break;

                case 2:
                    log.showThoughts();
                    break;

                case 3:
                    log.particularThought();
                    break;

                case 4:
                    log.delParticularThought();
                    break;

                case 5:
                    log.delAllThoughts();
                    break;

                case 6:
                    log.updatePassword();
                    break;

                default:
                    System.out.println("Wrong Choice");

            }
            System.out.println("Want to explore more? then Press 1 otherwise Press 2 : ");
            flag = sc.nextInt();
        } while (flag == 1);

        System.out.println("\nThought Of The Day!");
        thoughts quotes = new thoughts();
        quotes.quotes();
        System.out.println('\n');

        sc.close();
    }
}
