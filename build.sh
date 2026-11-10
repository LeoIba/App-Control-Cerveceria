#!/bin/sh
# Compila el backend y arma cerveceria.war (backend + FrontEnd) para copiarlo a tomcat/webapps.
# Requisitos: JDK 17+ (javac y jar en el PATH) y el driver de Oracle en lib/ojdbc17.jar.
# En Windows ejecutarlo desde Git Bash.
set -e
cd "$(dirname "$0")"

# CORRECCIÓN 1: Ajustado a ojdbc17.jar para coincidir con tu carpeta lib
OJDBC="${OJDBC:-lib/ojdbc17.jar}"

if [ ! -f "$OJDBC" ]; then
    echo "Falta $OJDBC (driver JDBC de Oracle). Bajalo de oracle.com o de Maven Central y copialo en lib/"
    exit 1
fi

rm -rf build cerveceria.war
mkdir -p build/WEB-INF/classes build/WEB-INF/lib

javac -encoding UTF-8 -cp "lib/*" -d build/WEB-INF/classes $(find BackEnd -name '*.java')

# CORRECCIÓN 2: Se añaden los 3 JARs de seguridad a la copia
cp lib/gson-2.10.1.jar "$OJDBC" lib/oraclepki.jar lib/osdt_core.jar lib/osdt_cert.jar build/WEB-INF/lib/   

cp FrontEnd/* build/
(cd build && jar cf ../cerveceria.war .)
echo "Listo: cerveceria.war -> copialo a tomcat/webapps y abrí http://localhost:8080/cerveceria/"