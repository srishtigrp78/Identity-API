package com.iemr.common.identity.service.familyTagging;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Description;

import com.google.gson.Gson;
import com.iemr.common.identity.data.familyTagging.BenFamilyMapping;
import com.iemr.common.identity.domain.MBeneficiarydetail;
import com.iemr.common.identity.domain.MBeneficiarymapping;
import com.iemr.common.identity.exception.IEMRException;
import com.iemr.common.identity.repo.BenDetailRepo;
import com.iemr.common.identity.repo.BenMappingRepo;
import com.iemr.common.identity.repo.familyTag.FamilyTagRepo;

@ExtendWith(MockitoExtension.class)
class FamilyTagServiceImplTest {

	@InjectMocks
	private FamilyTagServiceImpl familyTagServiceImpl;

	@Mock
	private BenMappingRepo benMappingRepo;
	@Mock
	private BenDetailRepo benDetailRepo;
	@Mock
	private FamilyTagRepo familyTagRepo;

	@Test
	@Description("Verifies successful addition of a family tag.")
	void testAddTag_Success() throws IEMRException {
		// Test Case ID: TC_ADD_TAG_001

		// Mocking input data
		BenFamilyMapping requestObj = new BenFamilyMapping();
		requestObj.setBeneficiaryRegId(1234456L);
		requestObj.setIsHeadOfTheFamily(true);
		requestObj.setFamilyId("FAM001"); // Unique family ID
		requestObj.setModifiedBy("admin");
		requestObj.setMemberName("John Doe"); // Example member name
		String request = new Gson().toJson(requestObj);

		MBeneficiarymapping mapping = new MBeneficiarymapping();
		mapping.setBenMapId(BigInteger.valueOf(123));
		mapping.setBenDetailsId(BigInteger.valueOf(456));
		mapping.setVanID(98);
		when(benMappingRepo.getBenDetailsId(BigInteger.valueOf(requestObj.getBeneficiaryRegId()))).thenReturn(mapping);

		// Mocking repository behavior for update and search operations
		BenFamilyMapping benFamilyMapping = new BenFamilyMapping();
		benFamilyMapping.setFamilyId(requestObj.getFamilyId());
		benFamilyMapping.setNoOfmembers(4);
		when(familyTagRepo.searchFamilyByFamilyId(requestObj.getFamilyId())).thenReturn(benFamilyMapping);

		// Executing the service method
		String resp = familyTagServiceImpl.addTag(request);

		// Verify behavior and assert results
		Assertions.assertEquals("Family tagging completed successfully", resp);

		// Verify that save method was called on familyTagRepo
		verify(familyTagRepo, times(1)).save(any(BenFamilyMapping.class));

		// Additional assertions to validate specific updates
		Assertions.assertEquals(5, benFamilyMapping.getNoOfmembers()); // Expected incremented members count
		Assertions.assertEquals("John Doe", benFamilyMapping.getFamilyHeadName()); // Expected family head name
	}

	@Test
	@Description("Verifies handling of scenario where beneficiary is not found during family tagging.")
	void testAddTag_BeneficiaryNotFound() {
		// Test Case ID: TC_ADD_TAG_002

		// Mocking input data
		BenFamilyMapping requestObj = new BenFamilyMapping();
		requestObj.setBeneficiaryRegId(9999999L); // Non-existent beneficiary ID
		String request = new Gson().toJson(requestObj);

		when(benMappingRepo.getBenDetailsId(BigInteger.valueOf(requestObj.getBeneficiaryRegId()))).thenReturn(null);

		// Executing the service method and expecting an exception
		Assertions.assertThrows(IEMRException.class, () -> familyTagServiceImpl.addTag(request),
				"Beneficiary is not found while doing family tagging. Please contact the administrator");
	}

