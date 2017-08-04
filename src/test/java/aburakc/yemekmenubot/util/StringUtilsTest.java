package aburakc.yemekmenubot.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class StringUtilsTest {


    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void compareStrings() throws Exception {
        String str1 = "temmuz";
        String str2 = "temmu";

        double compareStrings = StringUtils.compareStrings(str2, str1);
        System.out.println(compareStrings);
        Assert.assertTrue(compareStrings<0.3D);

    }

    @Test
    public void parseMessageDate() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
        Calendar cal = Calendar.getInstance();
        LocalDate localDate = LocalDate.now();

        String s1 = "4 haziran";
        Date d1Expect = sdf.parse("0406"+cal.get(Calendar.YEAR));
        Date d1Actual = StringUtils.parseMessageDate(s1);
        Assert.assertEquals(d1Expect,d1Actual);

        String s2 = "yarın";
        Date d2Expect = DateUtils.asDate(localDate.plusDays(1));
        Date d2Actual = StringUtils.parseMessageDate(s2);
        Assert.assertEquals(d2Expect,d2Actual);

    }

}