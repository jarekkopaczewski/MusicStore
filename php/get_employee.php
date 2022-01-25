<?php

    class GetEmployee
    {
        private $server_name = "localhost";
        private $user_name = "admin";
        private $user_password = "theadminpass";
        private $database_name = "bd";
        private $connection = null;
        private $type = null;

        function __construct($type)
        {
            $this->type = $type;
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
            if( $this->type == "sklep")
            {
                $sql = "SELECT l.KOD_KRESKOWY, l.NAZWA_PRODUKTU, l.DOSTEPNOSC, s.ILOSC FROM lista_prod l JOIN sklep s ON l.KOD_KRESKOWY = s.KOD_KRESKOWY;";
            }
            else if( $this->type == "magazyn")
            {
                 $sql = "SELECT l.KOD_KRESKOWY, l.NAZWA_PRODUKTU, l.DOSTEPNOSC, m.ILOSC FROM lista_prod l JOIN magazyn m ON l.KOD_KRESKOWY = m.KOD_KRESKOWY;";
            }
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

