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

		Optional<Organization> organization = orgRepo.findByorganizationDisplayName(org.getOrganizationDisplayName());

		if (organization.isPresent()) {
			logger.error("Organization Id" + org.getOrganizationDisplayName() + "already exist in Database");
			throw new ResourceAlreadyExistException(org.getOrganizationDisplayName());
		}
		
		Optional<Organization> orgByContactEmailId = orgRepo.findBycontactEmailId(org.getContactEmailId());
		
		if (orgByContactEmailId.isPresent()) {
			logger.error("Organization ContactEmailId" + org.getContactEmailId() + "already exist in Database");
			throw new ResourceAlreadyExistException(org.getContactEmailId());
		}
		
		Optional<Organization> orgByorganizationcontact = orgRepo.findByorganizationcontact(org.getOrganizationcontact());

		if(orgByorganizationcontact.isPresent()) {
			logger.error("Phone Number must be Unique" + org.getOrganizationcontact());
			throw new ResourceAlreadyExistException(org.getOrganizationcontact());
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
		
		if (!organization.isPresent())
			throw new ResourceNotFoundException("Organization with Id :" + newCompany.getId() + " not Found");
		
		return orgRepo.findById(newCompany.getId()).map(company -> {

			company.setOrganizationDisplayName(newCompany.getOrganizationDisplayName());
			company.setOrganizationDescription(newCompany.getOrganizationDescription());
			company.setRegDate(newCompany.getRegDate());
			company.setOrganizationLocation(newCompany.getOrganizationLocation());
			company.setStatus(newCompany.getStatus());
			company.setContactPersonName(newCompany.getContactPersonName());
			company.setContactEmailId(newCompany.getContactEmailId());
			company.setOrganizationcontact(newCompany.getOrganizationcontact());
			
			return orgRepo.save(company);

		}).orElseThrow(() -> new ResourceNotFoundException("Orgnization Id : "+ newCompany.getId() + "not found"));

	}

}
