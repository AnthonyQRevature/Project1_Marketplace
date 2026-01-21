package project.controller.Bodies;

//{id: int, newStatus:int} -- newStatus may need to change, for now 0 for open, nonZero for resolved
public class ReportStatusBody {
	private int id;

	//zero for open, nonZero for resolved
	private int status;

	public ReportStatusBody(int id, int status) {
		this.id = id;
		this.status = status;
	}

	public ReportStatusBody() {}

	public int getId() {return id;}
	public void setId(int id) {this.id = id;}
	public int getStatus() {return status;}
	public void setStatus(int status) {this.status = status;}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;

		ReportStatusBody that = (ReportStatusBody) o;
		return id == that.id && status == that.status;
	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + status;
		return result;
	}

	@Override
	public String toString() {
		return "ReportStatusBody{" +
				"id=" + id +
				", status=" + status +
				'}';
	}
}
