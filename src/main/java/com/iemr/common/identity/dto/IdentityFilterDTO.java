package com.iemr.common.identity.dto;

import java.math.BigInteger;
import java.util.Optional;

import com.iemr.common.identity.domain.Address;

public class IdentityFilterDTO {
	private Optional<BigInteger> beneficiaryId;
	private Optional<BigInteger> beneficiaryRegId;
	
	private Optional<String> firstName;
	private Optional<String> middleName;
	private Optional<String> lastName;
	
	private Optional<Integer> ageId;
	private Optional<Integer> age;
	private Optional<Integer> genderId;
	private Optional<String> genderName;
	
	private Optional<String> spouseName;
	private Optional<String> fatherName;
	
	private Optional<String> pinCode;
	private Optional<Address> currentAddress;
	private Optional<Address> permanentAddress;
	private Optional<Address> emergencyAddress;
	
	private Optional<String> contactNumber;

	public void setBeneficiaryId(BigInteger beneficiaryId) {
		this.beneficiaryId = Optional.of(beneficiaryId);
	}

	public void setBeneficiaryRegId(BigInteger beneficiaryRegId) {
		this.beneficiaryRegId = Optional.of(beneficiaryRegId);
	}

	public void setFirstName(String firstName) {
		this.firstName = Optional.of(firstName);
	}

	public void setMiddleName(String middleName) {
		this.middleName = Optional.of(middleName);
	}

	public void setLastName(String lastName) {
		this.lastName = Optional.of(lastName);
	}

	public void setAgeId(Integer ageId) {
		this.ageId = Optional.of(ageId);
	}

	public void setAge(Integer age) {
		this.age = Optional.of(age);
	}

	public void setGenderId(Integer genderId) {
		this.genderId = Optional.of(genderId);
	}

	public void setGenderName(String genderName) {
		this.genderName = Optional.of(genderName);
	}

	public void setSpouseName(String spouseName) {
		this.spouseName = Optional.of(spouseName);
	}

	public void setFatherName(String fatherName) {
		this.fatherName = Optional.of(fatherName);
	}

	public void setPinCode(String pinCode) {
		this.pinCode = Optional.of(pinCode);
	}

	public void setCurrentAddress(Address currentAddress) {
		this.currentAddress = Optional.of(currentAddress);
	}

	public void setPermanentAddress(Address permanentAddress) {
		this.permanentAddress = Optional.of(permanentAddress);
	}

	public void setEmergencyAddress(Address emergencyAddress) {
		this.emergencyAddress = Optional.of(emergencyAddress);
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = Optional.of(contactNumber);
	}

	public Optional<BigInteger> getBeneficiaryId() {
		return beneficiaryId;
	}

	public Optional<BigInteger> getBeneficiaryRegId() {
		return beneficiaryRegId;
	}

	public Optional<String> getFirstName() {
		return firstName;
	}

	public Optional<String> getMiddleName() {
		return middleName;
	}

	public Optional<String> getLastName() {
		return lastName;
	}

	public Optional<Integer> getAgeId() {
		return ageId;
	}

	public Optional<Integer> getAge() {
		return age;
	}

	public Optional<Integer> getGenderId() {
		return genderId;
	}

	public Optional<String> getGenderName() {
		return genderName;
	}

	public Optional<String> getSpouseName() {
		return spouseName;
	}

	public Optional<String> getFatherName() {
		return fatherName;
	}

	public Optional<String> getPinCode() {
		return pinCode;
	}

	public Optional<Address> getCurrentAddress() {
		return currentAddress;
	}

	public Optional<Address> getPermanentAddress() {
		return permanentAddress;
	}

	public Optional<Address> getEmergencyAddress() {
		return emergencyAddress;
	}

	public Optional<String> getContactNumber() {
		return contactNumber;
	}
}