	@Test
	@Description("Verifies handling of scenario where an invalid family ID is encountered during family tagging.")
	void testAddTag_InvalidFamilyId() {
		// Test Case ID: TC_ADD_TAG_003

		// Mocking input data
		BenFamilyMapping requestObj = new BenFamilyMapping();
		requestObj.setBeneficiaryRegId(1234456L);
		requestObj.setIsHeadOfTheFamily(true);
		requestObj.setFamilyId("InvalidFamilyID"); // Invalid family ID
		String request = new Gson().toJson(requestObj);

		MBeneficiarymapping mapping = new MBeneficiarymapping();
		mapping.setBenMapId(BigInteger.valueOf(123));
		mapping.setBenDetailsId(BigInteger.valueOf(456));
		mapping.setVanID(98);
		when(benMappingRepo.getBenDetailsId(BigInteger.valueOf(requestObj.getBeneficiaryRegId()))).thenReturn(mapping);

		// Mocking repository behavior for update and search operations
		when(familyTagRepo.searchFamilyByFamilyId(requestObj.getFamilyId())).thenReturn(null);

		// Executing the service method and expecting an exception
		Assertions.assertThrows(IEMRException.class, () -> familyTagServiceImpl.addTag(request),
				"Invalid family ID while searching family");
	}

	@Test
	@Description("Test case to verify the functionality of untagging a family member from a document.")
	void testDoFamilyUntag_Success() throws IEMRException, JSONException {
		// Test Case ID: TC_DO_FAMILY_UNTAG_001

		// Mocking input JSON request
		JSONObject benFamilymap = new JSONObject();
		benFamilymap.put("benFamilyTagId", Long.valueOf(123));
		benFamilymap.put("beneficiaryRegId", Long.valueOf(456));
		benFamilymap.put("familyId", "789"); // String type for familyId
		benFamilymap.put("isHeadOfTheFamily", true);
		JSONArray memberList = new JSONArray();
		memberList.put(benFamilymap);
		JSONObject requestJson = new JSONObject();
		requestJson.put("memberList", memberList);
		String request = requestJson.toString();

		// Mocking repository responses
		MBeneficiarymapping mBeneficiarymapping = new MBeneficiarymapping();
		mBeneficiarymapping.setBenDetailsId(BigInteger.valueOf(456));
		mBeneficiarymapping.setVanID(98);
		when(benMappingRepo.getBenDetailsId(BigInteger.valueOf(456))).thenReturn(mBeneficiarymapping);

		BenFamilyMapping benFamilyMapping = new BenFamilyMapping();
		benFamilyMapping.setFamilyId("789");
		benFamilyMapping.setNoOfmembers(4);
		when(familyTagRepo.searchFamilyByFamilyId("789")).thenReturn(benFamilyMapping);

		// Executing the service method
		String resp = familyTagServiceImpl.doFamilyUntag(request);

		// Verify behavior and assert results
		Assertions.assertEquals("Beneficiary untagged successfully", resp);

		verify(familyTagRepo, times(1)).save(any(BenFamilyMapping.class));
	}

	@Test
	@Description("Test case to verify the handling of an invalid member list in the request during family member untagging.")
	void testDoFamilyUntag_InvalidMemberList() throws JSONException {
		// Test Case ID: TC_DO_FAMILY_UNTAG_002

		// Mocking input JSON request with missing memberList
		JSONObject requestJson = new JSONObject();
		requestJson.put("invalidKey", "value"); // No memberList key
		String request = requestJson.toString();

		// Executing the service method and expecting an exception
		Assertions.assertThrows(IEMRException.class, () -> familyTagServiceImpl.doFamilyUntag(request),
				"Invalid request. Member-list is NULL");
	}

	@Test
	@Description("Test case to verify the handling of an invalid family ID during family member untagging.")
	void testDoFamilyUntag_InvalidFamilyId() throws JSONException {
		// Test Case ID: TC_DO_FAMILY_UNTAG_003

		// Mocking input JSON request with invalid familyId
		JSONObject benFamilymap = new JSONObject();
		benFamilymap.put("benFamilyTagId", Long.valueOf(123));
		benFamilymap.put("beneficiaryRegId", Long.valueOf(456));
		benFamilymap.put("familyId", "InvalidFamilyID"); // Invalid familyId
		benFamilymap.put("isHeadOfTheFamily", true);
		JSONArray memberList = new JSONArray();
		memberList.put(benFamilymap);
		JSONObject requestJson = new JSONObject();
		requestJson.put("memberList", memberList);
		String request = requestJson.toString();

		// Mocking repository responses
		MBeneficiarymapping mBeneficiarymapping = new MBeneficiarymapping();
		mBeneficiarymapping.setBenDetailsId(BigInteger.valueOf(456));
		when(benMappingRepo.getBenDetailsId(BigInteger.valueOf(456))).thenReturn(mBeneficiarymapping);

		when(familyTagRepo.searchFamilyByFamilyId("InvalidFamilyID")).thenReturn(null);

		// Executing the service method and expecting an exception
		Assertions.assertThrows(IEMRException.class, () -> familyTagServiceImpl.doFamilyUntag(request),
				"Invalid family ID while searching family");
	}

