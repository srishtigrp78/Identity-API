package com.iemr.common.identity.mapper;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import com.iemr.common.identity.domain.MBeneficiaryregidmapping;
import com.iemr.common.identity.dto.BenIdImportDTO;

@Mapper(componentModel = "spring")
public interface BenIdImportMapper {
	BenIdImportMapper INSTANCE = Mappers.getMapper(BenIdImportMapper.class);

	@Mappings({ @Mapping(source = "benRegId", target = "benRegId"),
			@Mapping(source = "beneficiaryId", target = "beneficiaryID"),
			@Mapping(source = "createdBy", target = "createdBy"),
			@Mapping(source = "createdDate", target = "createdDate") })
	MBeneficiaryregidmapping benIdImportDTOToMBeneficiaryregidmapping(BenIdImportDTO dto);

	ArrayList<MBeneficiaryregidmapping> benIdImportDTOToMBeneficiaryregidmappings(List<BenIdImportDTO> dto);
}
