/**
 * @author Bobby - rfparsons
 * CIS175 - Spring 2022
 * Apr 26, 2022
 */
package dmacc.controller;

import java.beans.PropertyEditorSupport;
import java.sql.Time;

/**
 * @author Bobby
 *
 */
public class TimeEditor extends PropertyEditorSupport {
	public void setAsText(String text) {
		Time strTime = new Time(0); // Initialize variable
		if (text.length() == 8) { // If length is 8, it likely already has the seconds added
			strTime = Time.valueOf(text);
		} else {
			strTime = Time.valueOf(text + ":00"); // if it works, it works
		}
		setValue(strTime);
	}

}
