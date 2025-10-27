package org.ticket_platform.ticket_platform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ticket_platform.ticket_platform.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

    
    public Categoria findByNome(String nome);

}
