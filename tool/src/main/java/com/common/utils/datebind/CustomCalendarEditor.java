package com.common.utils.datebind;

import com.common.utils.DateUtils;
import com.common.utils.StringUtils;

import java.beans.PropertyEditorSupport;
import java.util.Calendar;
import java.util.Locale;

public class CustomCalendarEditor extends PropertyEditorSupport {

	
	private static final String PATTERN = "yyyy-MM-dd HH:mm:ss";
//	private CalendarConverter converter = null;

	/**
	 * Creates a Custom Calendar Editor with a default format of date time with timezone in ISO
	 * format
	 * 
	 * @see DateFormatUtils.ISO_DATETIME_TIME_ZONE_FORMAT
	 */
	public CustomCalendarEditor() {
		this(PATTERN);
	}

	/**
	 * Returns a Custom Calendar Editor with the pattern specified and the default Locale
	 * 
	 * @param pattern
	 */
	public CustomCalendarEditor(String pattern) {
		this(pattern, null);
	}

	/**
	 * Returns a CustomCalendarEditor with the Locale specified, and the defult short Date Format
	 * 
	 * @param locale
	 */
	public CustomCalendarEditor(Locale locale) {
		//this(FastDateFormat.getDateInstance(FastDateFormat.SHORT, locale).getPattern(), locale);
	}

	/**
	 * Creates a custom Calendar Editor using the pattern and Locale passed The pattern will be used
	 * without modification as one of the parsing patterns The Locale will be used to generate a set
	 * of other patterns that may also be used to parse If no Locale is passed, the default Locale
	 * is used
	 * 
	 * @param dateFormat
	 * @param allowEmpty
	 */
	public CustomCalendarEditor(String pattern, Locale locale) {
		super();
		//converter = new CalendarConverter(pattern, locale);
	}

	/**
	 * Parses the text passed using the default pattern and the Locale specific patterns as parsing
	 * options. This method uses the DateUtils.parseDate method from Apache.
	 * 
	 * @see org.apache.commons.lang.time.DateUtils
	 * @see getFormatsForLocale
	 */
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if(StringUtils.isBlank(text)){
			return;
		}
		setValue(DateUtils.parseDate2Calendar(DateUtils.parseString2Date(text, PATTERN)));
	}

	/**
	 * Converts the current value to Text using the pattern that has been set or a default pattern
	 * if no pattern has been set
	 */
	@Override
	public String getAsText() {
		Object o = super.getValue();
		if (o != null && o instanceof Calendar) {
			return DateUtils.parseDate2String(DateUtils.parseCalendar2Date((Calendar)o), PATTERN);
		} else {
			return super.getAsText();
		}
	}
	
	/**
	 * Returns the Locale associated with this Calendar Editor If no Locale was specified, this
	 * method will return the Locale from TypeConversionUtils
	 * 
	 * @see TypeConversionUtils.getLocale()
	 * @return
	 */
	protected Locale getLocale() {
		return null;
//		return converter.getLocale();
	}
}
