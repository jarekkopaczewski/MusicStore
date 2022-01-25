<?php

    class LoginQue
    {
        private $server_name = "localhost";
        private $user_name = "admin";
        private $user_password = "theadminpass";
        private $database_name = "bd";
        private $connection = null;
        private $email = null;
        private $pass = null;

        function __construct($email, $pass)
        {
            $this->email = $email;
            $this->pass = $pass;
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
            $sql = "SELECT k.ID_KLIENTA FROM klienci k JOIN dane_osobowe d ON d.ID_DANYCH = k.ID_DANYCH WHERE email = '$this->email' AND HASLO = '$this->pass';";
            $query = $this->connection->query($sql);
            $row = mysqli_fetch_assoc($query);
            echo json_encode($row);
            return json_encode($row);
        }
    }

