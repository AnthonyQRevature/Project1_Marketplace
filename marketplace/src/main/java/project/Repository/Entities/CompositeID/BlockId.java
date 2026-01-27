package project.Repository.Entities.CompositeID;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

public class BlockId implements Serializable
{
	private Integer blocker_id;
	private Integer blocked_id;

	public BlockId() {}

	public BlockId(int blocked, int blocker) {
		this.blocked_id = blocked;
		this.blocker_id = blocker;
	}

	public Integer getBlocker_id() {return blocker_id;}
	public void setBlocker_id(Integer blocker_id) {this.blocker_id = blocker_id;}
	public Integer getBlocked_id() {return blocked_id;}
	public void setBlocked_id(Integer blocked_id) {this.blocked_id = blocked_id;}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 53 * hash + this.blocker_id;
		hash = 53 * hash + this.blocked_id;
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
		final BlockId other = (BlockId) obj;
		if (this.blocker_id != other.blocker_id) {
			return false;
		}
		return this.blocked_id == other.blocked_id;
	}

	@Override
	public String toString() {
		return "BlockId{" +
				"blocker_id=" + blocker_id +
				", blocked_id=" + blocked_id +
				'}';
	}
}