# Design document
Kompakti is Java program for data compression implementing Huffman and LZW algorithms using only self made data structures and achieving meaningful compression rates for real life data. There is also functionality to make statistics how the algorithms perform.

## Data structures
- LZW uses dictionary and dynamic array.
- Huffman uses binary tree and dynamic bitlist.

## Input/Output
Input can be any kind of file. It would be fun to extend this to be any folder.

Output is either compressed or decompressed file and information about how much algorithm were able to compress given file.
User can also ask the program to make statistics out of files in folder, so then output is the statistics in a file.

## Time and space complexity
Huffman compression is implemented as O(nlogn) complexity because priority queue takes O(logn) to add one value and we have n symbols to add.

LZW compression is O()

I haven't found any solid information about these yet. Huffman is probably O(n) complex with binary tree implementation and LZW probably O(nlog(n)) with hashmap, but both depend on implementation.

## Sources
- Lempel–Ziv–Welch [https://en.wikipedia.org/wiki/Lempel%E2%80%93Ziv%E2%80%93Welch](https://en.wikipedia.org/wiki/Lempel%E2%80%93Ziv%E2%80%93Welch)
- Huffman coding [https://en.wikipedia.org/wiki/Huffman_coding](https://en.wikipedia.org/wiki/Huffman_coding)
- Data compression [https://en.wikipedia.org/wiki/Data_compression](https://en.wikipedia.org/wiki/Data_compression)
- LZW Compression [https://www2.cs.sfu.ca/CourseCentral/365/li/squeeze/LZW.html](https://www2.cs.sfu.ca/CourseCentral/365/li/squeeze/LZW.html)