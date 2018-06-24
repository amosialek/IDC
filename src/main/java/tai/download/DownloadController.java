package tai.download;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
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
    public Map<String, Integer> get(@Valid @RequestHeader Principal principal, @Valid @RequestParam("tag") String tagName) {
        return downloadService.getImageLinksWithCounts(tagName, principal.getName());
    }
}
