<!DOCTYPE html>

<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
  <meta http-equiv="refresh" content="10">
  <title>Web Checkers | ${title}</title>
  <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>

<body>
<div class="page">

  <h1>Web Checkers | ${title}</h1>

  <!-- Provide a navigation bar -->
  <#include "nav-bar.ftl" />

  <div class="body">

    <!-- Provide a message to the user, if supplied. -->
    <#include "message.ftl" />

    <!-- Show a list of players for those signed in and a number of players for those not signed in -->
    <#if currentUser??>
      List of other players currently logged in:
      <br/>
      <br/>

      <#list usit as user>
        <a href="/game?redUser=${currentUser.name}&whiteUser=${user}">${user}</a>
        <br/>
      </#list>

    <#else>
      There are ${playerCount} players logged into Web Checkers at this moment.
    </#if>

    <!-- TODO: future content on the Home:
            to start games,
            spectating active games,
            or replay archived games
    -->

  </div>

</div>
</body>

</html>
