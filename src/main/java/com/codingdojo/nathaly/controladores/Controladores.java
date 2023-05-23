package com.codingdojo.nathaly.controladores;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.codingdojo.nathaly.modelos.Etiqueta;
import com.codingdojo.nathaly.modelos.Pregunta;
import com.codingdojo.nathaly.modelos.Respuesta;
import com.codingdojo.nathaly.servicios.Servicios;

@Controller
public class Controladores {

	@Autowired
	private Servicios servicio;
	
	@GetMapping("/")
	public String index(Model model) {
		List<Pregunta> preguntas=servicio.todasPreguntas();
		model.addAttribute("preguntas", preguntas);
		return "index.jsp";
	}
	
	@GetMapping("/nueva")
	public String nueva(@ModelAttribute("pregunta")Pregunta pregunta) {//modelAttribute recibe un objeto vacio y con los campos del usuario se llenan los atributos
		return "nueva.jsp";
	}
	
	@PostMapping("/crear")
	public String crear(@Valid @ModelAttribute("pregunta") Pregunta pregunta, BindingResult result, @RequestParam("textoEtiquetas")String textoEtiquetas) {
		if(result.hasErrors()) {
			return "nueva.jsp";
		}else {
			//textoEtiquetas="programacion, tecnologia, java"
			//transformarlo a listaEtiquetas={"programacion, tecnologia, java"}
			//lista tipo string
			String[] listaEtiquetas= textoEtiquetas.trim().split(",");// trim y split= quitar los espacios y dividir el texto en base a un caracter respectivamente
			//creo una lista vacia
			List<Etiqueta> etiquetas= new ArrayList<>();
			
			//debemos recorrer la lista
			for(String tema:listaEtiquetas) { //cada listaEtiquetas se va guardando en un String tema
				//Buscar si existe ese tema entre las etiquetas
				Etiqueta et=servicio.encuentraEtiqueta(tema);
				if(et==null) {
					//no existe la etiqueta y la debemos crear
					Etiqueta nuevaEtiqueta= new Etiqueta();
					nuevaEtiqueta.setTema(tema);
					servicio.guardarEtiqueta(nuevaEtiqueta);
					etiquetas.add(nuevaEtiqueta);
				}else {
					etiquetas.add(et);
				}
			}
			pregunta.setEtiquetas(etiquetas);
			servicio.guardarPregunta(pregunta);
			return "redirect:/";
		}
		
	}
	
	@GetMapping("/pregunta/{id}")
	public String pregunta(@PathVariable("id")Long id, Model model, @ModelAttribute("respuesta")Respuesta respuesta) {
		Pregunta pregunta=servicio.encuentraPregunta(id);
		model.addAttribute("pregunta", pregunta);
		return "pregunta.jsp";
	}
	
	@PostMapping("/respuesta")
	public String respuesta(@Valid @ModelAttribute("respuesta")Respuesta respuesta, BindingResult result) {
		if(result.hasErrors()) {
			return "pregunta.jsp";
		}else {
			servicio.guardarRespuesta(respuesta);
			return "redirect:/pregunta/" +respuesta.getPregunta().getId();
		}
	}
}
