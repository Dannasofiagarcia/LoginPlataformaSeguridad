# Login Plataforma Seguridad

# Informe final

## Desarrollo del proyecto 

Este proyecto lo realizamos con tres modulos principales: frontend, backend y almacenamiento de datos. Tenemos un backend desarrollado en Java que que almacena los datos en un archivo de texto plano, y el frontend utiliza los servicios del backend para modificar o utilizar los datos almacenados.

En el backend nos encargamos de realizar el proceso de encriptación de las contraseñas de los usuarios que están almacenadas o se van a registrar. Para este caso usamos la librería PBKDF2. Las contraseñas de los usuarios son encriptadas antes de que se guarden en el archivo de texto, por lo que al verificar el almacenamiento, el registro de la contraseña no es el texto original. Al momento del usuario iniciar sesión se encripta la contraseña ingresada para ser comparada con la contraseña almacenada que también está encriptada. Esto hace que la comparación de contraseña se demore menos que desencriptar la contraseña almacenada para compararla con la contraseña original ingresada.

En el frontend tenemos dos opciones al momento de iniciar el programa: podemos ingresar sesión con alguno de los usuarios previamente almacenados en el archivo de texto, o podemos registrar un usuario nuevo, esto almacenaría el nombre de usuario y la contraseña encriptada. También al momento de iniciar sesión con un usuario podemos realizar el cambio de contraseña. En el archivo de texto también almacenamos el ultimo inicio de sesión y esto lo podemos ver en el programa para saber cuando fue la ultima vez que había ingresado.

## Conclusiones:


- Para la seguridad de los datos, y de todos los usuarios que se registren en algún sitio web, es importante encriptar correctamente las contraseñas para proteger la información, ya que en caso de no ser encriptado quedarían expuestos todos los usuarios que están almacenados y esto puede ocasionar robo de información, mal uso del sitio, entre otros. 


## Developed by 🛠

* *Danna Sofia Garcia* [Danna](https://github.com/Dannasofiagarcia)
* *Christian David Flor*  [Christian](https://github.com/ChristianFlor)
* *Jhonnier Isaza Gonzalez*  [Jhonnier](https://github.com/Jhonnier20)
