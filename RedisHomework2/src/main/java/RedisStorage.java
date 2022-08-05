import org.redisson.Redisson;
import org.redisson.api.RKeys;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.redisson.client.RedisConnectionException;
import org.redisson.config.Config;

import java.util.Date;
import java.util.List;

import static java.lang.System.out;

public class RedisStorage {

    // Объект для работы с Redis
        private RedissonClient client;

    // Объект для работы с ключами
    private RKeys keys;

    // Объект для работы с Sorted Set
    private RScoredSortedSet<String> onlineUsers;

    private final static String KEY = "ONLINE_USERS";

    void init() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");
        try {
            client = Redisson.create(config);
        } catch (RedisConnectionException Exc) {
            out.println("Failed to connect to Redis");
            out.println(Exc.getMessage());
        }
        keys = client.getKeys();
        onlineUsers = client.getScoredSortedSet(KEY);
        keys.delete(KEY);
    }

    private double getTs() {
        return new Date().getTime() / 1000;
    }

    public void createUser(int userId) {
        onlineUsers.add(getTs(), String.valueOf(userId));
    }

    public Iterable<String> getUsers() {
        return client.getScoredSortedSet(KEY);
    }
}