	@Test
	@Description("Test case to verify the handling of an invalid or null family ID during family member untagging.")
	void testDoFamilyUntag_InvalidFamilyIdOrNull() throws JSONException {
		// Test Case ID: TC_DO_FAMILY_UNTAG_004

		// Mocking input JSON request with invalid familyId or null
		JSONObject benFamilymap = new JSONObject();
		benFamilymap.put("benFamilyTagId", 123);
		benFamilymap.put("beneficiaryRegId", 456);
		benFamilymap.put("familyId", JSONObject.NULL); // Setting familyId to null
		benFamilymap.put("isHeadOfTheFamily", true);

		JSONArray memberList = new JSONArray();
		memberList.put(benFamilymap);

		JSONObject requestJson = new JSONObject();
		requestJson.put("memberList", memberList);

		String request = requestJson.toString();

		// Mocking repository responses
		MBeneficiarymapping mBeneficiarymapping = new MBeneficiarymapping();
		mBeneficiarymapping.setBenDetailsId(BigInteger.valueOf(456));

		// Mocking Gson conversion
		Gson gsonMock = mock(Gson.class);
		BenFamilyMapping[] benFamilyMappings = new BenFamilyMapping[1];
		benFamilyMappings[0] = new BenFamilyMapping();

		// Executing the service method and expecting an exception
		Assertions.assertThrows(IEMRException.class, () -> familyTagServiceImpl.doFamilyUntag(request),
				"Invalid family ID / NULL");

		// Verify interactions
		verify(benDetailRepo, never()).untagFamily(Mockito.anyString(), any(BigInteger.class), Mockito.anyInt()); // Ensure
																													// untagFamily()
																													// is
																													// never
																													// called
		verify(familyTagRepo, never()).searchFamilyByFamilyId(Mockito.anyString()); // Ensure searchFamilyByFamilyId()
																					// is never called
	}

	@Test
	@Description("Verify exception handling when editing family member details with null request")
	void testEditFamilyDetailsCatchblock() {
		// Test Case ID: TC_Edit_Family_Details_001
		// Arrange
		String nullRequest = null;

		// Act + Assert
		IEMRException exception = Assertions.assertThrows(IEMRException.class,
				() -> familyTagServiceImpl.editFamilyDetails(nullRequest));
		Assertions.assertTrue(exception.getMessage().contains("Error while editing family tagging details"));
	}

	@Test
	@Description("Verify successful editing of family member details")
	void testEditFamilyDetails() throws IEMRException {
		// Test Case ID: TC_Edit_Family_Details_002
		// Arrange
		String familyId = "123";
		String memberName = "memberName";
		BenFamilyMapping benFamilyMapping = createBenFamilyMapping(familyId, true, memberName);

		String jsonRequest = new Gson().toJson(benFamilyMapping);

		when(familyTagRepo.searchFamilyByFamilyId(familyId)).thenReturn(benFamilyMapping);
		MBeneficiarymapping mBeneficiaryMapping = createMBeneficiaryMapping(benFamilyMapping.getBeneficiaryRegId());
		when(benMappingRepo.getBenDetailsId(BigInteger.valueOf(benFamilyMapping.getBeneficiaryRegId())))
				.thenReturn(mBeneficiaryMapping);

		// Act
		String result = familyTagServiceImpl.editFamilyDetails(jsonRequest);

		// Assert
		Assertions.assertEquals("Beneficiary family tagging updateed successfully", result);
		Assertions.assertEquals(memberName, benFamilyMapping.getFamilyHeadName());
	}

