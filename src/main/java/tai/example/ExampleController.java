package tai.example;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@Transactional
public class ExampleController {

    @RequestMapping("/example")
    public String get() {
        return "image_describe.html";
    }
    @RequestMapping("/download")
    public String downloadImages() {
        return "downloadImages.html";
    }
    @RequestMapping("/signin")
    public String signin() {
        return "signIn.html";
    }
    @RequestMapping("/profile")
    public String profile() {
        return "profile.html";
    }
}
