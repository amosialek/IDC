package tai;

import javafx.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Controller
public class MainPageController {

    @RequestMapping("/image_describe")
    public String image_describe() {
        System.out.println("aaaa");


        return "image_describe";
    }

    @RequestMapping(value="/image_describe/1", method=RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void image_describe_get_desc(@RequestParam String description) {
        System.out.println(description);
        System.out.println("bbbb");

    }
}