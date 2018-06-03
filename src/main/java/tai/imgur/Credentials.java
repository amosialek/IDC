package tai.imgur;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Credentials {
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

    public String getClient_id() {
        return client_id;
    }

    public String getClient_secret() {
        return client_secret;
    }

    public String getAccess_token() {
        return access_token;
    }

    public String getCredentialsAsURLParameters(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("client_id=");
        stringBuilder.append(client_id);
        stringBuilder.append("&client_secret=");
        stringBuilder.append(client_secret);
        stringBuilder.append("&access_token=");
        stringBuilder.append(access_token);
        return stringBuilder.toString();
    }
}
