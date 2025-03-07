package csjar.controlpatrimonial.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Table(name="cp_detalle_adquisicion")
@Entity
public class DetalleAdquisicion {

	@Id
	@Column(name="n_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@OneToOne
	@JoinColumn(name = "n_id_catalogo")
	private Catalogo catalogo;
	
	@Column(name="n_cantidad")
	private Integer cantidad;
	
	@Column(name="n_precio_unitario")
	private BigDecimal precioUnitario;
	
	@ManyToOne
	@JoinColumn(name = "n_id_adquisicion", nullable = false)
	private Adquisicion adquisicion;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Catalogo getCatalogo() {
		return catalogo;
	}

	public void setCatalogo(Catalogo catalogo) {
		this.catalogo = catalogo;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Adquisicion getAdquisicion() {
		return adquisicion;
	}

	public void setAdquisicion(Adquisicion adquisicion) {
		this.adquisicion = adquisicion;
	}

	public BigDecimal getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(BigDecimal precioUnitario) {
		this.precioUnitario = precioUnitario;
	}
	
}
