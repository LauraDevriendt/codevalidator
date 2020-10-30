package learning.com.codevalidator.repositories;

import learning.com.codevalidator.models.FileInfo;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class FileInfoRepository {

    private Map<String, FileInfo> files;

    public FileInfoRepository() {
        this.files = new HashMap<>();
    }

    public FileInfo addFileInfo(FileInfo fileInfo){
        files.put(fileInfo.getFile().getName(),fileInfo);
        return fileInfo;
    }

    public Map<String, FileInfo> getFiles() {
        return files;
    }


}
