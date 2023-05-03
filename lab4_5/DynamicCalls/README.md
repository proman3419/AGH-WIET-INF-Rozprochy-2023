# Wywołanie dynamiczne
Celem zadania jest demonstracja działania wywołania dynamicznego po stronie klienta middleware. Wywołanie dynamiczne to takie, w którym nie jest wymagana znajomość interfejsu zdalnego obiektu lub usługi w czasie kompilacji, lecz jedynie w czasie wykonania. Wywołania powinny być zrealizowane dla kilku (trzech) różnych operacji/procedur używających przynajmniej w jednym przypadku nietrywialnych struktur danych. Nie trzeba tworzyć żadnego formatu opisującego żądanie użytkownika ani parsera jego żądań - wystarczy zawrzeć to wywołanie "na sztywno" w kodzie źródłowym, co najwyżej z konsoli parametryzując szczegóły danych. Jako bazę można wykorzystać projekt z zajęć. Warto przemyśleć przydatność takiego podejścia w budowie aplikacji rozproszonych.
ICE: Dynamic Invocation https://doc.zeroc.com/ice/3.7/client-server-features/dynamic-ice/dynamic-invocation-and-dispatch
gRPC: „dynamic grpc”, “reflection”, grpcurl

Technologia middleware: Ice albo gRPC (gRPC)

Języki programowania: dwa różne (jeden dla klienta - C#, drugi dla serwera - Java)

Maksymalna punktacja: Ice: 6, gRPC: 7

## Uruchomienie
### Przygotowanie
```bash
cd DynamicCalls/DynamicCallsStatelessActions
./generate.sh
```

### Serwer
```bash
cd DynamicCallsServer
./run.sh
```

### Klient
```bash
cd DynamicCalls/DynamicCallsClient/DynamicCallsClient
./run.sh
```
