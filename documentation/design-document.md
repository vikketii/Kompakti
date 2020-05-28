# Design document
Kompakti is Java program for data compression. The goal of this project is to implement Huffman and LZW algorithms using only self made data structures and achieving meaningful compression rate for real life data. In the end there will be also some comparing functionality between implemented algorithms.

## Data structures
- LZW uses dictionary and dynamic array.
- Huffman uses binary tree which can be stored in to a regular array. Array size depends on the number of symbols, so dynamic array implementation is probably needed.

## Input/Output
Input can be first just text files. I would like to make the compression use bytes so you could compress any kind of file.

Output is size of original file and differently compressed files, and also compression rates and time taken for the compression.

## Time and space complexity
I haven't found any solid information about these yet. Huffman is probably O(n) complex with binary tree implementation and LZW probably O(nlog(n)) with hashmap, but both depend on implementation.

## Sources
- Lempel–Ziv–Welch [https://en.wikipedia.org/wiki/Lempel%E2%80%93Ziv%E2%80%93Welch](https://en.wikipedia.org/wiki/Lempel%E2%80%93Ziv%E2%80%93Welch)
- Huffman coding [https://en.wikipedia.org/wiki/Huffman_coding](https://en.wikipedia.org/wiki/Huffman_coding)
- Data compression [https://en.wikipedia.org/wiki/Data_compression](https://en.wikipedia.org/wiki/Data_compression)
- LZW Compression [https://www2.cs.sfu.ca/CourseCentral/365/li/squeeze/LZW.html](https://www2.cs.sfu.ca/CourseCentral/365/li/squeeze/LZW.html)