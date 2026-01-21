package project.Repository.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;
import java.util.Optional;

import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.type.PostgreSQLEnumJdbcType;

import jakarta.persistence.Enumerated;

@Entity
@Table(name="report")
public class ReportEntity {
	@Column(name="id")
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer report_id;

	@Column(name="reporter_id")
	private Integer reporter_id;

	@Column(name="reported_id")
	private Integer reported_id;

	@Column(name="post_id")
	private Optional<Integer> post_id;

	@Column(name="message_id")
	private Optional<Integer> message_id;

	@Column(name="reason")
	private String reason;

	@Enumerated(EnumType.STRING)
	//Hibernate 6
	//otherwise we'd need to use native query
	@JdbcType(PostgreSQLEnumJdbcType.class)
	@Column(name="status")
	private ReportStatus status;

    public ReportEntity(Integer report_id, Integer reporter_id, Integer reported_id, Optional<Integer> post_id, Optional<Integer> message_id, String reason, ReportStatus status) {
        this.report_id = report_id;
        this.reporter_id = reporter_id;
        this.reported_id = reported_id;
        this.post_id = post_id;
        this.message_id = message_id;
        this.reason = reason;
        this.status = status;
    }

    public ReportEntity() {}

    public Integer getReport_id() {
        return report_id;
    }
    public void setReport_id(Integer report_id) {
        this.report_id = report_id;
    }
    public Integer getReporter_id() {
        return reporter_id;
    }
    public void setReporter_id(Integer reporter_id) {
        this.reporter_id = reporter_id;
    }
    public Integer getReported_id() {
        return reported_id;
    }
    public void setReported_id(Integer reported_id) {
        this.reported_id = reported_id;
    }
    public String getReason() {
        return reason;
    }
    public void setReason(String reason) {
        this.reason = reason;
    }
    public ReportStatus getStatus() {
        return status;
    }
    public void setStatus(ReportStatus status) {
        this.status = status;
    }
    public Optional<Integer> getPost_id() {return post_id;}
    public void setPost_id(Optional<Integer> post_id) {this.post_id = post_id;}

    public Optional<Integer> getMessage_id() {
        return message_id;
    }

    public void setMessage_id(Optional<Integer> message_id) {
        this.message_id = message_id;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.report_id);
        hash = 97 * hash + Objects.hashCode(this.reporter_id);
        hash = 97 * hash + Objects.hashCode(this.reported_id);
        hash = 97 * hash + Objects.hashCode(this.post_id);
        hash = 97 * hash + Objects.hashCode(this.message_id);
        hash = 97 * hash + Objects.hashCode(this.reason);
        hash = 97 * hash + Objects.hashCode(this.status);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ReportEntity other = (ReportEntity) obj;
        if (!Objects.equals(this.reason, other.reason)) {
            return false;
        }
        if (!Objects.equals(this.report_id, other.report_id)) {
            return false;
        }
        if (!Objects.equals(this.reporter_id, other.reporter_id)) {
            return false;
        }
        if (!Objects.equals(this.reported_id, other.reported_id)) {
            return false;
        }
        if (!Objects.equals(this.post_id, other.post_id)) {
            return false;
        }
        if (!Objects.equals(this.message_id, other.message_id)) {
            return false;
        }
        return this.status == other.status;
    }

	public static enum ReportStatus
	{
		open,
		resolved
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