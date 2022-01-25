<?php
    include_once dirname(__FILE__).'\get_view.php';
    $con = new GetWConstrain($_GET['category']);
    $con->getView();