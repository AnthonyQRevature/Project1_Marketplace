package project.Repository.Entities;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.*;
import project.Repository.Entities.CompositeID.BlockId;


@Entity
@Table(name="block")
@IdClass(BlockId.class) //composite key
public class BlockEntity {
	/*
	 * The specified primary key type must:
	 * be a non-abstract regular Java class, or a Java record type,
	 * have a public or protected constructor with no parameters, unless it is a record type, and
	 * implement equals and hashCode, defining value equality consistently with equality of the mapped primary key of the database table.
	 */

//	private BlockId id;

	@Id
	@Column(name="blocker_id")
	private int blocker_id;
	@Id
	@Column(name="blocked_id")
	private int blocked_id;

	public int getBlocker() {return blocker_id;}
	public void setBlocker(int blocker) {this.blocker_id = blocker;}
	public int getBlocked() {return blocked_id;}
	public void setBlocked(int blocked) {this.blocked_id = blocked;}
//	public BlockId getId() {return id;}
//	public void setId(BlockId id) {this.id = id;}

	public BlockEntity() {}

	public BlockEntity(BlockEntity blockEntity) {
//		this.id = blockEntity.id;
		this.blocker_id = blockEntity.blocker_id;
		this.blocked_id = blockEntity.blocked_id;
	}

	public BlockEntity(/*BlockId id,*/ int blocker_id, int blocked_id) {
//		this.id = id;
		this.blocker_id = blocker_id;
		this.blocked_id = blocked_id;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;

		BlockEntity that = (BlockEntity) o;
		return blocker_id == that.blocker_id && blocked_id == that.blocked_id;
	}

	@Override
	public int hashCode() {
		int result = blocker_id;
		result = 31 * result + blocked_id;
		return result;
	}

	@Override
	public String toString() {
		return "BlockEntity{" +
				"blocker_id=" + blocker_id +
				", blocked_id=" + blocked_id +
				'}';
	}
}
