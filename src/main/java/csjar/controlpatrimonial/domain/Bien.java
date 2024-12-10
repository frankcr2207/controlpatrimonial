package csjar.controlpatrimonial.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Table(name="cp_bienes")
@Entity
@Data
public class Bien {

	@Id
	@Column(name="n_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="s_codigo_patrimonial")
	private String codigoPatrimonial;
	
	@Column(name="s_codigo_m")
	private String codigoM;
	
	@Column(name="s_descripcion")
	private String descripcion;
	
	@OneToOne
    @JoinColumn(name = "n_id_modelo")
	private Modelo modelo;
	
	@Column(name="s_color")
	private String color;
	
	@Column(name="s_serie")
	private String serie;
	
	@Column(name="s_estado")
	private String estado;
	
	@Column(name="s_observacion")
	private String observacion;
	
	@Column(name="n_id_detalle_adquisicion")
	private Integer idDetalleAdquisicion;
	
}
