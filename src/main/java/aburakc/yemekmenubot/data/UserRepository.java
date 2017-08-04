package aburakc.yemekmenubot.data;

import aburakc.yemekmenubot.data.bot.UserInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by Burak on 29/07/2017.
 */
public interface UserRepository extends MongoRepository<UserInfo, String> {
    public List<UserInfo> findByChatId(long chatId);
}
