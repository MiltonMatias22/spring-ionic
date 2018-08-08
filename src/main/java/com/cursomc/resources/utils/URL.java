package com.cursomc.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class URL {
	
	public static String decodeParam(String s) {
		try {
			
			return URLDecoder.decode(s, "UFT-8");
		
		} catch (UnsupportedEncodingException e) {
			return "";
		}		
	}
	
	public static List<Integer> decodeIntList(String s) {
		
		return 	Arrays.asList(s.split(",")).stream().map(x -> Integer.parseInt(x)).collect(Collectors.toList());
	}
}
