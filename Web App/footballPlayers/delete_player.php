<?php
// if this page was requested using a GET request then delete the player
if ($_SERVER['REQUEST_METHOD'] == 'GET') {
    // if player id is not empty then try to delete the player
    if (!empty($_GET['id'])) {
        // read player id from request
        $playerId = $_GET['id'];

        // define DB connection data and connect to database
        require_once 'db_connect.php';

        // query string to execute including placeholder '?' for player id
        $sql = "DELETE FROM players WHERE id = ?";

        // player id to be inserted into placeholder
        $params = array($playerId);

        // prepare and execute the query using parameters
        $stmt = $link->prepare($sql);
        $status = $stmt->execute($params);

        // if update executed ok then redirect the user to the view_players page;
        // redirection is used to prevent the request being accidently
        // resubmitted if the response page is reloaded by the user
        if ($status == true) {
            header("Location: view_players.php");
        }
        // else if update did not execute ok then send the user an error message
        else {
            $error_info = $stmt->errorInfo();
            $error_message = "failed to delete player: {$error_info[2]} - error code {$error_info[0]}";
            require 'error.php';
        }
    }
    // else if player id is empty then send the user back to the view players page
    // with an error message
    else {
        $error_message = "player id not specified";
        require 'view_players.php';
    }
}
// if this page was not requested using a GET request then ignore it
else {
}
?>
