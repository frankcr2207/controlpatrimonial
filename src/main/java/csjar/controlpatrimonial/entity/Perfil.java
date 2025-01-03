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

@Table(name="cp_perfil")
@Entity
@Data
public class Perfil {

	@Id
	@Column(name="n_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="s_descripcion")
	private String descripcion;
	
	@Column(name="s_estado")
	private String estado;
	
	@OneToMany(mappedBy = "perfil")
    private List<Usuario> usuarios;
	
}
