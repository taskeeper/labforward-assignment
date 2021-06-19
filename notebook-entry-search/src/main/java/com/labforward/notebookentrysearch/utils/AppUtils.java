package com.labforward.notebookentrysearch.utils;

import java.util.Arrays;

public class AppUtils {
	
	public static int calculate(String noteWord, String searchWord) {
		
        if (noteWord.isEmpty()) {
            return searchWord.length();
        }

        if (searchWord.isEmpty()) {
            return noteWord.length();
        } 
        
        int insert = calculate(noteWord, searchWord.substring(1)) + 1;
        int delete = calculate(noteWord.substring(1), searchWord) + 1;
        int replace = calculate(noteWord.substring(1), searchWord.substring(1)) 
         + replacementEval(noteWord.charAt(0), searchWord.charAt(0));
        
        
        return minValue(replace, insert, delete);
    }

    public static int replacementEval(char a, char b) {
        return a == b ? 0 : 1;
    }

    public static int minValue(int... numbers) {
    	
        return Arrays
        		.stream(numbers)
        		.min()
        		.orElse(Integer.MAX_VALUE);
    }
    

}
