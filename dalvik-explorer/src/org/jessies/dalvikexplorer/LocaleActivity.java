package org.jessies.dalvikexplorer;

import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import java.text.*;
import java.util.*;

public class LocaleActivity extends Activity {
    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        final String localeName = getIntent().getStringExtra("org.jessies.dalvikexplorer.Locale");
        setTitle("Locale \"" + localeName + "\"");
        
        TextView textView = (TextView) findViewById(R.id.output);
        textView.setText(describeLocale(localeName));
    }
    
    private static Locale localeByName(String name) {
        if (name.length() == 0) {
            return new Locale("", "", "");
        }
        
        int languageEnd = name.indexOf('_');
        if (languageEnd == -1) {
            return new Locale(name, "", "");
        }
        
        String language = name.substring(0, languageEnd);
        name = name.substring(languageEnd + 1);
        
        int countryEnd = name.indexOf('_');
        if (countryEnd == -1) {
            return new Locale(language, name, "");
        }
        
        String country = name.substring(0, countryEnd);
        String variant = name.substring(countryEnd + 1);
        
        return new Locale(language, country, variant);
    }
    
    private static String describeLocale(String name) {
        final StringBuilder result = new StringBuilder();
        
        final Locale locale = localeByName(name);
        
        result.append("Display Name: " + locale.getDisplayName() + "\n");
        result.append('\n');
        
        if (locale.getLanguage().length() > 0) {
            result.append("Display Language: " + locale.getDisplayLanguage() + "\n");
            result.append("2-Letter Language Code: " + locale.getLanguage() + "\n");
            result.append("3-Letter Language Code: " + locale.getISO3Language() + "\n");
            result.append('\n');
        }
        if (locale.getCountry().length() > 0) {
            result.append("Display Country: " + locale.getDisplayCountry() + "\n");
            result.append("2-Letter Country Code: " + locale.getCountry() + "\n");
            result.append("3-Letter Country Code: " + locale.getISO3Country() + "\n");
            result.append('\n');
        }
        if (locale.getVariant().length() > 0) {
            result.append("Display Variant: " + locale.getDisplayVariant() + "\n");
            result.append("Variant Code: " + locale.getVariant() + "\n");
        }
        result.append('\n');
        
        Date now = new Date(); // FIXME: it might be more useful to always show a time in the afternoon, to make 24-hour patterns more obvious.
        result.append("Date/Time Patterns\n\n");
        describeDateFormat(result, "Full Date", DateFormat.getDateInstance(DateFormat.FULL, locale), now);
        describeDateFormat(result, "Medium Date", DateFormat.getDateInstance(DateFormat.MEDIUM, locale), now);
        describeDateFormat(result, "Short Date", DateFormat.getDateInstance(DateFormat.SHORT, locale), now);
        result.append('\n');
        describeDateFormat(result, "Full Time", DateFormat.getTimeInstance(DateFormat.FULL, locale), now);
        describeDateFormat(result, "Medium Time", DateFormat.getTimeInstance(DateFormat.MEDIUM, locale), now);
        describeDateFormat(result, "Short Time", DateFormat.getTimeInstance(DateFormat.SHORT, locale), now);
        result.append('\n');
        describeDateFormat(result, "Full Date/Time", DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL, locale), now);
        describeDateFormat(result, "Medium Date/Time", DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, locale), now);
        describeDateFormat(result, "Short Date/Time", DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, locale), now);
        result.append('\n');
        result.append('\n');
        
        result.append("Date Format Symbols\n\n");
        DateFormat df = DateFormat.getDateInstance(DateFormat.FULL, locale);
        if (df instanceof SimpleDateFormat) {
            DateFormatSymbols dfs = ((SimpleDateFormat) df).getDateFormatSymbols();
            result.append("Am/pm: " + Arrays.toString(dfs.getAmPmStrings()) + "\n");
            result.append("Eras: " + Arrays.toString(dfs.getEras()) + "\n");
            result.append("Local Pattern Chars: " + dfs.getLocalPatternChars() + "\n");
            result.append("Months: " + Arrays.toString(dfs.getMonths()) + "\n");
            result.append("Short Months: " + Arrays.toString(dfs.getShortMonths()) + "\n");
            result.append("Weekdays: " + Arrays.toString(dfs.getWeekdays()) + "\n");
            result.append("Short Weekdays: " + Arrays.toString(dfs.getShortWeekdays()) + "\n");
        }
        
        return result.toString();
    }
    
    private static void describeDateFormat(StringBuilder result, String description, DateFormat dateFormat, Date when) {
        if (dateFormat instanceof SimpleDateFormat) {
            SimpleDateFormat sdf = (SimpleDateFormat) dateFormat;
            result.append(description + ": " + sdf.toLocalizedPattern() + "\n");
            result.append("    " + sdf.format(when) + "\n");
        }
    }
}
