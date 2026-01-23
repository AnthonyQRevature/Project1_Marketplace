package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import project.Repository.Entities.ReportEntity;
import project.controller.Bodies.ReportPostBody;
import project.controller.Bodies.ReportStatusBody;
import project.controller.Bodies.ReportsOfBody;
import project.service.ReportService;
import project.util.AllowCORS;
import project.util.SecureIndescriminate;
import project.util.SecurityLevel;

import java.util.List;

@RestController
@AllowCORS
public class ReportController {
	private ReportService reportService;

	@Autowired
	public ReportController(ReportService reportService) {
		this.reportService = reportService;
	}

	//figure out user type (admin/normal)

	//get all reports
	@GetMapping("/reports")
	@SecureIndescriminate(SecurityLevel.ADMIN)
	public ResponseEntity<List<ReportEntity>> getAllReports(
			@RequestHeader("Authorization") String authHeader
	)
	{
		return ResponseEntity.ok(reportService.getAllReports());
	}

	//change report status
		//changes status of a report with given id, to new status
	@PatchMapping("/reports")
	@SecureIndescriminate(SecurityLevel.ADMIN)
	public ResponseEntity ChangeStatus
	(
			@RequestHeader("Authorization") String authHeader,
			@RequestBody ReportStatusBody body, Errors error)
	{
		//call dao to change status
		if(! reportService.changeReportStatus(body.getId(), body.getStatus()))
		{
			//TODO ERROR HANDLING: if reportService is false do thingss here.
		}

		System.out.println(error.getAllErrors());

		return ResponseEntity.ok().build();
	}

	//delete report by id
	@DeleteMapping("/reports/{id}")
	@SecureIndescriminate(SecurityLevel.ADMIN)
	public ResponseEntity<?> DeleteReport(
			@RequestHeader("Authorization") String authHeader,
			@PathVariable Integer id
	)
	{
		if(! reportService.Delete(id))
		{
			//TODO: ERROR HANDLING: if report status is false, do things here
		}

		return ResponseEntity.ok().build();
	}

	//get all reports of user id
	@GetMapping("/users/{id}/reports")
	@SecureIndescriminate(SecurityLevel.ADMIN)
	public ResponseEntity<List<ReportEntity>> ReportsOf
		(
				@RequestHeader("Authorization") String authHeader,
				@PathVariable Integer id,
				@RequestBody() ReportsOfBody body
		)
	{
		return ResponseEntity.ok(reportService.getReportsOf(body.getId()));
	}

	//public void getAllReportsFromUser()
	//{}

	//create report from user ID
	@PostMapping("/users/{id}/reports")
	@SecureIndescriminate(SecurityLevel.USER)
	public ResponseEntity<ReportEntity> createNewReport(
			@RequestHeader("Authorization") String authHeader,
			@RequestBody() ReportPostBody body,
			@PathVariable Integer id)
	{
		return ResponseEntity.ok(reportService.createReport(body));
	}


}
