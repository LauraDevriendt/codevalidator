package learning.com.codevalidator.services;

import learning.com.codevalidator.models.FileInfo;
import learning.com.codevalidator.repositories.FileInfoRepository;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class FileInfoService {

    final
    FileInfoRepository fileInfoRepository;

    public FileInfoService(FileInfoRepository fileInfoRepository) {
        this.fileInfoRepository = fileInfoRepository;
    }

    public Map<String, FileInfo> getInfoFiles() {
        return fileInfoRepository.getFiles();
    }


}
