#!/bin/bash
# para ejecutar el script brindar permisos de ejecución
# chmod 750 db.sh


# Para cargar la base da datos
echo "Crear base de datos..."
psql -c "create database sapo"
echo "Crear esquema base de datos..."
psql -d sapo <./SAPOLogica/resources/db.sql
