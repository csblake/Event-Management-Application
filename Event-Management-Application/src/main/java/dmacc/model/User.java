/**
 * @author Benjamin Whisler - bwhisler1
 * CIS175 - Spring 2022
 * Apr 12, 2022
 */
package dmacc.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class User {
	@Id
	@GeneratedValue
	private long id;
	private String username;
	private String password;
	private ArrayList<Long> attendingEvents;
	private boolean canAddEvents;
	private boolean canEditEvents;
	private boolean isAdmin;
	
	
	public User(String username) {
		this.username = username;
	}
	
	
	
	public void attendEvent(Long id) {
		if (this.attendingEvents == null) {
			this.setAttendingEvents(new ArrayList<Long>());
		}
		ArrayList<Long> newList = this.attendingEvents;
		newList.add(id);
		this.setAttendingEvents(newList);
	}
	
	public void unattendEvent(Long id) {
		if (this.attendingEvents == null) {
			return;
		}
		ArrayList<Long> newList = this.attendingEvents;
		newList.remove(id);
		this.setAttendingEvents(newList);
	}
}
