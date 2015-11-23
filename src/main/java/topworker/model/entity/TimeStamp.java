package topworker.model.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import topWorker.restClient.model.ClientDetails;
import topWorker.restClient.model.StampType;

@Entity
@Table(name = "TIME_STAMP")
public class TimeStamp {

	private Date time;

	@Id
	@GeneratedValue
	private long id;

	@ManyToOne
	private User user;
	private StampType stampType;

	public TimeStamp() {

	}

	public TimeStamp(ClientDetails clientDetails) {
		this.stampType = clientDetails.getStampType();
		this.time = new Date();
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public StampType getStampType() {
		return stampType;
	}

	public void setStampType(StampType stampType) {
		this.stampType = stampType;
	}

}
