# Määrittelydokumentti
SudokuSolver on sudokujen automaattiseen ratkaisemiseen tarkoitettu ohjelma. Sen avulla voidaan mitata ja tarkastella erilaisten ratkaisutapojen tehokkuutta.

## Algoritmit ja tietorakenteet
SudokuSolver käyttää hakupuuta, min-max algoritmia sekä alpha-beta pruunausta pääasiallisena ratkaisutapana.

## Syötteet
Ohjelma saa syötteenään csv-muotoisia sudokuja, joita se sitten ratkaisee ja lopulta koostaa ratkaistuista sudokuista raportin. Raportti sisältää esimerkiksi ratkaisemiseen käytetyn kokonaisajan.

## Aika ja tilaavaativuus
Alpha-beta pruunauksen aikavaativuus on O(b^d), missä b on puun haarojen määrä ja d puun syvyys. Parhaimmillaan pruunaus vähentää aikavaativuuden neliöjuureen huonoimmasta tapauksesta.

## Lähteet
https://en.wikipedia.org/wiki/Alpha%E2%80%93beta_pruning