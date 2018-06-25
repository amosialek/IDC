package tai.imgur;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

@Component
public class ImgurAPI {

    private List<String >randomImagesURLs=null;
    private final Random random = new Random();

    private String getRandomPageJson(Integer pageNumber){
        if (pageNumber<0 || pageNumber>50)
            pageNumber=pageNumber%51;
        URL url = null;

        try {
            url = new URL("https://api.imgur.com/3/gallery/random/random/"+pageNumber.toString()+"?"+new Credentials(new FileInputStream("imgur_credentials.txt")).getCredentialsAsURLParameters());
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpURLConnection con = null;
        try {
            con = (HttpURLConnection) (url != null ? url.openConnection() : null);


            con.setRequestMethod("GET");

            con.setUseCaches(false);
            con.setDoOutput(true);

            //Get Response
            InputStream is = con.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return response.toString();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (con != null) {
                con.disconnect();
            }

        }

        return "";

    }

    private Map<String,Object> parseJSONResponse(String JSONResponse){
        HashMap<String,Object> result =new HashMap<>();
        System.out.println(JSONResponse);
        try {
             result = new ObjectMapper().readValue(JSONResponse, HashMap.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Scheduled(fixedRate = 7200000)
    public List<String> getRandomImagesURLs(){
        if(randomImagesURLs!=null)
            return randomImagesURLs;
        randomImagesURLs= new ArrayList<>();
        Map<String, Object> jsonMap = parseJSONResponse(getRandomPageJson(0));
        for (String s:jsonMap.keySet()) {
            System.out.println(s+": "+jsonMap.get(s).getClass());
        }
        System.out.println(jsonMap.get("data"));
        List<HashMap<String,Object>> data = (ArrayList<HashMap<String,Object>>)(jsonMap.get("data"));


        for (HashMap<String,Object> imageData:data) {
            if(imageData.get("is_album")==(Boolean)false) {
                randomImagesURLs.add(imageData.get("link").toString());

            }
        }
        return randomImagesURLs;
    }

}
