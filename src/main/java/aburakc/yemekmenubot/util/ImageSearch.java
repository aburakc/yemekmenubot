package aburakc.yemekmenubot.util;


import com.google.gson.Gson;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by Burak on 29/07/2017.
 */
public class ImageSearch {
    public static String getImageAddress(String searchString){
        String retVal = null;

        searchString = searchString.trim().replace(" ", "+");
        searchString+="+menu";

        final String uri = "https://api.cognitive.microsoft.com/bing/v5.0/images/search?q="+searchString+"&mkt=tr-tr&cc=TR&safeSearch=Strict&count=1";
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

        if(i==-1||j==-1){
            return "http://ge.mam.tubitak.gov.tr/sites/images/ge_mam/ekmek2.jpg";
        }
        //System.out.println(result.getBody().substring(i+strContentUrl.length(),j));

        Map<String,Object> map = new Gson().fromJson(result.getBody(),Map.class);

        List value = (List) map.get("value");

        for(Object o : value){
            Map oMap = (Map)o;
            if(oMap.get("thumbnailUrl") != null){
                retVal = (String) oMap.get("thumbnailUrl");
                break;
            }
        }

        return retVal;
    }
}

