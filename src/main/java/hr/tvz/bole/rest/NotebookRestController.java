package hr.tvz.bole.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import hr.tvz.bole.model.Notebook;
import hr.tvz.bole.server.service.NotebookService;

@RestController
@RequestMapping("/api/notebook")
public class NotebookRestController {

	@Autowired
	NotebookService notebookService;

	/**** REST - POSTMAN ****/

	@GetMapping
	public List<Notebook> findAll() {
		return notebookService.findAll();
	}

	@GetMapping("/{id}")
	public Notebook findOne(@PathVariable Integer id) {
		return notebookService.findOne(id);
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public Notebook save(@RequestBody Notebook notebook) {
		return notebookService.save(notebook);
	}

	@PutMapping("/{id}")
	public Notebook update(@RequestBody Notebook notebook) {
		return notebookService.save(notebook);
	}

	/**** AJAX ***/

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Integer id) {
		notebookService.delete(id);
	}

}
