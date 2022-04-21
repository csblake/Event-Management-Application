/**
 * @author Benjamin Whisler - bwhisler1
 * CIS175 - Spring 2022
 * Apr 12, 2022
 */
package dmacc.model;

import java.util.List;

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
	private String date;
	private double pricePerTicket;
	private String startTime;
	private String doorsOpenTime;
	private String type;
	private String attendeeInfo;
	
	// constructors
	public Event(String eventName) {
		super();
		this.eventName = eventName;
	}
	
	public Event(String eventName, int maxAttendence, String description, String sponsors, String date, double pricePerTicket, String startTime, String doorsOpenTime, String type) {
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
	}
	
	public Event(long id, String eventName, int maxAttendence, String description, String sponsors, String date, double pricePerTicket, String startTime, String doorsOpenTime, String type) {
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
	}
}
