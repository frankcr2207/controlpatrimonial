package csjar.controlpatrimonial.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Table(name="cp_modelo")
@Entity
@Data
public class Modelo {

	@Id
	@Column(name="n_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="s_descripcion")
	private String nombre;
	
	@ManyToOne
	@JoinColumn(name = "n_id_marca", nullable = false)
	private Marca marca;
	
}
