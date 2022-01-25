<?php

    class AddOrder2
    {
        private $server_name = "localhost";
        private $user_name = "admin";
        private $user_password = "theadminpass";
        private $database_name = "bd";
        private $connection = null;
        private $id_k = null;
        private $kod = null;
        private $ilosc = null;
        private $status = null;

        function __construct($id_k, $kod, $ilosc, $status)
        {
            $this->id_k = $id_k;
            $this->kod = $kod;
            $this->ilosc = $ilosc;
            $this->status = $status;
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

        function getRow()
        {
            $sql = "CALL dodajProduktyDoZamowienia($this->id_k, $this->kod, $this->ilosc, $this->status);";
            $query = $this->connection->query($sql);
            $row = mysqli_fetch_assoc($query);
            echo json_encode($row);
            return json_encode($row);
        }
    }

