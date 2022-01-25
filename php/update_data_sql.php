<?php

    class UpdateData
    {
        private $server_name = "localhost";
        private $user_name = "admin";
        private $user_password = "theadminpass";
        private $database_name = "bd";
        private $connection = null;
        private $miasto = null;
        private $ulica = null;
        private $numer = null; 
        private $kod = null;
        private $id = null;

        function __construct($miasto, $ulica, $numer, $kod, $id)
        {
            $this->miasto = $miasto;
            $this->ulica = $ulica;
            $this->numer = $numer;
            $this->kod = $kod;
            $this->id = $id;
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
            $sql = "CALL aktualizujAdres('$this->kod', '$this->miasto', $this->numer, '$this->ulica', $this->id);";
            $this->connection->query($sql);
        }
    }

