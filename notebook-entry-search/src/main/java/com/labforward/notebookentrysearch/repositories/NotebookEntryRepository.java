package com.labforward.notebookentrysearch.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.labforward.notebookentrysearch.models.NotebookEntry;

@Repository
public interface NotebookEntryRepository extends JpaRepository<NotebookEntry, Long> {
	
	public Set<NotebookEntry> findByNotebookId(Long notebookId);
	
}
