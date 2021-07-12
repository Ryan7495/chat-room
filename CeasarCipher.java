/*
 Institution: Dalhousie University
 Class: CSCI 3171 Network Computing
 Instructor: Shrini Sampali
 Assignment: 4 Chat Server

 Author: Ryan
 Date: November 25 2019

 
 This object is a Ceasar Cipher. It can encrypt text using a specified key.
 It can also decrypt a peice of encrypted text if you have the associated key.
 This object can also brute force crack an encrypted text.

 This file is adapated from The CSCI 2201 Assignment 4 CeasarCipher Written by: Ryan.
 (Winter semester 2019)
 
 This file is adapted from The CSCI 3171 Assignment 3 CeasarCipher Written by: Ryan.
 (Fall semester 2019)
*/


import java.util.LinkedList;

public class CeasarCipher
{
    //Attributes
    private int key;
    private String inputText;
        
    //Constructor
    public CeasarCipher()
    {
        this.key = 0;
        this.inputText = "0";
    }

    //Accessors and Mutators
    public void setKey(int key)
    {
        this.key = key;
    }
    
    public int getKey()
    {
        return key;
    }

    public void setInputText(String inputText)
    {
        this.inputText = inputText;
    }

    public String getInputText()
    {
        return inputText;
    }


    //Other Methods
    //This method encrypts a peice of text
    public String encrypt(int key, String inputText)
    {
        key = key % 26;
      
        char [] text = inputText.toCharArray();
        int length = inputText.length();
        int con = 0;

		for (int i = 0; i < length; i++){
            if (Character.isLetter(text[i]) && Character.isLowerCase(text[i]))
            {
                con = text[i];
                con = (((con - 97) + key) % 26) + 97;
                text[i] = (char)con;
            }
            else if (Character.isLetter(text[i]) && Character.isUpperCase(text[i]))
            {
                con = text[i];
                con = (((con - 65) + key) % 26) + 65;
                text[i] = (char)con;
            }
        }
                
        String encryptedText = new String(text);

        return encryptedText;
    }


    //This method decryptes an encrypted text
    public String decrypt(int key, String inputText){
        key = key % 26;
        
        if (inputText.isEmpty())
            return " ";
      
        char [] text = inputText.toCharArray();
        int length = inputText.length();
        int con = 0;

		for (int i = 0; i < length; i++)
        {
            if (Character.isLetter(text[i]) && Character.isLowerCase(text[i]))
            {
                con = text[i];
                con = (((con - 97) - key + 26) % 26) + 97;
                text[i] = (char)con;
            }
            else if (Character.isLetter(text[i]) && Character.isUpperCase(text[i]))
            {
                con = text[i];
                con = (((con - 65) - key + 26) % 26) + 65;
                text[i] = (char)con;
		   }
        }
                
        String decryptedText = new String(text);
        return decryptedText;
    }
   
    //This method brute force cracks an encrypted text
    public LinkedList<String> bruteForce(String inputText)
    {
        LinkedList<String> outputs = new LinkedList<String>();
      
        for (int i = 0; i < 26; i++){
            outputs.add(decrypt(i, inputText));
        }
        return outputs;
    }
}
