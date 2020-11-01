package com.webcheckers.ui;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import spark.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

@Tag("UI-tier")
public class PostSignInRouteTest {

    /*** The component-under-test (CuT).*/
    private PostSignInRoute CuT;

    // friendly object
    private PlayerLobby lobby;

    //to be mocked
    private Request request;
    private Response response;
    private Session session;
    private TemplateEngine engine;

    @BeforeEach
    public void setup() {
        request = Mockito.mock(Request.class);
        response = mock(Response.class);
        session = Mockito.mock(Session.class);
        when(request.session()).thenReturn(session);
        engine = mock(TemplateEngine.class);
        lobby = new PlayerLobby();
        CuT = new PostSignInRoute(lobby, engine);
    }

    @Test
    void testConstructor_nullLobby() {
        assertThrows(NullPointerException.class, () -> new PostSignInRoute(null, engine));
    }

    @Test
    void testConstructor_nullEngine() {
        assertThrows(NullPointerException.class, () -> new PostSignInRoute(lobby, null));
    }

    @Test
    void testHandle_ValidUserName(){
        final String VALID_USERNAME = "Username";
        when(request.queryParams(CuT.USERNAME_PARAM)).thenReturn(VALID_USERNAME);

        try {
            CuT.handle(request, response);
        } catch (HaltException e) {
            // expected
        }

        verify(response).redirect(WebServer.HOME_URL);
    }

    @Test
    void testHandle_InvalidUserName(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        final String INVALID_USERNAME = "!@#(*#%)+";
        when(request.queryParams(CuT.USERNAME_PARAM)).thenReturn(INVALID_USERNAME);

        try {
            CuT.handle(request, response);
        } catch (HaltException e) {
            // expected
        }

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewName(GetSignInRoute.VIEW_NAME);
        testHelper.assertViewModelAttribute(GetSignInRoute.ERROR_MESSAGE_ATTR, "The username you entered is invalid. Try again.");
    }

    @Test
    void testHandle_UsernameTaken(){
        final TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        final String VALID_USERNAME = "Username";
        lobby.signInPlayer(VALID_USERNAME);
        when(request.queryParams(eq(PostSignInRoute.USERNAME_PARAM))).thenReturn(VALID_USERNAME);

        try {
            CuT.handle(request, response);
        } catch (HaltException e) {
            // expected
        }

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();
        testHelper.assertViewName(GetSignInRoute.VIEW_NAME);
        testHelper.assertViewModelAttribute(GetSignInRoute.ERROR_MESSAGE_ATTR, "The username you entered is already taken. Try again.");
    }
}
