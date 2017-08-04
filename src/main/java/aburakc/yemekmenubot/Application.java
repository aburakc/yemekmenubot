package aburakc.yemekmenubot;

import aburakc.yemekmenubot.bot.BotService;
import aburakc.yemekmenubot.bot.BotUpdateListener;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableMongoRepositories
@PropertySource("classpath:/config.properties")
public class Application extends AbstractMongoConfiguration implements CommandLineRunner {

    @Autowired(required = true)
    private AppConfiguration appConfiguration;

    @Autowired(required = true)
    private BotService botService;

    @Autowired(required = true)
    private BotUpdateListener botUpdateListener;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class);
    }

    @Override
    protected String getDatabaseName() {
        return appConfiguration.getDatabaseName();
    }

    @Override
    public Mongo mongo() throws Exception {

        String userName = appConfiguration.getUserName();
        String password = appConfiguration.getPassword();
        String mongoConnStr = appConfiguration.getMONGO_CONN_STR();
        MongoClientURI mongoClientURI = new MongoClientURI(mongoConnStr.replace("<USERNAME>", userName).replace("<PASSWORD>", password));
        MongoClient mongoClient = new MongoClient(mongoClientURI);

        return mongoClient;
    }


    @Override
    public void run(String... strings) throws Exception {
        initBot();
    }

    private void initBot() {
        TelegramBot telegramBot = TelegramBotAdapter.build(appConfiguration.getBotToken());
        telegramBot.setUpdatesListener(botUpdateListener);
        botService.setTelegramBot(telegramBot);
    }
}
