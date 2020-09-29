package com.webcheckers.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import spark.*;

import com.webcheckers.util.Message;

/**
 * The UI Controller to GET the Home page.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class GetHomeRoute implements Route {
  //Values used in the view-model map for rendering home view.
  static final String TITLE_ATTR = "title";
  //Key in session attribute map for the player who started the session.
  static final String PLAYERLOBBY_KEY = "playerLobby";

  private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

  private static final Message WELCOME_MSG = Message.info("Welcome to the world of online Checkers.");

  public static final String VIEW_NAME = "home.ftl";
  public static final String PLAYER_COUNT_ATTR = "playerCount";
  public static final String PLAYER_LIST_ATTR = "playerList";
  public static final String CURRENT_USER_ATTR = "currentUser";
  public static final String CURRENT_USER_NAME_ATTR = "name";
  public static final String RED_USER_ATTR = "redUser";
  public static final String WHITE_USER_ATTR = "whiteUser";

  private final PlayerLobby playerLobby;
  private final TemplateEngine templateEngine;

  /**
   * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
   *
   * @param playerLobby
   *    The player lobby instance for handling log in related stuff
   * @param templateEngine
   *   the HTML template rendering engine
   */
  public GetHomeRoute(final PlayerLobby playerLobby, final TemplateEngine templateEngine) {
    this.playerLobby = Objects.requireNonNull(playerLobby, "playerLobby is required");
    this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
    //
    LOG.config("GetHomeRoute is initialized.");
  }

  /**
   * Render the WebCheckers Home page.
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
    LOG.finer("GetHomeRoute is invoked.");

    final Session httpSession = request.session();
    Map<String, Object> vm = new HashMap<>();

    vm.put("title", "Welcome!");
    vm.put("message", WELCOME_MSG);
    vm.put(PLAYER_COUNT_ATTR, playerLobby.getPlayerCount());

    // If the current session has a player logged in, they need to be displayed different information
    Player sessionPlayer;
    if ((sessionPlayer = httpSession.attribute(PostSignInRoute.PLAYER_SESSION_KEY)) != null) {
      Map<String, Object> vmCurrentUser = new HashMap<>();
      vmCurrentUser.put(CURRENT_USER_NAME_ATTR, sessionPlayer.getUsername());
      vm.put(CURRENT_USER_ATTR, vmCurrentUser);

      // Build and display the list of players, excluding the current one, to the home page
      List<String> playerUsernames = playerLobby.getPlayerUsernames(sessionPlayer.getUsername());
      vm.put("usit", playerUsernames);

      if (playerUsernames.size() > 0) {
        StringBuilder usernameList = new StringBuilder();
        for (String username : playerUsernames) {
          usernameList.append(", ").append(username);
        }

        // The .substring(..) is to remove the leading separator
        vm.put(PLAYER_LIST_ATTR, usernameList.substring(", ".length()));
      } else {
        vm.put(PLAYER_LIST_ATTR, "No other players currently logged in");
      }
    }

    return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
  }
}
