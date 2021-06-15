package com.labforward.notebookentrysearch.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.labforward.notebookentrysearch.models.Notebook;
import com.labforward.notebookentrysearch.services.NotebookService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/notebook")
@Api(tags="Notebook APIs", value ="Notebook APIs", produces = "Application/json", consumes = "application/json")
public class NotebookResource {
	
	@Autowired
	private NotebookService notebookService;
	
	// Get notebook by Id
	@RequestMapping(method = RequestMethod.GET, value = "/{notebookId}")
	@ApiOperation(value = "find notebook by notebook id", notes = "it returns notebook and its entries")
	public ResponseEntity<?> getNotebookById(@PathVariable Long notebookId) {
		
		return notebookService.getNotebookById(notebookId);
	}
	
	// Add new notebook
	@RequestMapping(method = RequestMethod.POST)
	@ApiOperation(value = "add notebook", notes = "it returns added notebook details")
	public ResponseEntity<?> addNotebook(@RequestBody Notebook notebook) {
		
		return notebookService.addNotebook(notebook);
	}
	
	// Update existing notebook
	@RequestMapping(method = RequestMethod.PUT, value = "/{notebookId}")
	@ApiOperation(value = "update notebook by notebook id", notes = "it returns updated notebook details")
	public ResponseEntity<?> updateNotebookById(@PathVariable Long notebookId, @RequestBody Notebook notebook) {
		
		return notebookService.updateNotebook(notebookId, notebook);
	}
	
	// Delete notebook
	@RequestMapping(method = RequestMethod.DELETE, value = "/{notebookId}")
	@ApiOperation(value = "delete notebook by notebook id", notes = "it returns confirmation that a notebook has been deleted")
	public ResponseEntity<?> deleteNotebookEntryById(@PathVariable Long notebookId) {
		
		return notebookService.deleteNotebook(notebookId);
	}

}
