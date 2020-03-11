package com.asiczen.api.attendancemgmt.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiczen.api.attendancemgmt.exception.ResourceAlreadyExistException;
import com.asiczen.api.attendancemgmt.exception.ResourceNotFoundException;
import com.asiczen.api.attendancemgmt.model.Organization;
import com.asiczen.api.attendancemgmt.repository.OrganizationRepository;

@Service
public class OrganizationServiceImpl {

	private static final Logger logger = LoggerFactory.getLogger(OrganizationServiceImpl.class);

	@Autowired
	OrganizationRepository orgRepo;

	/* Add Organization */

	public Organization addOrganization(Organization org) {

		Optional<Organization> organization = orgRepo.findByorgId(org.getOrgId());

		if (organization.isPresent()) {
			logger.error("Organization Id" + org.getOrgId() + "already exist in Database");
			throw new ResourceAlreadyExistException(org.getOrgId());
		}

		return orgRepo.save(org);
	}

	public List<Organization> getAllOrganization() {

		if (orgRepo.findAll().isEmpty()) {
			throw new ResourceNotFoundException("There are no company registered.");
		} else {
			return orgRepo.findAll();
		}
	}
	
	public Organization updateOrganization(Organization newCompany) {

		Optional<Organization> organization = orgRepo.findById(newCompany.getId());
		
		if (! organization.isPresent())
			throw new ResourceNotFoundException("Organization with Id :" + newCompany.getId() + " not Found");
		
		return orgRepo.findById(newCompany.getId()).map(company -> {

			company.setOrgId(newCompany.getOrgId());
			company.setOrgName(newCompany.getOrgName());
			company.setOrgDescription(newCompany.getOrgDescription());
			//company.setRegDate(newCompany.getRegDate());
			company.setStatus(newCompany.isStatus());
			return orgRepo.save(company);

		}).orElseThrow(() -> new ResourceNotFoundException("Orgnization Id : "+ newCompany.getId() + "not found"));

	}

}
