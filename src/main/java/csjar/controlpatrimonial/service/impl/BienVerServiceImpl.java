package csjar.controlpatrimonial.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import csjar.controlpatrimonial.entity.Bien;
import csjar.controlpatrimonial.entity.BienVer;
import csjar.controlpatrimonial.repository.BienVerRepository;
import csjar.controlpatrimonial.service.BienVerService;

@Service
public class BienVerServiceImpl implements BienVerService {

	private BienVerRepository repository;
	
	public BienVerServiceImpl(BienVerRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public void generarVersion(List<Bien> bienes) {
		
		List<Integer> idsBienes = bienes.stream().map(Bien::getId).distinct()
				.collect(Collectors.toList());
		
		List<BienVer> versiones = this.repository.findByIdBienIn(idsBienes);
		
		List<BienVer> nuevos = new ArrayList<>();
		
		Map<Integer, List<BienVer>> mapaPorIdBien = versiones.stream()
	            .collect(Collectors.groupingBy(BienVer::getIdBien));
		
		bienes.stream().forEach(b -> {
			BienVer version = new BienVer();
			version.setEstado(b.getEstado());
			version.setEstadoConservacion(b.getEstadoConservacion());
			version.setIdEmpleado(b.getIdEmpleado());
			version.setObservacion(b.getObservacion());
			version.setVersion(mapaPorIdBien.containsKey(b.getId()) ? mapaPorIdBien.get(b.getId()).size() + 1 : 1);
			version.setIdBien(b.getId());
			nuevos.add(version);
		});
		
		this.repository.saveAll(nuevos);
	}

}
