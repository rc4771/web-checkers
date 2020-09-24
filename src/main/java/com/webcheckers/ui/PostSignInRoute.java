package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import static spark.Spark.halt;

/**
 * The {@code POST /signin} route handler.
 *
 * @author <a href='mailto:dja7394@rit.edu'>David Allen</a>
 */
public class PostSignInRoute implements Route {
  private static final Logger LOG = Logger.getLogger(PostSignInRoute.class.getName());

  public static final String USERNAME_PARAM = "username";
  public static final String PLAYER_SESSION_KEY = "currentUser";

  private final PlayerLobby playerLobby;
  private final TemplateEngine templateEngine;

  /**
   * The constructor for the {@code POST /signin} route handler.
   *
   * @param templateEngine
   *   the HTML template rendering engine
   */
  public PostSignInRoute(PlayerLobby playerLobby, final TemplateEngine templateEngine) {
    this.playerLobby = Objects.requireNonNull(playerLobby, "playerLobby is required");
    this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
    //
    LOG.config("PostSignInRoute is initialized.");
  }

  /**
   * Attempts to sign in a user with the username they entered.
   *
   * @param request
   *   the HTTP request
   * @param response
   *   the HTTP response
   *
   * @return
   *   the rendered HTML for the Home page
   */
  @Override
  public Object handle(Request request, Response response) {
    LOG.finer("PostSignInRoute is invoked.");
    //
    Map<String, Object> vm = new HashMap<>();
    vm.put("title", "Sign in?");

    final String username = request.queryParams(USERNAME_PARAM);
    final PlayerLobby.SignInResult signInResult = playerLobby.signInPlayer(username);

    switch (signInResult) {
      case INVALID_USERNAME: {
        vm.put(GetSignInRoute.ERROR_MESSAGE_ATTR, "The username you entered is invalid. Try again.");
        return templateEngine.render(new ModelAndView(vm, GetSignInRoute.VIEW_NAME));
      }
      case USERNAME_TAKEN: {
        vm.put(GetSignInRoute.ERROR_MESSAGE_ATTR, "The username you entered is already taken. Try again.");
        return templateEngine.render(new ModelAndView(vm, GetSignInRoute.VIEW_NAME));
      }
      case OK: {
        // Add the newly signed in player to the HTTP session
        final Session httpSession = request.session();
        httpSession.attribute(PLAYER_SESSION_KEY, playerLobby.getPlayer(username));
      }
    }

    response.redirect(WebServer.HOME_URL);
    halt();
    return null;
  }
}
