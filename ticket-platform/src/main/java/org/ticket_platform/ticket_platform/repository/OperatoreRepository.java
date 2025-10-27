

package org.ticket_platform.ticket_platform.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ticket_platform.ticket_platform.model.Operatore;

public interface OperatoreRepository extends JpaRepository<Operatore, Integer> { 
    

    public List<Operatore> findByStato (String stato);

    public Optional<Operatore> findByMail (String mail);

    public List<Operatore> findByCodiceContainingIgnoreCase(String keyword);
   
   

}
