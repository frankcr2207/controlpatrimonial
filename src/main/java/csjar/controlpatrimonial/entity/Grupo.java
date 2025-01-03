package csjar.controlpatrimonial.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Table(name="cp_grupos")
@Entity
@Data
public class Grupo {

	@Id
	@Column(name="n_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="s_codigo")
	private String codigo;
	
	@Column(name="s_descripcion")
	private String descripcion;
	
}
