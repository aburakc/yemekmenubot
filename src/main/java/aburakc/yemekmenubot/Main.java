package aburakc.yemekmenubot;

import com.google.gson.Gson;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Property;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Burak on 24/07/2017.
 */
public class Main {

    public static void main(String[] args) {

        /*new Main().telegramDeneme();

        try {
            new Main().calendarRead();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserException e) {
            e.printStackTrace();
        }
        */

        restDeneme();
    }



    public void telegramDeneme(){
        TelegramBot bot = TelegramBotAdapter.build("");

        GetUpdates getUpdates = new GetUpdates().limit(1000).offset(95197282).timeout(0);

        GetUpdatesResponse updatesResponse = bot.execute(getUpdates);
        List<Update> updates = updatesResponse.updates();
        for(Update update : updates){
            Long chatId = update.message().chat().id();
            SendMessage message = new SendMessage(chatId,"Merhaba") .parseMode(ParseMode.HTML)
                    .disableWebPagePreview(true)
                    .disableNotification(true);
            System.out.println(update.updateId());
            bot.execute(message);
        }
    }


    public void calendarRead() throws IOException, ParserException {

        InputStream is=null;
        is = new URL("http://tbtyemek.appspot.com/menu.ics").openStream();

        CalendarBuilder builder = new CalendarBuilder();

        Calendar calendar = builder.build(is);

        for (Iterator i = calendar.getComponents().iterator(); i.hasNext();) {
            Component component = (Component) i.next();
            System.out.println("Component [" + component.getName() + "]");

            for (Iterator j = component.getProperties().iterator(); j.hasNext();) {
                Property property = (Property) j.next();
                System.out.println("Property [" + property.getName() + ", " + property.getValue() + "]");
            }
        }

    }

    public static void restDeneme(){
        final String uri = "https://api.cognitive.microsoft.com/bing/v5.0/images/search?q=et+döner&mkt=tr-tr&cc=TR&safeSearch=Strict&count=1";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();

        headers.set("Ocp-Apim-Subscription-Key","7a86b403bdb04549946663b11c872beb");
        headers.set("User-Agent","Mozilla/5.0 (compatible; MSIE 10.0; Windows Phone 8.0; Trident/6.0; IEMobile/10.0; ARM; Touch; NOKIA; Lumia 822)");
        headers.set("Host","api.cognitive.microsoft.com");
        headers.set("Accept-Language","tr-tr");

        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

        ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
        String strContentUrl = "\"contentUrl\":";
        String strhostPageUrl = "\"hostPageUrl\"";
        int i = result.getBody().indexOf(strContentUrl);

        int j = result.getBody().indexOf(strhostPageUrl);

        System.out.println(result.getBody().substring(i+strContentUrl.length(),j));

        Map<String,Object> map = new Gson().fromJson(result.getBody(),Map.class);

        List value = (List) map.get("value");

        for(Object o : value){
            Map oMap = (Map)o;
            if(oMap.get("thumbnailUrl") != null){
                System.out.println(oMap.get("thumbnailUrl"));
                break;
            }
        }

    }
}
