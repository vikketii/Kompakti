# Implementation
Program uses Lempel-Ziv-Welch algorithm to compress and decompress data. At this point compression works seemingly fine but there is some problems with decompression. Compression loops over given data with size n and makes dictionary searches. This adds to time complexity O(nlog(n)).

## Data structures
- LZW uses dictionary and dynamic array.
- Huffman uses binary tree and dynamic bitlist.

## Input/Output
Input can be any kind of file. It would be fun to extend this to be any folder.

Output is either compressed or decompressed file and information about how much algorithm were able to compress given file.
User can also ask the program to make statistics out of files in folder, so then output is the statistics in a file.

## Time and space complexity
Length of given file is n.

Huffman compression is implemented as O(nlogn) complexity because priority queue takes O(logn) to add one value and we have n symbols to add.

LZW compression is O(nlogn) with dictionary searches as O(logn) for n times.

## Comparing algorithms
With Canterbury corpus data set for testing it seems that with my implementation LZW takes always atleast twice the time vs. Huffman. With binary file 'ptt5' LZW takes 464ms when Huffman can do it just in 9ms.

LZW has almost always better compression ratios. For ptt5 this is quite significant difference with LZW 7.32 and Huffman 3.22. LZW with Huffman gives the best results almost always, but when the data is really small the overhead from LZW encoded with 16 bits instead of 12 can make LZW with Huffman worser than only Huffman.

## Improving
LZW could use dynamic encoding for data. Now encoding always with 16 bit size adds overhead to the compressed file. If the program would use for example 9 to 16 bits dynamically it would improve compression for all files.

Huffman compression could be improved with O(n) time complexity using two arrays explained in the wikipedia article. Also canonical Huffman codebook fits to half the size that it is now in, because the depth of Huffman tree is always under 16.

## Sources
- Lempel–Ziv–Welch [https://en.wikipedia.org/wiki/Lempel%E2%80%93Ziv%E2%80%93Welch](https://en.wikipedia.org/wiki/Lempel%E2%80%93Ziv%E2%80%93Welch)
- Huffman coding [https://en.wikipedia.org/wiki/Huffman_coding](https://en.wikipedia.org/wiki/Huffman_coding)
- Tietorakenteet ja algoritmit, Antti Laaksonen (2019)