<?php
// if this page was requested using a POST request then process the form data
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    // if the team field is not empty then process the form data
    if (!empty($_POST['team'])) {
        // read form data
        $team = $_POST['team'];
        $firstName = $_POST['firstName'];
        $lastName = $_POST['lastName'];
        $age = $_POST['age'];
        $country_origin = $_POST['country_origin'];
        $position = $_POST['position'];
        $pref_foot = $_POST['pref_foot'];

        // define DB connection data and connect to database
        require_once 'db_connect.php';

        // query string to execute including placeholders '?'
        $sql = "INSERT INTO players(team, firstName, lastName, age, country_origin, position, pref_foot) VALUES "
             . "(?, ?, ?, ?, ?, ?, ?)";

        // parameters to be inserted into placeholders
        $params = array($team, $firstName, $lastName, $age, $country_origin, $position, $pref_foot);

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
            $error_message = "failed to insert new player: {$error_info[2]} - error code {$error_info[0]}";
            require 'error.php';
        }
    }
    // else if the title field is empty then send the form back to the
    // user with an error message
    else {
        $error_message = "team field must not be empty";
        require 'create_player_form.php';
    }
}
// if this page was not requested using a POST request then ignore it
else {
}
?>
