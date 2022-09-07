package com.iemr.common.identity.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import com.iemr.common.identity.domain.MBeneficiarymapping;
import com.iemr.common.identity.dto.BeneficiariesPartialDTO;
import com.iemr.common.identity.dto.BeneficiaryCreateResp;

@Mapper(componentModel = "spring")
public interface IdentityPartialMapper
{

	IdentityPartialMapper INSTANCE = Mappers.getMapper(IdentityPartialMapper.class);

	@Mappings({ @Mapping(target = "benId", source = "map.MBeneficiaryregidmapping.beneficiaryID"),
			@Mapping(target = "benRegId", source = "map.MBeneficiaryregidmapping.benRegId"),
			@Mapping(target = "beneficiaryDetailsId", source = "map.MBeneficiarydetail.beneficiaryDetailsId"),
			@Mapping(target = "firstName", source = "map.MBeneficiarydetail.firstName"),
			@Mapping(target = "lastName", source = "map.MBeneficiarydetail.lastName"),
			@Mapping(target = "middleName", source = "map.MBeneficiarydetail.middleName"),
			@Mapping(target = "fatherName", source = "map.MBeneficiarydetail.fatherName"),
			@Mapping(target = "spouseName", source = "map.MBeneficiarydetail.spouseName"),
			@Mapping(target = "beneficiaryAge",
					expression = "java(MBeneficiarydetail.calculateAge(map.getMBeneficiarydetail().getDob()))"), })

	BeneficiariesPartialDTO MBeneficiarymappingToBeneficiariesPartialDTO(MBeneficiarymapping map);

	@Mappings({ @Mapping(target = "benId", source = "map.MBeneficiaryregidmapping.beneficiaryID"),
			@Mapping(target = "benRegId", source = "map.MBeneficiaryregidmapping.benRegId") })
	BeneficiaryCreateResp MBeneficiarymappingToBeneficiaryCreateResp(MBeneficiarymapping map);
}
