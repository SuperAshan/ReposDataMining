package org.jabsorb;

import java.io.Serializable;

public class Message implements Serializable{
	public String getMessage(String s){
		return "Hello, "+s;
	}
}
