# Implementation
Program uses Lempel-Ziv-Welch algorithm to compress and decompress data. At this point compression works seemingly fine but there is some problems with decompression. Compression loops over given data with size n and makes dictionary searches. This adds to time complexity O(nlog(n)).

## Sources
- Tietorakenteet ja algoritmit, Antti Laaksonen (2019)