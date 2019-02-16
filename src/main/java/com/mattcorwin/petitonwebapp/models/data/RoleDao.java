package com.mattcorwin.petitonwebapp.models.data;


import com.mattcorwin.petitonwebapp.models.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface RoleDao extends CrudRepository<Role, Integer> {
    Role findByRole(String role);
}
