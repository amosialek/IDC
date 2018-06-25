package tai.main;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.security.core.context.ReactiveSecurityContextHolder.clearContext;

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
    public String logout(){
        clearContext();return "ok";}
}
