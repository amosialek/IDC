package tai.main;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@Transactional
public class MainController {

    @RequestMapping("/describe")
    public String get() {
        return "image_describe.html";
    }
    @RequestMapping("/download")
    public String downloadImages() {
        return "downloadImages.html";
    }
    @RequestMapping("/profile")
    public String profile() {
        return "profile.html";
    }
    @RequestMapping("/logout")
    public String logout(){logout();return "ok";}
}