	@Test
	@Description("Verify behavior when BenFamilyMapping is null")
	void testEditFamilyDetails_If_BenFamilyMapping_Null() {
		// Test Case ID: TC_Edit_Family_Details_003
		// Arrange
		String familyId = "123";
		BenFamilyMapping benFamilyMapping = createBenFamilyMapping(familyId, true, "memberName");

		String jsonRequest = new Gson().toJson(benFamilyMapping);

		when(familyTagRepo.searchFamilyByFamilyId(familyId)).thenReturn(null);

		// Act + Assert
		IEMRException exception = Assertions.assertThrows(IEMRException.class,
				() -> familyTagServiceImpl.editFamilyDetails(jsonRequest));
		Assertions.assertTrue(exception.getMessage().contains("Invalid Family ID"));
	}

	@Test
	@Description("Verify behavior when MBeneficiarymapping is null")
	void testEditFamilyDetailsIfMBeneficiarymappingNull() {
		// Test Case ID: TC_Edit_Family_Details_004
		// Arrange
		String familyId = "123";
		BenFamilyMapping benFamilyMapping = createBenFamilyMapping(familyId, true, "memberName");

		String jsonRequest = new Gson().toJson(benFamilyMapping);

		when(familyTagRepo.searchFamilyByFamilyId(familyId)).thenReturn(benFamilyMapping);
		when(benMappingRepo.getBenDetailsId(BigInteger.valueOf(benFamilyMapping.getBeneficiaryRegId())))
				.thenReturn(null);

		// Act + Assert
		IEMRException exception = Assertions.assertThrows(IEMRException.class,
				() -> familyTagServiceImpl.editFamilyDetails(jsonRequest));
		Assertions.assertTrue(exception.getMessage().contains("Error in getting beneficiary details"));
	}

	private BenFamilyMapping createBenFamilyMapping(String familyId, boolean isHead, String memberName) {
		BenFamilyMapping benFamilyMapping = new BenFamilyMapping();
		benFamilyMapping.setFamilyId(familyId);
		benFamilyMapping.setIsHeadOfTheFamily(isHead);
		benFamilyMapping.setMemberName(memberName);
		benFamilyMapping.setFamilyHeadName("familyHeadName"); // Assuming this is set in the service method
		benFamilyMapping.setBeneficiaryRegId(123L); // Sample value, adjust as per your needs
		return benFamilyMapping;
	}

	private MBeneficiarymapping createMBeneficiaryMapping(long beneficiaryRegId) {
		MBeneficiarymapping mBeneficiaryMapping = new MBeneficiarymapping();
		mBeneficiaryMapping.setBenDetailsId(BigInteger.valueOf(986));
		mBeneficiaryMapping.setVanID(123);
		return mBeneficiaryMapping;
	}

	@Test
	@Description("Test Case for Exception Handling in Family Search (TC_Search_Family_Details_001)")
	void testSearchFamilyCatchblock() throws IEMRException {
		Assertions.assertThrows(IEMRException.class, () -> familyTagServiceImpl.searchFamily(null));
	}

	@Test
	@Description("Test Case for Family Search Functionality (TC_Search_Family_Details_002)")
	void testSearchFamily() throws IEMRException {
		BenFamilyMapping benFamilyMapping = new BenFamilyMapping();
		benFamilyMapping.setFamilyId("123");
		benFamilyMapping.setIsHeadOfTheFamily(true);
		benFamilyMapping.setMemberName("memberName");
		benFamilyMapping.setFamilyHeadName("familyHeadName");
		String json = new Gson().toJson(benFamilyMapping);
		List<BenFamilyMapping> list = new ArrayList<>();
		list.add(benFamilyMapping);
		when(familyTagRepo.searchFamilyWithFamilyId(benFamilyMapping.getFamilyName(), benFamilyMapping.getVillageId(),
				benFamilyMapping.getFamilyId())).thenReturn(list);
		Assertions.assertNotNull(familyTagServiceImpl.searchFamily(json));
	}

