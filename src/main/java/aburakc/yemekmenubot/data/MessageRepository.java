package aburakc.yemekmenubot.data;

import aburakc.yemekmenubot.data.bot.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by Burak on 30/07/2017.
 */
public interface MessageRepository extends MongoRepository<Message, String> {

}
