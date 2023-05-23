package com.codingdojo.nathaly.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codingdojo.nathaly.modelos.Etiqueta;
import com.codingdojo.nathaly.modelos.Pregunta;
import com.codingdojo.nathaly.modelos.Respuesta;
import com.codingdojo.nathaly.repositorios.RepositorioEtiquetas;
import com.codingdojo.nathaly.repositorios.RepositorioPreguntas;
import com.codingdojo.nathaly.repositorios.RepositorioRespuestas;

@Service
public class Servicios {

	@Autowired
	private RepositorioPreguntas repoPreguntas;
	
	@Autowired
	private RepositorioRespuestas repoRespuestas;
	
	@Autowired
	private RepositorioEtiquetas repoEtiquetas;
	
	//metodo para encontrar todas las preguntas
	public List<Pregunta>todasPreguntas(){
		return repoPreguntas.findAll();
	}
	
	//metodo para guardar una pregunta 
	public Pregunta guardarPregunta(Pregunta nuevaPregunta) {
		return repoPreguntas.save(nuevaPregunta);
	}
	
	//quiero  recibir el texto o tema por el cual estoy buscamdo la etiqueta
	public Etiqueta encuentraEtiqueta(String tema) {
		return repoEtiquetas.findByTema(tema);
	}
	
	//metodo para guardar una etiqueta
	public Etiqueta guardarEtiqueta(Etiqueta nuevaEtiqueta) {
		return repoEtiquetas.save(nuevaEtiqueta);
	}
	
	//regresa en base al id
	public Pregunta encuentraPregunta(Long id) {
		return repoPreguntas.findById(id).orElse(null);
	}
	
	//metodo para recibir una respuesta
	public Respuesta guardarRespuesta(Respuesta nuevaRespuesta){
		return repoRespuestas.save(nuevaRespuesta);
		
	}
}
