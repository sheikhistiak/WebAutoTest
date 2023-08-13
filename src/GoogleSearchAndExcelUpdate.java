import java.time.Duration;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GoogleSearchAndExcelUpdate {
    public static void main(String[] args) {
        String excelFilePath = "D:\\MyGoogle\\googlekeywords\\src\\Excel.xlsx"; // Replace with the path to your Excel file
        List<String> sheetNames = getAllSheetNames(excelFilePath);

        for (String sheetName : sheetNames) {
            System.out.println("Processing sheet: " + sheetName);
            performSearchAndExcelUpdate(excelFilePath, sheetName);
        }
    }

    public static List<String> getAllSheetNames(String excelFilePath) {
        List<String> sheetNames = new ArrayList<>();
    
        try (Workbook workbook = new XSSFWorkbook(new FileInputStream(excelFilePath))) {
            int numberOfSheets = workbook.getNumberOfSheets();
            for (int i = 0; i < numberOfSheets; i++) {
                sheetNames.add(workbook.getSheetName(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        return sheetNames;
    }

    public static void performSearchAndExcelUpdate(String excelFilePath, String activeSheetName) {
        List<String> keywords = loadKeywords(excelFilePath, activeSheetName);

        System.setProperty("webdriver.chrome.driver", "D:\\\\MyGoogle\\\\googlekeywords\\\\src\\\\drivers\\\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.google.com");

        int startRow = 2;
        for (String keyword : keywords) {
            List<String> searchResults = performSearch(driver, keyword);

            try (Workbook workbook = new XSSFWorkbook(new FileInputStream(excelFilePath))) {
                Sheet sheet = workbook.getSheet(activeSheetName);
                if (sheet == null) {
                    System.out.println("Sheet not found for current day: " + activeSheetName);
                    return;
                }

                Row row = sheet.getRow(startRow);
                if (row == null) {
                    row = sheet.createRow(startRow);
                }

                Cell cell = row.getCell(3);
                if (cell == null) {
                    cell = row.createCell(3);
                }
                cell.setCellValue(searchResults.get(1));

                cell = row.getCell(4);
                if (cell == null) {
                    cell = row.createCell(4);
                }
                cell.setCellValue(searchResults.get(0));
                
                startRow += 1;

                try (FileOutputStream outputStream = new FileOutputStream(excelFilePath)) {
                    workbook.write(outputStream);
                    System.out.println("file saved");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        driver.quit();
    }

    public static List<String> loadKeywords(String excelFilePath, String activeSheetName) {
        List<String> keywords = new ArrayList<>();

        try (Workbook workbook = new XSSFWorkbook(new FileInputStream(excelFilePath))) {
            Sheet activeSheet = workbook.getSheet(activeSheetName);

            if (activeSheet == null) {
                throw new IllegalArgumentException("Sheet not found for current day: " + activeSheetName);
            }

            int startRow = 2;
            int columnC = 2;

            for (int rowNum = startRow; rowNum <= activeSheet.getLastRowNum(); rowNum++) {
                Row row = activeSheet.getRow(rowNum);
                if (row != null) {
                    Cell cell = row.getCell(columnC);
                    if (cell != null) {
                        keywords.add(cell.getStringCellValue());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return keywords;
    }

    public static List<String> performSearch(WebDriver driver, String keyword) {
        List<String> results = new ArrayList<>();
        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.clear();
        searchBox.sendKeys(keyword);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("wM6W7d")));

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<WebElement> suggestionElements = driver.findElements(By.className("wM6W7d"));
        List<String> searchSuggestions = new ArrayList<>();

        for (WebElement suggestionElement : suggestionElements) {
            searchSuggestions.add(suggestionElement.getText());
        }

        searchSuggestions.sort(Comparator.comparingInt(String::length));

        if (!searchSuggestions.isEmpty()) {
            String firstElement = searchSuggestions.get(2);
            String lastElement = searchSuggestions.get(searchSuggestions.size() - 1);
            results.add(firstElement);
            results.add(lastElement);
        } else {
            System.out.println("No search suggestions found.");
        }
        System.out.println(results);
        return results;
    }
}
