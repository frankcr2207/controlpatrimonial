package csjar.controlpatrimonial.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Table(name="cp_catalogo")
@Entity
@Data
public class Catalogo {

	@Id
	@Column(name="n_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="s_codigo")
	private String codigo;
	
	@Column(name="s_denominacion")
	private String denominacion;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "n_id_grupo_clase")
	private GrupoClase grupoClase;
	
	@Column(name="n_secuencia")
	private Integer secuencia;
	
}
