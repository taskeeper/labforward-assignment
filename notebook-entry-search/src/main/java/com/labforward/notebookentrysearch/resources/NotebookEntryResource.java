package com.labforward.notebookentrysearch.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.labforward.notebookentrysearch.models.NotebookEntry;
import com.labforward.notebookentrysearch.services.NotebookEntryService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/notebookEntry")
@Api(tags="Notebook Entry APIs", value ="Notebook Entry APIs", produces = "Application/json", consumes = "application/json")
public class NotebookEntryResource {
	
	@Autowired
	private NotebookEntryService notebookEntryService;
	
	// Get All entries in Notebook
	@RequestMapping(method = RequestMethod.GET, value = "/all")
	@ApiOperation(value = "find all notebook entries", notes = "it returns all entries in a researcher notebook")
	public ResponseEntity<?> getAllNotebookEntries(@RequestParam("notebookId") Long notebookId) {
		
		return notebookEntryService.getNotebookEntries(notebookId);
	}
	
	// Get count of occurrences of a specific word in a notebook entry
	@RequestMapping(method = RequestMethod.GET, value = "/searchWordFrequency")
	@ApiOperation(value = "find frequency of specific word in a notebook entry ", notes = "it returns number of occurences of a word in a researcher notebook")
	public ResponseEntity<?> searchWordFrequencyInNotebookEntry(@RequestParam("notebookEntryId") Long notebookEntryId, @RequestParam("searchWord") String searchWord) {
		
		Long count = notebookEntryService.countWordOccurencesInNotebookEntry(notebookEntryId, searchWord);
		
		if(count == -1l) {
			
			return new ResponseEntity<>("Notebook entry (Id: " + notebookEntryId + ") does not exist", HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(count, HttpStatus.OK);
		
	}
	
	// Get similar words of a specific word in a notebook entry
	@RequestMapping(method = RequestMethod.GET, value = "/searchWordSimilarity")
	@ApiOperation(value = "find similar words to a specific word in a notebook entry ", notes = "it returns list of words similiar to a word in a researcher notebook")
	public ResponseEntity<?> findSimilarWordsInNotebookEntry(@RequestParam("notebookEntryId") Long notebookEntryId, @RequestParam("searchWord") String searchWord) {
		
		List<String> similarWords = notebookEntryService.findSimilarWordsInNotebookEntry(notebookEntryId, searchWord);
		
		if(similarWords == null) {
			
			return new ResponseEntity<>("Notebook entry (Id: " + notebookEntryId + ") does not exist", HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(similarWords, HttpStatus.OK);
	}
	
	// Get similar words of a specific word in a notebook entry using Levenshtein Logic
	@RequestMapping(method = RequestMethod.GET, value = "/searchWordSimilarityUsingLevenshteinLogic")
	@ApiOperation(value = "find similar words to a specific word in a notebook entry using Levenshtein Logic ", notes = "it returns list of words similiar to a word in a researcher notebook")
	public ResponseEntity<?> findSimilarWordsInNotebookEntryUsingLevenshteinLogic(@RequestParam("notebookEntryId") Long notebookEntryId, @RequestParam("searchWord") String searchWord) {
		
		List<String> similarWords = notebookEntryService.findSimilarWordsInNotebookEntry_UsingLevenshtienLogic(notebookEntryId, searchWord);
		
		if(similarWords == null) {
			
			return new ResponseEntity<>("Notebook entry (Id: " + notebookEntryId + ") does not exist", HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(similarWords, HttpStatus.OK);
	}
	
	// Add new Entry in Notebook
	@RequestMapping(method = RequestMethod.POST)
	@ApiOperation(value = "add new entry in a notebook", notes = "it returns saved notebook entry")
	public ResponseEntity<?> addEntryToNotebook(@RequestParam("notebookId") Long notebookId, @RequestBody NotebookEntry notebookEntry) {
		
		return notebookEntryService.addNotebookEntry(notebookId, notebookEntry);
	}
	
	// Update existing Entry in Notebook
	@RequestMapping(method = RequestMethod.PUT, value = "/{notebookEntryId}")
	@ApiOperation(value = "update entry in a notebook", notes = "it returns updated notebook entry")
	public ResponseEntity<?> updateNotebookEntryById(@PathVariable Long notebookEntryId, @RequestBody NotebookEntry notebookEntry) {
		
		return notebookEntryService.updateNotebookEntry(notebookEntryId, notebookEntry);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{notebookEntryId}")
	@ApiOperation(value = "delete an entry in a notebook", notes = "it returns a confirmation that entry has been deleted")
	public ResponseEntity<?> deleteNotebookEntryById(@PathVariable Long notebookEntryId) {
		
		return notebookEntryService.deleteNotebookEntry(notebookEntryId);
	}

}
