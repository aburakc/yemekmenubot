package aburakc.yemekmenubot.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class BotService {

    private static final Logger logger = LoggerFactory.getLogger(BotService.class);

    private TelegramBot telegramBot;

    public SendResponse sendMessage(SendMessage sendMessageRequest) {
        SendResponse sendResponse = getTelegramBot().execute(sendMessageRequest);
        logger.debug("SendResponseMessage :" + sendResponse.toString());
        return sendResponse;
    }

    public TelegramBot getTelegramBot() {
        return telegramBot;
    }
        public void setTelegramBot(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }
}
