package tests;

import config.AppiumConfig;
import models.Auth;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import screens.AuthenticationScreen;
import screens.ContactListScreen;

import java.util.Random;

public class RegistrationTests extends AppiumConfig {

    @Test
    public void registrationSuccess() {
        int i = new Random().nextInt(1000) + 1000;
       boolean res =  new AuthenticationScreen(driver)
                .fillLoginRegistrationForm(Auth.builder()
                        .email("roman" + i + "@gmail.com")
                        .password("Rrom123456$").build())
                .submitRegistration()
                .isActivityTitleDisplayed("Contact list");
        Assert.assertTrue(res);
    }

    @Test
    public void registrationWrongEmail(){
        new AuthenticationScreen(driver)
                .fillLoginRegistrationForm(Auth.builder()
                        .email("romangmail.com")
                        .password("Rrom123456$").build())
                .submitRegistrationNegative()
                .isErrorMessageHasText("{username=must be a well-formed email address}");
    }

    @Test
    public void registrationWrongPassword(){
        new AuthenticationScreen(driver)
                .fillLoginRegistrationForm(Auth.builder()
                        .email("roman@gmail.com")
                        .password("Rrom123").build())
                .submitRegistrationNegative()
                .isErrorMessageHasText("{password= At least 8 characters; Must contain at least 1 uppercase letter, 1 lowercase letter, and 1 number; Can contain special characters [@$#^&*!]}");
    }

    @Test
    public void registrationUserAlreadyExists(){
        new AuthenticationScreen(driver)
                .fillLoginRegistrationForm(Auth.builder()
                        .email("margo@gmail.com")
                        .password("Mmar123456$").build())
                .submitRegistrationNegative()
                .isErrorMessageHasText("User already exists");
    }

    @AfterMethod
    public void postCondition(){
        new ContactListScreen(driver).logout();
    }
}
