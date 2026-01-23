package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.Repository.Entities.BlockEntity;
import project.Repository.dao.BlocksDAO;

import java.util.List;

@Service
public class BlocksService
{
	private BlocksDAO dao;

	@Autowired
	public BlocksService(BlocksDAO dao) {
		this.dao = dao;
	}

	public void delete(Integer blocker_id, Integer blocked_id)
	{
		BlockEntity blockEntity = new BlockEntity();
		blockEntity.setBlocked(blocked_id);
		blockEntity.setBlocker(blocker_id);

		dao.delete(blockEntity);
	}

	public void insertBlock(Integer blocker_id, Integer blocked_id)
	{
		BlockEntity blockEntity = new BlockEntity();
		blockEntity.setBlocker(blocker_id);
		blockEntity.setBlocked(blocked_id);

		dao.save(blockEntity);
	}

	public List<BlockEntity> getAllBy(Integer blocker_id)
	{
		return dao.findBlocksByUser(blocker_id);
	}
}
