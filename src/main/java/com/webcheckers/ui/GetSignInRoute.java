package com.webcheckers.ui;

import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * The UI Controller to GET the Sign In page.
 *
 * @author <a href='mailto:dja7394@rit.edu'>David Allen</a>
 */
public class GetSignInRoute implements Route {
  private static final Logger LOG = Logger.getLogger(GetSignInRoute.class.getName());

  public static final String VIEW_NAME = "signin.ftl";
  public static final String ERROR_MESSAGE_ATTR = "errorMsg";

  private final TemplateEngine templateEngine;

  /**
   * The constructor for the {@code GET /signin} route handler.
   *
   * @param templateEngine
   *   the HTML template rendering engine
   */
  public GetSignInRoute(final TemplateEngine templateEngine) {
    this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
    //
    LOG.config("GetSignInRoute is initialized.");
  }

  /**
   * Render the sign in page.
   *
   * @param request
   *   the HTTP request
   * @param response
   *   the HTTP response
   *
   * @return
   *   the rendered HTML for the sign in page
   */
  @Override
  public Object handle(Request request, Response response) {
    LOG.finer("GetSignInRoute is invoked.");
    //
    Map<String, Object> vm = new HashMap<>();
    vm.put("title", "Please sign in");

    // render the View
    return templateEngine.render(new ModelAndView(vm , VIEW_NAME));
  }
}
