# Problema

You are tasked with implementing an application that authorizes a transaction for a specific account following a set of predefined rules.

```
$ cat operations
{ "account": { "activeCard": true, "availableLimit": 100 } }
{ "transaction": { "merchant": "Burger King", "amount": 20, "time": "2019-02-13T10:00:00.000Z" } } { "transaction": { "merchant": "Habbib's", "amount": 90, "time": "2019-02-13T11:00:00.000Z" } }
$ authorize < operations
{ "account": { "activeCard": true, "availableLimit": 100 }, "violations": [] }
{ "account": { "activeCard": true, "availableLimit": 80 }, "violations": [] }
{ "account": { "activeCard": true, "availableLimit": 80 }, "violations": [ "insufficient-limit" ] }
```

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
