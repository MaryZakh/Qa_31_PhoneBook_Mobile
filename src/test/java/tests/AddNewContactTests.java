package tests;

import config.AppiumConfig;
import models.Auth;
import models.Contact;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import screens.AuthenticationScreen;
import screens.ContactListScreen;

import java.util.Random;

public class AddNewContactTests extends AppiumConfig {

    @BeforeClass
    public void preCondition() {
        new AuthenticationScreen(driver)
                .fillLoginRegistrationForm(Auth.builder().email("margo@gmail.com")
                        .password("Mmar123456$").build())
                .submitLogin();
        //.isActivityTitleDisplayed("Contact List");
    }

    @Test
    public void createNewContactSuccess() {
        int i = new Random().nextInt(1000) + 1000;

        Contact contact = Contact.builder()
                .name("Simon")
                .lastName("Wow" + i)
                .email("wow" + i + "@gmail.com")
                .phone("6789456" + i)
                .address("Haifa")
                .description("The best friend")
                .build();
        new ContactListScreen(driver)
                .openContactForm()
                .fillContactForm(contact)
                .submitContactForm()
                .isContactAddedByName(contact.getName(), contact.getLastName());
    }

    @Test
    public void createNewContactSuccessReq() {
        int i = new Random().nextInt(1000) + 1000;

        Contact contact = Contact.builder()
                .name("Simon")
                .lastName("Wow" + i)
                .email("wow" + i + "@gmail.com")
                .phone("6789456" + i)
                .address("Haifa")
                .build();
        new ContactListScreen(driver)
                .openContactForm()
                .fillContactForm(contact)
                .submitContactForm()
                .isContactAddedByName(contact.getName(), contact.getLastName());
    }

    @Test
    public void CreateContactWithEmptyName() {
        Contact contact = Contact.builder()
                .lastName("Wow")
                .email("wow@gmail.com")
                .phone("678945645956")
                .address("Haifa")
                .description("The best friend")
                .build();
        new ContactListScreen(driver)
                .openContactForm()
                .fillContactForm(contact)
                .submitContactFormNegative()
                .isErrorMessageHasText("{name=must not be blank}");
    }


    @AfterClass
    public void postCondition() {
        new ContactListScreen(driver)
                .logout();
    }
}
