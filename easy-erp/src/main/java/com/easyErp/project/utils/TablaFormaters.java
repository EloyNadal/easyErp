package com.easyErp.project.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import javafx.util.StringConverter;

public class TablaFormaters {
	
	public static StringConverter<Integer> getIntegerFormatter() { 
		NumberFormat nf = NumberFormat.getIntegerInstance(new Locale("CA","ES"));

		StringConverter<Integer> formatter = new StringConverter<Integer>()
		{
			@Override
			public Integer fromString(String string)
			{
				Integer value;
				try {
					value = nf.parse(string).intValue();
				} catch (ParseException e) {
					value = 0;
				}
				return value;
			}

			@Override
			public String toString(Integer object)
			{
				return (object == null)?nf.format(0):nf.format(object);
			}
		};
		return formatter;
	}
	
	public static StringConverter<Double> getModedaFormatter() {
		StringConverter<Double> formatter = new StringConverter<Double>()
		{
			@Override
			public Double fromString(String string)
			{
				NumberFormat nf = NumberFormat.getInstance(new Locale("es","CA"));

				Double value;
				try {
					value = nf.parse(string).doubleValue();
				} catch (ParseException e) {
					value = 0.0;
				}
				return value;
			}

			@Override
			public String toString(Double object)
			{
				DecimalFormat df = new DecimalFormat( "###,##0.00 €" );

				return (object == null)?df.format(0.0):df.format(object);
			}
		};
		return formatter;
	}
}
