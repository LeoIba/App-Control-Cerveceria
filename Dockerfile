# Compila el backend, arma la aplicación (backend + FrontEnd) y la publica en Tomcat 10.1.
# Pensado para servicios como Render: lee el puerto de la variable PORT y la base de DB_USER / DB_PASS.

FROM eclipse-temurin:21-jdk AS build
WORKDIR /src
COPY BackEnd BackEnd
COPY FrontEnd FrontEnd
COPY lib lib
RUN mkdir -p /app/WEB-INF/classes /app/WEB-INF/lib
RUN javac -encoding UTF-8 -cp "lib/*" -d /app/WEB-INF/classes $(find BackEnd -name '*.java')
RUN cp lib/gson-2.10.1.jar lib/ojdbc17.jar lib/oraclepki.jar lib/osdt_core.jar lib/osdt_cert.jar /app/WEB-INF/lib/
RUN cp -r FrontEnd/* /app/
FROM tomcat:10.1-jre21
RUN rm -rf /usr/local/tomcat/webapps/*
COPY --from=build /app /usr/local/tomcat/webapps/ROOT
COPY Database/Wallet /opt/wallet
# El wallet viaja dentro de la imagen; usuario y clave se definen como variables de entorno (DB_USER, DB_PASS)
ENV DB_URL="jdbc:oracle:thin:@cerveceria_high?TNS_ADMIN=/opt/wallet"
CMD sed -i "s/port=\"8080\"/port=\"${PORT:-8080}\"/" /usr/local/tomcat/conf/server.xml && exec catalina.sh run
