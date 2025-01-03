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
	
	@Column(name="s_denominacion")
	private String denominacion;
	
	@Column(name="s_direccion")
	private String direccion;
	
	@OneToMany(mappedBy = "sede")
	private List<Area> areas;
	
}
