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

  private static final String USERNAME_PARAM = "username";

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

    if (!playerLobby.isValidUsername(username)) {
      // TODO Handle incorrect usernames
      vm.put(GetSignInRoute.ERROR_MESSAGE_ATTR, "The username you entered is invalid. Try again.");
      return templateEngine.render(new ModelAndView(vm, GetSignInRoute.VIEW_NAME));
    }

    if (playerLobby.isPlayerSignedIn(username)) {
      // TODO Handle usernames that have already been taken

      return templateEngine.render(new ModelAndView(vm, GetSignInRoute.VIEW_NAME));
    }

    return templateEngine.render(new ModelAndView(vm, GetHomeRoute.VIEW_NAME));
  }
}
