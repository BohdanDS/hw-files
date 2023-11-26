package files;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


public class FilesReaderTest {

    private final ClassLoader classLoader = FilesReaderTest.class.getClassLoader();

    @Test
    @DisplayName("Проверка CSV файла")
    void parseCSVFileFromZipTest() throws Exception {
        try (InputStream inputSteam = classLoader.getResourceAsStream("test-data.zip")) {
            assert inputSteam != null;
            try (ZipInputStream zipInputSteam = new ZipInputStream(inputSteam)) {
                ZipEntry entry;
                while ((entry = zipInputSteam.getNextEntry()) != null) {
                    if (entry.getName().equals("verifySearchReturnsCorrectResultTest.csv")) {
                        CSVReader reader = new CSVReader(new InputStreamReader(zipInputSteam));
                        List<String[]> csvContent = reader.readAll();
                        Assertions.assertEquals(csvContent.size(), 3);
                        Assertions.assertArrayEquals(new String[]{
                                "iPhone 15",
                                "iPhone 15 Pro and iPhone 15 Pro Max"
                        }, csvContent.get(0));
                    }
                }
            }
        }
    }

    @Test
    @DisplayName("Проверка PDF файла")
    void parsePDFFileFromZipTest() throws Exception{
        try (InputStream inputSteam = classLoader.getResourceAsStream("test-data.zip")) {
            assert inputSteam != null;
            try (ZipInputStream zipInputSteam = new ZipInputStream(inputSteam)) {
                ZipEntry entry;
                while ((entry = zipInputSteam.getNextEntry()) != null) {
                    if (entry.getName().equals("sample.pdf")) {
                        PDF pdf = new PDF(zipInputSteam);
                        Assertions.assertEquals(pdf.numberOfPages, 2);
                        Assertions.assertEquals("Rave (http://www.nevrona.com/rave)", pdf.creator);
                        System.out.println(pdf.creator);
                    }
                }
            }
        }
    }
    @Test
    @DisplayName("Проверка xlsx файла")
    void parseXlsxFileFromZipTest() throws Exception{
        try (InputStream inputSteam = classLoader.getResourceAsStream("test-data.zip")) {
            assert inputSteam != null;
            try (ZipInputStream zipInputSteam = new ZipInputStream(inputSteam)) {
                ZipEntry entry;
                while ((entry = zipInputSteam.getNextEntry()) != null) {
                    if (entry.getName().equals("file_example_XLSX_50.xlsx")) {
                        XLS xls = new XLS(zipInputSteam);
                        double idCellValue = xls.excel.getSheetAt(0).getRow(1).getCell(7).getNumericCellValue();
                        String firstNameCellValue = xls.excel.getSheetAt(0).getRow(1).getCell(1).getStringCellValue();
                        Assertions.assertEquals(idCellValue, 1562);
                        Assertions.assertEquals(firstNameCellValue, "Dulce");
                    }
                }
            }
        }
    }
}
