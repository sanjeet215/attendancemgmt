package com.asiczen.api.attendancemgmt.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.asiczen.api.attendancemgmt.model.AppliedLeaves;
import com.asiczen.api.attendancemgmt.payload.response.AvailedLeaveResponse;

@Repository
public interface ApplyLeaveRepository extends JpaRepository<AppliedLeaves,Long>{

	/*Employee Specific by Organization Id and Employee id*/
	Optional<List<AppliedLeaves>> findByOrgIdAndEmpId(String orgId,String empId);
	
	/*Employee Specific by Organization Id , Employee id , Status*/
	Optional<List<AppliedLeaves>> findByOrgIdAndEmpIdAndStatus(String orgId,String empId,String status);
	
	/*This is for Admin and Moderator organization wise*/
	Optional<List<AppliedLeaves>> findByOrgIdAndStatus(String orgId,String status);
	
	
//	@Query(value = "SELECT sum(appliedleaves.quantity) availedLeaves,\r\n" + 
//			"	   leavetypes.quantity availableLeaves,\r\n" + 
//			"       leavetypes.quantity-sum(appliedleaves.quantity) as balance,\r\n" + 
//			"	   appliedleaves.leave_type_name,\r\n" + 
//			"       appliedleaves.empid,\r\n" + 
//			"       appliedleaves.orgid,\r\n" + 
//			"       appliedleaves.status\r\n" + 
//			"from appliedleaves , leavetypes\r\n" + 
//			"where appliedleaves.orgid = 'finalcase1'\r\n" + 
//			"  and appliedleaves.empid = 'AZFINAL3'\r\n" + 
//			"  and appliedleaves.orgid = leavetypes.orgid\r\n" + 
//			"  and appliedleaves.leave_type_name = leavetypes.leave_type_name\r\n" + 
//			"  and appliedleaves.status in ('pending','approved')\r\n" + 
//			"	 group by appliedleaves.leave_type_name,\r\n" + 
//			"			  appliedleaves.empid,\r\n" + 
//			"              appliedleaves.orgid",nativeQuery = true)
//	Optional<List<AvailedLeaveResponse>> findLeaveHistory();
	
	
	
	
}
