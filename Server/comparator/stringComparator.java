package comparator;

import java.util.Comparator;

public class stringComparator implements Comparator<String>{
	public int compare(String str1, String str2){

        int l1 = str1.length(); 
        int l2 = str2.length(); 
        int lmin = Math.min(l1, l2); 
  
        for (int i = 0; i < lmin; i++) { 
            double str1_ch = (double)str1.charAt(i); 
			double str2_ch = (double)str2.charAt(i); 
			
			if(str1_ch < 96){
				str1_ch += 32.5;
			}

			if(str2_ch < 96){
				str2_ch += 32.5;
			}
  
            if(str1_ch != str2_ch){
                if (str1_ch > str2_ch) { 
                    return 1; 
                }else if(str1_ch < str2_ch){
                    return -1;
                }
            }

        }

        if (l1 != l2) { 
            return l1 - l2; 
        } 
        
        return 0;
	}
}