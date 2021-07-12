# Makefile for CSCI 3171 Assignment 4

start:
	make compile_
	
compile_: ChatServer_.java ChatClient_.java CeasarCipher.java
	javac ChatServer_.java
	javac ChatClient_.java
	javac CeasarCipher.java

compile: ChatServer.java ChatClient.java CeasarCipher.java
	javac ChatServer.java
	javac ChatClient.java
	javac CeasarCipher.java
	
test: ChatServer_.class ChatClient_.class CeasarCipher.class
	open -a Terminal /Users/ryansawchuk/Desktop/csci3171_a4_B00787509/
	

test-server: ChatServer.class CeasarCipher.class Clienthandler.class
	java ChatServer
	
test-client: ChatClient.class CeasarCipher.class
	java ChatClient
	
clean: ChatServer.class ChatClient.class CeasarCipher.class Clienthandler.class
	rm ChatServer.class ChatClient.class CeasarCipher.class Clienthandler.class
