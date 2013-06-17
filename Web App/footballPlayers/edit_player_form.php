<?php
    // if this page was not requested using a GET request then ignore it
    if ($_SERVER['REQUEST_METHOD'] != 'GET') {
        exit();
    }

    // if this player id is not specified then return send the user back the
    // view players page with an error message
    if (!isset($_GET['id'])) {
        $error_message = "player id not specified";
        require 'error.php';
        exit();
    }

    // define DB connection data and connect to database
    require_once 'db_connect.php';

    // query string to execute including placeholder '?' for player id
    $sql = "SELECT * FROM players WHERE id = ?";

    // player id to be inserted into placeholder
    $params = array($_GET['id']);

    // prepare and execute the query using parameters
    $stmt = $link->prepare($sql);
    $status = $stmt->execute($params);

    // if there was an error retrieving the book from the database then
    // send the user an error message
    if ($status != true) {
        $error_info = $stmt->errorInfo();
        $error_message = "failed to retrieve player: {$error_info[2]} - error code {$error_info[0]}";
        require 'error.php';
        exit();
    }

    // determine how many players where retrieved -- should be only one
    $row_count = $stmt->rowCount();
    // if more than one player was retrieved then send the user an error message
    if ($row_count != 1) {
        $error_message = "failed to retrieve player: duplicate copies of player id found -- contact system administrator";
        require 'error.php';
        exit();
    }

    // if we get this far then we has retrieved the player to be edited and
    // we can display the edit player form
    $player = $stmt->fetch();
?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title></title>
    </head>
    <body>
        <!-- if the form is being returned with an error message the print the message -->
        <?php if (!empty($error_message)) echo "<p>Error: $error_message</p>"; ?>
        <form action="edit_player.php" method="POST">
            <!-- insert player id as a hidden field on form so that
                 the edit_player script knows which player to update in
                 the database -->
            <input type="hidden" name="id" value="<?php echo $player['id']; ?>" />
            <table border="0">
                <tbody>
                    <tr>
                        <td>Title</td>
                        <td><input type="text" name="team" value="<?php echo $player['team']; ?>" /></td>
                    </tr>
                    <tr>
                        <td>First Name</td>
                        <td><input type="text" name="firstName" value="<?php echo $player['firstName']; ?>" /></td>
                    </tr>
                    <tr>
                        <td>Last Name</td>
                        <td><input type="text" name="lastName" value="<?php echo $player['lastName']; ?>" /></td>
                    </tr>
                    <tr>
                        <td>Age</td>
                        <td><input type="text" name="age" value="<?php echo $player['age']; ?>" /></td>
                    </tr>
                    <tr>
                        <td>Country of Origin</td>
                        <td><input type="text" name="country_origin" value="<?php echo $player['country_origin']; ?>" /></td>
                    </tr>
                    <tr>
                        <td>Position</td>
                        <td><input type="text" name="position" value="<?php echo $player['position']; ?>" /></td>
                    </tr>
                    <tr>
                        <td>Preferred Foot</td>
                        <td><input type="text" name="pref_foot" value="<?php echo $player['pref_foot']; ?>" /></td>
                    </tr>
                </tbody>
            </table>
            <input type="submit" value="Update Player" />
        </form>
        <p><a href="index.php">Home</a></p>
    </body>
</html>
