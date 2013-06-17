<?php
    $host = "";
    $database = "";
    $user = "";
    $pass = "";

    // connection string to mysql database server host and required database
    $dbinfo = "mysql:host=$host;dbname=$database";
    try {
        $link = new PDO($dbinfo, $user, $pass);
    } catch (PDOException $e) {
        $error_message =  'Could not connect to database: ' . $e->getMessage();
        require 'error.php';
        exit();
    }

?>
