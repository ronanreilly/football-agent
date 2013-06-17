<?php
// if this page was requested using a POST request then process the form data
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    // if the book_id and title fields are not empty then process the form data
    if (!empty($_POST['player_id']) && !empty($_POST['team'])) {
        // read the form data
        $player_id = $_POST['player_id'];
        $team = $_POST['team'];
        $firstName = $_POST['firstName'];
        $lastName = $_POST['lastName'];
        $age = $_POST['age'];
        $country_origin = $_POST['country_origin'];
        $position = $_POST['position'];
        $pref_foot = $_POST['pref_foot'];

        // define DB connection data and connect to database
        require_once '../db_connect.php';

        // query string to execute including placeholders '?'
        $sql = "UPDATE players SET "
                . "team = ?, "
                . "firstName = ?, "
                . "lastName = ?, "
                . "age = ?, "
                . "country_origin = ?, "
                . "position = ?, "
                . "pref_foot = ? "
                . "WHERE id = ?";

        // parameters to be inserted into placeholders
        $params = array($team, $firstName, $lastName, $age, $country_origin, $position, $pref_foot, $player_id);

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
            $error_message = "failed to insert new player: {$error_info[2]} - error code {$error_info[0]}";
            echo '<status>Error - ' . $error_message . '</status>';
        }
    }
    // else if the book_id or title fields are empty then send the form back to the
    // user with an error message
    else {
        $error_message = "team field must not be empty";
        echo '<status>Error - ' . $error_message . '</status>';
    }
}
// else if this page was not requested using a POST request then ignore it
else {
    echo '<status>Error - POST request expected</status>';
}
?>
