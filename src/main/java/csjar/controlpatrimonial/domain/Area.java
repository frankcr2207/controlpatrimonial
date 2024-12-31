package csjar.controlpatrimonial.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Table(name="cp_instancia")
@Entity
@Data
public class Area {

	@Id
	@Column(name="n_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="c_instancia")	
	private String codigo;
	
	@Column(name="s_instancia")
	private String denominacion;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "n_id_sede", nullable = false)
	private Sede sede;
	
}
