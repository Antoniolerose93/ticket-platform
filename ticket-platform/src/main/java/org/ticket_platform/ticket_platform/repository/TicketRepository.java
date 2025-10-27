package org.ticket_platform.ticket_platform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ticket_platform.ticket_platform.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
   

public List<Ticket> findByOperatoreMail(String mail); 

public List<Ticket> findByOperatoreMailAndTitoloContainingIgnoreCase(String mail, String keyword);

public List<Ticket> findByTitoloContainingIgnoreCase(String keyword);
   

}
