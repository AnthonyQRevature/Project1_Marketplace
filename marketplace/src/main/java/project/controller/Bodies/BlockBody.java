package project.controller.Bodies;

import java.util.Objects;

public class BlockBody {
	private Integer id_blocked;
	//private Integer id_blocker;

	public BlockBody() {}
	public BlockBody(Integer id_blocked) {
		this.id_blocked = id_blocked;
	}

	public Integer getId_blocked() {return id_blocked;}
	public void setId_blocked(Integer id_blocked) {this.id_blocked = id_blocked;}

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.id_blocked);
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
        final BlockBody other = (BlockBody) obj;
        return Objects.equals(this.id_blocked, other.id_blocked);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("BlockBody{");
        sb.append("id_blocked=").append(id_blocked);
        sb.append('}');
        return sb.toString();
    }

}