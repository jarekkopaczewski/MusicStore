<?php
    include_once dirname(__FILE__).'\update_data_sql.php';
    $con = new UpdateData($_GET['miasto'], $_GET['ulica'], $_GET['numer'], $_GET['kod'], $_GET['id']);
    $con->update();