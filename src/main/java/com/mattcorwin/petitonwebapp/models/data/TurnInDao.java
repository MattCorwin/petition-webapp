package com.mattcorwin.petitonwebapp.models.data;

import com.mattcorwin.petitonwebapp.models.TurnIn;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface TurnInDao extends CrudRepository<TurnIn, Integer> {
}


