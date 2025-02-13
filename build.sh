#!/bin/bash

project_name="cosmix"

server_dir="/opt/tomcat/webapps"
classes_dir="./out/production/Cosmix"
jar_dir="/Users/macbook/Documents/Java Library"

rm -r build
mkdir -p build
cp -R "$classes_dir"/* build/
# cp -R "$web_dir"/* build/
# cp -R "web/WEB-INF" "build/WEB-INF/"
# cp -R "lib" build/

jar cf "$project_name.jar" -C build .

#Dispatch framework jar to java library
mkdir -p "$jar_dir/$project_name"
cp "./$project_name.jar" "$jar_dir/$project_name"

#Dispatch framework jar to project test
cp "./$project_name.jar" "/Users/macbook/Documents/ITU/S5/Framework/Biblio/lib/"
cp "./$project_name.jar" "/Users/macbook/Documents/ITU/S5/Framework/ticketing/lib"





# cp "$project_name.war" "$server_dir"