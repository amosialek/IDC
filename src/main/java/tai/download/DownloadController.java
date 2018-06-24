package tai.download;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import java.util.Map;

@RestController
@RequestMapping("/download")
@Transactional
public class DownloadController {
    private final DownloadService downloadService;

    public DownloadController(DownloadService downloadService) {
        this.downloadService = downloadService;
    }

    @GetMapping(produces = "application/json")
    public Map<String, Integer> get(@Valid @RequestHeader @Email String email, @Valid @RequestParam("tag") String tagName) {
        return downloadService.getImageLinksWithCounts(tagName, email);
    }
}
