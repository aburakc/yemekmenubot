package aburakc.yemekmenubot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@PropertySource("classpath:/config.properties")
@Component
public class AppConfiguration {
    @Value("${mongodb.connstr:127.0.0.1}")
    private String MONGO_CONN_STR;// = "mongodb://<username>:<password>@cluster0-shard-00-00-dqvih.mongodb.net:27017,cluster0-shard-00-01-dqvih.mongodb.net:27017,cluster0-shard-00-02-dqvih.mongodb.net:27017/<DATABASE>?ssl=true&replicaSet=Cluster0-shard-0&authSource=admin";

    @Value("${mongodb.databaseName:launchmenu}")
    private String databaseName;

    @Value("${telegram.bottoken}")
    private String botToken;

    @Value("${mongodb.username}")
    private String userName;
    @Value("${mongodb.password}")
    private String password;


    public String getMONGO_CONN_STR() {
        return MONGO_CONN_STR;
    }

    public void setMONGO_CONN_STR(String MONGO_CONN_STR) {
        this.MONGO_CONN_STR = MONGO_CONN_STR;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getBotToken() {
        return botToken;
    }

    public void setBotToken(String botToken) {
        this.botToken = botToken;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
