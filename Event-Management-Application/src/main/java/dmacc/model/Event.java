/**
 * @author Benjamin Whisler - bwhisler1
 * CIS175 - Spring 2022
 * Apr 12, 2022
 */
package dmacc.model;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Event {
	// attributes
	@Id
	@GeneratedValue
	private long id;
	private String eventName;
	private int maxAttendence;
	private String description;
	private String sponsors;
	@Basic
	private Date date;
	private double pricePerTicket;
	@Basic
	private Time startTime;
	@Basic
	private Time doorsOpenTime;
	private String type;
	private String attendeeInfo;
	private double costToSponsor;
	
	// constructors
	public Event(String eventName) {
		super();
		this.eventName = eventName;
	}

	/**
	 * @param id
	 * @param eventName
	 * @param maxAttendence
	 * @param description
	 * @param sponsors
	 * @param date
	 * @param pricePerTicket
	 * @param startTime
	 * @param doorsOpenTime
	 * @param type
	 * @param attendeeInfo
	 * @param costToSponsor
	 */
	public Event(long id, String eventName, int maxAttendence, String description, String sponsors, Date date,
			double pricePerTicket, Time startTime, Time doorsOpenTime, String type, String attendeeInfo,
			double costToSponsor) {
		super();
		this.id = id;
		this.eventName = eventName;
		this.maxAttendence = maxAttendence;
		this.description = description;
		this.sponsors = sponsors;
		this.date = date;
		this.pricePerTicket = pricePerTicket;
		this.startTime = startTime;
		this.doorsOpenTime = doorsOpenTime;
		this.type = type;
		this.attendeeInfo = attendeeInfo;
		this.costToSponsor = costToSponsor;
	}

	/**
	 * @param eventName
	 * @param maxAttendence
	 * @param description
	 * @param sponsors
	 * @param date
	 * @param pricePerTicket
	 * @param startTime
	 * @param doorsOpenTime
	 * @param type
	 * @param attendeeInfo
	 * @param costToSponsor
	 */
	public Event(String eventName, int maxAttendence, String description, String sponsors, Date date,
			double pricePerTicket, Time startTime, Time doorsOpenTime, String type, String attendeeInfo,
			double costToSponsor) {
		super();
		this.eventName = eventName;
		this.maxAttendence = maxAttendence;
		this.description = description;
		this.sponsors = sponsors;
		this.date = date;
		this.pricePerTicket = pricePerTicket;
		this.startTime = startTime;
		this.doorsOpenTime = doorsOpenTime;
		this.type = type;
		this.attendeeInfo = attendeeInfo;
		this.costToSponsor = costToSponsor;
	}
	
	
}
