<?php
    include_once dirname(__FILE__).'\login_emp_sql.php';
    $con = new LoginEmp($_GET['email'], $_GET['pass']);
    $con->getRow();