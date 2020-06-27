# Testing document
Source code includes automatic unit testing for a part of data structures (ArrayList, BitList and PriorityQueue are tested) and for all methods in Converter class. LZW and Huffman classes are tested automaticly with small inputs, and also with one longer file included in resources.

Compression algorithms are thoroughly tested manually with different kinds of inputs. (Canterbury corpus) test files were used to test that the algorithms work correctly and also the files were used to generate statistics about the compressions.

Junit tests can be run with gradle wrapper included in source code and the command is `./gradlew test`.

Statistics for any kinds of data can be generated with `./gradlew run --args="--statistics [FOLDER] [NUM]"` where FOLDER is the folder that contains the files and NUM is times that the tests are run. When NUM is 100 you usually get pretty reliable results.

## Statistics for Canterbury corpus


### Compression times (ms):

filename | LZW | Huffman | LZW+Huffman
---- | --- | ------- | -----------
asyoulik.txt|      25.1253|        6.4272|       28.1749
grammar.lsp|       0.4095|        0.2246|        0.7342
ptt5|     464.7156|        9.1224|      465.9748
lcet10.txt|      96.8708|       22.0573|      115.1180
fields.c|       1.2882|        0.6050|        1.9630
sum|       5.2971|        2.0833|        7.3355
xargs.1|       0.4816|        0.2885|        0.8356
cp.html|       3.4358|        1.4466|        4.9151
plrabn12.txt|     106.5952|       22.3689|      132.3721
alice29.txt|      30.4682|        7.3204|       36.1851
kennedy.xls|     180.5161|       28.1898|      199.5358

### Decompression times (ms):
filename |          LZW|       Huffman|   LZW+Huffman
------------|-------------|--------------|--------------
asyoulik.txt|      52.4207|       12.0519|       57.3391
grammar.lsp|       3.3880|        0.3817|        3.9161
ptt5|      57.6211|       22.0765|       67.5284
lcet10.txt|     154.6829|       41.2824|      182.7219
fields.c|       5.9589|        1.0512|        7.1061
sum|      17.7744|        3.6350|       21.7899
xargs.1|       4.0237|        0.4896|        4.4330
cp.html|      11.4248|        2.5581|       14.9317
plrabn12.txt|     182.7544|       42.8004|      223.8063
alice29.txt|      59.4248|       13.9798|       68.7089
kennedy.xls|     261.1875|       57.3319|      293.5475

### Compression results (bytes):
filename    |     Original|           LZW|       Huffman|   LZW+Huffman
------------|-------------|--------------|--------------|--------------
asyoulik.txt|       125179|         62748|         78845|         61072
grammar.lsp|         3721|          2818|          2470|          2435
ptt5|       513216|         70116|        159388|         67359
lcet10.txt|       426754|        185168|        259515|        182785
fields.c|        11150|          7084|          7421|          6259
sum|        38240|         25054|         26515|         22547
xargs.1|         4227|          3584|          2919|          3032
cp.html|        24603|         14948|         17028|         13419
plrabn12.txt|       481861|        220978|        281798|        219368
alice29.txt|       152089|         70148|         89793|         68564
kennedy.xls|      1029744|        332864|        469967|        311169

### Compression ratio:
filename    |          LZW|       Huffman|   LZW+Huffman
------------|-------------|--------------|--------------
asyoulik.txt|         1.99|          1.59|          2.05
grammar.lsp|         1.32|          1.51|          1.53
ptt5|         7.32|          3.22|          7.62
lcet10.txt|         2.30|          1.64|          2.33
fields.c|         1.57|          1.50|          1.78
sum|         1.53|          1.44|          1.70
xargs.1|         1.18|          1.45|          1.39
cp.html|         1.65|          1.44|          1.83
plrabn12.txt|         2.18|          1.71|          2.20
alice29.txt|         2.17|          1.69|          2.22
kennedy.xls|         3.09|          2.19|          3.31

