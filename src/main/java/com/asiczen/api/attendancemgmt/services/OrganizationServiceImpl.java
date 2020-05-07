package com.asiczen.api.attendancemgmt.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

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
			throw new ResourceAlreadyExistException("Organization Id " + org.getOrganizationDisplayName()
					+ " is already taken.Please try a different one.");
		}

		Optional<Organization> orgByContactEmailId = orgRepo.findBycontactEmailId(org.getContactEmailId());

		if (orgByContactEmailId.isPresent()) {
			logger.error(org.getContactEmailId() + " is already taken.Please try a different one.");
			throw new ResourceAlreadyExistException(org.getContactEmailId());
		}

		Optional<Organization> orgByorganizationcontact = orgRepo
				.findByorganizationcontact(org.getOrganizationcontact());

		if (orgByorganizationcontact.isPresent()) {
			logger.error("Phone Number must be Unique" + org.getOrganizationcontact());
			throw new ResourceAlreadyExistException(
					org.getOrganizationcontact() + " is already taken.Please try a different one.");
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

		}).orElseThrow(() -> new ResourceNotFoundException("Orgnization Id : " + newCompany.getId() + " is not found"));

	}

	public boolean validateOrganization(String orgId) {

		Optional<Organization> org = orgRepo.findByorganizationDisplayNameAndStatus(orgId, "true");

		if (!org.isPresent()) {
			throw new ResourceNotFoundException("Organization with OrgId: " + orgId + " is not registered yet");
		} else {
			return true;
		}
	}

	public Long countOrganization() {
		return orgRepo.count();
	}

	public void updateImage(@Valid String orgId, String fileName) {

		Organization org = orgRepo.findByorganizationDisplayName(orgId)
				.orElseThrow(() -> new ResourceNotFoundException("Orgnizatio is not found"));
		org.setOrgLogo(fileName);

		orgRepo.save(org);
	}

	public Organization getOrganizationByid(String orgId) {
		return orgRepo.findByorganizationDisplayName(orgId).orElseThrow(()-> new ResourceNotFoundException("Organizaton id is not found."));
		
	}

}
