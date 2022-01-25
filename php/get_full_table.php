<?php
    include_once dirname(__FILE__).'\get_w_o_constrains.php';
    $con = new GetWOConst($_GET['tableName'], $_GET['selectedRows']);
    $con->getTable();