package com.codingdojo.nathaly.repositorios;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.codingdojo.nathaly.modelos.Etiqueta;

@Repository
public interface RepositorioEtiquetas extends CrudRepository<Etiqueta, Long>{
	
	List<Etiqueta> findAll();
	
	//SELECT*FROM etiquetas WHERE tema=TEMA QUE RECIBIMOS
	Etiqueta findByTema(String t); //buscamos en base a un tema, t es la variable que vamos a recibir a traves del servicio que a su vez lo manda al controlador para poder encontar un tema

}
