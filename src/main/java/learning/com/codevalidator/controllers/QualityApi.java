package learning.com.codevalidator.controllers;

import io.swagger.annotations.ApiOperation;
import learning.com.codevalidator.models.FileInfo;
import learning.com.codevalidator.services.FileInfoService;
import learning.com.codevalidator.services.FileQualityService;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;

@RestController
@RequestMapping("qualityControl/files")
public class QualityApi {

    private final FileQualityService fileService;
    private final FileInfoService fileInfoService;

    public QualityApi(FileQualityService fileService, FileInfoService fileInfoService) {
        this.fileService = fileService;
        this.fileInfoService = fileInfoService;
    }

    @ApiOperation( value = "getting all the files", notes = "NOTE that these are not validated yet")
    @GetMapping("/")
    public Map<Integer, String> getFiles() {
        return fileService.getFiles();
    }

    @GetMapping("/{id}")
    public FileInfo getFileInfo(@PathVariable Integer id) throws FileNotFoundException {
        return fileService.getFileInfo(id);
    }

    @PostMapping("/")
    public void gatherFileInfo(@RequestBody String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        fileService.gatherFileInfo(file);
    }

    @GetMapping("/info")
    public Map<String, FileInfo> getInfoFiles() {
        return fileInfoService.getInfoFiles();
    }



}
