package com.labforward.notebookentrysearch.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.labforward.notebookentrysearch.models.Notebook;
import com.labforward.notebookentrysearch.models.NotebookEntry;
import com.labforward.notebookentrysearch.repositories.NotebookEntryRepository;
import com.labforward.notebookentrysearch.repositories.NotebookRepository;
import com.labforward.notebookentrysearch.utils.AppUtils;

@Service
@Transactional
public class NotebookEntryService {
	
	Logger logger = LoggerFactory.getLogger(NotebookEntryService.class);
	
	@Autowired
	private NotebookRepository notebookRepository;
	
	@Autowired
	private NotebookEntryRepository notebookEntryRepository;
	
	public NotebookEntryService(NotebookEntryRepository notebookEntryRepository, NotebookRepository notebookRepository) {
		
		this.notebookEntryRepository = notebookEntryRepository;
		this.notebookRepository = notebookRepository;
	}
	
	// get notebook entries by notebook id
	public ResponseEntity<?> getNotebookEntries(Long notebookId) {
		
		if(!notebookRepository.existsById(notebookId)) {
			
			logger.error("Notebook does not exist");
			
			logger.error("getNotebookEntries(" + notebookId + ") failed");
			
			return new ResponseEntity<>("Notebook (Id: " + notebookId + ") does not exist", HttpStatus.NOT_FOUND);
			
		}
		
		logger.info("getNotebookEntries(" + notebookId + ") executed");
		
		return new ResponseEntity<>(notebookEntryRepository.findByNotebookId(notebookId), HttpStatus.OK);
		
	}
	
	// get one notebook entry by notebook entry id
	public Optional<NotebookEntry> getNotebookEntry(Long notebookEntryId) {
		
		return notebookEntryRepository.findById(notebookEntryId);
				
	}
	
	// counts number of occurrences of a word in a notebook entry
	public Long countWordOccurencesInNotebookEntry(Long notebookEntryId, String searchWord) {
		
		Long noteBookEntryCount = 0l;
		
		Optional<NotebookEntry> entry = getNotebookEntry(notebookEntryId);
		
		// check if entry exists
		if(!entry.isPresent()) {
			
			logger.error("Notebook entry (Id: " + notebookEntryId + ") does not exist");
			
			logger.error("countWordOccurencesInNotebookEntry(" + notebookEntryId + ", " + searchWord + ") failed");
			
			return -1l;
			
		}
		
		// convert notebook entry text to array
		String[] entryTextInStringArray = entry.get().getNoteText().split(" ");
		
		// count occurrences of a specific word
		noteBookEntryCount = Arrays.stream(entryTextInStringArray)
								   .filter(s -> s.equals(searchWord))
								   .count();
		
		logger.info("countWordOccurencesInNotebookEntry(" + notebookEntryId + ", " + searchWord + ") executed");
		
		return noteBookEntryCount;
		
	}
	
	// find similar entries of a word in notebook entry
	public List<String> findSimilarWordsInNotebookEntry(Long notebookEntryId, String searchWord) {
		
		int wordLength = searchWord.length();
		
		List<String> simialrWords = new ArrayList<>();
		
		Optional<NotebookEntry> entry = getNotebookEntry(notebookEntryId);
		
		// check if entry exists
		if(!entry.isPresent()) {
			
			logger.error("Notebook entry (Id: " + notebookEntryId + ") does not exist");
			
			logger.error("findSimilarWordsInNotebookEntry(" + notebookEntryId + ", " + searchWord + ") failed");
			
			return null;
			
		}
			
		String[] noteBookSimilarEntries = entry.get().getNoteText().split(" ");
		
		// find similar words to search word
		simialrWords = Arrays.asList(noteBookSimilarEntries).parallelStream()
								.filter(s -> 
							   		( (s.trim().contains(searchWord.substring(2, wordLength)) && s.trim().length() == wordLength - 1) ||
							   		  (s.trim().contains(searchWord.substring(2, wordLength)) && s.trim().length() == wordLength + 1) || 
							   		  (s.trim().contains(searchWord.substring(1, wordLength - 1)) && s.trim().length() == wordLength - 1) ||
							   		  (s.trim().contains(searchWord.substring(1, wordLength - 1)) && s.trim().length() == wordLength + 1) ||
							   		  s.equalsIgnoreCase(searchWord)
							   		) && !s.equals(searchWord))
							   .collect(Collectors.toList());
		
		
		logger.info("findSimilarWordsInNotebookEntry(" + notebookEntryId + ", " + searchWord + ") executed");
		
		return simialrWords;
		
	}
	
