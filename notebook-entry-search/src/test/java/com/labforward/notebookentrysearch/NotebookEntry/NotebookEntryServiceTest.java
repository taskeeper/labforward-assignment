package com.labforward.notebookentrysearch.NotebookEntry;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.labforward.notebookentrysearch.models.Notebook;
import com.labforward.notebookentrysearch.models.NotebookEntry;
import com.labforward.notebookentrysearch.repositories.NotebookEntryRepository;
import com.labforward.notebookentrysearch.repositories.NotebookRepository;
import com.labforward.notebookentrysearch.services.NotebookEntryService;

@ExtendWith(MockitoExtension.class)
@Transactional(propagation = Propagation.SUPPORTS)
public class NotebookEntryServiceTest {
	
	@Mock private NotebookRepository notebookRepository;
	@Mock private NotebookEntryRepository notebookEntryRepository;
	
	private NotebookEntryService notebookEntryService;
	private NotebookEntry notebookEntry;
	private Notebook notebook;
	
	@BeforeEach
	void setUp() {
		
		notebookEntryService = new NotebookEntryService(notebookEntryRepository, notebookRepository);
	}
	
	@Test
	void canGetNotebookEntryById() {	
		
		// given
		notebookEntry = new NotebookEntry(1l, "Notebook Entry", new Notebook(1l, "Notebook", new HashSet<NotebookEntry>()));
		
		// when
		notebookEntryService.getNotebookEntry(notebookEntry.getId());
		
		// then
		verify(notebookEntryRepository).findById(notebookEntry.getId());
		
	}
	
	@Test
	void canAddNotebookEntry() {
		
		// given
		notebook = new Notebook(1l, "Notebook", new HashSet<NotebookEntry>());
		
		notebookEntry = new NotebookEntry(1l, "Notebook Entry", notebook);
				
		given(notebookRepository.findById(notebook.getId())).willReturn(Optional.of(notebook));
		
		// when
		notebookEntryService.addNotebookEntry(notebook.getId(), notebookEntry);
		
		// then
		ArgumentCaptor<NotebookEntry> argCaptor = ArgumentCaptor.forClass(NotebookEntry.class);
		
		verify(notebookEntryRepository).save(argCaptor.capture());
		
		NotebookEntry capturedNotebookEntry = argCaptor.getValue();
		
		assertTrue(capturedNotebookEntry.equals(notebookEntry));
		
	}
	
	@Test
	void verifyCountOfWordOccurencesInNotebookEntry() {
		
		// given
		notebook = new Notebook(1l, "Notebook", new HashSet<NotebookEntry>());
		
		notebookEntry = new NotebookEntry(1l, "Word Word Word word wor", notebook);
			
		given(notebookEntryService.getNotebookEntry(notebookEntry.getId())).willReturn(Optional.of(notebookEntry));
		
		// when
		Long count = notebookEntryService.countWordOccurencesInNotebookEntry(notebookEntry.getId(), "Word");
		
		//then
		assertEquals(count , 3);
		
	}
	
	@Test
	void verifyCountOfWordOccurencesInNotebookEntryThatDoesNotExist() {
		
		// given
		notebook = new Notebook(2l, "Notebook", new HashSet<NotebookEntry>());
		
		notebookEntry = new NotebookEntry(2l, "Word Word Word word wor", notebook);

		given(notebookEntryService.getNotebookEntry(notebookEntry.getId())).willReturn(Optional.empty());
		
		// when
		Long count = notebookEntryService.countWordOccurencesInNotebookEntry(notebookEntry.getId(), "Word");
		
		//then
		assertEquals(count , -1);
		
	}
	
	@Test
	void verifySimilarWordsInNotebookEntry() {
		
		// given
		notebook = new Notebook(1l, "Notebook", new HashSet<NotebookEntry>());
		
		notebookEntry = new NotebookEntry(1l, "Word Word Word word wor", notebook);
		
		List<String> expected = Arrays.asList("word", "wor");
			
		given(notebookEntryService.getNotebookEntry(notebookEntry.getId())).willReturn(Optional.of(notebookEntry));
		
		// when
		List<String> similarWords = notebookEntryService.findSimilarWordsInNotebookEntry(notebookEntry.getId(), "Word");
		
		//then
		assertEquals(similarWords, expected);
		
	}
	
	@Test
	void verifySimilarWordsInNotebookEntryThatDoesNotExist() {
		
		// given
		notebook = new Notebook(2l, "Notebook", new HashSet<NotebookEntry>());
		
		notebookEntry = new NotebookEntry(2l, "Word Word Word word wor", notebook);
		
		List<String> expected = Arrays.asList();

		given(notebookEntryService.getNotebookEntry(notebookEntry.getId())).willReturn(Optional.empty());
		
		// when
		List<String> similarWords = notebookEntryService.findSimilarWordsInNotebookEntry(notebookEntry.getId(), "Word");
		
		//then
		assertEquals(similarWords , expected);
		
	}

}
