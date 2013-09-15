package ru.hh.school;

import android.text.InputFilter;
import android.text.SpannableStringBuilder;
import android.text.Spanned;

/**
 *  
 * @author pws
 * Restrict input to only digits and +
 */
class PhoneInputFilter implements InputFilter {
	@Override
    public CharSequence filter(CharSequence source, int start, int end,
            Spanned dest, int dstart, int dend) {

        if (source instanceof SpannableStringBuilder) {
            SpannableStringBuilder sourceAsSpannableBuilder = (SpannableStringBuilder)source;
            for (int i = end - 1; i >= start; i--) { 
                char currentChar = source.charAt(i);
                 if (!Character.isDigit(currentChar) && currentChar != '+') {    
                     sourceAsSpannableBuilder.delete(i, i+1);
                 }     
            }
            return source;
        } else {
            StringBuilder filteredStringBuilder = new StringBuilder();
            for (int i = 0; i < end; i++) { 
                char currentChar = source.charAt(i);
                if (Character.isDigit(currentChar) || currentChar == '+') {    
                    filteredStringBuilder.append(currentChar);
                }     
            }
            return filteredStringBuilder.toString();
        }
    }
}