	// find similar entries of a word in notebook entry using Levenshtien Logic
	public List<String> findSimilarWordsInNotebookEntry_UsingLevenshtienLogic(Long notebookEntryId, String searchWord) {
		
		List<String> simialrWords = new ArrayList<>();
		
		Optional<NotebookEntry> entry = getNotebookEntry(notebookEntryId);
		
		// check if entry exists
		if(!entry.isPresent()) {
			
			logger.error("Notebook entry (Id: " + notebookEntryId + ") does not exist");
			
			logger.error("findSimilarWordsInNotebookEntry_UsingLevenshtienLogic(" + notebookEntryId + ", " + searchWord + ") failed");
			
			return null;
			
		}
			
		String[] noteBookSimilarEntries = entry.get().getNoteText().split(" ");
		
		// find similar words to search word
		simialrWords = Arrays.asList(noteBookSimilarEntries).parallelStream()
						   .filter(s -> AppUtils.calculate(s, searchWord) <= 1 && !s.equals(searchWord))
						   .collect(Collectors.toList());
		
		
		logger.info("findSimilarWordsInNotebookEntry_UsingLevenshtienLogic(" + notebookEntryId + ", " + searchWord + ") executed");
		
		return simialrWords;
		
	}
	
	// add notebook entry
	public ResponseEntity<?> addNotebookEntry(Long notebookId, NotebookEntry notebookEntry) {
		
		// fetch current notebook
		Optional<Notebook> currentNotebook =  notebookRepository.findById(notebookId);
		
		// check if current notebook exists
		if(!currentNotebook.isPresent()) {
			
			logger.error("Notebook (Id: " + notebookId + ") does not exist");
			
			logger.error("addNotebookEntry(" + notebookId + ") failed");
			
			return new ResponseEntity<>("Notebook (Id: " + notebookId + ") does not exist", HttpStatus.NOT_FOUND);
		}
		
		// set notebook of notebook entry
		notebookEntry.setNotebook(currentNotebook.get());
		
		// persist notebook entry to database
		NotebookEntry savedEntry = notebookEntryRepository.save(notebookEntry);
		
		logger.info("addNotebookEntry(" + notebookId + ") executed");
		
		return new ResponseEntity<>(savedEntry, HttpStatus.OK);
		
	}
	
	// update notebook entry
	public ResponseEntity<?> updateNotebookEntry(Long notebookEntryId, NotebookEntry notebookEntry) {
		
		// check if entry exists
		if(!notebookEntryRepository.existsById(notebookEntryId)) {
			
			logger.error("Notebook Entry (Id: " + notebookEntryId + ") does not exist");
			
			logger.error("updateNotebookEntry(" + notebookEntryId + ") failed");
			
			return new ResponseEntity<>("Notebook entry (Id: " + notebookEntryId + ") does not exist", HttpStatus.NOT_FOUND);
			
		}
		
		// set notebook entry Id
		notebookEntry.setId(notebookEntryId);
		
		// persist notebook entry to database
		NotebookEntry savedEntry = notebookEntryRepository.save(notebookEntry);
		
		logger.info("updateNotebookEntry(" + notebookEntryId + ") executed");
		
		return new ResponseEntity<>(savedEntry, HttpStatus.OK);
		
	}
	
	// delete notebook entry
	public ResponseEntity<?> deleteNotebookEntry(Long notebookEntryId) {
		
		// check if entry exists
		if(!notebookEntryRepository.existsById(notebookEntryId)) {
			
			logger.error("Notebook Entry (Id: " + notebookEntryId + ") does not exist");
			
			logger.error("deleteNotebookEntry(" + notebookEntryId + ") failed");
			
			return new ResponseEntity<>("Notebook entry (Id: " + notebookEntryId + ") does not exist", HttpStatus.NOT_FOUND);
			
		}
		
		// remove notebook entry from database
		notebookEntryRepository.deleteById(notebookEntryId);
		
		logger.info("deleteNotebookEntry(" + notebookEntryId + ") executed");
		
		return new ResponseEntity<>("Notebook Entry deleted", HttpStatus.OK);

	}
	
}
