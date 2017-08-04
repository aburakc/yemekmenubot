package aburakc.yemekmenubot.bot;

import aburakc.yemekmenubot.data.LauncMenuRepository;
import aburakc.yemekmenubot.data.bot.UserInfo;
import aburakc.yemekmenubot.data.UserRepository;
import aburakc.yemekmenubot.data.menu.LaunchMenu;
import aburakc.yemekmenubot.util.DateUtils;
import aburakc.yemekmenubot.util.StringUtils;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Locale;


@Component
public class BotUpdateListener implements UpdatesListener {
    private static final Locale trLocale = new Locale("tr", "TR");
    private static final Logger logger = LoggerFactory.getLogger(BotUpdateListener.class);

    @Autowired
    BotService botService;

    @Autowired
    private LauncMenuRepository launcMenuRepository;

    @Autowired
    private UserRepository userInfoRepository;

    @Override
    public int process(List<Update> list) {

        for (Update update : list) {

            String text = update.message().text();
            Integer messageDate = update.message().date();
            logger.debug("Message Date: " + messageDate.toString());
            Integer userId = update.message().from().id();
            logger.debug("User Id: " + userId.toString());

            Date date = StringUtils.parseMessageDate(text);
            Long chatId = update.message().chat().id();
            if (date != null) {

                List<LaunchMenu> menuList = launcMenuRepository.findByFormattedDate(DateUtils.getFormattedDate(date));
                if (menuList.size() > 0) {
                    LaunchMenu menu = menuList.get(0);
                    String strMenu = StringUtils.formatMenu(menu);
                    SendMessage request = createSendMessageRequest(chatId, strMenu);
                    botService.sendMessage(request);

                } else {
                    String ms = "Üzgünüm. Belirttiğin gün için bir menü kaydı yok.";
                    boolean isWeekend = DateUtils.isWeekEnd(date);
                    if (isWeekend) {
                        ms += " Hafta sonları için menü kaydı olmuyor.";
                    }
                    SendMessage sendMessageRequest = createSendMessageRequest(chatId, ms);
                    botService.sendMessage(sendMessageRequest);
                }
            } else {
                String ms = "Komutu anlayamadım!. Kullanabileceğin ifadeler:'bugün', 'yarın', 'dün' gibi ifadeler olabilir. Ya da bir tarih belirtebilirsin 14 Temmuz gibi.";
                SendMessage sendMessageRequest = createSendMessageRequest(chatId, ms);
                botService.sendMessage(sendMessageRequest);
            }

            List<UserInfo> byChatId = userInfoRepository.findByChatId(chatId);
            if (byChatId == null || byChatId.size() == 0) {
                UserInfo u = new UserInfo();
                u.setChatId(chatId);
                userInfoRepository.save(u);
                logger.debug("Saved New User : " + u.getChatId());
            }
        }

        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    private static SendMessage createSendMessageRequest(Long chatId, String menu) {
        return new SendMessage(chatId,
                menu)
                .parseMode(ParseMode.HTML)
                .disableWebPagePreview(false)
                .disableNotification(true);
    }

    @Scheduled(cron = "0 30 7 * * 1-5") //0 30 8 * * 1-5 - hafta içi her gün 8:30 da
    //0 */1 * * * 6-7 her dakika hafta sonu
    public void sendTodaysMenu() {
        LocalDate localDate = LocalDate.now();
        long formattedDate = DateUtils.getFormattedDate(DateUtils.asDate(localDate));

        List<LaunchMenu> menuList = launcMenuRepository.findByFormattedDate(formattedDate);
        if (menuList == null || menuList.size() == 0) {
            return;
        }
        LaunchMenu todaysMenu = menuList.get(0);
        List<UserInfo> all = userInfoRepository.findAll();
        for (UserInfo userInfo : all) {
            String menu = StringUtils.formatMenu(todaysMenu);
            SendMessage sendMessageRequest = createSendMessageRequest(userInfo.getChatId(), menu);
            botService.sendMessage(sendMessageRequest);
        }
    }

}
