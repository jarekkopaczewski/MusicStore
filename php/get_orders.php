<?php

    class GetOrders
    {
        private $server_name = "localhost";
        private $user_name = "admin";
        private $user_password = "theadminpass";
        private $database_name = "bd";
        private $connection = null;

        function __construct()
        {
            $this->connection = new mysqli($this->server_name, $this->user_name, $this->user_password, $this->database_name);
            if ($this->connection->connect_error) 
            {
                die("Connection failed: " . $this->connection->connect_error);
            }
        }

        function __destruct() 
        {
            $this->connection->close(); 
        }

        function getView()
        {
            $sql = "SELECT * FROM zamowienia;";
            $query = $this->connection->query($sql);
            $rows = array();    
            
            while($row = mysqli_fetch_assoc($query)) 
            {
                $rows[] = $row;
            }
            
            echo json_encode($rows);
            return json_encode($rows);
        }
    }

