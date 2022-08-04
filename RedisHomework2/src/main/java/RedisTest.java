import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class RedisTest {

    // Кол-во пользователей
    private static final int NUMBER_USERS = 20;
    private static final int SLEEP = 20;

    public static void main(String[] args) {

        RedisStorage storage = new RedisStorage();
        storage.init();
        ArrayList<Integer> users = new ArrayList<>();

        for (int i = 0; i < NUMBER_USERS; i++) {
            int userId = new Random().nextInt(999);
            storage.createUser(userId);
            users.add(userId);
            try {
                Thread.sleep(SLEEP);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        for (Integer user : users) {
            System.out.println(String.valueOf(storage.getScores(user) * 1000));
        }

        while (true) {

        }
    }
}

