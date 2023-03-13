# Chat

## Uruchomienie
### Klient
`./run.sh "c <server_port> <log_level> <nick>"`
### Serwer
`./run.sh "s <server_port> <log_level>"`
\
Gdzie log_level przyjmuje wartości `[o, e, w, i, d]` (od najmniejszej ilości generowanych logów do największej).\
Można również skorzystać z dostarczonych skryptów.

## Interakcja
### Klient
* ```T`<text>``` - wysyła za pomocą TCP wiadomośC o treści <text> do innych klientów (przez serwer)
* `U` - wysyła za pomocą UDP rysunek ASCII do innych klientów (przez serwer)
* `M` - wysyła za pomocą Multicastu rysunek ASCII do innych klientów (bezpośrednio)
* `Q / ctrl+c` - wyjście

### Serwer
* `ctrl+c` - wyjście

## Komentarz
Nie ma gwarancji na wykonanie całości hooka ([źródło](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/Runtime.html#addShutdownHook)), dlatego po stronie, która została zamknięta za pomocą sygnału gniazdo może nie zostać zamknięte z poziomu programu.
Będzie to zauważalne w logach ale nie powinno wpłynąć na zwalnianie zasobów ponieważ system przeważnie zamyka gniazda otwarte przez proces, który się zakończył ([źródło](https://superuser.com/questions/375604/does-windows-take-care-of-closing-sockets-when-processes-exit)).
