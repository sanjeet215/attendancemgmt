package com.asiczen.api.attendancemgmt.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiczen.api.attendancemgmt.exception.ResourceAlreadyExistException;
import com.asiczen.api.attendancemgmt.exception.ResourceNotFoundException;
import com.asiczen.api.attendancemgmt.model.PayStructure;
import com.asiczen.api.attendancemgmt.repository.PayStructureRepository;

@Service
public class PayStructureServiceImpl {

	private static final Logger logger = LoggerFactory.getLogger(PayStructureServiceImpl.class);
	
	@Autowired
	PayStructureRepository paystructRepo;
	
	// Generate Pay Structure for organization.
	
	public List<PayStructure> addSalaryComponents(List<PayStructure> components,String orgId){
		
		Optional<List<PayStructure>> payStructure = paystructRepo.findByorgId(orgId);
		
		//Check Component names for duplication
		 Set<String> componentNames = new HashSet<>(); 
		 Set<String> duplicates = new HashSet<>();
		
		components.forEach(item->{
			if(!componentNames.add(item.getComponentName())) {
				duplicates.add(item.getComponentName());
			}
		});
		
		if(!duplicates.isEmpty()) {
			throw new ResourceAlreadyExistException("Component already in use"+duplicates.toString());
		}
		
		if(payStructure.isPresent()) {
			payStructure.get().forEach(item ->{
				Optional<PayStructure> orgpayStructure = paystructRepo.findByOrgIdAndComponentName(orgId, item.getComponentName());
				if(orgpayStructure.isPresent()) {
					throw new ResourceAlreadyExistException("Component already present"+item.getComponentName());
				}
			});	
		}
		
		return paystructRepo.saveAll(components);
	}
	
	
	/* Get salary components by orgId */
	
	public List<PayStructure> getSalaryComponentsByOrganization(String orgId){
		
		Optional<List<PayStructure>> orgPayStructure = paystructRepo.findByorgId(orgId);
		
		if(!orgPayStructure.isPresent()) {
			throw new ResourceNotFoundException("No Components added yet for organization Id. "+orgId);
		}
		
		return orgPayStructure.get();
		
	}
	
	
	
	/* Update a component */
	
	public List<PayStructure> updateComponents(List<PayStructure> components,String orgId){
		
		List<PayStructure> updatedRecords = new ArrayList<PayStructure>();
		
		components.forEach(item->{
			Optional<PayStructure> findComponent= paystructRepo.findByOrgIdAndComponentName(orgId, item.getComponentName());
			if(findComponent.isPresent()) {
				findComponent.get().setComponentName(item.getComponentName());
				findComponent.get().setSign(item.getSign());
				
				paystructRepo.save(findComponent.get());
				updatedRecords.add(findComponent.get());
			}
			
		});
		
		return updatedRecords;
	}
	
	
	public void deleteComponent(PayStructure component,String orgId) {
		
		Optional<PayStructure> orgpayStructure = paystructRepo.findByOrgIdAndComponentName(orgId, component.getComponentName());
		
		if(!orgpayStructure.isPresent()) {
			throw new ResourceNotFoundException("Salary Component not Found. "+component.getComponentName());
		}
		
		paystructRepo.delete(component);
	}
	
}
