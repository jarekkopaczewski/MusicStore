<?php
    include_once dirname(__FILE__).'\get_employee.php';
    $con = new GetEmployee($_GET['type']);
    $con->getView();