	@Test
	@Description("Test Case for Handling Null FamilyID in Family Search (TC_Search_Family_Details_003)")
	void testSearchFamily_If_FamilyID_NULL() throws IEMRException {
		BenFamilyMapping benFamilyMapping = new BenFamilyMapping();
		benFamilyMapping.setFamilyId(null);
		benFamilyMapping.setIsHeadOfTheFamily(true);
		benFamilyMapping.setMemberName("memberName");
		benFamilyMapping.setFamilyHeadName("familyHeadName");
		String json = new Gson().toJson(benFamilyMapping);
		List<BenFamilyMapping> list = new ArrayList<>();
		list.add(benFamilyMapping);
		when(familyTagRepo.searchFamily(benFamilyMapping.getFamilyName(), benFamilyMapping.getVillageId()))
				.thenReturn(list);
		Assertions.assertNotNull(familyTagServiceImpl.searchFamily(json));
	}

	@Test
	@Description("Test Case for Handling Null RESP Parameter in Family Search (TC_Search_Family_Details_004)")
	void testSearchFamily_If_RESP_NULL() throws IEMRException {
		BenFamilyMapping benFamilyMapping = new BenFamilyMapping();
		benFamilyMapping.setFamilyId(null);
		benFamilyMapping.setIsHeadOfTheFamily(true);
		benFamilyMapping.setMemberName("memberName");
		benFamilyMapping.setFamilyHeadName("familyHeadName");
		String json = new Gson().toJson(benFamilyMapping);

		when(familyTagRepo.searchFamily(benFamilyMapping.getFamilyName(), benFamilyMapping.getVillageId()))
				.thenReturn(null);
		Assertions.assertNotNull(familyTagServiceImpl.searchFamily(json));
	}

	@Test
	@Description("Test Case for exception handling in createFamily method")
	void testCreateFamilyCatchBlock() throws IEMRException {
		Assertions.assertThrows(IEMRException.class, () -> familyTagServiceImpl.createFamily(null));
	}

	@Test
	@Description("Test Case for handling null family tagged ID in createFamily method")
	void testCreateFamilyFamilyTaggedIDNull() throws IEMRException {
		BenFamilyMapping benFamilyMapping = new BenFamilyMapping();
		benFamilyMapping.setFamilyId(null);
		benFamilyMapping.setIsHeadOfTheFamily(true);
		benFamilyMapping.setMemberName("memberName");
		benFamilyMapping.setFamilyHeadName("familyHeadName");
		benFamilyMapping.setCreatedBy("Admin");
		benFamilyMapping.setBeneficiaryRegId(Long.valueOf(1234));
		String json = new Gson().toJson(benFamilyMapping);

		when(familyTagRepo.getUserId("Admin")).thenReturn(123);
		when(familyTagRepo.save(any())).thenReturn(benFamilyMapping);
		MBeneficiarymapping mBeneficiarymapping = new MBeneficiarymapping();
		mBeneficiarymapping.setBenDetailsId(BigInteger.valueOf(987));
		mBeneficiarymapping.setVanID(765);
		when(benMappingRepo.getBenDetailsId(BigInteger.valueOf(benFamilyMapping.getBeneficiaryRegId())))
				.thenReturn(mBeneficiarymapping);

		Assertions.assertThrows(IEMRException.class, () -> familyTagServiceImpl.createFamily(json));
	}

	@Test
	@Description("Test Case for creating a family in the system")
	void testCreateFamily() throws IEMRException {
		BenFamilyMapping benFamilyMapping = new BenFamilyMapping();
		benFamilyMapping.setFamilyId(null);
		benFamilyMapping.setIsHeadOfTheFamily(true);
		benFamilyMapping.setMemberName("memberName");
		benFamilyMapping.setFamilyHeadName("familyHeadName");
		benFamilyMapping.setCreatedBy("Admin");
		benFamilyMapping.setBeneficiaryRegId(Long.valueOf(1234));
		benFamilyMapping.setBenFamilyTagId(Long.valueOf(987));
		String json = new Gson().toJson(benFamilyMapping);

		when(familyTagRepo.getUserId("Admin")).thenReturn(123);
		when(familyTagRepo.save(any())).thenReturn(benFamilyMapping);
		MBeneficiarymapping mBeneficiarymapping = new MBeneficiarymapping();
		mBeneficiarymapping.setBenDetailsId(BigInteger.valueOf(987));
		mBeneficiarymapping.setVanID(765);
		when(benMappingRepo.getBenDetailsId(BigInteger.valueOf(benFamilyMapping.getBeneficiaryRegId())))
				.thenReturn(mBeneficiarymapping);

		String resp = familyTagServiceImpl.createFamily(json);
		Assertions.assertNotNull(resp);
	}

