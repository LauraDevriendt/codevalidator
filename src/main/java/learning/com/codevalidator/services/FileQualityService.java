package learning.com.codevalidator.services;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.google.common.io.Files;
import learning.com.codevalidator.Exceptions.NoValidFileException;
import learning.com.codevalidator.models.FileInfo;
import learning.com.codevalidator.repositories.FileInfoRepository;
import learning.com.codevalidator.repositories.FileRepository;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;



@Service
public class FileQualityService {

    enum excludedImports {
        JAVA ("java."),
        SUN ("com.sun."),
        SPRING("org.springframework"),
        JAVAX ("javax."),
        JDK ("jdk."),
        W3C ("org.w3c"),
        XML ("org.xml")
        ;

        private String packagePrefix;

        excludedImports(String packagePrefix) {
        }

        public String getPackagePrefix() {
            return packagePrefix;
        }
    }

    private final FileRepository fileRepository;
    private final FileInfoRepository fileInfoRepository;

    public FileQualityService(FileRepository fileRepository, FileInfoRepository fileInfoRepository) {
        this.fileRepository = fileRepository;
        this.fileInfoRepository = fileInfoRepository;
    }

    public boolean validateFile(File file) {

        if (file.isFile() & rightExtension(file)) {
            return true;
        }
        throw new NoValidFileException("you have not requested a java file");
    }

    private boolean rightExtension(File file) {
        if (Files.getFileExtension(file.getName()).equals("java")) {
            return true;
        }
        return false;
    }

    private int getNumOfLines(File file) throws FileNotFoundException {
        Scanner fileReader = new Scanner(file);
        int numOflines = 0;
        while (fileReader.hasNextLine()) {
            fileReader.nextLine();
            numOflines++;
        }
        return numOflines;
    }

    private int getNumOfImports(File file) throws FileNotFoundException {
        Scanner fileReader = new Scanner(file);
        int numOfImports = 0;
        while (fileReader.hasNextLine()) {
            String str = fileReader.nextLine();
            List<String> imports = Arrays.stream(str.split(" ")).filter(word -> word.equals("import")).collect(Collectors.toList());
            numOfImports += imports.size();
        }
        return numOfImports;
    }

    private int getNumOfIfElse(File file) throws FileNotFoundException {
        int numOfConditions = 0;
        Scanner fileReader = new Scanner(file);
        while (fileReader.hasNextLine()) {
            String str = fileReader.nextLine();
            List<String> conditions = Arrays.asList(str.split(" "));
            for (int i = 0; i < conditions.size(); i++) {
                if (conditions.get(i).equals("if")) {
                    numOfConditions++;
                }
                if (conditions.get(i).equals("else") && !conditions.get(i + 1).equals("if")) {
                    numOfConditions++;
                }
            }
        }
        return numOfConditions;
    }

    // @todo ask Nicholas
    public int calculateCyclicComplexity(Node node){
        int complexity = 0;
        for (IfStmt ifStmt : node.getChildNodesByType(IfStmt.class)) {
            // We found an "if" - cool, add one.
            complexity++;
            printLine(ifStmt);
            if (ifStmt.getElseStmt().isPresent()) {
                // This "if" has an "else"
                Statement elseStmt = ifStmt.getElseStmt().get();
                if (elseStmt instanceof IfStmt) {
                    // it's an "else-if". We already count that by counting the "if" above.
                } else {
                    // it's an "else-something". Add it.
                    complexity++;
                    printLine(elseStmt);
                }
            }
        }
        return complexity;
    }

    private static void printLine(Node node) {
        node.getRange().ifPresent(r -> System.out.println("line:"+r.begin.line));
    }



    public Map<Integer, String> getFiles() {

        return fileRepository.getFiles();
    }

    public FileInfo getFileInfo(Integer id) throws FileNotFoundException {
        File file = new File(fileRepository.getFile(id));
        if (this.validateFile(file)) {
            return convertToFileInfo(file);
        }
        throw new FileNotFoundException("file is not found");
    }

    public FileInfo convertToFileInfo(File file) throws FileNotFoundException {
        return new FileInfo(
                file, this.getNumOfLines(file),
                this.getNumOfIfElse(file),
                this.getNumOfImports(file));

    }

    public FileInfo gatherFileInfo(File file) throws FileNotFoundException {
        if (this.validateFile(file)) {
            int numOfLines = this.getNumOfLines(file);
            int numOfIfElse = this.getNumOfIfElse(file);
            int numOfImports = this.getNumOfImports(file);
            FileInfo fileInfo = new FileInfo(file,numOfLines,
                    numOfIfElse,numOfImports);
            return fileInfoRepository.addFileInfo(fileInfo);
        }
        throw new FileNotFoundException("file is not found");
    }


}
