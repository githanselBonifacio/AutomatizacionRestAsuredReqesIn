
Feature: utilizacion de recursos en reqresin
        yo como usuario
        quiero poder acceder a los recursos de la API
        para utilizar los recursos

  Scenario: registro exitoso
    Given el usuario está en la página de registro e ingresa su correo como "eve.holt@reqres.in" y "pistol" como contraseña
    When cuando el usuario hace una petición de registrar
    Then el usuario deberá ver un código id de usuario y un token de respuesta

  Scenario: busqueda de recurso por id
    When el usuario ingresa al recurso con in "2"
    Then el usuario deberá ver los datos del recurso
