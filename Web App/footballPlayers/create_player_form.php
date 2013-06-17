<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Your Own Player</title>
    </head>
    <body>
        <!-- if the form is being returned with an error message the print the message -->
        <?php if (!empty($error_message)) echo "<p>Error: $error_message</p>"; ?>
        <form action="create_player.php" method="POST">
            <table border="0">
                <tbody>
                    <tr>
                        <td>Team</td>
                        <td><input type="text" name="team" value="" /></td>
                    </tr>
                    <tr>
                        <td>First Name</td>
                        <td><input type="text" name="firstName" value="" /></td>
                    </tr>
                    <tr>
                        <td>Last Name</td>
                        <td><input type="text" name="lastName" value="" /></td>
                    </tr>
                    <tr>
                        <td>Age</td>
                        <td><input type="text" name="age" value="" /></td>
                    </tr>
                    <tr>
                        <td>Country of Origin</td>
                        <td><input type="text" name="country_origin" value="" /></td>
                    </tr>
                    <tr>
                        <td>Position</td>
                        <td><input type="text" name="position" value="" /></td>
                    </tr>
                    <tr>
                        <td>Preferred Foot</td>
                        <td><input type="text" name="pref_foot" value="" /></td>
                    </tr>
                </tbody>
            </table>
            <input type="submit" value="Create Player" />
        </form>
        <p><a href="index.php">Home</a></p>
    </body>
</html>
