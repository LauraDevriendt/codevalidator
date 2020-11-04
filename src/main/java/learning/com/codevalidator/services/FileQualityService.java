package learning.com.codevalidator.services;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.google.common.io.Files;
import learning.com.codevalidator.Exceptions.NoValidFileException;
import learning.com.codevalidator.models.FileInfo;
import learning.com.codevalidator.models.MainClass;
import learning.com.codevalidator.repositories.FileInfoRepository;
import learning.com.codevalidator.repositories.FileRepository;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

// @todo: assisters class maken (main and specific ones) this class will extend from the main
@Service
public class FileQualityService {

    // EXCLUDE THE DOTS IN PACKAGEPREFIX
    enum ExcludedImports {
        JAVA("java"),
        SUN("comsun"),
        SPRING("orgspringframework"),
        JAVAX("javax"),
        JDK("jdk"),
        W3C("orgw3c"),
        XML("orgxml");

        private String packagePrefix;

        ExcludedImports(String packagePrefix) {
            this.packagePrefix = packagePrefix;
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

    public Map<Integer, String> getFiles() {

        return fileRepository.getFiles();
    }

    public FileInfo getFileInfo(Integer id) throws FileNotFoundException {
        File file = new File(fileRepository.getFile(id));
        this.validateFile(file);
        return convertToFileInfo(file);
    }

    public FileInfo convertToFileInfo(File file) throws FileNotFoundException {
        return new FileInfo(
                file, this.getNumOfLines(file),
                this.getNumOfIfElse(file),
                this.getNumOfNotExcludedImports(file),
                this.getNumOfOtherUsedClasses(file));
    }

    public FileInfo gatherFileInfo(File file) throws FileNotFoundException {
        this.validateFile(file);
        int numOfLines = this.getNumOfLines(file);
        int numOfIfElse = this.getNumOfIfElse(file);
        int numOfImports = this.getNumOfNotExcludedImports(file);
        int numOfOtherUsedClasses = this.getNumOfOtherUsedClasses(file);
        FileInfo fileInfo = new FileInfo(file, numOfLines,
                numOfIfElse, numOfImports, numOfOtherUsedClasses);
        return fileInfoRepository.addFileInfo(fileInfo);

    }

    //@todo: no nested classes that exist inside a main class are not in the classlist
    //@todo: only the past files classnames are in the list needs to be replace by complete classList
    public int getNumOfOtherUsedClasses(File inputFile) throws FileNotFoundException {
        Scanner fileReader = new Scanner(inputFile);
        String classNameInputFile = this.getMainClass(inputFile).getName();
        Set<String> otherClassNames = new HashSet<>();
        while (fileReader.hasNextLine()) {
            String line = fileReader.nextLine();
            for (String filePath : fileRepository.getFiles().values()) {
                File file = new File(filePath);
                String className = this.getMainClass(file).getName();
                if (rightExtension(file) & line.contains(className) & !classNameInputFile.equals(className)) {
                    otherClassNames.add(className);
                }
            }
        }
        return otherClassNames.size();
    }

    private boolean validateFile(File file) {

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


    private int getNumOfNotExcludedImports(File file) throws FileNotFoundException {
        Scanner fileReader = new Scanner(file);
        int numOfNotExclImports = 0;
        while (fileReader.hasNext()) {
            String str = fileReader.next();
            if (str.equals("import")) {
                String importPackage = fileReader.next().replace(".", "");
                if(isNotExcludedImport(importPackage)){
                    numOfNotExclImports++;
                }
            } else {
                fileReader.next();
            }

        }
        return numOfNotExclImports;
    }

    public Boolean isNotExcludedImport(String importPackage) {
        for (ExcludedImports prefix : ExcludedImports.values()) {
            if (importPackage.startsWith(prefix.getPackagePrefix())) {
                return false;
            }
        }
        return true;
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

    private MainClass getMainClass(File file) {
        String className = FilenameUtils.removeExtension(file.getName());
        return new MainClass(className);
    }

    // @todo ask Nicholas
    public int calculateCyclicComplexity(Node node) {
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
        node.getRange().ifPresent(r -> System.out.println("line:" + r.begin.line));
    }


}
