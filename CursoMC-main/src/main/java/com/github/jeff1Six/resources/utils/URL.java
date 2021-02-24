package com.github.jeff1Six.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class URL {

	public static List<Integer> decodeIntList(String s){
		String[] vet = s.split(",");
		List<Integer> list = new ArrayList<>();
		for (int i=0; i<vet.length; i++) {
			list.add(Integer.parseInt(vet[i]));
		}
		return list;
	}
	
	public static String decodeParam(String name) {
		try {
			return URLDecoder.decode(name,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}
}
