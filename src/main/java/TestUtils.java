import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class TestUtils {
    final static String BASIC_TESTING_URL = "https://www.przepisy.pl/";
    final static String URL_WITH_VEGE_FILTER = "https://www.przepisy.pl/przepisy/posilek/obiad?dish_type=vege";
    final static String URL_WITH_OCCASION_FILTER_APPLIED = "occasion=";
    final static String BLOG_PAGE_URL = "https://www.przepisy.pl/blog";
    static Logger LOGGER = Logger.getLogger(TestUtils.class.getName());
    private static WebDriver driver;
    private static WebDriverWait wait;

    public static void goToPage() {
        driver.get(BASIC_TESTING_URL);
        String currentUrl = driver.getCurrentUrl();
        assertEquals(BASIC_TESTING_URL, currentUrl);
        LOGGER.info("Testing page opened correctly.");
    }

    public static void acceptCookies() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("ot-sdk-row")));
        driver.findElement(By.id("onetrust-accept-btn-handler")).click();
        LOGGER.info("Cookies banner has been closed.");
    }

    public static void searchingForDinnerMeals() {
        Actions actions = new Actions(driver);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("menu-element__name")));
        WebElement przepisyButton = driver.findElement(By.className("menu-element__name"));
        actions.moveToElement(przepisyButton).perform();
        LOGGER.info("Mouse hovered on PRZEPISY button from menu.");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[href='/przepisy/posilek'] span")));
        actions.moveToElement(driver.findElement(By.cssSelector("a[href='/przepisy/posilek'] span"))).perform();
        LOGGER.info("Mouse hovered on POSILEK button from menu.");

        wait.until(ExpectedConditions.visibilityOfElementLocated((By.linkText("OBIAD"))));
        WebElement obiadButtonToClick = driver.findElement(By.linkText("OBIAD"));
        obiadButtonToClick.click();
        LOGGER.info("Mouse clicked OBIAD button from menu.");

        wait.until(ExpectedConditions.visibilityOfElementLocated((By.cssSelector("h1.category-name"))));
        WebElement categoryHeaderOnDinnerPage = driver.findElement(By.cssSelector("h1.category-name"));
        assertTrue(categoryHeaderOnDinnerPage.isDisplayed());
        LOGGER.info("Category header is displayed. User is on Dinner page.");
    }

    public static void choosingVegeMeals() {
        List<WebElement> wegetarianskieCheckbox = driver.findElements(By.className("label-checkbox"));
        wegetarianskieCheckbox.get(0).click();

        String linkWithChosenFilter = driver.getCurrentUrl();
        assertEquals(URL_WITH_VEGE_FILTER, linkWithChosenFilter);
        LOGGER.info("User chose vege meals.");
    }

    public static void choosingGrillOccasionForMeals() {
        List<WebElement> openOccasionDropdownList = driver.findElements(By.className("element"));
        openOccasionDropdownList.get(0).click();

        List<WebElement> checkBoxToClick = driver.findElements(By.className("label-checkbox-box"));
        checkBoxToClick.get(1).click();
    }

    public static void applyingOccasionFilter() {
        List<WebElement> zastosujButton = driver.findElements(By.className("buttons"));
        zastosujButton.get(0).click();

        LOGGER.info("'Zastosuj' button has been clicked.");

        String currentURL = driver.getCurrentUrl();
        assertTrue(currentURL.contains(URL_WITH_OCCASION_FILTER_APPLIED));
        LOGGER.info("Occasion filter was applied correctly.");
    }

    public static void clickBlogButton() {
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//span[@class='menu-element__name' and text()='BLOG']")));
        WebElement blogButton = driver.findElement(
                By.xpath("//span[@class='menu-element__name' and text()='BLOG']"));
        blogButton.click();
        LOGGER.info("BLOG button has been clicked");

        driver.navigate().refresh();
        wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText("Makarony")));

        String currentUrl = driver.getCurrentUrl();
        assertEquals(BLOG_PAGE_URL, currentUrl);
        LOGGER.info("Redirection to 'BLOG' page works correctly. Assertion passed.");
    }

    public static void clickingMakaronyButton() {
        wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText("Makarony")));
        LOGGER.info("'MAKARONY' button is visible to click. Page loaded correctly.");

        WebElement makaronyButton = driver.findElement(By.partialLinkText("Makarony"));
        makaronyButton.click();
        WebElement makaronyPageHeader = driver.findElement(By.className("category-name"));
        assertTrue(makaronyPageHeader.isDisplayed());
        LOGGER.info("Category was chosen correctly.");
    }

    public static void clickingPastaWithWegetablesTile() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        WebElement pastaWithWegetableTile = driver.findElement(
                By.xpath("//a[@href='/przepisy/dania-i-przekaski/makarony/makaron-z-warzywami']"));
        pastaWithWegetableTile.click();

        LOGGER.info("Tile has been clicked.");

        WebElement wegePastaHeader = driver.findElement(By.className("category-name"));
        assertTrue(wegePastaHeader.isDisplayed());

        LOGGER.info("Category has been chosen correctly. User was redirected to another page");
    }

    public static void choosingForLactoseFreeDiet() {
        Actions actions = new Actions(driver);
        WebElement przepisyButton = driver.findElement(By.className("menu-element__name"));

        actions.moveToElement(przepisyButton).perform();
        LOGGER.info("Mouse hovered on PRZEPISY button from menu.");

        actions.moveToElement(driver.findElement(By.linkText("DIETY"))).perform();
        LOGGER.info("Mouse hovered on DIETY button from menu.");

        WebElement bezLaktozyButtonToClick = driver.findElement(By.linkText("BEZ LAKTOZY"));
        bezLaktozyButtonToClick.click();
        LOGGER.info("Mouse clicked BEZ LAKTOZY button from menu.");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h1.category-name")));
        WebElement categoryHeaderOnBezLaktozyPage = driver.findElement(By.cssSelector("h1.category-name"));
        assertTrue(categoryHeaderOnBezLaktozyPage.isDisplayed());
        LOGGER.info("Category header is displayed. User is on lactoze free page.");
    }

    public static void choosingHolidayFromOccasionFilters() {
        List<WebElement> openOccasionDropdownList = driver.findElements(By.className("element"));
        openOccasionDropdownList.get(0).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("span.label-checkbox-text")));

        List<WebElement> checkBoxToClick = driver.findElements(By.cssSelector("span.label-checkbox-text"));
        checkBoxToClick.get(10).click();

        LOGGER.info("Checkbox for 'WAKACJE' meals has been marked.");
    }

    public static void clickingFridgeIcon() {
        List<WebElement> fridgeIcon = driver.findElements(By.className("extended-menu-icon"));
        fridgeIcon.get(0).click();
        LOGGER.info("Fridge icon has been clicked.");
    }

    public static void enteringIngredientsByUser() {
        WebElement inputField = driver.findElement(By.className("search-input"));
        inputField.sendKeys("Pomidor, kurczak, dynia");
        inputField.sendKeys(Keys.ENTER);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h2.appsoup-header")));

        WebElement searchEffectHeader = driver.findElement(By.cssSelector("span.ng-star-inserted"));
        assertTrue(searchEffectHeader.isDisplayed());
        LOGGER.info("Searching works correctly.");
    }

    public static void setDriver(WebDriver driver) {
        TestUtils.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
}
