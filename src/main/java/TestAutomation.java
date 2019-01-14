package main.java;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestAutomation {
    private WebDriver driver = null;

    public TestAutomation(String nav, String path) {
        switch(nav) {
            case "Firefox":
                this.setDriver(new FirefoxDriver());
                break;
            default:
                System.setProperty("webdriver.chrome.driver", path);
                this.setDriver(new ChromeDriver());
                break;
        }
    }

    public WebDriver getDriver() {
        return driver;
    }

    private void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    public void Start() throws InterruptedException {

        Robot robot;

        // URL

        driver.get("https://produits.groupe-pomona.fr/nuxeo/login.jsp?requestedUrl=ui%2F");

        (new WebDriverWait(driver, 60)).until(ExpectedConditions.presenceOfElementLocated(By.id("username")));

        Thread.sleep(2000);

        // LOGIN

        driver.findElement(By.id("username")).sendKeys("vente");

        driver.findElement(By.id("password")).sendKeys("pomona");

        Thread.sleep(2000);

        driver.findElement(By.id("password")).sendKeys(Keys.ENTER);

        (new WebDriverWait(driver, 60)).until(ExpectedConditions.titleIs("Accueil - Groupe Pomona"));

        // HOMEPAGE

        Thread.sleep(2000);

        (new WebDriverWait(driver, 60)).until(ExpectedConditions.presenceOfElementLocated(By.id("app")));

        Thread.sleep(2000);

        // ICON FOURNISSEUR

        ((JavascriptExecutor)driver).executeScript("Array.prototype.slice.call(document.getElementById('app')"+
                ".root.getElementById('menu').root.host.getElementsByTagName('nuxeo-menu-icon'))"+
                ".find((el) => { if (el.label == 'Fournisseurs') { el.click() } })");

        Thread.sleep(2000);

        // ESPACE FOURNISSEUR

        ((JavascriptExecutor)driver).executeScript("let s = document.getElementById('app').root.getElementById('drawerPanel');"+
                "s = s.getElementsByTagName('iron-pages')[0].getElementsByTagName('keendoo-document-tree');"+
                "s = Array.prototype.slice.call(s).find((el) => { if ( el.label == 'Fournisseurs' ) { return el } });"+
                "s = s.root.getElementById('root').getElementsByTagName('a')[0];" +
                "s.click()");

        Thread.sleep(2000);

        // OPEN POP-UP

        ((JavascriptExecutor)driver).executeScript("let s = document.getElementById('app');"+
                "s = Array.prototype.slice.call(s.shadowRoot.childNodes).find((el) => { if (el.className == 'browse') { return el } });"+
                "s = s.root.getElementById('tray');" +
                "s = s.getElementsByTagName('paper-fab')[0].click()");

        Thread.sleep(2000);

        // OPEN FORMULAIRE

        ((JavascriptExecutor)driver).executeScript("let s = document.getElementById('app').root.getElementById('importPopup').root.getElementById('createDocDialog')"+
                ".getElementsByTagName('iron-pages')[0].getElementsByTagName('pomona-document-create')[0].root.querySelector('*:nth-child(3)')"+
                ".getElementsByTagName('paper-dialog-scrollable')[0].getElementsByTagName('paper-button')[0]; s.click()");

        Thread.sleep(2000);

        // REMPLISSAGE FORMULAIRE

        String form = "let s = document.getElementById('app').root.getElementById('importPopup').root.getElementById('createDocDialog').getElementsByTagName('iron-pages')[0].getElementsByTagName('pomona-document-create')[0].root.querySelector('iron-pages').getElementsByTagName('paper-dialog-scrollable')[1].shadowRoot.lastElementChild.firstElementChild.assignedElements()[0].getElementsByTagName('nuxeo-document-layout')[0].root.getElementById('layout').root.getElementById('container').firstChild.shadowRoot.querySelector('keendoo-grid-container').getElementsByTagName('keendoo-labelled-row');";

        ((JavascriptExecutor)driver).executeScript(form + "s[1].firstElementChild.root.querySelector('keendoo-selectivity').root.querySelector('.selectivity-single-result-container').firstChild.click()");

        Thread.sleep(1000);

        ((JavascriptExecutor)driver).executeScript(form + "s[1].firstElementChild.root.querySelector('keendoo-selectivity').root.getElementById('input').lastElementChild.lastElementChild.querySelector('*:nth-child(2)').click()");

        Thread.sleep(2000);

        ((JavascriptExecutor)driver).executeScript(form + "s[2].lastElementChild.root.querySelector('keendoo-value-toggle').shadowRoot.getElementById('left').parentNode.getElementsByTagName('paper-toggle-button')[0].click()");

        Thread.sleep(1000);

        // RAISON SOCIAL

        ((JavascriptExecutor)driver).executeScript(form + "s[3].firstElementChild.root.getElementById('input').root.getElementById('container').getElementsByTagName('iron-input')[0].firstElementChild.__dataHost.$.nativeInput.focus()");

        try {
            robot = new Robot();
            robot.keyPress(KeyEvent.VK_W);
            robot.keyPress(KeyEvent.VK_A);
            robot.keyPress(KeyEvent.VK_P);
            robot.keyPress(KeyEvent.VK_S);
            robot.keyPress(KeyEvent.VK_I);
            robot.keyPress(KeyEvent.VK_SPACE);
            robot.keyPress(KeyEvent.VK_A);
        } catch (AWTException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //((JavascriptExecutor)driver).executeScript(form + "s[3].firstElementChild.root.getElementById('input').root.getElementById('container').getElementsByTagName('iron-input')[0].firstElementChild.__dataHost.value =  'WAPSI A'");

        Thread.sleep(1000);

        //((JavascriptExecutor)driver).executeScript(form + "t = s[3].firstElementChild.root.getElementById('input').root.getElementById('container').getElementsByTagName('iron-input')[0].firstElementChild.click(); e = new KeyboardEvent('keydown',{'keyCode':32,'which':32}); t.dispatchEvent(e);");


        Thread.sleep(1000);

        ((JavascriptExecutor)driver).executeScript(form + "s[3].click()");

        Thread.sleep(1000);

        ((JavascriptExecutor)driver).executeScript(form + "s[4].firstElementChild.root.querySelector('keendoo-selectivity').root.querySelector('.selectivity-single-result-container').firstChild.click()");

        Thread.sleep(1000);

        ((JavascriptExecutor)driver).executeScript(form + "s[4].firstElementChild.root.querySelector('keendoo-selectivity').root.getElementById('input').lastElementChild.lastElementChild.querySelector('*:nth-child(2)').click();");

        Thread.sleep(2000);

        // ID PAYS

        String al = "1";

        Integer len = 9;

        for ( Integer i = 0; i < len; i++ ) {
            al += Math.floor(Math.random() * 10)%10;
            al = al.substring(0, al.length() - 2);
        }

        ((JavascriptExecutor)driver).executeScript(form + "s[7].firstElementChild.root.getElementById('input').root.getElementById('container').getElementsByTagName('iron-input')[0].firstElementChild.__dataHost.$.nativeInput.focus()");

        try {
            robot = new Robot();
            al = "DE" + al;
            System.out.println(al);
            for ( int i = 0; i < al.length(); i++ ) {
                robot.keyPress(KeyEvent.VK_SHIFT);
                robot.keyPress(Character.toUpperCase(al.charAt(i)));
                robot.keyRelease(KeyEvent.VK_SHIFT);
            }
        } catch (AWTException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Thread.sleep(2000);

        // SUBMIT

        ((JavascriptExecutor)driver).executeScript("Array.prototype.slice.call(document.getElementById('app').root.getElementById('importPopup').root.getElementById('createDocDialog').getElementsByTagName('iron-pages')[0].getElementsByTagName('pomona-document-create')[0].root.querySelector('iron-pages').getElementsByTagName('paper-button')).find((el) => { if (el.id == 'create') { el.click() } })");

        Thread.sleep(5000);

        // LOGOUT

        ((JavascriptExecutor)driver).executeScript("document.getElementsByTagName('keendoo-app-header')[0].root.getElementById('actions').lastChild.root.getElementById('keendoo-button-logout-icon').click()");

        Thread.sleep(2000);

        // QUIT

        driver.quit();
    }
}