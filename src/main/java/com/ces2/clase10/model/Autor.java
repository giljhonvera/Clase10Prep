package com.ces2.clase10.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
@Table(name="autor")
public class Autor {
	@Id
	@SequenceGenerator(name="autor_sequence",
			sequenceName="autor_sequence",
			allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,
			generator="autor_sequence")
	@Column(name="id",
			updatable=false)
	private Integer id;
	
	@Column(name="name",
			nullable=false)
	@NonNull
	private String name;
	
	@Column(name="lastname",
			nullable=false)
	@NonNull
	private String lastname;
	
	@Column(name="email",
			nullable=false,
			unique=true)
	@NonNull
	private String email;
	
	@Column(name="username",
			nullable=false,
			unique=true)
	@NonNull
	private String username;
	
	//One To One:
	@OneToOne
	@JoinColumn(name="anuncioid")
	private Anuncio anuncio;
	
}
