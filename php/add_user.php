<?php
    include_once dirname(__FILE__).'\add_user_sql.php';
    $con = new AddUser($_GET['imie'], $_GET['nazwisko'], $_GET['email'], 
                       $_GET['login'], $_GET['pass'], $_GET['phone'], $_GET['miasto'], 
                       $_GET['ulica'], $_GET['numer'], $_GET['kod']);
    $con->update();