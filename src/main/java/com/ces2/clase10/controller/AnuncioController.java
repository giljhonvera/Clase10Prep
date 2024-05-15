package com.ces2.clase10.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ces2.clase10.model.Anuncio;
import com.ces2.clase10.model.Autor;
import com.ces2.clase10.model.Comentario;
import com.ces2.clase10.model.Estado;
import com.ces2.clase10.model.Evento;
import com.ces2.clase10.model.Tipo;
import com.ces2.clase10.repository.AnuncioRepositorio;
import com.ces2.clase10.repository.AutorRepositorio;
import com.ces2.clase10.repository.ComentarioRepositorio;
import com.ces2.clase10.repository.EventoRepositorio;

@Controller
@RequestMapping("/anuncios")
public class AnuncioController {
	
	private final AnuncioRepositorio anuncios;
	private final AutorRepositorio autores;
	private final ComentarioRepositorio comentarios;
	private final EventoRepositorio eventos;
	
	public AnuncioController(AnuncioRepositorio anuncios,
			AutorRepositorio autores,
			ComentarioRepositorio comentarios,
			EventoRepositorio eventos) {
		this.anuncios = anuncios;
		this.autores = autores;
		this.comentarios = comentarios;
		this.eventos = eventos;
	}
	
	@GetMapping
	public String getHomePage(Model model) {
		model.addAttribute("newAutor", new Autor());
		return "home";
	}
	
	@GetMapping("/listar")
	public String getAnuncios(Model model) {
		model.addAttribute("anuncios", anuncios.findAll());
		return "anuncios";
	}
	
	@PostMapping("/ingresar")
	public String ingresarAnuncio(
			@ModelAttribute Autor autor,
			@RequestParam(required=true, name ="titulo") String titulo,
			@RequestParam(required=true, name ="contenido") String contenido,
			@RequestParam(required=true, name ="year") String year,
			@RequestParam(required=true, name ="mes") String mes,
			@RequestParam(required=true, name ="dia") String dia,
			@RequestParam(required=true, name ="hora") String hora,
			@RequestParam(required=true, name ="minuto") String minuto,
			Model model) {
		
		Autor newAutor = autores.save(autor);
		
//		System.out.println(titulo);
//		System.out.println(contenido);
//		System.out.println(year);
//		System.out.println(mes);
//		System.out.println(dia);
//		System.out.println(hora);
//		System.out.println(minuto);
		
		LocalDateTime localdate = LocalDateTime.of(Integer.valueOf(year),Integer.valueOf(mes),
				Integer.valueOf(dia),Integer.valueOf(hora),Integer.valueOf(minuto));
		
		Anuncio anuncio = new Anuncio(titulo,contenido,localdate,newAutor);
		
		anuncios.save(anuncio);
		
		newAutor.setAnuncio(anuncio);
		autores.save(newAutor);

		model.addAttribute("anuncios", anuncios.findAll());
		
		return "anuncios";
	} 
	
	@GetMapping("/verAutor/{id}")
	public String buscarAutor(Model model,
			@PathVariable String id) {
		Optional<Autor> autorById = autores.findById(Integer.valueOf(id));
		Autor autor = autorById.orElse(new Autor());
		model.addAttribute("autor", autor);
		return "autor";
	}
	
	@GetMapping("/menucomentarios")
	public String menuComentarios(Model model) {
		model.addAttribute("anuncios", anuncios.findAll());
		return "menucomentarios";
	}
	
	@GetMapping("/agregarComentario/{id}")
	public String agregarComentario(
			Model model,
			@PathVariable String id) {
		
		model.addAttribute("idAnuncio", id);
		return "agregarcomentario";
	}
	
	@PostMapping("/agregarComentario")
	public String nuevoComentario(
			@RequestParam(required=true, name ="idAnuncio") String idAnuncio,
			@RequestParam(required=true, name ="name") String name,
			@RequestParam(required=true, name ="content") String content,
			@RequestParam(required=true, name ="published") String published,
			Model model) {
		
		String[] partes = published.split("-");
		
		LocalDate fecha = LocalDate.of(Integer.valueOf(partes[0]),
				Integer.valueOf(partes[1]),
				Integer.valueOf(partes[2])); 	
		
		Optional<Anuncio> anuncioById = anuncios.findById(Integer.valueOf(idAnuncio));
		Anuncio anuncio = anuncioById.orElse(new Anuncio());
		
		// Muchos a uno
		Comentario comentario = comentarios.save(new Comentario(name,content,fecha,anuncio));
		// Uno a muchos
		anuncio.addComentario(comentario);
		anuncios.save(anuncio);
		
//		System.out.println(idAnuncio);
//		System.out.println(name);
//		System.out.println(content);
//		System.out.println(published);
		
		return "redirect:/anuncios/menucomentarios";
	}
	
