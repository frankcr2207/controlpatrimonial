package csjar.controlpatrimonial.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Table(name="cp_sedeorgano")
@Entity
@Data
public class SedeOrgano {

	@Id
	@Column(name="n_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="n_id_sede")
	private Integer idSede;
	
	@Column(name="n_id_organo")
	private Integer idOrgano;
	
	@OneToMany(mappedBy = "sedeOrgano", fetch = FetchType.LAZY)
    private List<Area> area;
	
}
