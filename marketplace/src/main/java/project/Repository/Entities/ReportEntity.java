package project.Repository.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name="report")
public class ReportEntity {
	@Column(name="id")
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer post_id;

	@Column(name="reporter_id")
	private Integer reporter_id;

	@Column(name="reported_id")
	private Integer reported_id;

	@Column(name="post_id")
	private Integer post_id;

	@Column(name="message_id")
	private Integer message_id;

	@Column(name="reason")
	private String reason;

	static enum ReportStatus
	{
		open,
		resolved
	}

	@Enumerated(EnumType.STRING)
	//Hibernate 6
	//otherwise we'd need to use native query
	@JdbcType(PostgreSQLEnumJdbcType.class)
	@Column(name="status")
	private ReportStatus status;

	public ReportEntity() {}

	public ReportEntity(ReportEntity reportEntity) {
		this.post_id = reportEntity.post_id;
		this.reporter_id = reportEntity.reporter_id;
		this.reported_id = reportEntity.reported_id;
		this.post_id = reportEntity.post_id1;
		this.message_id = reportEntity.message_id;
		this.reason = reportEntity.reason;
		this.status = reportEntity.status;
	}

	public ReportEntity(Integer post_id, Integer reporter_id, Integer reported_id, Integer post_id1, Integer message_id, String reason, project.Repository.Entities.ReportEntity.ReportStatus status) {
		this.post_id = post_id;
		this.reporter_id = reporter_id;
		this.reported_id = reported_id;
		this.post_id = post_id1;
		this.message_id = message_id;
		this.reason = reason;
		this.status = status;
	}

	public Integer getPost_id() {return post_id;}
	public void setPost_id(Integer post_id) {this.post_id = post_id;}
	public Integer getMessage_id() {return message_id;}
	public void setMessage_id(Integer message_id) {this.message_id = message_id;}
	public String getReason() {return reason;}
	public void setReason(String reason) {this.reason = reason;}
	public ReportStatus getStatus() {return status;}
	public void setStatus(ReportStatus status) {this.status = status;}
	public Integer getReporter_id() {return reporter_id;}
	public void setReporter_id(Integer reporter_id) {this.reporter_id = reporter_id;}
	public Integer getReported_id() {return reported_id;}
	public void setReported_id(Integer reported_id) {this.reported_id = reported_id;}

	public boolean equals(Object object) {
		if (object == null || getClass() != object.getClass()) return false;
		if (!super.equals(object)) return false;

		ReportEntity that = (ReportEntity) object;
		return java.util.Objects.equals(post_id, that.post_id) && java.util.Objects.equals(reporter_id, that.reporter_id) && java.util.Objects.equals(reported_id, that.reported_id) && java.util.Objects.equals(post_id, that.post_id) && java.util.Objects.equals(message_id, that.message_id) && java.util.Objects.equals(reason, that.reason) && status == that.status;
	}

	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + Objects.hashCode(post_id);
		result = 31 * result + Objects.hashCode(reporter_id);
		result = 31 * result + Objects.hashCode(reported_id);
		result = 31 * result + Objects.hashCode(post_id);
		result = 31 * result + Objects.hashCode(message_id);
		result = 31 * result + Objects.hashCode(reason);
		result = 31 * result + Objects.hashCode(status);
		return result;
	}

	@java.lang.Override
	public java.lang.String toString() {
		return "ReportEntity{" +
				"post_id=" + post_id +
				", reporter_id=" + reporter_id +
				", reported_id=" + reported_id +
				", post_id=" + post_id +
				", message_id=" + message_id +
				", reason='" + reason + '\'' +
				", status=" + status +
				'}';
	}
}

/*
    id SERIAL PRIMARY KEY,
    reporter_id INT REFERENCES users(id) ON DELETE CASCADE,
    reported_id INT REFERENCES users(id) ON DELETE CASCADE,
    post_id INT REFERENCES post(id) ON DELETE SET NULL,
    message_id INT REFERENCES messages(id) ON DELETE SET NULL,
    reason TEXT NOT NULL,
    status report_status_enum DEFAULT 'open'
 */