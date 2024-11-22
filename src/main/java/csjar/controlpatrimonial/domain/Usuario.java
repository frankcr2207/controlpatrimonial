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

@Table(name="cp_usuarios")
@Entity
@Data
public class Usuario {

	@Id
	@Column(name="n_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="s_dni")
	private String dni;
	
	@Column(name="s_nombres")
	private String nombres;
	
	@Column(name="s_apellidos")
	private String apellidos;
	
	@Column(name="s_correo")
	private String correo;
	
	@Column(name="s_login")
	private String login;
	
	@Column(name="s_clave")
	private String clave;
	
	@Column(name="s_estado")
	private String estado;
	
	@ManyToOne
	@JoinColumn(name = "n_id_perfil", nullable = false)
	private Perfil perfil;
	
}
