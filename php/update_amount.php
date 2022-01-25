<?php
    include_once dirname(__FILE__).'\update_amount_sql.php';
    $con = new UpdateAmount($_GET['ilosc'], $_GET['kod'], $_GET['tabela']);
    $con->getRow();