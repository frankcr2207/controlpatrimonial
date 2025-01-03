package csjar.controlpatrimonial.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Table(name="cp_bienes_ver")
@Entity
@Data
public class BienVer {

	@Id
	@Column(name="n_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="n_version")
	private Integer version;
	
	@Column(name="s_estado_conservacion")
	private String estadoConservacion;
	
	@Column(name="s_estado")
	private String estado;
	
	@Column(name="s_observacion")
	private String observacion;
	
	@Column(name = "n_id_empleado")
	private Integer idEmpleado;
	
	@CreationTimestamp
	@Column(name="f_version", insertable = true, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime fecRegistro;
	
	@Column(name = "n_id_bien")
	private Integer idBien;
	
	@Column(name = "n_id_acta")
	private Integer idActa;
	
}
