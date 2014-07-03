rzseie-scrapper
===============

 Scrapper Rejestru zużytego sprzętu elektrycznego i elektronicznego - www.rzseie.gios.gov.pl/

Uruchomienie
===============

1. Zależności
Aby zbudować scrapper Rejestr zużytego sprzętu elektrycznego i elektronicznego wymagane są narzędzia:

Apache Maven 3.0.5
Java w wersji 1.7 (Testowane z openjdk)

2. Budowa

mvn clean compile assembly:single

3. Uruchomienie

java -jar target/scrapper-1.0-SNAPSHOT-jar-with-depdencies.jar 

Utworzone zostaną pliki rzseie.xml i rzseie.json zawierające pełną zawartość rejestru. W aktualnej wersji eksportowane są tylko nagłówki rekordów rejestru:

Lp.
Numer Rejestrowy
Firma 
Oznaczenie siedziby organu zarządzającego
Województwo
NIP
REGON
Info - identyfikator szczegółowego rekordu

