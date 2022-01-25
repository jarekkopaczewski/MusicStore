<?php
    include_once dirname(__FILE__).'\GetTable.php';
    $con = new GetTable($_GET['tableName'], $_GET['rowId']);
    $con->getRow();