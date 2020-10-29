package com.webcheckers.ui;

import java.util.*;
import java.util.logging.Logger;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import spark.*;

import com.webcheckers.util.Message;

import static spark.Spark.halt;

/**
 * The UI Controller to GET the Home page.
 *
 * @author <a href='mailto:bdbvse@rit.edu'>Bryan Basham</a>
 */
public class GetHomeRoute implements Route {
  /** Values used in the view-model map for rendering home view. */
  static final String TITLE_ATTR = "title";

  /** handles logging */
  private static final Logger LOG = Logger.getLogger(GetHomeRoute.class.getName());

  /** Welcome message */
  private static final Message WELCOME_MSG = Message.info("Welcome to the world of online Checkers.");

  // Attributes

  /** Homepage FTL file */
  public static final String VIEW_NAME = "home.ftl";

  /** The player count attribute */
  public static final String PLAYER_COUNT_ATTR = "playerCount";

  /** The player list attribute */
  public static final String PLAYER_LIST_ATTR = "playerList";

  /** The current user attribute */
  public static final String CURRENT_USER_ATTR = "currentUser";

  /** The name attribute */
  public static final String CURRENT_USER_NAME_ATTR = "name";

  /** The opponent's name attribute */
  public static final String OPPONENT_USER_ATTR = "opponent";

  /** The error message attribute */
  public static final String ERROR_MESSAGE_ATTR = "errMsg";

  /** The message attribute */
  public static final String MESSAGE_ATTR = "message";

  /** The AI player's index attribute (if there is an AI player) */
  public static final String AI_OPPONENT_ATTR = "aiplayer";

  /** The list of AI player names (w/ the emoji) */
  public static final String[] AI_NAMES = {"\uD83E\uDD16Mrs. Hudson", "\uD83E\uDD16Inspector Lestrade",
          "\uD83E\uDD16Doctor Watson", "\uD83E\uDD16Enola Holmes", "\uD83E\uDD16Mycroft Holmes",
          "\uD83E\uDD16Professor Moriarty", "\uD83E\uDD16Sherlock Holmes"};

  // State

  /** Stores the players */
  private final PlayerLobby playerLobby;

  /** Stores all the games */
  private final GameCenter gameCenter;

  /** Renders the webpage */
  private final TemplateEngine templateEngine;

  /**
   * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
   *
   * @param playerLobby
   *    The player lobby instance for handling log in related stuff
   * @param templateEngine
   *   the HTML template rendering engine
   */
  public GetHomeRoute(final PlayerLobby playerLobby, final GameCenter gameCenter, final TemplateEngine templateEngine) {
    this.playerLobby = Objects.requireNonNull(playerLobby, "playerLobby is required");
    this.gameCenter = Objects.requireNonNull(gameCenter, "gameCenter is required");
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

    if (request.queryParams(ERROR_MESSAGE_ATTR) != null) {
      vm.put(MESSAGE_ATTR, Message.error(request.queryParams(ERROR_MESSAGE_ATTR)));
    }

    // If the current session has a player logged in, they need to be displayed different information
    Player sessionPlayer;
    if ((sessionPlayer = httpSession.attribute(PostSignInRoute.PLAYER_SESSION_KEY)) != null) {
      // Check to see if the player is in a game, in which case redirect them to it
      int gameID;
      if ((gameID = gameCenter.getGameFromPlayer(sessionPlayer)) != -1) {
        response.redirect(String.format("%s?%s=%d", WebServer.GAME_URL, GetGameRoute.GAME_ID_ATTR, gameID));
        halt();
        return null;
      }

      // If not in a game, then show them the home screen with the other players shown on it

      Map<String, Object> vmCurrentUser = new HashMap<>();
      vmCurrentUser.put(CURRENT_USER_NAME_ATTR, sessionPlayer.getName());
      vm.put(CURRENT_USER_ATTR, vmCurrentUser);

      // Build and display the list of players, excluding the current one, to the home page
      List<String[]> displayList = getDisplayList(sessionPlayer.getName());
      vm.put(PLAYER_LIST_ATTR, displayList.size() > 0 ? displayList : null);
    }

    return templateEngine.render(new ModelAndView(vm, VIEW_NAME));
  }

  private List<String[]> getDisplayList(String sessionPlayerName) {
    List<String> playerUsernames = playerLobby.getPlayerUsernames(sessionPlayerName);
    List<String[]> displayList = new ArrayList<>();

    for (int i = 0; i < AI_NAMES.length; i++) {
      float score = (i + 1) / (float) AI_NAMES.length;
      displayList.add(new String[] {AI_NAMES[i], String.format("%s?%s=%d", WebServer.GAME_URL, AI_OPPONENT_ATTR, i), Float.toString(score)});
    }

    for (String username : playerUsernames) {
      float score = new Random().nextFloat();
      displayList.add(new String[] {username, String.format("/game?%s=%s", OPPONENT_USER_ATTR, username), Float.toString(score)});
    }

    return displayList;
  }
}
