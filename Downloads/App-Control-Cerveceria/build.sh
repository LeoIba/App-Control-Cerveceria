#!/bin/sh
# Compila el backend y arma cerveceria.war (backend + FrontEnd) para copiarlo a tomcat/webapps.
# Requisitos: JDK 17+ (javac y jar en el PATH) y el driver de Oracle en lib/ojdbc11.jar.
# En Windows ejecutarlo desde Git Bash.
set -e
cd "$(dirname "$0")"
OJDBC="${OJDBC:-lib/ojdbc11.jar}"
if [ ! -f "$OJDBC" ]; then
    echo "Falta $OJDBC (driver JDBC de Oracle). Bajalo de oracle.com o de Maven Central (com.oracle.database.jdbc:ojdbc11) y copialo en lib/"
    exit 1
fi
rm -rf build cerveceria.war
mkdir -p build/WEB-INF/classes build/WEB-INF/lib
javac -encoding UTF-8 -cp "lib/*" -d build/WEB-INF/classes $(find BackEnd -name '*.java')
cp lib/gson-2.10.1.jar "$OJDBC" build/WEB-INF/lib/   # la API de servlets la aporta Tomcat
cp FrontEnd/* build/
(cd build && jar cf ../cerveceria.war .)
echo "Listo: cerveceria.war -> copialo a tomcat/webapps y abrí http://localhost:8080/cerveceria/"
