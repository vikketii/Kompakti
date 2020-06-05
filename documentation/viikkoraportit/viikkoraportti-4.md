# Viikkoraportti 4
Aikaa käytetty 8h.

## Mitä olen tehnyt tällä viikolla?
Olen lukenut lisää LZW-algoritmin tehokkaista toteutuksista ja kiinnittänyt erityisesti huomiota sopivan hajautusfunktion valintaan.

## Miten ohjelma on edistynyt?
Sain tehtyä oman version hajautustaulusta. Hajautustauluni käyttää polynomista hajautusta ja linkitettyä listaa. Polynomisen hajautuksen vakio on 31, joka on ainakin Tirakirjan mukaan Javassa käytettävä vakio merkkijonojen hajautusarvon laskemiseen. Muutin myös LZW:n käyttämään 16 bittistä koodausta ja selkiytin luokkien vastuuta.


## Mitä opin tällä viikolla / tänään?
Opin lisää hajautustauluista ja niiden toteutuksesta.

## Mikä jäi epäselväksi tai tuottanut vaikeuksia?
Käytin projektiin tällä viikolla vähemmän aikaa kuin mitä olin ajatellut. Tämän takia en selvittänyt vielä Huffmanin toimintaa enkä tehnyt LZW-algoritmille pakkauksen purkamisfunktiota. Huomasin myös että ainakin LZW-algoritmin tehostamiseen on useita eri tapoja, esimerkiksi binääripuun käyttö hajautustaulun sijaan. Tämän kurssin puitteissa hyvään arvosanaan riittää varmaan kuitenki toteuttaa jokin tehokas versio algoritmista?

## Mitä teen seuraavaksi?
Seuraavaksi lisään testauksen HashMap luokalle ja teen LZW-algoritmille pakkauksen purkufunktion, joka helpottaa myös testausta. Sitten aloitan Huffman-pakkauksen toteutuksen.

