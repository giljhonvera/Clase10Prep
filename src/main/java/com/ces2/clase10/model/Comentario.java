package com.ces2.clase10.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name="comentario")
public class Comentario {
	@Id
	@SequenceGenerator(name="comentario_sequence",
			sequenceName="comentario_sequence",
			allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,
			generator="comentario_sequence")
	@Column(name="id",
			updatable=false)
	private Integer id;
	
	@Column(name="name",
			nullable=false)
	@NonNull
	private String name;
	
	@Column(name="content",
			nullable=false)
	@NonNull
	private String content;
	
	@Column(name="published",
			nullable=false)
	@NonNull
	private LocalDate published;
	
	@Column(name="updated")
	private LocalDate updated;
	
	//ManyToOne:
	@ManyToOne(fetch=FetchType.LAZY,
				optional=false)
	@JoinColumn(name="anuncioid")
	@NonNull
	private Anuncio anuncio;
}
