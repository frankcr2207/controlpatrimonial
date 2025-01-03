package csjar.controlpatrimonial.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Table(name="cp_grupo_clase")
@Entity
@Data
public class GrupoClase {

	@Id
	@Column(name="n_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="n_id_grupo")
	private Integer idGrupo;
	
	@Column(name="n_id_clase")
	private Integer idClase;
	
	@OneToMany(mappedBy = "grupoClase")
    private List<Catalogo> catalogo;
	
}
