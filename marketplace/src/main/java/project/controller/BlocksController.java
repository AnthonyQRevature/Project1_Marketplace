package project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import project.Repository.Entities.BlockEntity;
import project.controller.Bodies.BlockBody;
import project.service.BlocksService;
import project.util.AllowCORS;
import project.util.Secure;
import project.util.SecurityLevel;

@RestController
@AllowCORS
public class BlocksController {
	private BlocksService blocksService;

	@Autowired
	public BlocksController(BlocksService blocksService) {
		this.blocksService = blocksService;
	}

	@GetMapping("/users/{id}/blocks")
	@Secure(SecurityLevel.USER)
	public ResponseEntity<List<BlockEntity>> getAllBlocksBy(
			@RequestHeader("Authorization") String authHeader,
			@PathVariable int id
	) {
		try {
			return ResponseEntity.ok(blocksService.getAllBy(id));
		}
		catch (DataIntegrityViolationException e)
		{
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("/users/{id}/blocks")
	@Secure(SecurityLevel.USER)
	public ResponseEntity<?> insertBlock(	
		@RequestHeader("Authorization") String authHeader,
		@PathVariable int id,
		@RequestBody BlockBody body)
	{
		if(id == body.getId_blocked())
		{
			//a user cannot block themself
			return ResponseEntity.badRequest().build();
		}
		try
		{
			blocksService.insertBlock(id, body.getId_blocked());
			return ResponseEntity.ok().build();
		}
		catch (DataIntegrityViolationException e)
		{
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping("/users/{id}/blocks")
	@Secure(SecurityLevel.USER)
	public ResponseEntity<?> unBlock(
		@RequestHeader("Authorization") String authHeader,
		@PathVariable int id,
		@RequestBody BlockBody body, Errors error)
	{
		try
		{
			blocksService.delete(id, body.getId_blocked());
			return ResponseEntity.ok().build();
		}
		catch (DataIntegrityViolationException e)
		{
			return ResponseEntity.notFound().build();
		}
	}
}
