# Instrucciones

Se ha creado una clase con un metodo main como punto de entrada que espera transacciones de la entrada estandar y returna las transacciones procesadas deacuerdo a las reglas de negocio y las imprime an la salida estandar.

```
com.nubank.TransactionHandler
```

### Resources

Se incluyeron algunos ejemplos con transacciones en el directorio ./resources

## Compilar

Para la compilacion de esta aplicacion, se asume que Maven estan configurado en el PATH.

```
> mvn clean install
```

## Ejecutar

El jar generado por maven ya incluye el main class en su manifest, por lo que es posible ejecutarlo directamente.

```
> java -jar ./target/transactions-0.0.1-SNAPSHOT-jar-with-dependencies.jar < ./resources/operations
```

## Probar

```
> mvn test
```
