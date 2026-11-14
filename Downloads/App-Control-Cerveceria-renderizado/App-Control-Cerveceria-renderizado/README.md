# App Control Cerveceria
Una web app para controlar solamente con tu mano la producción de una cerveceria, de la manera más rápida y segura

# CÓMO EJECUTARLO

1. Oracle XE con el usuario del proyecto: ejecutar en orden `Database/Script_DATABASE.sql`, `Database/ProcedimientosTx.sql` y `Database/Script_Datos_Iniciales.sql`.
2. Copiar el driver `ojdbc11.jar` en `lib/`.
3. `./build.sh` (en Windows, desde Git Bash). Genera `cerveceria.war`.
4. Definir las variables de entorno `DB_USER` y `DB_PASS` (y `DB_URL` si el servicio no es `XEPDB1`, p. ej. `jdbc:oracle:thin:@localhost:1521/freepdb1`) **antes** de iniciar Tomcat 10.1 y copiar el war a `tomcat/webapps`.
5. Abrir `http://localhost:8080/cerveceria/`. Si el servidor no llega a la base, la pantalla muestra un aviso rojo con el motivo.
