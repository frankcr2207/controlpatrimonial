package csjar.controlpatrimonial.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import csjar.controlpatrimonial.utils.CollectionUtils;

@Table(name="cp_adquisicion")
@Entity
public class Adquisicion {

	@Id
	@Column(name="n_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="s_documento")
	private String documento;
	
	@Column(name="f_documento")
	private LocalDateTime fecAdquisicion;
	
	@CreationTimestamp
	@Column(name="f_registro", insertable = true, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime fecRegistro;
	
	@Column(name="s_estado")
	private String estado;
	
	@Column(name="s_login")
	private String usuario;
	
	@ManyToOne
	@JoinColumn(name = "n_id_tipo", nullable = false)
	private TipoAdquisicion tipoAdquisicion;
	
	@OneToMany(mappedBy = "adquisicion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleAdquisicion> detalleAdquisicion = new ArrayList<>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public LocalDateTime getFecAdquisicion() {
		return fecAdquisicion;
	}

	public void setFecAdquisicion(LocalDateTime fecAdquisicion) {
		this.fecAdquisicion = fecAdquisicion;
	}

	public LocalDateTime getFecRegistro() {
		return fecRegistro;
	}

	public void setFecRegistro(LocalDateTime fecRegistro) {
		this.fecRegistro = fecRegistro;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public TipoAdquisicion getTipoAdquisicion() {
		return tipoAdquisicion;
	}

	public void setTipoAdquisicion(TipoAdquisicion tipoAdquisicion) {
		this.tipoAdquisicion = tipoAdquisicion;
	}

	public List<DetalleAdquisicion> getDetalleAdquisicion() {
		return detalleAdquisicion;
	}

	public void setDetalleAdquisicion(List<DetalleAdquisicion> detalleAdquisicion) {
        for (DetalleAdquisicion d : this.detalleAdquisicion) {
            d.setAdquisicion(null);
        }
        this.detalleAdquisicion.clear();
        this.detalleAdquisicion.addAll(detalleAdquisicion);
		if(CollectionUtils.isValidate(this.detalleAdquisicion))
			this.detalleAdquisicion.stream().forEach(d -> d.setAdquisicion(this));
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
}
