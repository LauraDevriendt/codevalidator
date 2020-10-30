package learning.com.codevalidator.models;

import java.io.File;

public class FileInfo {
    private File file;
    private Integer numOfLines;
    private Integer numOfIfElse;
    private Integer numOfNotExclImports;
    private Integer numOfOtherUsedClasses;


    public FileInfo(File file, Integer numOfLines, Integer numOfIfElse, Integer numOfImports, Integer numOfOtherUsedClasses) {
        this.file = file;
        this.numOfLines = numOfLines;
        this.numOfIfElse = numOfIfElse;
        this.numOfNotExclImports = numOfImports;
        this.numOfOtherUsedClasses = numOfOtherUsedClasses;

    }

    public Integer getNumOfOtherUsedClasses() {
        return numOfOtherUsedClasses;
    }

    public void setNumOfOtherUsedClasses(Integer numOfOtherUsedClasses) {
        this.numOfOtherUsedClasses = numOfOtherUsedClasses;
    }

    public Integer getNumOfLines() {
        return numOfLines;
    }

    public void setNumOfLines(Integer numOfLines) {
        this.numOfLines = numOfLines;
    }

    public Integer getNumOfIfElse() {
        return numOfIfElse;
    }

    public void setNumOfIfElse(Integer numOfIfElse) {
        this.numOfIfElse = numOfIfElse;
    }

    public Integer getNumOfNotExclImports() {
        return numOfNotExclImports;
    }

    public void setNumOfNotExclImports(Integer numOfNotExclImports) {
        this.numOfNotExclImports = numOfNotExclImports;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "FileInfo{" +
                "file=" + file.getName() +
                ", numOfLines=" + numOfLines +
                ", numOfIfElse=" + numOfIfElse +
                ", numOfImports=" + numOfNotExclImports +
                '}';
    }
}
