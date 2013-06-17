<?php
    // define DB connection data and connect to database
    require_once 'db_connect.php';

    // query string to retrieve all players
    $sql = "SELECT * FROM players";

    // prepare and execute the query
    $stmt = $link->prepare($sql);
    $status = $stmt->execute();

    // if there is an error retrieving the data then send the user an error message
    if ($status != true) {
        $error_info = $stmt->errorInfo();
        $error_message = "failed to retrieve players: {$error_info[2]} - error code {$error_info[0]}";
        require 'error.php';
        exit();
    }

    // if we get this far then we have managed to connect to and query the
    // database and we should generate a response page
?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <style>
            a img { border-style: none; }
            a:link, a:visited { text-decoration: none; }
            a:hover, a:active {text-decoration: underline;}
        </style>
        <title></title>
    </head>
    <body>
        <?php
        // if the page is being returned with an error message the print the message
        if (!empty($error_message)) {
            echo "<p>Error: $error_message</p>";
        }
        // determine how many players were retrieved
        $row_count = $stmt->rowCount();
        // if at least one player was retrieved from the database then display the
        // details of the players retrieved in a table
        if ($row_count > 0) {
        ?>
            <table>
                <tr>
                    <th>Team</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Age</th>
                    <th>Country of Origin</th>
                    <th>Position</th>
                    <th>Preferred Foot</th>
                    <th></th>
                </tr>
            <?php
            // use a sentinel-controlled loop to iterate through the players
            // retrieved and generate HTML code to display the details of each
            // player in a table row
            $row = $stmt->fetch();
            while ($row != NULL) {
            ?>
                <tr>
                    <td><?php echo $row['team']; ?></td>
                    <td><?php echo $row['firstName']; ?></td>
                    <td><?php echo $row['lastName']; ?></td>
                    <td><?php echo $row['age']; ?></td>
                    <td><?php echo $row['country_origin']; ?></td>
                    <td><?php echo $row['position']; ?></td>
                    <td><?php echo $row['pref_foot']; ?></td>
                    <td>
                        <!-- Create a link to the edit player form with the player id included as a required parameter -->
                        <a href="edit_player_form.php?id=<?php echo $row['id']; ?>"><img src="images/edit20.png" alt="Edit Player" /></a>
                        <!-- Create a link to the delete player script with the player id included as a required parameter
                             Note that JavaScript code is included to ensure that the user confirms this request -->
                        <a href="delete_player.php?id=<?php echo $row['id']; ?>"
                           onclick="return confirm('Are you sure you want to delete this player?');">
                            <img src="images/delete20.png" alt="Delete Player" />
                        </a>
                    </td>
                </tr>
            <?php
                $row = $stmt->fetch();
            }
            ?>
            </table>
        <?php
        }
        // if no player were retrieved from the database the print a message indicating this
        else {
            echo "<p>There are no players in the database.</p>";
        }
        echo '<p><a href="create_player_form.php"><img src="images/new20.png" alt="New Player" /> New Player</a></p>';
        ?>
        <p><a href="index.php">Home</a></p>
    </body>
</html>
