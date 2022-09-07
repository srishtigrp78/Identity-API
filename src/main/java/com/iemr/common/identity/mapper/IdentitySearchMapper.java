package com.iemr.common.identity.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import com.iemr.common.identity.domain.MBeneficiaryaddress;
import com.iemr.common.identity.domain.MBeneficiarycontact;
import com.iemr.common.identity.domain.MBeneficiarydetail;
import com.iemr.common.identity.dto.IdentitySearchDTO;

@Mapper(componentModel = "spring")
public interface IdentitySearchMapper {

	IdentitySearchMapper INSTANCE = Mappers.getMapper(IdentitySearchMapper.class);

	@Mappings({

			@Mapping(source = "dto.beneficiaryRegId", target = "beneficiaryRegID"),
			@Mapping(source = "dto.fatherName", target = "fatherName"),
			@Mapping(source = "dto.firstName", target = "firstName"),
			@Mapping(source = "dto.genderId", target = "genderId"),
			@Mapping(source = "dto.genderName", target = "gender"),
			//@Mapping(source = "dto.ageId", target = "ageId"),
			//@Mapping(source = "dto.age", target = "age"),
			@Mapping(source = "dto.lastName", target = "lastName"),
			@Mapping(source = "dto.middleName", target = "middleName"),
			@Mapping(source = "dto.spouseName", target = "spouseName")

			})
	MBeneficiarydetail IdentitySearchDTOMBeneficiarydetail(IdentitySearchDTO dto);

	@Mappings({

			@Mapping(source = "dto.currentAddress.stateId", target = "currStateId"),
			@Mapping(source = "dto.currentAddress.state", target = "currState"),
			@Mapping(source = "dto.currentAddress.districtId", target = "currDistrictId"),
			@Mapping(source = "dto.currentAddress.district", target = "currDistrict"),
			@Mapping(source = "dto.pinCode", target = "currPinCode") 
			})
	MBeneficiaryaddress IdentitySearchDTOToMBeneficiaryaddress(IdentitySearchDTO dto);

	@Mappings({

			@Mapping(source = "dto.contactNumber", target = "preferredPhoneNum"),
			@Mapping(source = "dto.contactNumber", target = "phoneNum1"),
			@Mapping(source = "dto.contactNumber", target = "phoneNum2"),
			@Mapping(source = "dto.contactNumber", target = "phoneNum3"),
			@Mapping(source = "dto.contactNumber", target = "phoneNum4"),
			@Mapping(source = "dto.contactNumber", target = "phoneNum5")
			})
	MBeneficiarycontact IdentitySearchDTOToMBeneficiarycontact(IdentitySearchDTO dto);
}
