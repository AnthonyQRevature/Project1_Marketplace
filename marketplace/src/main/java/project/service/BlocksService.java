package project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.Repository.dao.BlocksDAO;
import project.Repository.dao.ReportDAO;

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
		//dao.delete();
	}
}
