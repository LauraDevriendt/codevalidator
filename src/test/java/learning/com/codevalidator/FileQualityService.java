package learning.com.codevalidator;


import learning.com.codevalidator.models.FileInfo;
import learning.com.codevalidator.models.MainClass;
import learning.com.codevalidator.repositories.FileInfoRepository;
import learning.com.codevalidator.repositories.FileRepository;
import learning.com.codevalidator.services.FileQualityService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


class FileQualityServiceTests {

    private final FileRepository fileRepository = Mockito.mock(FileRepository.class);
    private final FileInfoRepository fileInfoRepository = Mockito.mock(FileInfoRepository.class);
    private final String testFilePath = "src/main/resources/java/App.java";
    private final FileInfo testFileInfo = new FileInfo(new File(testFilePath), 40, 3, 3, 2);
    private final Map<Integer, String> files = new HashMap<>() {{
        put(1, "src/main/resources/java/App.java");
        put(2, "src/main/resources/java/CompoundCost.java");
        put(3, "src/main/resources/java/Order.java");
        put(4, "src/main/resources/java/java-collections-cheat-sheet.pdf");
    }};
    private final FileQualityService service = new FileQualityService(fileRepository, fileInfoRepository);
    // @todo do you make a testfile???

    @Test
    void getFileInfo() throws FileNotFoundException {
        int id = 1;
        Mockito.when(fileRepository.getFile(id)).thenReturn(testFilePath);
        FileInfo fileInfo = service.getFileInfo(1);
        Assertions.assertNotEquals(null, fileInfo);
    }

    // @todo doe ik hier het wel juist met die errors (this.validate file if weg)
    @Test
    void getFileInfo_notFound() throws FileNotFoundException {
        int id = 1;
        Mockito.when(fileRepository.getFile(id)).thenThrow(FileNotFoundException.class);
        Assertions.assertThrows(FileNotFoundException.class, () -> service.getFileInfo(id));
    }

    // @todo hoe testen want returned toch altijd tenzij geen juiste file
    @Test
    void convertToFileInfo() throws FileNotFoundException {
        File file = new File(testFilePath);
        Assertions.assertNotEquals(null, service.convertToFileInfo(file));
    }

    @Test
    void convertToFileInfo_notPossible() {
        File file = new File("wrongPath");
        Assertions.assertThrows(FileNotFoundException.class, () -> service.convertToFileInfo(file));
    }

    @Test
    void gatherFileInfo() throws FileNotFoundException {
        Mockito.when(fileInfoRepository.addFileInfo(testFileInfo)).thenReturn(testFileInfo);
        // @todo waarom null heb toch mock hierboven
        FileInfo fileInfo1 = service.gatherFileInfo(new File(testFilePath));
        Assertions.assertEquals(fileInfo1, testFileInfo);

    }

    @Test
        // @ todo LOOPT VOLLEDIG FOUT WEET NIET WAT TE DOEN stomme map
    void getNumOfOtherUsedClasses() throws FileNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        File file = new File(testFilePath);
        //Mockito.when(fileInfoRepository.getFiles()).thenReturn("")
        //Method method = FileQualityService.class.getDeclaredMethod("getNumOfOtherUsedClasses", File.class);
        //method.setAccessible(true);
        //Integer getNumOfOtherUsedClasses = (Integer) method.invoke(service, file);
        Assertions.assertEquals(testFileInfo.getNumOfOtherUsedClasses(), service.getNumOfOtherUsedClasses(file));
    }

    @Test
    void validateFile() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        File file = new File(testFilePath);
        Method method = FileQualityService.class.getDeclaredMethod("validateFile", File.class);
        method.setAccessible(true);
        Assertions.assertTrue((Boolean) method.invoke(service, file));
    }

    @Test
        // @todo goed zo want eingelijk andere exception???
    void validateFile_wrongFile() throws NoSuchMethodException {
        File file = new File("src/main/resources/java/java-collections-cheat-sheet.pdf");
        Method method = FileQualityService.class.getDeclaredMethod("validateFile", File.class);
        method.setAccessible(true);
        Assertions.assertThrows(InvocationTargetException.class, () -> method.invoke(service, file));
    }

    @Test
    void rightExtension_wrongFile() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        File file = new File("src/main/resources/java/java-collections-cheat-sheet.pdf");
        Method method = FileQualityService.class.getDeclaredMethod("rightExtension", File.class);
        method.setAccessible(true);
        Assertions.assertFalse((Boolean) method.invoke(service, file));
    }

    @Test
    void rightExtension() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        File file = new File(testFilePath);
        Method method = FileQualityService.class.getDeclaredMethod("rightExtension", File.class);
        method.setAccessible(true);
        Assertions.assertTrue((Boolean) method.invoke(service, file));
    }

    @Test
    void getNumOfLines() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        File file = new File(testFilePath);
        Method method = FileQualityService.class.getDeclaredMethod("getNumOfLines", File.class);
        method.setAccessible(true);
        Assertions.assertEquals(40, (Integer) method.invoke(service, file));
    }

    @Test
    void getNumOfNotExcludedImports() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, FileNotFoundException {
        File file = new File(testFilePath);
        Method method = FileQualityService.class.getDeclaredMethod("getNumOfNotExcludedImports", File.class);
        method.setAccessible(true);
        Assertions.assertEquals(2, (Integer) method.invoke(service, file));
    }

    @Test
    void isNotExcludedImport() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String importPackage = "be.facetit.rest.nu.open.strategydecorator.model.Order";
        Method method = FileQualityService.class.getDeclaredMethod("isNotExcludedImport", String.class);
        method.setAccessible(true);
        Assertions.assertTrue((Boolean) method.invoke(service, importPackage));
    }

    @Test
    void isNotExcludedImport_false() throws NoSuchMethodException {
                List<String> ExcludedPackages = new ArrayList<>(){{
                    add("java.util.LinkedList");
                    add("comsunjarsigner");
                    add("orgspringframeworkcontextannotationAnnotationConfigApplicationContext");
                    add("javaximageiospi");
                    add("jdkjfr");
                    add("orgw3cdom");
                    add("orgxmlsax");
                }};

        Method method = FileQualityService.class.getDeclaredMethod("isNotExcludedImport", String.class);
        method.setAccessible(true);
        ExcludedPackages.forEach((excludedPackage) -> {
            try {
                Assertions.assertFalse((Boolean) method.invoke(service, excludedPackage));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    void  getNumOfIfElse() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        File file = new File(testFilePath);
        Method method = FileQualityService.class.getDeclaredMethod("getNumOfIfElse", File.class);
        method.setAccessible(true);
        Assertions.assertEquals(3, (Integer) method.invoke(service, file));

    }

    @Test
    void getMainClass() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        File file = new File(testFilePath);
        Method method = FileQualityService.class.getDeclaredMethod("getMainClass", File.class);
        method.setAccessible(true);
        MainClass mainClass = (MainClass) method.invoke(service, file);
        Assertions.assertEquals("App", mainClass.getName() );

    }


}
