package com.ces2.clase10.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
@Table(name="anuncio")
public class Anuncio {
	@Id
	@SequenceGenerator(name="anuncio_sequence",
			sequenceName="anuncio_sequence",
			allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,
			generator="anuncio_sequence")
	@Column(name="id",
			updatable=false)
	private Integer id;
	
	@Column(name="title",
			nullable=false)
	@NonNull
	private String title;
	
	@Column(name="content",
			nullable=false)
	@NonNull
	private String content;
	
	@Column(name="published",
			nullable=false)
	@NonNull
	private LocalDateTime published;
	
	@Column(name="updated")
	private LocalDateTime updated;
	
	//One To One:
	@OneToOne
	@JoinColumn(name="autorid")
	@NonNull
	private Autor autor;
	
	//OneToMany:
	@OneToMany(fetch=FetchType.EAGER,
			cascade=CascadeType.ALL)
	private Set<Comentario> comentarios = new HashSet<>();
	
	public void addComentario(Comentario comentario) {
		this.comentarios.add(comentario);
	}
	 
	
}
