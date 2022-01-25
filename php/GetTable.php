<?php

    class GetTable
    {
        private $server_name = "localhost";
        private $user_name = "admin";
        private $user_password = "theadminpass";
        private $database_name = "bd";
        private $connection = null;
        private $tableName = null;
        private $rowId = null;

        function __construct($tableName, $rowId)
        {
            $this->tableName = $tableName;
            $this->rowId = $rowId;
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

        function getTable()
        {
            $sql = "SELECT * FROM ".$this->tableName;
            $query = $this->connection->query($sql);
            $rows = array();
            
            while($row = mysqli_fetch_assoc($query)) 
            {
                $rows[] = $row;
            }
            
            echo json_encode($rows);
            return json_encode($rows);
        }

        function getRow()
        {
            $sql = "SELECT kategoria FROM ".$this->tableName." WHERE ID_KATEGORII = ".$this->rowId.";";
            $query = $this->connection->query($sql);
            $row = mysqli_fetch_assoc($query);
            echo json_encode($row);
            return json_encode($row);
        }
    }

