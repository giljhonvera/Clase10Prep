package com.ces2.clase10.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="evento")
public class Evento {
	@Id
	@SequenceGenerator(name="evento_sequence",
			sequenceName="evento_sequence",
			allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,
			generator="evento_sequence")
	@Column(name="id",
			updatable=false)
	private Integer id;
	
	@Column(name="title",
			nullable=false)
	@NonNull
	private String title;
	
	@Column(name="descripcion",
			nullable=false)
	@NonNull
	private String descripcion;
	
	@Column(name="estado",
			nullable=false)
	@Enumerated(EnumType.STRING)
	@NonNull
	private Estado estado;
	
	@Column(name="tipo",
			nullable=false)
	@Enumerated(EnumType.STRING)
	@NonNull
	private Tipo tipo;
	
	@Column(name="datecreated",
			nullable=false)
	@NonNull
	private LocalDate datecreated;
	
	@Column(name="dateupdated")
	private LocalDate dateupdated;
	
	//ManyToMany:
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
			name="evento_autor",
			joinColumns= {
					@JoinColumn(name="fk_evento")
			},
			inverseJoinColumns= {
					@JoinColumn(name="fk_autor")
			}
	)
	private Set<Autor> autores = new HashSet<>();
	
	public Evento addAutor(Autor autor) {
		this.autores.add(autor);
		return this;
	}
	
	public Set<Integer> getAutorIds(){
		return this.autores.stream()
				.map(Autor::getId)
				.collect(Collectors.toSet());
	}
	
	public Set<String> getAutorNames(){
		return this.autores.stream()
				.map(Autor::getName)
				.collect(Collectors.toSet());
	}
	
}
