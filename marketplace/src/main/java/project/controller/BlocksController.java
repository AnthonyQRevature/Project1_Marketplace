package project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import project.service.BlocksService;
import project.util.AllowCORS;
import project.util.SecureIndescriminate;
import project.util.SecurityLevel;
import project.controller.Bodies.unBlockBody;
import project.controller.Bodies.insertBlockBody;

import java.util.List;

@RestController
@AllowCORS
public class BlocksController {
	private BlocksService blocksService;

	@Autowired
	public BlocksController(BlocksService blocksService) {
		this.blocksService = blocksService;
	}

	@GetMapping("/users/{id}/blocks")
	@SecureIndescriminate(SecurityLevel.ADMIN)
	public void getAllBlocksBy(@RequestHeader("Authorization") String authHeader)
	{

	}

	@PutMapping("/users/{id}/blocks")
	@SecureIndescriminate(SecurityLevel.ADMIN)
	public void insertBlock(	@RequestHeader("Authorization") String authHeader,
								@RequestBody insertBlockBody body, Errors error)
	{

	}

	@DeleteMapping("/users/{id}/blocks")
	@SecureIndescriminate(SecurityLevel.USER)
	public void unBlock(@RequestHeader("Authorization") String authHeader,
						@PathVariable Integer id,
						@RequestBody unBlockBody body, Errors error)
	{
		//remove the block
		blocksService.delete(id, body.getId_blocked());
	}
}
