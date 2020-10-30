package learning.com.codevalidator;

import learning.com.codevalidator.repositories.FileInfoRepository;
import learning.com.codevalidator.repositories.FileRepository;
import learning.com.codevalidator.services.FileQualityService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileNotFoundException;



@SpringBootApplication
public class CodevalidatorApplication {

    public static void main(String[] args) throws FileNotFoundException {
        SpringApplication.run(CodevalidatorApplication.class, args);
        File file = new File("src/main/resources/java/test.java");
        FileQualityService service = new FileQualityService(new FileRepository(), new FileInfoRepository());

        


    }



}
