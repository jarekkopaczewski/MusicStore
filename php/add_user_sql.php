<?php

    class AddUser
    {
        private $server_name = "localhost";
        private $user_name = "admin";
        private $user_password = "theadminpass";
        private $database_name = "bd";
        private $connection = null;
        private $imie = null;
        private $nazwisko = null;
        private $email = null; 
        private $login = null;
        private $pass = null;
        private $miasto = null;
        private $ulica = null;
        private $numer = null; 
        private $kod = null;

        function __construct($imie, $nazwisko,  $email, $login, $pass, $phone, $miasto, $ulica, $numer, $kod)
        {
            $this->imie = $imie;
            $this->nazwisko = $nazwisko;
            $this->email = $email;
            $this->login = $login;
            $this->pass = $pass;
            $this->phone = $phone;
            $this->miasto = $miasto;
            $this->ulica = $ulica;
            $this->numer = $numer;
            $this->kod = $kod;
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

        function update()
        {
            $sql = "CALL dodajUzytkownika('$this->imie', '$this->nazwisko', '$this->email', '$this->phone', 
            '$this->login', '$this->pass', '$this->miasto', '$this->ulica', $this->numer, '$this->kod');";
            $this->connection->query($sql);
        }
    }

