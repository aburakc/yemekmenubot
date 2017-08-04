package aburakc.yemekmenubot.util;

import aburakc.yemekmenubot.data.menu.LaunchMenu;
import info.debatty.java.stringsimilarity.NormalizedLevenshtein;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    private static final Locale trLocale = new Locale("tr", "TR");
    private static final SimpleDateFormat longDateFormatter = new SimpleDateFormat("dd MMMMMM", trLocale);
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddMMyyyy");

    public static double compareStrings(String str1, String str2){
        NormalizedLevenshtein l = new NormalizedLevenshtein();
        return l.distance(str1,str2);
    }

    public static Date parseMessageDate(String message) {
        if(message == null || message.length()==0){
            return null;
        }
        boolean isDateSet = false;
        String text = message.toLowerCase(trLocale);
        LocalDate localDate = LocalDate.now();
        //Month

        Pattern p = Pattern.compile("-?\\d+");
        Matcher m = p.matcher(text);
        boolean givenDate = false;
        int dayOfMonth = 0;
        int year = 0;
        while (m.find()) {
            givenDate = true;
            String group = m.group();
            if (group.length() <= 2) {
                dayOfMonth = Integer.parseInt(group);
            } else if (group.length() == 4) {
                year = Integer.parseInt(group);
            }
        }

        if(givenDate){
            text = text.replaceAll("[0-9]", "");

            int month = 0;

            DateFormatSymbols dateFormatSymbols = new DateFormatSymbols(trLocale);
            String[] months = dateFormatSymbols.getMonths();
            int i = 1;
            for (String strMonth : months) {
                double compareStrings = StringUtils.compareStrings(text, strMonth.toLowerCase(trLocale));
                if (compareStrings < 0.3) {
                    month = i;
                    break;
                }
                ++i;
            }

            if(i<13) {

                if (year == 0) {
                    year = localDate.getYear();
                }
                String sMonth = "" + (month < 10 ? "0" + month : month);
                String sDayOfMonth = "" + (dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth);

                try {
                    Date date = simpleDateFormat.parse(sDayOfMonth + sMonth + year);
                    return date;
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

        //Day
        int day = 0;
        if (text.contains("pazartesi") || text.contains("pazartesi")) {
            day = 1;
        } else if (text.contains("salı") || text.contains("sali")) {
            day = 2;
        } else if (text.contains("carsamba") || text.contains("carşamba")) {
            day = 3;
        } else if (text.contains("perşembe") || text.contains("persembe")) {
            day = 4;
        } else if (text.contains("cuma")) {
            day = 5;
        } else if (text.contains("cumartesi") || text.contains("cumartesı")) {
            day = 6;
        } else if (text.contains("pazar")) {
            day = 7;
        }

        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        if (day != 0) {
            int d = day - dayOfWeek.getValue();
            if (d < 0) {
                d += 7;
            }
            localDate = localDate.plusDays(d);
            isDateSet = true;
        } else {

            if (text.contains("dün") || text.contains("dun")) {
                localDate = localDate.minusDays(1);
                isDateSet = true;
            }
            if (text.contains("yarın") || text.contains("yarin")) {
                localDate = localDate.plusDays(1);
                isDateSet = true;
            }
            if (text.contains("bugün") || text.contains("bugun")) {
                isDateSet = true;
            }
        }

        return (isDateSet?DateUtils.asDate(localDate):null);
    }


    public static String formatMenu(LaunchMenu menu){
        StringBuilder sb = new StringBuilder();
        menu.getFormattedDate();
        sb.append(longDateFormatter.format(menu.getDate()));
        sb.append(" öğle yemeği menüsü:\n");

        sb.append("<a href=\""+menu.getImageUrl()+"\">&#8205;</a>");
        String a = menu.getMenu().replace(menu.getSummary(),"<strong>"+menu.getSummary()+"</strong>");
        sb.append(a);
        sb.append("\nAfiyet olsun!");
        return sb.toString();
    }
}
