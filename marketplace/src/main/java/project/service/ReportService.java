package project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.Repository.Entities.MessageEntity;
import project.Repository.Entities.PostEntity;
import project.Repository.Entities.ReportEntity;
import project.Repository.dao.MessageDao;
import project.Repository.dao.PostDao;
import project.Repository.dao.ReportDAO;
import project.controller.Bodies.ReportPostBody;

@Service
public class ReportService {
	ReportDAO dao;
	PostDao postDao;
	MessageDao messageDao;

	@Autowired
	public ReportService(ReportDAO dao, PostDao postDao, MessageDao messageDao) {
		this.dao = dao;
		this.postDao = postDao;
		this.messageDao = messageDao;
	}

	public Optional<ReportEntity> getReportById(Integer id) {return dao.findById(id);}

	public List<ReportEntity> getAllReports() {return dao.findAll();}

	public Boolean changeReportStatus(Integer id, Integer newStatus)
	{
		try {
			if(newStatus == 0)
				dao.ChangeStatus(id, ReportEntity.ReportStatus.open);
			else
				dao.ChangeStatus(id, ReportEntity.ReportStatus.resolved);
			return true;
		}catch (RuntimeException e)
		{
			return false;
		}
	}

	public Boolean Delete(Integer id)
	{
		try
		{
			dao.deleteById(id);
			return true;
		}catch(RuntimeException e)
		{
			//TODO handle exceptions
			return false;
		}
	}

	public List<ReportEntity> getReportsOf(Integer id) {return dao.getReportsOf(id);}

	public List<ReportEntity> getReportsFrom(Integer id) {return dao.getReportsFrom(id);}

	//TODO check that the post/message matches the reported_id
	public ReportEntity createReport(ReportPostBody body)
	{
		ReportEntity newReport = new ReportEntity();

		newReport.setReporter_id(body.getReporter_id());
		newReport.setReported_id(body.getReported_id());
		newReport.setReason(body.getReason());
		newReport.setStatus(ReportEntity.ReportStatus.open);
		newReport.setMessage_id(body.getMessage_id());
		newReport.setPost_id(body.getPost_id());


		return dao.save(newReport);
	}

	public Boolean deleteAssociatedMessage(ReportEntity entity)
	{
		if(entity.getMessage_id() != null) {
			messageDao.deleteById(entity.getMessage_id());
			return true;
		}
		return false;
	}

	public boolean deleteAssociatedPost(ReportEntity entity)
	{
		if(entity.getPost_id() != null)
		{
			postDao.deleteById(entity.getPost_id());
			return true;
		}
		return false;
	}
}
