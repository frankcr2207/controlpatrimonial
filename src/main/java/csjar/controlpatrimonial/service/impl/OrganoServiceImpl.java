package csjar.controlpatrimonial.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import csjar.controlpatrimonial.dto.ResponseOrganoDTO;
import csjar.controlpatrimonial.entity.Organo;
import csjar.controlpatrimonial.entity.SedeOrgano;
import csjar.controlpatrimonial.mapper.service.OrganoMapperService;
import csjar.controlpatrimonial.repository.OrganoRepository;
import csjar.controlpatrimonial.repository.SedeOrganoRepository;
import csjar.controlpatrimonial.service.OrganoService;

@Service
public class OrganoServiceImpl implements OrganoService{
	
	private SedeOrganoRepository sedeOrganoRepository;
	private OrganoRepository repository;
	private OrganoMapperService mapper;
	
	public OrganoServiceImpl(SedeOrganoRepository sedeOrganoRepository, OrganoRepository repository,
			OrganoMapperService mapper) {
		super();
		this.sedeOrganoRepository = sedeOrganoRepository;
		this.repository = repository;
		this.mapper = mapper;
	}

	@Override
	public List<ResponseOrganoDTO> listarOrganos(Integer idSede) {
		
		List<SedeOrgano> sedeOrgano = this.sedeOrganoRepository.findByIdSede(idSede);
		List<Integer> ids = sedeOrgano.stream().map(SedeOrgano::getIdOrgano).collect(Collectors.toList());
		List<Organo> organos = this.repository.findByIdIn(ids);
		return this.mapper.toDTO(organos);
		
	}

	@Override
	public List<SedeOrgano> obtenerEntidades(Integer idSede, List<Integer> idsOrgano) {
		return this.sedeOrganoRepository.findByIdSedeAndIdOrganoIn(idSede, idsOrgano);
	}

}
