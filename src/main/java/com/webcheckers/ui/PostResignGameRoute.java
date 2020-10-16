package com.webcheckers.ui;

import java.util.*;
import java.util.logging.Logger;
import spark.*;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.model.Game;
import com.webcheckers.util.Message;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class PostResignGameRoute implements Route {
    private static final Logger LOG = Logger.getLogger(PostResignGameRoute.class.getName());

    //String Constants
    //static final String

    //private final TemplateEngine templateEngine;
    private final GameCenter gameCenter;
    private final Gson gson;

    /**
     * The constructor for the {@code POST /resignGame} route handler.
     *
     * @param gameCenter
     *    The
     * @param gson
     *   The
     */
    public PostResignGameRoute(GameCenter gameCenter, Gson gson){
        this.gameCenter = gameCenter;
        this.gson = gson;
    }

    /**
     * Attempts to resign player from their game.
     *
     * @param request
     *   the HTTP request
     * @param response
     *   the HTTP response
     *
     * @return
     *   Message with info stored as JSON
     */
    @Override
    public Object handle(Request request, Response response){
        //gameCenter.endGame()
        //gameCenter.getGame()
        return gson.toJson(Message.info("Resigned successfully"), Message.class);
    }
}