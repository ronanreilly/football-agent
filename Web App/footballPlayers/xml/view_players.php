<?php
    // define DB connection data and connect to database
    require_once '../db_connect.php';

    // query string to retrieve all books
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
    header("Content-Type: text/xml");
?>
<players>
<?php
    $row_count = $stmt->rowCount();
    if ($row_count > 0) {
        $row = $stmt->fetch();
        while ($row != NULL) {
        ?>
            <player id="<?php echo $row['id']; ?>">
                <team><?php echo $row['team']; ?></team>
                <firstName><?php echo $row['firstName']; ?></firstName>
                <lastName><?php echo $row['lastName']; ?></lastName>
                <age><?php echo $row['age']; ?></age>
                <country_origin><?php echo $row['country_origin']; ?></country_origin>
                <position><?php echo $row['position']; ?></position>
                <pref_foot><?php echo $row['pref_foot']; ?></pref_foot>
            </player>
        <?php
            $row = $stmt->fetch();
        }
    }
?>
</players>
