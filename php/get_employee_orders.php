<?php
    include_once dirname(__FILE__).'\get_orders.php';
    $con = new GetOrders();
    $con->getView();