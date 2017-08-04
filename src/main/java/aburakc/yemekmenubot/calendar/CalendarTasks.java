package aburakc.yemekmenubot.calendar;

import aburakc.yemekmenubot.data.LauncMenuRepository;
import aburakc.yemekmenubot.data.menu.LaunchMenu;
import aburakc.yemekmenubot.util.ImageSearch;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Property;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

@Component
public class CalendarTasks {

    @Value("${menu.calendarUrl}")
    private static String calendarUrl;

    @Autowired
    LauncMenuRepository launcMenuRepository;

    @Scheduled(fixedRate = 1000*60*60)
    public void getCalendarItems(){
        try {
            calendarRead();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserException e) {
            e.printStackTrace();
        }

    }

    public void calendarRead() throws IOException, ParserException {

        InputStream is=null;
        is = new URL(calendarUrl).openStream();

        CalendarBuilder builder = new CalendarBuilder();
        Calendar calendar = builder.build(is);

        for (Iterator i = calendar.getComponents().iterator(); i.hasNext();) {
            net.fortuna.ical4j.model.Component component = (net.fortuna.ical4j.model.Component) i.next();
            LaunchMenu menu = new LaunchMenu();

            Property dtstart = component.getProperty("DTSTART");
            Property summary = component.getProperty("SUMMARY");
            Property uid = component.getProperty("UID");
            Property desc = component.getProperty("DESCRIPTION");

            menu.setFormattedDate(Long.parseLong(dtstart.getValue()));
            menu.setSummary(summary.getValue());
            menu.setUid(uid.getValue());
            menu.setMenu(desc.getValue());

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            try {
                menu.setDate(simpleDateFormat.parse(dtstart.getValue()));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            List<LaunchMenu> menuList = launcMenuRepository.findByFormattedDate(menu.getFormattedDate());
            if(menuList == null || menuList.size() == 0){
                String imageAddress = ImageSearch.getImageAddress(menu.getSummary());
                menu.setImageUrl(imageAddress);
                launcMenuRepository.save(menu);
            }else {
                for(LaunchMenu l : menuList){
                    if(l.getImageUrl() == null){
                        String imageAddress = ImageSearch.getImageAddress(menu.getSummary());
                        menu.setImageUrl(imageAddress);
                        launcMenuRepository.delete(l.getId());
                        launcMenuRepository.save(menu);
                    }else if(!menu.getMenu().equals(l.getMenu())){
                        launcMenuRepository.delete(l.getId());
                        launcMenuRepository.save(menu);
                    }

                }
            }
        }
    }

}
