package learning.com.codevalidator.repositories;

import learning.com.codevalidator.models.FileInfo;
import learning.com.codevalidator.models.MainClass;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class MainClassRepository {


    private Map<String, MainClass> classes;

    public MainClassRepository() {
        this.classes = new HashMap<>();
    }

    public MainClass addMainClass(MainClass mainClass, FileInfo fileInfo) {
        classes.put(fileInfo.getFile().getName(), mainClass);
        return mainClass;
    }

    public Map<String, MainClass> getClasses() {
        return classes;
    }

}
