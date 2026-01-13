package project.Repository.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;

import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.type.PostgreSQLEnumJdbcType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
@Table(name="user_demerits")
public class UserDemeritsEntity {
	@Column(name="id")
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer post_id;

	@Column(name="user_id")
	private Integer user_id;
	@Column(name="admin_id")
	private Integer admin_id;
	@Column(name="report_id")
	private Integer report_id;
	@Column(name="reason")
	private String reason;

	public static enum DemeritActionEnum
	{
		warning,
		post_removed,
		user_suspended,
		user_deleted
	}

	@Enumerated(EnumType.STRING)
	//Hibernate 6
	//otherwise we'd need to use native query
	@JdbcType(PostgreSQLEnumJdbcType.class)
	@Column(name="action")
	private DemeritActionEnum actionEnum;

	public UserDemeritsEntity() {}

	public UserDemeritsEntity(UserDemeritsEntity userDemeritsEntity) {
		this.post_id = userDemeritsEntity.post_id;
		this.user_id = userDemeritsEntity.user_id;
		this.admin_id = userDemeritsEntity.admin_id;
		this.report_id = userDemeritsEntity.report_id;
		this.reason = userDemeritsEntity.reason;
		this.actionEnum = userDemeritsEntity.actionEnum;
	}

	public UserDemeritsEntity(Integer post_id, Integer user_id, Integer admin_id, Integer report_id, String reason, DemeritActionEnum actionEnum) {
		this.post_id = post_id;
		this.user_id = user_id;
		this.admin_id = admin_id;
		this.report_id = report_id;
		this.reason = reason;
		this.actionEnum = actionEnum;
	}

	public Integer getPost_id() {return post_id;}
	public void setPost_id(Integer post_id) {this.post_id = post_id;}
	public Integer getUser_id() {return user_id;}
	public void setUser_id(Integer user_id) {this.user_id = user_id;}
	public Integer getAdmin_id() {return admin_id;}
	public void setAdmin_id(Integer admin_id) {this.admin_id = admin_id;}
	public Integer getReport_id() {return report_id;}
	public void setReport_id(Integer report_id) {this.report_id = report_id;}
	public String getReason() {return reason;}
	public void setReason(String reason) {this.reason = reason;}
	public DemeritActionEnum getActionEnum() {return actionEnum;}
	public void setActionEnum(DemeritActionEnum actionEnum) {this.actionEnum = actionEnum;}

	@Override
	public boolean equals(Object object) {
		if (object == null || getClass() != object.getClass()) return false;
		if (!super.equals(object)) return false;

		UserDemeritsEntity that = (UserDemeritsEntity) object;
		return java.util.Objects.equals(post_id, that.post_id) && java.util.Objects.equals(user_id, that.user_id) && java.util.Objects.equals(admin_id, that.admin_id) && java.util.Objects.equals(report_id, that.report_id) && java.util.Objects.equals(reason, that.reason) && actionEnum == that.actionEnum;
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + Objects.hashCode(post_id);
		result = 31 * result + Objects.hashCode(user_id);
		result = 31 * result + Objects.hashCode(admin_id);
		result = 31 * result + Objects.hashCode(report_id);
		result = 31 * result + Objects.hashCode(reason);
		result = 31 * result + Objects.hashCode(actionEnum);
		return result;
	}

	@java.lang.Override
	public java.lang.String toString() {
		return "UserDemeritsEntity{" +
				"post_id=" + post_id +
				", user_id=" + user_id +
				", admin_id=" + admin_id +
				", report_id=" + report_id +
				", reason='" + reason + '\'' +
				", actionEnum=" + actionEnum +
				'}';
	}
}

/*
    id SERIAL PRIMARY KEY,
    user_id INT REFERENCES users(id) ON DELETE CASCADE,
    admin_id INT REFERENCES users(id) ON DELETE SET NULL,
    report_id INT REFERENCES report(id) ON DELETE SET NULL,
    reason TEXT NOT NULL,
    action demerit_action_enum NOT NULL
 */