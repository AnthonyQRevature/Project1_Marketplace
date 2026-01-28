package project.controller.Bodies;

import java.util.Objects;

//{reporter_id: int, reported_id: int, post_id?: int, message_id?:  int, reason:String}
public class ReportPostBody {
	private int reporter_id;
	private int reported_id;
	private Integer post_id;
	private Integer message_id;
	private String reason;

	public ReportPostBody() {}

	public ReportPostBody(int reporter_id, int reported_id, Integer post_id, Integer message_id, String reason) {
		this.reporter_id = reporter_id;
		this.reported_id = reported_id;
		this.post_id = post_id;
		this.message_id = message_id;
		this.reason = reason;
	}

	public int getReporter_id() {return reporter_id;}
	public void setReporter_id(int reporter_id) {this.reporter_id = reporter_id;}
	public int getReported_id() {return reported_id;}
	public void setReported_id(int reported_id) {this.reported_id = reported_id;}
	public String getReason() {return reason;}
	public void setReason(String reason) {this.reason = reason;}
	public Integer getPost_id() {return post_id;}
	public void setPost_id(Integer post_id) {this.post_id = post_id;}
	public Integer getMessage_id() {return message_id;}
	public void setMessage_id(Integer message_id) {this.message_id = message_id;}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;

		ReportPostBody that = (ReportPostBody) o;
		return reporter_id == that.reporter_id && reported_id == that.reported_id && Objects.equals(post_id, that.post_id) && Objects.equals(message_id, that.message_id) && Objects.equals(reason, that.reason);
	}

	@Override
	public int hashCode() {
		int result = reporter_id;
		result = 31 * result + reported_id;
		result = 31 * result + Objects.hashCode(post_id);
		result = 31 * result + Objects.hashCode(message_id);
		result = 31 * result + Objects.hashCode(reason);
		return result;
	}

	@Override
	public String toString() {
		return "ReportPostBody{" +
				"reporter_id=" + reporter_id +
				", reported_id=" + reported_id +
				", post_id=" + post_id +
				", message_id=" + message_id +
				", reason='" + reason + '\'' +
				'}';
	}
}
