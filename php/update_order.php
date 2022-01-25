<?php
    include_once dirname(__FILE__).'\update_order_sql.php';
    $con = new UpdateOrder($_GET['typ'], $_GET['id']);
    $con->getRow();