	@GetMapping("/verComentarios/{id}")
	public String verComentarios(
			Model model,
			@PathVariable String id) {
		
		Optional<Anuncio> anuncioById = anuncios.findById(Integer.valueOf(id));
		Anuncio anuncio = anuncioById.orElse(new Anuncio());
		
		Set<Comentario> comentarios = anuncio.getComentarios();
		
		model.addAttribute("comentarios", comentarios);
		return "comentarios";
	}
	
	@GetMapping("/eventos")
	public String ingresarEventos() {
		return "evento";
	}
	
	@PostMapping("/ingresarevento")
	public String ingresarEvento(Model model,
			@RequestParam(required=true, name ="title") String title,
			@RequestParam(required=true, name ="descripcion") String descripcion,
			@RequestParam(required=true, name ="estado") String estado,
			@RequestParam(required=true, name ="tipo") String tipo,
			@RequestParam(required=true, name ="datecreated") String datecreated) {
		
		String[] partes = datecreated.split("-");
		
		LocalDate fecha = LocalDate.of(Integer.valueOf(partes[0]),
				Integer.valueOf(partes[1]),
				Integer.valueOf(partes[2])); 
		
		Evento evento = new Evento(title,descripcion,Estado.valueOf(estado),Tipo.valueOf(tipo),fecha);
		
		eventos.save(evento);
		
		model.addAttribute("eventos", eventos.findAll());
		
		return "eventos";
		
	}
	
	@GetMapping("/vereventos")
	public String verEventos(Model model) {
		
		model.addAttribute("eventos", eventos.findAll());
		
		return "eventos";
	}
	
	@GetMapping("/agregarAutor/{id}")
	public String agregarAutor(
			Model model,
			@PathVariable String id) {
		
		model.addAttribute("idEvento", id);
		model.addAttribute("autores",autores.findAll());
		return "agregarautor";
	}
	
	// agregar un autor a un evento:
	@PostMapping("/asignarautor")
	public String asignarAutor(
			Model model,
			@RequestParam(required=true, name ="idEvento") String idEvento,
			@RequestParam(required=true, name ="idAutor") String idAutor) {
		
		Optional<Autor> autorById = autores.findById(Integer.valueOf(idAutor));
		Autor autor = autorById.orElse(new Autor());
		
		Optional<Evento> eventoById = eventos.findById(Integer.valueOf(idEvento));
		Evento evento = eventoById.orElse(new Evento());
		
		Evento newEvento = evento.addAutor(autor);
		
		eventos.save(newEvento);
		
		return "redirect:/anuncios/vereventos";
	}
	
	// ver todos los autores que participan en el evento con id:
	@GetMapping("/verautores/{id}")
	public String verAutores(
			Model model,
			@PathVariable String id) {
		
		Optional<Evento> eventoById = eventos.findById(Integer.valueOf(id));
		Evento evento = eventoById.orElse(new Evento());
		
		Set<Integer> autoresIds = evento.getAutorIds();
		
		Iterable<Autor> autoresDeEvento = autores.findAllById(autoresIds);
		
		model.addAttribute("evento", evento);
		model.addAttribute("autores",autoresDeEvento);
		return "autoresevento";
	}
	
	//Ver los eventos del autor con id:
	@GetMapping("/eventosautor/{id}")
	public String verEventosAutor(
			Model model,
			@PathVariable String id) {
		
		Optional<Autor> autorById = autores.findById(Integer.valueOf(id));
		Autor autor = autorById.orElse(new Autor());
		
		Iterable<Evento> eventosByAutorId = eventos.findByAutorId(autor.getId());
		
		model.addAttribute("eventos", eventosByAutorId);
		
		return "eventosdelautor";
	}
}
