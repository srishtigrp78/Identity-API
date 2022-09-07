package com.iemr.common.identity.filter;

import com.iemr.common.identity.dto.IdentityFilterDTO;

public class QuerySelector {
	
	public void getQuery(IdentityFilterDTO search) {
		
		/**
		 * Search Criteria: the following are necessary to conduct a finite search
		 * 
		 *  1. beneficiary ID
		 *  2. beneficiary Reg ID
		 *  3. Contact Number
		 *  4. Name (First, Middle, Last), Age, Gender
		 *  5. Village Name
		 *  6. District
		 *     State
		 *     Zone
		 *     Coordinates
		 *  7. Pin Code
		 *  8. Father Name
		 *  9. Spouse Name
		 *  
		 *  Allowed Single Parameters:
		 *  - beneficiary Id
		 *  - beneficiary Reg Id
		 *  - primary phone number
		 *  - Contact Number
		 *  
		 *  Allowed Multi-Parameters:
		 *  - Name (First, Middle, Last), Age, Gender
		 *  - Contact Number & Name (First, Middle, Last)
		 *  - Pin Code & Name (First, Middle, Last)
		 *  - Village, Father Name & Name (First, Middle, Last)
		 *  - Village, Spouse Name & Name (First, Middle, Last)
		 *  - Village, District, State
		 *  - Govt identities(keys, values)
		 */
		
		/**
		 * Beneficiary ID is present, no need for other criteria 
		 */
		if(search.getBeneficiaryId().isPresent()) {
			search.getBeneficiaryId().get();
		}
		
		/**
		 * Beneficiary Reg ID is present, no need for other criteria 
		 */
		if(search.getBeneficiaryRegId().isPresent()) {
			search.getBeneficiaryRegId().get();
		}
		
		/**
		 * First Name is present, additional criteria needed 
		 */
		if(search.getFirstName().isPresent()) {
			search.getFirstName().get();
		}
		
		if(search.getPinCode().isPresent()){
			search.getPinCode().get();
		}
		
		if(search.getContactNumber().isPresent()){
			search.getContactNumber().get();
		}
	}
}
