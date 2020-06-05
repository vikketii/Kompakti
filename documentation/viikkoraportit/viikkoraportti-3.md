# Viikkoraportti 3
Aikaa käytetty 24h.

## Mitä olen tehnyt tällä viikolla?
Tällä viikolla olen lukenut lisää LZW algoritmin erilaisista toteutuksista ja selvittänyt tapoja muuttaa algoritmi toimimaan binäärinä kirjainten sijaan. Olen myös selvittänyt Javan binäärioperaatioiden toimintaa ja dynaamisen listan tehokasta toteutusta.

## Miten ohjelma on edistynyt?
Ohjelmalla voi nyt pakata mitä tahansa tiedostoja LZW-pakkausalgoritmilla. Ohjelma tekee alkuperäisen tiedoston viereen uuden '.lzw' päätteisen pakatun tiedoston. Toteutin algoritmia varten oman version dynaamisesta listasta. Aloitin yksikkötestauksen ja lisäsin jacoco-testiraportin ja checkstylen.

## Mitä opin tällä viikolla / tänään?
Tällä viikolla opin lisää LZW algoritmien toteutuksesta ja löysin monta aihetta käsittelevää lähdettä, esimerkiksi R. Nigel Horspoolin IEEE:ssä julkaistun artikkelin 'Improving LZW' vuodelta 1991.

## Mikä jäi epäselväksi tai tuottanut vaikeuksia?
Yritin kovasti saada pakkauksen toimimaan 12 bittisellä koodauksella, mikä tuntuu nyt jälkeenpäin aika turhalta työltä. Koodauksesta voisi tehdä mielummin 16 bittisen. Siihen Javan valmis `byte` primääri taipuisi paljon helpommin. Pohdin myös että onko Javan kanssa jotenkin helpohkosti mahdollista toteuttaa binääristä koodausta, joka muuttuisi dynaamisesti isommaksi. Luultavasti tästä saatava hyöty olisi kuitenkin niin pientä, että pohdin asiaa uudestaan vasta kun projekti on muuten valmis.

## Mitä teen seuraavaksi?
Seuraavaksi muutan LZW:n koodauksen 16 bittiseksi. Sen jälkeen toteutan oman hajautustaulun ja alan etsimään tietoa Huffmanin toteuttamisesta.