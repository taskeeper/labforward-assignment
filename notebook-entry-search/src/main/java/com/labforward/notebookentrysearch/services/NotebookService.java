package com.labforward.notebookentrysearch.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.labforward.notebookentrysearch.models.Notebook;
import com.labforward.notebookentrysearch.repositories.NotebookRepository;

@Service
@Transactional
public class NotebookService {
	
	Logger logger = LoggerFactory.getLogger(NotebookEntryService.class);
	
	@Autowired
	private NotebookRepository notebookRepository;
	
	public NotebookService(NotebookRepository notebookRepository) {
		
		this.notebookRepository = notebookRepository;
	}
	
	// get notebook by Id
	public ResponseEntity<?> getNotebookById(Long notebookId) {
		
		Optional<Notebook> currentNotebook = notebookRepository.findById(notebookId);
		
		if(!currentNotebook.isPresent()) {
			
			logger.error("Notebook (Id:" + notebookId + ") does not exist");
			
			logger.error("getNotebookById(" + notebookId + ") failed");
			
			return new ResponseEntity<>("Notebook (Id: " + notebookId + ") does not exist", HttpStatus.NOT_FOUND);
			
		}
		
		logger.info("getNotebookById(" + notebookId + ") executed");
		
		return new ResponseEntity<>(currentNotebook, HttpStatus.OK);
	}
	
	// add new notebook
	public ResponseEntity<?> addNotebook(Notebook notebook) {
		
		Notebook savedNotebook = notebookRepository.save(notebook);
		
		logger.info("addNotebook() executed");
		
		return new ResponseEntity<>(savedNotebook, HttpStatus.OK);
		
	}
	
	// update notebook by notebook Id
	public ResponseEntity<?> updateNotebook(Long notebookId, Notebook notebook) {
		
		// check if notebook exists in database
		if(!notebookRepository.existsById(notebookId)) {
			
			logger.error("Notebook (Id:" + notebookId + ") does not exist");
			
			logger.error("updateNotebook(" + notebookId + ") failed");
			
			return new ResponseEntity<>("Notebook (Id: " + notebookId + ") does not exist", HttpStatus.NOT_FOUND);
			
		}
		
		notebook.setId(notebookId);
		
		// persist notebook in database
		Notebook savedNotebook = notebookRepository.save(notebook);
		
		logger.info("updateNotebook(" + notebookId + ")  executed");
		
		return new ResponseEntity<>(savedNotebook, HttpStatus.OK);
		
	}
	
	// delete notebook by notebook Id
	public ResponseEntity<?> deleteNotebook(Long notebookId) {
		
		// check if notebook exists in database
		if(!notebookRepository.existsById(notebookId)) {
			
			logger.error("Notebook (Id: " + notebookId + ") does not exist");
			
			logger.error("deleteNotebook(" + notebookId + ") failed");
			
			return new ResponseEntity<>("Notebook (Id: " + notebookId + ") does not exist", HttpStatus.NOT_FOUND);
			
		}
		
		// delete notebook from database
		notebookRepository.deleteById(notebookId);
		
		logger.info("deleteNotebook(" + notebookId + ")  executed");
		
		return new ResponseEntity<>("Notebook deleted", HttpStatus.OK);
	}

}
