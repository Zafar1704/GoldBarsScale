import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class GoldBar {

    public GoldBar(int number, int weight){
        this.number = number;
        this.weight = weight;
    }
    int number;
    int weight;

    // METHOD TO FIND FAKE GOLD BAR WITH GIVING THE LIST OF GOLD BARS
    public static GoldBar findFakeGoldBar(List<GoldBar> goldBars){

        for(int i = 1; i < goldBars.size(); i++){
            if(goldBars.get(0).weight != goldBars.get(i).weight ){
                if(goldBars.get(0).weight > goldBars.get(i).weight ){
                    System.out.println("Fake Gold bar number is: " + goldBars.get(i).number);
                    return goldBars.get(i);
                }else{
                    System.out.println("Fake Gold bar number is: " + goldBars.get(0).number);
                    return goldBars.get(0);
                }

            }
        }
        System.out.println("There is no fake gold bar found. all the bars have same weight");
        return null;
    }



    // HERE IS THE UI AUTOMATION CODE TO RUN AND IT WILL FIND YOU THE RIGHT GOLD BAR WHICH IS FAKE
    public static void main(String[] args) throws InterruptedException {


        WebDriverManager.chromedriver().setup();

        WebDriver driver = new ChromeDriver();

        WebDriverWait wait = new WebDriverWait(driver, Duration.of(10, ChronoUnit.SECONDS));

        driver.get("http://sdetchallenge.fetch.com/");

        WebElement leftBowl = driver.findElement(By.xpath("//input[@id='left_0']"));

        WebElement rightBowl = driver.findElement(By.xpath("//input[@id='right_0']"));

        WebElement resetButton = driver.findElement(By.xpath("//button[.='Reset']"));

        WebElement weighButton = driver.findElement(By.xpath("//button[.='Weigh']"));

        for (int i = 1; i < 9; i++){

            wait.until(ExpectedConditions.visibilityOf(leftBowl));

            leftBowl.sendKeys("0");

            rightBowl.sendKeys(String.valueOf(i));

            weighButton.click();

            WebElement result = driver.findElement(By.xpath("//div[@class='result']/button"));

            wait.until(ExpectedConditions.not(ExpectedConditions.textToBePresentInElement(result, "?")));

            WebElement listOfResults = driver.findElement(By.xpath("(//div[.='Weighings']/following-sibling::ol/li)["+i+"]"));

            if (!listOfResults.getText().contains("=")){

                if(listOfResults.getText().contains(">")){

                    System.out.println("Fake gold bar number is: " + i);

                    WebElement number = driver.findElement(By.xpath("//button[.='"+i+"']"));

                    number.click();

                }else{

                    System.out.println("Fake gold bar number is: 0");

                    WebElement number = driver.findElement(By.xpath("//button[.='0']"));

                    number.click();

                }

                break;

            }else{

                resetButton.click();

            }


        }


    }




}
