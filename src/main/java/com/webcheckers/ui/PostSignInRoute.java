package com.webcheckers.ui;

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

  private final TemplateEngine templateEngine;

  /**
   * The constructor for the {@code POST /signin} route handler.
   *
   * @param templateEngine
   *   the HTML template rendering engine
   */
  public PostSignInRoute(final TemplateEngine templateEngine) {
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

    response.redirect(WebServer.HOME_URL);
    halt();
    return null;
  }
}
