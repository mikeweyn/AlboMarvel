# README #

Api Rest para consultas de Comic de Marvel para Albo

Objetivos
-------
* tener actualizado todo el listado de escritores, editores y coloristas de cómics.
* Obtener los otros héroes con los cuales nuestro personaje ha interactuado  en cada uno de los cómics.
* Actualizaciones Diarias Automáticas.

### Tecnologias Implementadas ###

* Java 11
* Spting Boot
* Data Base H2
* Docker
* Maven

### Arquitectura  ###

Clean architecture es un conjunto de principios cuya finalidad principal es ocultar los detalles de implementación a la lógica de dominio de la aplicación.

De esta manera mantenemos aislada la lógica, consiguiendo tener una lógica mucho más mantenible y escalable en el tiempo. [leer más](http://xurxodev.com/por-que-utilizo-clean-architecture-en-mis-proyectos/)

### Patron de Diseños ###
Builder (patrón de diseño), Como patrón de diseño, el patrón builder (Constructor) se usa para permitir la creación de una variedad de objetos complejos desde un objeto fuente (Producto), el objeto fuente se compone de una variedad de partes que contribuyen individualmente a la creación de cada objeto complejo a través de un conjunto de llamadas secuenciales a una implementación específica que extienda la clase Abstract Builder. Así, cada implementación existente de Abstract Builder construirá un objeto complejo Producto de una forma diferente deseada. [Leer más](https://es.wikipedia.org/wiki/Builder_(patr%C3%B3n_de_dise%C3%B1o))

### Servicios  Expuestos Ambiente Producción ###

~~~
http://test.albo.mx/marvel/colaborators/{nameSuperHero}

http://test.albo.mx/marvel/characters/{nameSuperHero}

http://test.albo.mx/marvel/superhero/{nameSuperHero}
~~~

### Servicios  Expuestos Ambiente Desarrollo ###

~~~
localhost:8070/marvel/colaborators/{nameSuperHero}

localhost:8070/marvel/characters/{nameSuperHero}

localhost:8070/marvel/superhero/{nameSuperHero}
~~~


### Cron Jobs ###
~~~
someJob.cron = 0 30 8 * * *
~~~

Parametros de la Aplicación
--------

| Parameter         | Description                                                                         |
|-------------------|-------------------------------------------------------------------------------------|
| {nameSuperHero}   | Nombre del Super Heroe de Marvel                                                    |
| marvel.public.key | clave publica para poder acceder a los Servicios de Marvel                          |
| marvel.private    | clave privada para poder acceder a los Servicios de Marvel                          |
| marvel.ts.key     | una marca de tiempo (u otra cadena larga que puede cambiar solicitud por solicitud) |
| someJob.cron    | Configuración para la Actualización automatica.                                     |

Parametros de la Base de Datos H2
--------
| Parameter         | Description                                      |
|-------------------|--------------------------------------------------|
| spring.datasource.url  | url del almacenamiento en memoria                |
| spring.datasource.driverClassName | Driver para la conexión*                         |
| spring.datasource.username    | Usuario (albo)                                   |
| spring.datasource.password    | Contraseña (marvel)                              |
|spring.sql.init.data-locations   | Ubicacion del archivo sql para generar consultas |

Puede Acceder aplicacion de la BD a través 
~~~
http://localhost:8070/h2-console
~~~

Autenticación para aplicaciones del lado del servidor (API MARVEL)
--
Las aplicaciones del lado del servidor deben pasar dos parámetros además del parámetro apikey:

ts : una marca de tiempo (u otra cadena larga que puede cambiar solicitud por solicitud)
hash : un resumen md5 del parámetro ts, su clave privada y su clave pública (p. ej. md5(ts+privateKey+publicKey)
Por ejemplo, un usuario con una clave pública de "1234" y una clave privada de "abcd" podría construir una llamada válida de la siguiente manera: http://gateway.marvel.com/v1/public/comics?ts=1&apikey=1234&hash=ffd275c5130566a2916217b101f26150 (el valor hash es el resumen md5 de 1abcd1234)


Flujo de detección de información
------------
### Busqueda de colaborators

| Parameter | Description                  |
| ------------- |------------------------------|
| Método  | GET                          |
| URL  | http://test.albo.mx/marvel/colaborators/{nameSuperHero}   |

1. ingresa a la url http://test.albo.mx/marvel/colaborators/{nameSuperHero}.
2. Generea el hash en formato md5 para acceder a la api de Marvel
3. Se conecta a la api de Marvel http://gateway.marvel.com/v1/public/characters?name={name}&ts={ts}&apikey={apikey}&hash={hash}
4. Extrae la información de Super Heroe.
5. Guarda la transaccion para su actualización, esta la hace en la BD H2 que funciona como una base de datos en memoria que es perfecta para nuestras aplicaciones de Spring Boot.
6. Muestra la Información en formato Json.

```json
{
    "last_sync": "2022-05-10T10:48:08.386+00:00",
    "editors": [
        {
            "name": "Jeff Youngquist"
        }
    ],
    "writers": [
        {
            "name": "Christopher Cantwell"
        }
    ],
    "colorists": [
        {
            "name": "Frank D'ARMATA"
        }
    ]
}
```

### Busqueda de characters


| Parameter | Description                  |
| ------------- |------------------------------|
| Método  | GET                          |
| URL  | http://test.albo.mx/marvel/characters/{nameSuperHero}   |


1. ingresa a la url http://test.albo.mx/marvel/characters/{nameSuperHero}.
2. Generea el hash en formato md5 para acceder a la api de Marvel
3. Se conecta a la api de Marvel http://gateway.marvel.com/v1/public/characters?name={name}&ts={ts}&apikey={apikey}&hash={hash}
4. Extrae la información de Super Heroe.
5. Guarda la transaccion para su actualización, esta la hace en la BD H2 que funciona como una base de datos en memoria que es perfecta para nuestras aplicaciones de Spring Boot.
6. Muestra la Información en formato Json.

```json
[
  {
    "last_sync": "2022-05-10T10:48:08.386+00:00",
    "character": "Beast",
    "comics": [
      {
        "name": "Indestructible Hulk (2012) #18"
      },
      {
        "name": "Indestructible Hulk (2012) #17"
      }
    ]
  },
  {
    "character": "Betty Ross",
    "comics": [
      {
        "name": "Indestructible Hulk (2012) #15"
      }
    ]
  },
  {
    "character": "Black Knight (Sir Percy of Scandia)",
    "comics": [
      {
        "name": "Indestructible Hulk (2012) #13"
      },
      {
        "name": "Indestructible Hulk (2012) #12"
      }
    ]
  }
]
```
### Busqueda de SuperHero (Opcional)


| Parameter | Description                  |
| ------------- |------------------------------|
| Método  | GET                          |
| URL  | http://test.albo.mx/marvel/superhero/{nameSuperHero}   |


1. ingresa a la url http://test.albo.mx/marvel/superhero/{nameSuperHero}.
2. Generea el hash en formato md5 para acceder a la api de Marvel
3. Se conecta a la api de Marvel http://gateway.marvel.com/v1/public/characters?name={name}&ts={ts}&apikey={apikey}&hash={hash}
4. Extrae la información de Super Heroe.
5. Guarda la transaccion para su actualización, esta la hace en la BD H2 que funciona como una base de datos en memoria que es perfecta para nuestras aplicaciones de Spring Boot.
6. Muestra la Información en formato Json.

```json
{
    "id": 1009368,
    "name": "Iron Man",
    "description": "Wounded, captured and forced to build a weapon by his enemies, billionaire industrialist Tony Stark instead created an advanced suit of armor to save his life and escape captivity. Now with a new outlook on life, Tony uses his money and intelligence to make the world a safer, better place as Iron Man."
}
```

### Actualización Automatica de SuperHero

1. Esta se Ejecuta todos los día a las 8:30 am (esta hora es modificable en la properties variable `someJob.cron` )
2. Busca una lista de los superHero en la BD H2
3. Si tiene registro actualiza el contenido.
4. Se genera un log.inf `Actualizando  Super Hero`


Compilación
--
### assemble.sh
construcción del aplicativo que exponen los servicios.

Para la contrucción de la imagen correr este comando  `/bin/bash assemble.sh`, esta Genera una copilación de la aplicación mas el Jar del artefacto.

### avengers.sh

construcción del aplicativo que exponen los servicios.

Para la contrucción de la imagen docker correr este comando  `/bin/bash avengers.sh`,
esta Genera una imagen Docker que es expuesta en el puerto 80

Requerimientos
--
1. Intalar Java 11
2. Instalar Maven 3.6
3. Instalar Docker

