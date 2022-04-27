/**
 * @author Bobby - rfparsons
 * CIS175 - Spring 2022
 * Apr 26, 2022
 */
package dmacc.controller;

import java.beans.PropertyEditorSupport;
import java.sql.Date;

/**
 * @author Bobby
 *
 */
public class DateEditor extends PropertyEditorSupport {
	public void setAsText(String text) {
		Date strDate = Date.valueOf(text);
		setValue(strDate);
	}
}
