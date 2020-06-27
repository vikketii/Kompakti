# User guide
Kompakti is used with command line interface, so the usage is pretty simple.

If running from source code, you can use gradle wrapper for running the program like this `./gradlew run --args="[OPTION] [FILE]"`.

If using jar, you can run `java -jar kompakti [OPTION] [FILE]`.

Kompakti gives user info how to use the program if invalid argument or `--help` option is given.

Usage: [OPTION] [FILE]

- --all: makes LZW + Huffman compression of given file and saves it as .comp file
- --all-decomp: makes LZW + Huffman decompression of given file and saves it as .decomp file
- --lzw: makes LZW compression of given file and saves it as .lzw file
- --lzw-decomp: makes LZW decompression of given file and saves it as .delzw file
- --huffman: makes Huffman compression of given file and saves it as .huffman file
- --huffman-decomp: makes Huffman decompression of given file and saves it as .dehuffman file
- --statistics FOLDER NUM: Uses files in given FOLDER to make stats about huffman and lzw compression.
    Goes trough tests NUM times (default 10).
- --help: prints this help message
