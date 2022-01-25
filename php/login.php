<?php
    include_once dirname(__FILE__).'\login_sql.php';
    $con = new LoginQue($_GET['email'], $_GET['pass']);
    $con->getRow();