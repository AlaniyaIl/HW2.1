package ru.netology.web;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

//import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CallbackTest {
    private WebDriver driver;

    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setupTest() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    void teardown() {
        driver.quit();
        driver = null;
    }
    @Test
    void shouldTestSomething()  {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id= name] input")).sendKeys("Альбус-Северус Снейп");
        driver.findElement(By.cssSelector("[data-test-id= phone ] input")).sendKeys("+79250745042");
        driver.findElement(By.cssSelector("[data-test-id= agreement ]")).click();
        driver.findElement(By.className("button_view_extra")).click();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void nameVerification()  {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id= name] input")).sendKeys("Albus");
        driver.findElement(By.cssSelector("[data-test-id= phone ] input")).sendKeys("+79250745042");
        driver.findElement(By.cssSelector("[data-test-id= agreement ]")).click();
        driver.findElement(By.className("button_view_extra")).click();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void phoneVerification()  {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id= name] input")).sendKeys("Альбус");
        driver.findElement(By.cssSelector("[data-test-id= phone ] input")).sendKeys("+799999999999");
        driver.findElement(By.cssSelector("[data-test-id= agreement ]")).click();
        driver.findElement(By.className("button_view_extra")).click();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id= phone].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void checkboxVerification()  {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id= name] input")).sendKeys("Альбус");
        driver.findElement(By.cssSelector("[data-test-id= phone ] input")).sendKeys("+79012345678");
        driver.findElement(By.className("button_view_extra")).click();
        String expected = "Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй";
        String actual = driver.findElement(By.cssSelector("[data-test-id= agreement].input_invalid")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void nameNotFiled()  {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id= name] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id= phone ] input")).sendKeys("+79012345678");
        driver.findElement(By.cssSelector("[data-test-id= agreement ]")).click();
        driver.findElement(By.className("button_view_extra")).click();
        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void phoneNotFiled()  {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id= name] input")).sendKeys("Альбус");
        driver.findElement(By.cssSelector("[data-test-id= phone ] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id= agreement ]")).click();
        driver.findElement(By.className("button_view_extra")).click();
        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("[data-test-id= phone].input_invalid .input__sub")).getText().trim();
        assertEquals(expected, actual);
    }

    @Test
    void checkboxNotFiled()  {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id= name] input")).sendKeys("Альбус");
        driver.findElement(By.cssSelector("[data-test-id= phone ] input")).sendKeys("+79012345678");
        driver.findElement(By.className("button_view_extra")).click();
        String expected = "Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй";
        String actual = driver.findElement(By.cssSelector("[data-test-id= agreement].input_invalid ")).getText().trim();
        assertEquals(expected, actual);
    }


}