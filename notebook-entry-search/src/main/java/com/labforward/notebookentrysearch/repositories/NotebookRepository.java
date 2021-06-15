package com.labforward.notebookentrysearch.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.labforward.notebookentrysearch.models.Notebook;

@Repository
public interface NotebookRepository extends JpaRepository<Notebook, Long>  {
	
}
