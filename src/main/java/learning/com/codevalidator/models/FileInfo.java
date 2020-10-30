package learning.com.codevalidator.models;

import java.io.File;

public class FileInfo {
    private File file;
    private Integer numOfLines;
    private Integer numOfIfElse;
    private Integer numOfImports;


    public FileInfo(File file, Integer numOfLines, Integer numOfIfElse, Integer numOfImports) {
        this.file = file;
        this.numOfLines = numOfLines;
        this.numOfIfElse = numOfIfElse;
        this.numOfImports = numOfImports;
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

    public Integer getNumOfImports() {
        return numOfImports;
    }

    public void setNumOfImports(Integer numOfImports) {
        this.numOfImports = numOfImports;
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
                ", numOfImports=" + numOfImports +
                '}';
    }
}
