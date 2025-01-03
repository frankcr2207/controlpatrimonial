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

@Table(name="cp_tipoadquisicion")
@Entity
@Data
public class TipoAdquisicion {

	@Id
	@Column(name="n_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="s_denominacion")
	private String denominacion;
	
	@OneToMany(mappedBy = "tipoAdquisicion")
    private List<Adquisicion> adquisicion;
	
}
