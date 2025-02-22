package csjar.controlpatrimonial.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Table(name = "cp_acta")
@Entity
@Data
public class Acta {

	@Id
	@Column(name = "n_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "n_acta")
	private Integer numero;

	@Column(name = "s_tipo")
	private String tipo;
	
	@Column(name = "s_estado")
	private String estado;

	@Column(name = "n_id_transferente")
	private Integer idUsuario;
//
//	@Column(name = "n_id_empleado")
//	private Integer idEmpleado;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "n_id_empleado", nullable = false)
	private Usuario usuario;

	@Column(name = "n_id_area_empleado")
	private Integer idArea;

	@CreationTimestamp
	@Column(name = "f_registro", insertable = true, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime fecRegistro;

	@ManyToMany
	@JoinTable(name = "cp_actabienes", 
		joinColumns = @JoinColumn(name = "n_id_acta"), 
		inverseJoinColumns = @JoinColumn(name = "n_id_bien"))
	private List<Bien> bienes;

	@Column(name = "s_pdf_original")
	private String nombrePdfOriginal;
	
	@Column(name = "s_ruta_pdf")
	private String rutaPdf;
	
	@Column(name = "f_notificado")
	private LocalDateTime fecNotificado;
	
	@Column(name = "s_pdf_firmado")
	private String nombrePdfFirmado;
	
	@Column(name = "f_firmado")
	private LocalDateTime fecFirmado;
	
	@Column(name = "s_token")
	private String token;
	
}
