package com.webcheckers.ui;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.*;
import spark.*;

public class GetSignInRouteTest {
    private GetSignInRoute CuT;
    private Request request;
    private Session session;
    private Response response;

    private TemplateEngine engine;

    @BeforeEach
    public void setup() {
        engine = mock(TemplateEngine.class);
        response = mock(Response.class);
        session = mock(Session.class);
        request = mock(Request.class);
        CuT = new GetSignInRoute(engine);
    }

    @Test
    public void testConstructor_nullTemplateEngine() {
        assertThrows(NullPointerException.class, () -> new GetSignInRoute(null));
    }

    @Test
    public void testHandle() {
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewName(GetSignInRoute.VIEW_NAME);
    }
}
