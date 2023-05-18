# NetCap Viasat

NetCap was developed during the hackathon Inatel App Challenge 2k23 in partnership with Viasat.
This app was built using Java, Spring Boot and Thymeleaf.

## Features

This app receives the traffic information from a server and manipulate this data to provide useful information for the user such as, the current consumption, the history and the applications/process that are most consuming the internet plan.

## Usage

The folder *trafficanalyzer* contains a Python application to collect the network packages and generate the payload to be sent to the application using socket connection.

The application connects to the Python server using socket connection. The connection can be configured using the following properties:

```
netcap:
  connection:
    socket:
      port: 50000
      host: localhost
```

https://github.com/aduilio/inatel-app-challenge-2k23/assets/7264464/dadfb0ee-04fe-40eb-88a8-b1aa87254927
