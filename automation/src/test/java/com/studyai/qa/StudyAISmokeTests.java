package com.studyai.qa;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

public class StudyAISmokeTests {
    private WebDriver driver;
    private WebDriverWait wait;
    private String baseUrl;

    @BeforeClass
    public void setUp() {
        baseUrl = System.getProperty("baseUrl", System.getenv().getOrDefault("STUDYAI_BASE_URL", "http://localhost:3000"));
        ChromeOptions options = new ChromeOptions();
        if (Boolean.parseBoolean(System.getenv().getOrDefault("HEADLESS", "true"))) {
            options.addArguments("--headless=new");
        }
        options.addArguments("--window-size=1440,1000", "--no-sandbox", "--disable-dev-shm-usage");
        driver = new ChromeDriver(options); // Selenium Manager resolves driver in supported environments.
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        if (driver != null) driver.quit();
    }

    @Test
    public void homeRouteLoadsAndHasMeaningfulDocumentTitle() {
        driver.get(baseUrl);
        wait.until(d -> ((JavascriptExecutor)d).executeScript("return document.readyState").equals("complete"));
        Assert.assertTrue(driver.getTitle() != null && !driver.getTitle().trim().isEmpty(),
            "Home route should set a meaningful document title.");
        Assert.assertTrue(driver.findElements(By.tagName("body")).size() == 1,
            "Rendered page should contain a body element.");
    }

    @Test
    public void pageDoesNotRenderCompletelyBlankBody() {
        driver.get(baseUrl);
        wait.until(d -> ((JavascriptExecutor)d).executeScript("return document.readyState").equals("complete"));
        String text = driver.findElement(By.tagName("body")).getText().trim();
        List<WebElement> visibleControls = driver.findElements(By.cssSelector("button, a, input, form, main, [role='main']"));
        Assert.assertTrue(!text.isEmpty() || !visibleControls.isEmpty(),
            "Page appears blank: no visible text or common interactive/main elements were found.");
    }

    @Test
    public void pageHasNoObviousBrokenInternalNavigationTargets() {
        driver.get(baseUrl);
        wait.until(d -> ((JavascriptExecutor)d).executeScript("return document.readyState").equals("complete"));
        String origin = driver.getCurrentUrl();
        List<WebElement> links = driver.findElements(By.cssSelector("a[href]"));
        for (WebElement link : links) {
            String href = link.getAttribute("href");
            if (href == null || href.isBlank()) continue;
            if (href.startsWith(origin) && (href.endsWith("#") || href.equals(origin + "#"))) {
                Assert.fail("Found an internal link with an empty fragment target: " + href);
            }
        }
    }
}
