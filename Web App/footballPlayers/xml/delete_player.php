<?php
// if this page was requested using a GET request then delete the book
if ($_SERVER['REQUEST_METHOD'] == 'GET') {
    // if book id is not empty then try to delete the book
    if (!empty($_GET['player_id'])) {
        // read book id from request
        $playerId = $_GET['player_id'];

        // define DB connection data and connect to database
        require_once '../db_connect.php';

        // query string to execute including placeholder '?' for book id
        $sql = "DELETE FROM players WHERE id = ?";

        // book id to be inserted into placeholder
        $params = array($playerId);

        // prepare and execute the query using parameters
        $stmt = $link->prepare($sql);
        $status = $stmt->execute($params);

        // if update executed ok then redirect the user to the view_books page;
        // redirection is used to prevent the request being accidently
        // resubmitted if the response page is reloaded by the user
        if ($status == true) {
            echo '<status>OK</status>';
        }
        // else if update did not execute ok then send the user an error message
        else {
            $error_info = $stmt->errorInfo();
            $error_message = "failed to delete player: {$error_info[2]} - error code {$error_info[0]}";
            echo '<status>Error - ' . $error_message . '</status>';
        }
    }
    // else if book id is empty then send the user back to the view books page
    // with an error message
    else {
        $error_message = "player id not specified";
        echo '<status>Error - ' . $error_message . '</status>';
    }
}
// if this page was not requested using a GET request then ignore it
else {
    echo '<status>Error - GET request expected</status>';
}
?>
