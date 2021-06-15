package com.labforward.notebookentrysearch.Notebook;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.HashSet;
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
import com.labforward.notebookentrysearch.repositories.NotebookRepository;
import com.labforward.notebookentrysearch.services.NotebookService;

@ExtendWith(MockitoExtension.class)
@Transactional(propagation = Propagation.SUPPORTS)
public class NotebookServiceTest {
	
	@Mock private NotebookRepository notebookRepository;
	
	private NotebookService notebookService;
	private Notebook notebook;
	
	@BeforeEach
	void setUp() {
		
		notebookService = new NotebookService(notebookRepository);
	}
	
	@Test
	void canGetNotebookById() {	
		
		// given
		notebook = new Notebook(2l, "Notebook", new HashSet<NotebookEntry>());
		
		given(notebookRepository.findById(notebook.getId())).willReturn(Optional.of(notebook));
		
		// when
		notebookService.getNotebookById(notebook.getId());
		
		// then
		verify(notebookRepository).findById(notebook.getId());
		
	}
	
	@Test
	void canAddNotebook() {
		
		// given
		notebook = new Notebook(1l, "Notebook", new HashSet<NotebookEntry>());
		
		// when
		notebookService.addNotebook(notebook);
		
		// then
		ArgumentCaptor<Notebook> argCaptor = ArgumentCaptor.forClass(Notebook.class);
		
		verify(notebookRepository).save(argCaptor.capture());
		
		Notebook capturedNotebook = argCaptor.getValue();
		
		assertTrue(capturedNotebook.equals(notebook));
		
	}
	
	@Test
	void canUpdateNotebook() {
		
		// given
		notebook = new Notebook(2l, "Notebook 2", new HashSet<NotebookEntry>());
		
		given(notebookRepository.existsById(notebook.getId())).willReturn(true);
		
		// when
		notebookService.updateNotebook(2l, notebook);
		
		// then
		ArgumentCaptor<Notebook> argCaptor = ArgumentCaptor.forClass(Notebook.class);
		
		verify(notebookRepository).save(argCaptor.capture());
		
		Notebook capturedNotebook = argCaptor.getValue();
		
		assertTrue(capturedNotebook.equals(notebook));
		
		assertTrue(capturedNotebook.getNoteBookTitle().equalsIgnoreCase("Notebook 2"));
		
	}
	
	@Test
	void canNotUpdateNotebookBecasueItDoesNotExist() {
		
		// given
		notebook = new Notebook(2l, "Notebook 2", new HashSet<NotebookEntry>());
		
		given(notebookRepository.existsById(notebook.getId())).willReturn(false);
		
		// when
		notebookService.updateNotebook(2l, notebook);
		
		// then
        verify(notebookRepository, never()).save(notebook);
		
	}
	
	@Test
    void canDeleteNotebook() {
		
        // given
        long notebookId = 10;
        
        given(notebookRepository.existsById(notebookId)).willReturn(true);
        
        // when
        notebookService.deleteNotebook(notebookId);

        // then
        verify(notebookRepository).deleteById(notebookId);
        
    }
	
	@Test
    void canNotDeleteNotebookBecauseItDoesNotExist() {
		
        // given
        long notebookId = 10;
        
        given(notebookRepository.existsById(notebookId)).willReturn(false);
        
        // when
        notebookService.deleteNotebook(notebookId);

        // then
        verify(notebookRepository, never()).deleteById(notebookId);
        
    }
	
}
