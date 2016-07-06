import org.junit.*;

import play.mvc.*;
import play.test.*;

import static play.test.Helpers.*;
import static org.junit.Assert.*;

import static org.fluentlenium.core.filter.FilterConstructor.*;

public class IntegrationTest {

    /**
     * add your integration test here
     * in this example we just check if the welcome page is being shown
     */
    @Test
    public void test() {
        running(testServer(3333, fakeApplication()), HTMLUNIT, browser -> {
            System.out.println("Executing test");
            browser.goTo("http://localhost:3333");
            assertTrue(browser.pageSource().contains("Gentellela Admin is a free to use Bootstrap admin template."));
        });
    }

}
