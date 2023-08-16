import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class hishabee_auto {

    public static void main(String[] args) throws InterruptedException {
        String phone = "01937733017";
        String pin = "12345";
        
        System.setProperty("webdriver.chrome.driver", "D:\\MyGoogle\\googlekeywords\\src\\drivers\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        // Navigate to the site
        driver.get("https://web.hishabee.business/sign-in");

        // Wait for the site to load
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("form-control")));

        // Find the login box
        WebElement numberBox = driver.findElement(By.className("form-control"));
        numberBox.sendKeys(phone);
        numberBox.sendKeys(Keys.RETURN);

        // Wait for a few seconds
        Thread.sleep(2000);

        // Wait for the site to load
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("form-control")));

        // Find password box
        WebElement passwordBox = driver.findElement(By.className("form-control"));
        passwordBox.sendKeys(pin);
        passwordBox.sendKeys(Keys.RETURN);

        // Wait for a few seconds
        Thread.sleep(2000);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("btn-primary")));
        // Edit Shop 
        WebElement Shop_buttons = driver.findElement(By.className("btn-primary"));
        Shop_buttons.click(); // Click the button
        
        // Wait for a few seconds
        Thread.sleep(2000);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("card-body")));
        List<WebElement> Ecardss = driver.findElements(By.className("card-body"));
        int esll_no = 1;
        for (WebElement card : Ecardss) {
            System.out.println("\n" + esll_no + "\n");
            System.out.println(card.getText()); 
            if (esll_no == 2){
                WebElement selectShopButton = card.findElement(By.xpath(".//button[text()='Edit Shop']"));
                // Wait for a few seconds
                Thread.sleep(2000);
                selectShopButton.click();
            }
            
            esll_no +=1;
        }

        // Find the input field using its placeholder attribute
        WebElement inputField = driver.findElement(By.cssSelector("input[placeholder='Address']"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("btn-primary")));
        // Enter the desired text into the input field
        // Wait for a few seconds
        Thread.sleep(2000);
        inputField.clear();
        // Wait for a few seconds
        Thread.sleep(2000);
        inputField.sendKeys("kafrul33" + Keys.ENTER);
        // Wait for a few seconds
        Thread.sleep(2000);
        // Find the "Update" button
        WebElement updateButton = driver.findElement(By.className("btn-primary"));

        // Click the "Update" button
        updateButton.click();

        // Wait for a few seconds
        Thread.sleep(2000);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("card-body")));
        List<WebElement> cards = driver.findElements(By.className("card-body"));
        int sl_no = 1;
        for (WebElement card : cards) {
            System.out.println("\n" + sl_no + "\n");
            System.out.println(card.getText()); 
            // Wait for a few seconds
            Thread.sleep(2000);
            sl_no +=1;
        }

        // driver.navigate().back();
        // Wait for a few seconds
        Thread.sleep(2000);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("btn-primary")));
        Thread.sleep(2000);
        WebElement selShopButton = driver.findElement(By.cssSelector("a[href='/select/shop']"));
        selShopButton.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("card-body")));
        List<WebElement> cardss = driver.findElements(By.className("card-body"));
        int sll_no = 1;
        for (WebElement card : cardss) {
            System.out.println("\n" + sll_no + "\n");
            System.out.println(card.getText()); 
            if (sll_no == 2){
                // Wait for a few seconds
                Thread.sleep(2000);
                WebElement selectShopButton = card.findElement(By.xpath(".//button[text()='Select Shop']"));
                selectShopButton.click();
                // Wait for a few seconds
                Thread.sleep(2000);
            }
            
            sll_no +=1;
        }


        // Find all card containers
        // Extract information from each card
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("card-body")));
        List<WebElement> allcard = driver.findElements(By.className("card-body"));
        int s_no = 1;
        for (WebElement card : allcard) {
            System.out.println("\n" + s_no + "\n");
            System.out.println("Information: " + card.getText()); 
            // Wait for a few seconds
            Thread.sleep(2000);
            s_no +=1;
        }

        // Maximize the browser window
        driver.manage().window().maximize();
        // Find the link element by its href attribute
        WebElement linkElement = driver.findElement(By.cssSelector("a[href='/transaction']"));
        // Click the link
        linkElement.click();
        // Wait for a few seconds
        Thread.sleep(2000);

        // Find the parent container for the buttons
        WebElement container = driver.findElement(By.className("col-10"));

        // Find all the buttons within the container
        java.util.List<WebElement> buttons = container.findElements(By.tagName("button"));

        // Iterate through each button and click it
        for (WebElement button : buttons) {
            // Find the button using its HTML attributes
            WebElement Abutton = driver.findElement(By.className("fa-chevron-right"));

            // Click the arrow button and then button
            Abutton.click();
            Thread.sleep(3000);
            button.click();
            // Wait for a few seconds
            Thread.sleep(2000);
            WebElement AbuttonL = driver.findElement(By.className("fa-chevron-left"));

            // Click the arrow button and then button
            AbuttonL.click();
            
            // Wait for a few seconds
            Thread.sleep(2000);
            // Wait for a moment before proceeding to the next button
            try {
                Thread.sleep(2000); // Adjust the sleep duration as needed
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Find the link element
        WebElement linkElement2 = driver.findElement(By.cssSelector("a[href='/create/sale/stop/one']"));
        // Click the link
        linkElement2.click();

        // Find the input field
        WebElement gotcashField = driver.findElement(By.cssSelector("input[placeholder='Got cash']"));
        // Wait for a few seconds
        Thread.sleep(2000);
        gotcashField.clear();
        // Wait for a few seconds
        Thread.sleep(2000);
        gotcashField.sendKeys("5+60");
        
        // Wait for a few seconds
        Thread.sleep(2000);
        WebElement selectEqualButton = driver.findElement(By.xpath(".//button[text()='=']"));
        selectEqualButton.click();

        // Wait for a few seconds
        Thread.sleep(2000);
        WebElement ReceivedButton = driver.findElement(By.xpath(".//button[text()='Amount Received']"));
        ReceivedButton.click();
        
        // Wait for a few seconds
        Thread.sleep(2000);



        // Find the link element 
        WebElement linkElement3 = driver.findElement(By.cssSelector("a[href='/purchase']"));
        // Click the link
        linkElement3.click();
        // Wait for a few seconds
        Thread.sleep(2000);
        // Find the link element Add product
        WebElement AddProduct = driver.findElement(By.cssSelector("a[href='/products/create']"));
        // Click the link
        AddProduct.click();
        // Wait for a few seconds
        Thread.sleep(2000);


        // Wait for a few seconds
        // Thread.sleep(90000);

        // Close the browser
        driver.quit();
    }
}
