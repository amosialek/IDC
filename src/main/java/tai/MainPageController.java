package tai;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import tai.models.image.Image;
import tai.models.image.ImageService;

@Controller
public class MainPageController {

    private final ImageService imageService;

    @Autowired
    public MainPageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @RequestMapping("/image_describe")
    public String image_describe(Model model) {
        Image image = imageService.getRandom();
        if (image!=null)
            model.addAttribute("image_link",image.getImageLink());

        return "image_describe";
    }

    @RequestMapping(value="/image_describe/1", method=RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void image_describe_get_desc(@RequestParam String description) {
        System.out.println(description);

    }
}