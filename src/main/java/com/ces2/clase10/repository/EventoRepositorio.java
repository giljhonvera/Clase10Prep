package com.ces2.clase10.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ces2.clase10.model.Comentario;
import com.ces2.clase10.model.Evento;

public interface EventoRepositorio extends JpaRepository<Evento,Integer> {
	@Query(value="select e.* from evento e join evento_autor ea on e.id = ea.fk_evento where ea.fk_autor = ?1",
			nativeQuery=true)
	Set<Evento> findByAutorId(Integer id);
}
