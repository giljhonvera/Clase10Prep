package com.ces2.clase10.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ces2.clase10.model.Autor;
import com.ces2.clase10.model.Comentario;

public interface ComentarioRepositorio extends JpaRepository<Comentario,Integer>  {
	
}
