package tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import supporte.Generator;
import supporte.Screenshot;

import java.util.concurrent.TimeUnit;

public class InformacoesUsuarioTest {
    private  WebDriver navegador;

    @Rule
    public TestName test = new TestName();
    @Before
    public void setup(){
        System.setProperty("webdriver.chrome.driver","/Users/abmaelrodrigo/drivers/chromedriver");
        navegador = new ChromeDriver();

        navegador.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        navegador.get("http://www.juliodelima.com.br/taskit");

        // Clicar no link que possui o texto Sign In
        navegador.findElement(By.linkText("Sign in")).click();

        //Identificando o formulario de log in
        WebElement formularioSignInBox = navegador.findElement(By.id("signinbox"));

        //Digitar no compo com name login que esta dentro do formulario de id signinbox o texto julio0001
        formularioSignInBox.findElement(By.name("login")).sendKeys("julio0001");

        //Digitar no compo com name password que esta dentro do formulario de id signinbox o texto 123456
        formularioSignInBox.findElement(By.name("password")).sendKeys("123456");

        //Clicar no link com o texto SIGN IN
        navegador.findElement(By.linkText("SIGN IN")).click();

        //Clicar em m link que possui a class me
        navegador.findElement(By.className("me")).click();

        //Clicar em um link que possui um texto 'MORE DATA ABOUT YOU'
        navegador.findElement(By.linkText("MORE DATA ABOUT YOU")).click();

    }
    //@Test
    public void testAdicionarUmaInfomacaoAdicionalDoUsuario(){

        //Clicar no botão através do seu XPath //button[@data-target='addmoredata']
        navegador.findElement(By.xpath("//button[@data-target=\"addmoredata\"]")).click();

        //Identificar o popup de onde esta o formulario de id addmoredata
        WebElement popupAddMoreData = navegador.findElement(By.id("addmoredata"));

        //Na combo de name type, escolher a opção "Phone"
        WebElement campoType = popupAddMoreData.findElement(By.name("type"));
        new Select(campoType).selectByVisibleText("Phone");

        //No campo de name contact, colocar o número "+5599999999999"
        popupAddMoreData.findElement(By.name("contact")).sendKeys("+5599999999999");

        //Clicar no link de text "SAVE" que está na popup
        popupAddMoreData.findElement(By.linkText("SAVE")).click();

        //Na mensagem de id "toast-container", validar que o texto "Your contact has been added!"
        WebElement mensagemPop = navegador.findElement(By.id("toast-container"));
        String mensagem = mensagemPop.getText();
        assertEquals("Your contact has been added!",mensagem);

    }

    @Test
    public void removerUmContatoDeUmUsuario(){
        //clicar no elemento pelo seu xPath //span[text()="+559833334444"]/following-sibling::a
        navegador.findElement(By.xpath("//span[text()=\"+559833334444\"]/following-sibling::a")).click();

        //Confirmar a janela javascript
        navegador.switchTo().alert().accept();

        //Validar que a mensagem apresentada foi "Rest in peace, dear phone!"
        WebElement mensagemPop = navegador.findElement(By.id("toast-container"));
        String mensagem = mensagemPop.getText();
        assertEquals("Rest in peace, dear phone!",mensagem);

        String screenshotArquivo = "/Users/abmaelrodrigo/test-report/taskit/" + Generator.dataHoraParaArquivo() + test.getMethodName() +".png";

        Screenshot.tirar(navegador,screenshotArquivo);

        //Aguardar ate 10 segundos para que a janela desapareça
        WebDriverWait aguardar = new WebDriverWait(navegador,10);
        aguardar.until(ExpectedConditions.stalenessOf(mensagemPop));

        //Clicar no link com o texto "logout"
        navegador.findElement(By.linkText("Logout")).click();
    }

    @After
    public void tearDown(){

        //navegador.quit();

    }
}
