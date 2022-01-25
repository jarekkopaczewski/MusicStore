<?php
    include_once dirname(__FILE__).'\get_address_sql.php';
    $con = new GetAddress($_GET['id']);
    $con->getRow();