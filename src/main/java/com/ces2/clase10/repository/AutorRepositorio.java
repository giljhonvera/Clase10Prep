package com.ces2.clase10.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ces2.clase10.model.Autor;

public interface AutorRepositorio extends JpaRepository<Autor,Integer> {

}
