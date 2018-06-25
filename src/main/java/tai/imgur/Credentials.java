package tai.imgur;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

class Credentials {
    private String client_id;
    private String client_secret;
    private String access_token;

    public Credentials(FileInputStream fileInputStream) throws IOException {
        Properties properties = new Properties();
        properties.load(fileInputStream);
        client_id=properties.getProperty("client_id");
        client_secret=properties.getProperty("client_secret");
        access_token=properties.getProperty("access_token");
    }

    public String getCredentialsAsURLParameters(){
        String stringBuilder = "client_id=" +
                client_id +
                "&client_secret=" +
                client_secret +
                "&access_token=" +
                access_token;
        return stringBuilder;
    }
}
