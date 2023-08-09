import java.time.Duration;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class googlesearch {
    public static void main(String[] args) {

        String excelFilePath = "D:\\MyGoogle\\googlekeywords\\src\\Excel.xlsx"; // Replace with the path to your Excel file
        
        
        List<String> keywords = loadKeywords(excelFilePath);   

        // Set the path to the ChromeDriver executable
        System.setProperty("webdriver.chrome.driver", "D:\\\\MyGoogle\\\\googlekeywords\\\\src\\\\drivers\\\\chromedriver.exe");

        // Create a new instance of the ChromeDriver
        WebDriver driver = new ChromeDriver();

        // Open Google
        driver.get("https://www.google.com");

        // List of search keywords
        List<String> searchKeywords = new ArrayList<>();
        searchKeywords = keywords;
        
        
        int start_row = 2;
        // Loop through the search keywords
        List<String> searchresults = new ArrayList<>();
        for (String keyword : searchKeywords) {
            searchresults = performSearch(driver, keyword);


        
            try (Workbook workbook = new XSSFWorkbook(new FileInputStream(excelFilePath))) {
                String sheetName = new SimpleDateFormat("EEEE").format(new Date());
                
                Sheet sheet = workbook.getSheet(sheetName);
                
                if (sheet == null) {
                    System.out.println("Sheet not found for current day: " + sheetName);
                    return;
                }
                
                Row row = sheet.getRow(start_row);
                if (row == null) {
                    row = sheet.createRow(start_row);
                }

                Cell cell = row.getCell(3);
                if (cell == null) {
                    cell = row.createCell(3);
                }

                cell.setCellValue(searchresults.get(1));

                cell = row.getCell(4);
                if (cell == null) {
                    cell = row.createCell(4);
                }

                cell.setCellValue(searchresults.get(0));

                start_row += 1;
                // Save the changes to the Excel file
                try (FileOutputStream outputStream = new FileOutputStream(excelFilePath)) {
                    workbook.write(outputStream);
                    // System.out.println("Excel file updated with new values on " + sheetName + ".");
                }
                
            } catch (IOException e) {
                e.printStackTrace();
            }


            //for (String searchresult : searchresults) {
            //System.out.println(searchresult);
            //}
        }

        // Close the browser
        driver.quit();
    }

    public static List<String> loadKeywords(String excelFilePath) {
        List<String> keywords = new ArrayList<>();
        
        try (Workbook workbook = new XSSFWorkbook(new FileInputStream(excelFilePath))) {
            // Get the current day of the week 
            String sheetName = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(new Date());
            
            // Get the active sheet based on the day of the week
            Sheet activeSheet = workbook.getSheet(sheetName);
            
            if (activeSheet == null) {
                throw new IllegalArgumentException("Sheet not found for current day: " + sheetName);
            }
            
            // Start reading from the 3rd row and the C column (column index 2)
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

    // Function to perform a search and capture suggestions
    public static List<String> performSearch(WebDriver driver, String keyword) {
        List<String> results = new ArrayList<>();
        // Locate the search box element and send keys (keywords)
        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.clear(); // Clear existing text

        searchBox.sendKeys(keyword);

        // Wait for the search suggestions to appear
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("wM6W7d")));
        
        // Wait clearing the text
        try {
            Thread.sleep(2000); // Wait for 2 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Locate the search suggestion elements with class name "wM6W7d"
        List<WebElement> suggestionElements = driver.findElements(By.className("wM6W7d"));

        // Create a list to store search suggestions
        List<String> searchSuggestions = new ArrayList<>();

        // Iterate through the suggestion elements and store text in the list
        for (WebElement suggestionElement : suggestionElements) {
            searchSuggestions.add(suggestionElement.getText());
        }

        // Sort search suggestions in ascending order based on length
        searchSuggestions.sort(Comparator.comparingInt(String::length));

        // Print the search suggestions in ascending order
        // for (String suggestion : searchSuggestions) {
        //     System.out.println(suggestion);
        // }

        // Trace the first and last elements
        if (!searchSuggestions.isEmpty()) {
            String firstElement = searchSuggestions.get(2);
            String lastElement = searchSuggestions.get(searchSuggestions.size() - 1);
            
            // System.out.println("First Element: " + firstElement);
            // System.out.println("Last Element: " + lastElement);
            results.add(firstElement);
            results.add(lastElement);
            

        } else {
            System.out.println("The ArrayList is empty.");
        }
        // return searchSuggestions;
        return results;
        
        

        // Close the browser
        // driver.quit();
    }
    
}
