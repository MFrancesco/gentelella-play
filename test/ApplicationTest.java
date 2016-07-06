import org.junit.Test;
import play.twirl.api.Content;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import views.html.sample.index;
import views.html.utils.error500;

/**
 *
 * Simple (JUnit) tests that can call all parts of a play app.
 * If you are interested in mocking a whole application, see the wiki for more details.
 * This test are simply checking that some template are rendered correctly
 */
public class ApplicationTest {

    @Test
    public void simpleCheck() {
        int a = 1 + 1;
        assertEquals(2, a);
    }

    @Test
    public void renderIndex() {
        Content html = index.render();
        assertEquals("text/html", html.contentType());
        assertTrue(html.body().contains("Gentellela Admin is a free to use Bootstrap admin template."));
    }


    @Test
    public void renderServerError() {
        Content html = error500.render("My error 500 message");
        assertEquals("text/html", html.contentType());
        assertTrue(html.body().contains("My error 500 message"));
    }

}
