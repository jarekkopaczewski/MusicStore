# Table of contents
* [General info](#general-info)
* [Built With](#built-with)
* [Espresso test](#espresso-test)
* [Serialized class](#serialized-class)
* [Example of database connection](#example-of-database-connection)
* [Example of PHP script](#example-of-php-script)
* [License](#license)

# About The Project

## General info
Music store app connected to MySQL database hosted on the local XAMPP server. The application is written in Kotlin for the Android devices. Access to the database is done by php scripts that invoke SQL queries and return responses as a JSON array/object. The application uses the Volley library to send URL requests and GSON library to process responses to the objects.

## Espresso test
<img src="https://github.com/jarekkopaczewski/music_store/blob/42b8d69d67fb717a10f988b8de4b53004fa91962/pres.gif" height="750"/>

## Built With

* [Kotlin](https://kotlinlang.org/)
* [PHP](https://www.php.net/)
* [MySQL](https://www.mysql.com/)
* [XAMPP](https://www.apachefriends.org/pl/index.html)
* [Gradle](https://gradle.org/)
* [IntelliJ IDEA](https://www.jetbrains.com/idea/)
* [Volley](https://github.com/google/volley)
* [Gson](https://github.com/google/gson)

## Serialized class 
Example of class used to parse from JSON objects.

```kotlin
package com.example.musicstore.data

import com.google.gson.annotations.SerializedName

data class Address(
    @SerializedName("MIASTO") val miasto: String,
    @SerializedName("ULICA") val ulica: String,
    @SerializedName("NUMER_MIESZKANIA") val numer_domu: Int,
    @SerializedName("KOD_POCZTOWY") val kod_pocztowy: String
)
```
## Example of database connection

```kotlin
        fun getAddressBase(context: Context): Boolean {
            queue = Volley.newRequestQueue(context)
            val request = StringRequest(
                Request.Method.GET, "http://192.168.0.32/get_address.php?id=${LoginInterface.getClientID()}",
                {
                    if (LoginInterface.getType() == Type.K) {
                        currentAddress = Gson().fromJson(it, Address::class.java)
                        if (currentAddress.miasto != "" && currentAddress.ulica != "" && currentAddress.numer_domu != 0 && currentAddress.kod_pocztowy != "")
                            LoginInterface.setAddressState(true)
                        println("pobrano adres.")
                    }
                })
            {
                VolleyLog.e(it, "Unhandled exception %s", it.toString());
            }
            queue.add(request)
            return true
        }

```
## Example of PHP script
```php
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
```

## License

Distributed under the Apache-2.0 License.

<p align="right">(<a href="#top">back to top</a>)</p>
