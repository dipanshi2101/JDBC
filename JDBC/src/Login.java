import java.util.*;

public class Login {

    private String username;
    Scanner sc = new Scanner(System.in);

    public void enterDetails() {

        System.out.println("Enter username: ");
        username = sc.nextLine();

        System.out.println("Enter password: ");
        String password = sc.nextLine();

        db db = new db();
        db.createTable();
        db.executeSQL1(username, password);

        // Close resources

    }

    public void addThoughts() {
        db db = new db();
        System.out.println("Enter your thoughts: ");
        String thoughts = sc.nextLine();

        db.executeSQl2(username, thoughts);

    }

    public void showThoughts() {
        db db = new db();
        db.showThoughts(username);

    }

    public void particularThought() {
        db db = new db();
        System.out.println("Search for your thought through keyword: ");
        String particularThought = sc.nextLine();
        db.showParticularThought(username, particularThought);
    }

    public void delParticularThought() {
        db db = new db();
        System.out.println("Search for your thought through keyword to delete: ");

        String particularThought = sc.nextLine();
        System.out.println("The below record/s are found for deletion ");
        db.showParticularThought(username, particularThought);

        System.out.print("\nPress 1 to delete: ");
        int flag = sc.nextInt();

        if (flag == 1) {
            db.deleteThought(username, particularThought);
        }

        else
            System.out.println("NO RECORD IS DELETED");
    }

    public void delAllThoughts() {
        db db = new db();
        System.out.println("Enter 1 if you want to delete all you records: ");
        int flag = sc.nextInt();

        if (flag == 1) {
            System.out.println("Deleting all thought.. ");
            db.deleteAllThoughts(username);
        }

        else
            System.out.println("No Record Is Deleted!");
    }

    public void updatePassword() {
        db db = new db();
        System.out.print("Are you sure you want to update the password, then Press 1: ");
        int flag = sc.nextInt();

        if (flag == 1) {
            // Consume the newline character left in the buffer
            sc.nextLine();

            System.out.println("Enter the existing username: ");
            String username = sc.nextLine();

            System.out.println("Enter the new password: ");
            String pass = sc.nextLine();
            System.out.println(pass);
            db.updatePassword(username, pass);
        }
    }

    // sc.close();
}
// see your tweets
// read today thoughts
// update profile
// delete profile
// add data in table