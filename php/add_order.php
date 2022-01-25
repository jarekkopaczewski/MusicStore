<?php
    include_once dirname(__FILE__).'\add_order_sql.php';
    $con = new AddOrder($_GET['typ'], $_GET['id']);
    $con->getRow();