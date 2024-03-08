# ZookeeperWatches

## Uruchomienie
`./run.sh "<host>:<port> <zNodeName> <execPath> <execArgs>*"`

Gdzie:
* `host` - adres serwera
* `port` - port serwera do połączeń klienckich
* `zNodeName` - nazwa zNode'a który ma być 'śledzony'
* `execPath` - ścieżka do uruchamianego programu
* `execArgs` - opcjonalne argumenty dla uruchamianego programu

Można również skorzystać z dostarczonych skryptów, zakładają one [tę konfigurację (Linux)](conf).

## Interakcja
* `quit` - wyjście
* `tree` - wyświetla całą strukturę drzewa mającego za korzeń `<zNodeName>`
