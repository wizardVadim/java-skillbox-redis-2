import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class RedisTest {

    // Кол-во пользователей
    private static final int NUMBER_USERS = 20;
    private static final int SLEEP = 20;
    private static int CHANCE;

    public static void main(String[] args) {

        RedisStorage storage = new RedisStorage();
        storage.init();
        ArrayList<String> keys;

        for (int i = 0; i < NUMBER_USERS; i++) {
            int userId = i;
            storage.createUser(userId);
            try {
                Thread.sleep(SLEEP);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        while (true) {
            keys = new ArrayList<>();

            Iterable<String> keysIter = storage.getUsers();
            for (String key : keysIter) {
                keys.add(key);
            }

            // Получаем кол-во тех, кто проплатил
            CHANCE = NUMBER_USERS / 10;

            // Ставим на первое место тех, кто заплатил
            for (int i = 0; i < CHANCE; i++) {
                int value = new Random().nextInt(20);
                ArrayList<String> vipUser = new ArrayList<>();
                vipUser.add(keys.get(value));
                keys.remove(value);
                keys.add(0, vipUser.get(0));
            }

            for (String key : keys) {
                System.out.println("USER: " + key);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}

