package learning.com.codevalidator.repositories;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class FileRepository {

    private Map<Integer, String> files;

    public FileRepository() {
        Map<Integer, String> files = new HashMap<>();
        files.put(1, "src/main/resources/java/test.java");
        files.put(2, "src/main/resources/java/java-collections-cheat-sheet.pdf");
        this.files = files;
    }

    public Map<Integer, String> getFiles() {
        return files;
    }

    public void setFiles(Map<Integer, String> files) {
        this.files = files;
    }

    public String getFile(Integer id) {
        return this.files.get(id);
    }
}
