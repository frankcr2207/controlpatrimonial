package csjar.controlpatrimonial.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Table(name="cp_sedes")
@Entity
@Data
public class Sede {

	@Id
	@Column(name="n_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="s_codigo")
	private String codigo;
	
	@Column(name="s_denminacion")
	private String denominacion;
	
	@Column(name="s_direccion")
	private String direccion;
	
}
