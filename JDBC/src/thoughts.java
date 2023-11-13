import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class thoughts {
    public void quotes() {
        // Provide the path to your existing file
        int randomFileNumber = getRandomNumber(1, 4);

        String filePath = "C:\\Users\\Dipanshi\\OneDrive\\Desktop\\JDBC\\JDBC\\src\\file" + randomFileNumber + ".txt";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int getRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }
}