	@Test
	@Description("Positive scenario for retrieving a family ID (TC_GET_FAMILY_ID_001)")
	void testGetFamilyId_validUsername() throws IEMRException {
		// Given
		String username = "validUsername";

		// When
		String familyId = familyTagServiceImpl.getFamilyId(username);

		// Then
		Assertions.assertNotNull(familyId, "Family ID should not be null");
		Assertions.assertEquals(16, familyId.length(), "Family ID should be 16 characters long");
		Assertions.assertTrue(familyId.matches("\\d{13}\\d{3}"), "Family ID format should be timestamp + userId");
	}
	
	@Test
    @Description("Exception handling scenario (TC_GET_FAMILY_ID_002)")
    void testGetFamilyId_exceptionHandling() {
        // Given
        String username = "testUser";
        Mockito.when(familyTagRepo.getUserId(username)).thenThrow(new RuntimeException("Simulated Repository Exception"));

        // When & Then
        IEMRException exception = Assertions.assertThrows(IEMRException.class, () -> {
            familyTagServiceImpl.getFamilyId(username);
        });

        // Assert the exception message contains the expected substring
        Assertions.assertTrue(exception.getMessage().contains("Simulated Repository Exception"));
    }
	
	@Test
	@Description("Test Case for handling invalid username in getFamilyId (TC_GET_FAMILY_ID_003)")
	void testGetFamilyId_invalidUsername() {
	  // Arrange (Given)
	  String username = "invalidUsername";
	  Mockito.when(familyTagRepo.getUserId(username)).thenReturn(null); // Simulate user not found

	  // Act (When)
	  Assertions.assertThrows(IEMRException.class, () -> familyTagServiceImpl.getFamilyId(username));
	}


	@Test
	@Description("Test Case for exception handling in getFamilyDetails method")
	void testGetFamilyDetailsCatchBlock() throws IEMRException {
		Assertions.assertThrows(IEMRException.class, () -> familyTagServiceImpl.getFamilyDetails(null));
	}

	@Test
	@Description("Test Case for retrieving family details")
	void testGetFamilyDetails() throws IEMRException {
		BenFamilyMapping benFamilyMapping = new BenFamilyMapping();
		benFamilyMapping.setFamilyId(null);
		benFamilyMapping.setFamilyId("123");
		benFamilyMapping.setIsHeadOfTheFamily(true);
		benFamilyMapping.setMemberName("memberName");
		benFamilyMapping.setFamilyHeadName("familyHeadName");
		benFamilyMapping.setCreatedBy("Admin");
		benFamilyMapping.setBeneficiaryRegId(Long.valueOf(1234));
		String json = new Gson().toJson(benFamilyMapping);

		List<MBeneficiarydetail> list = new ArrayList<>();
		MBeneficiarydetail mBeneficiarydetail = new MBeneficiarydetail();
		mBeneficiarydetail.setOther("other");
		list.add(mBeneficiarydetail);
		when(benMappingRepo.getBenRegId(any(), any())).thenReturn(BigInteger.valueOf(987));
		when(benDetailRepo.getFamilyDetails(any())).thenReturn(list);
		String resp = familyTagServiceImpl.getFamilyDetails(json);
		Assertions.assertNotNull(resp);
	}
	
	@Test
	@Description("Test Case for exception handling in getFamilyDetails when familyId is null")
	void testGetFamilyDetailsEmptyFamilyId() {
	  // Arrange
	  BenFamilyMapping benFamilyMapping = new BenFamilyMapping();
	  benFamilyMapping.setFamilyId(null); // Set familyId to null

	  // Act and Assert
	  Assertions.assertThrows(IEMRException.class, () -> familyTagServiceImpl.getFamilyDetails(new Gson().toJson(benFamilyMapping)));
	}
}
