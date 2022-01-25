<?php
    include_once dirname(__FILE__).'\add_order_sql_2.php';
    $con = new AddOrder2($_GET['id_k'],$_GET['kod'], $_GET['ilosc'], $_GET['status'],);
    $con->getRow();