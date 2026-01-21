package project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.Repository.Entities.ReportEntity;
import project.Repository.dao.ReportDAO;
import project.controller.Bodies.ReportPostBody;

@Service
public class ReportService {
	ReportDAO dao;

	@Autowired
	public ReportService(ReportDAO dao) {
		this.dao = dao;
	}

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
			e.printStackTrace();
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
			e.printStackTrace();
			return false;
		}
	}

	public List<ReportEntity> getReportsOf(Integer id) {return dao.getReportsOf(id);}

	public ReportEntity createReport(ReportPostBody body)
	{
		ReportEntity newReport = new ReportEntity();

		newReport.setReporter_id(body.getReporter_id());
		newReport.setReported_id(body.getReported_id());
		newReport.setReason(body.getReason());
		newReport.setStatus(ReportEntity.ReportStatus.open);
		if(body.getMessage_id().isPresent())
			newReport.setMessage_id(body.getMessage_id());
		if(body.getPost_id().isPresent())
			newReport.setPost_id(body.getPost_id());

		return dao.save(newReport);
	}
}
