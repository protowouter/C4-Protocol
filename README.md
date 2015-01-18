[![Build Status](https://travis-ci.org/protowouter/C4-Protocol.svg)](https://travis-ci.org/protowouter/C4-Protocol)
# C4-Protocol
C4-Protocol is the protocol for the Connect4 game of the IN3 group. In this repository you will find the protocol description document
and also Java files with constants and a reference implementation of the protocol.

### Maven
If you use Maven for your project you can simply add the protocol to your dependencies

    <groupId>nl.woutertimmermans.connect4</groupId>
    <artifactId>c4-protocol</artifactId>
    <version>0.2</version>
    
## How to use the constants?
Simply download the java files containing the constants from this repositor and include it in your java code.

## What about the reference implementation?
The reference protocol implementation puts a remote procedure call wrapper around the String protocol.
Instead of generating and parsing Strings in your client and server handlers you simply call a method of an local object.
It's almost as if there is no network connection.

More information and code samples can be found in the wiki TODO: add information and code samples to the wiki

  

