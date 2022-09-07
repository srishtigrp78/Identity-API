package com.iemr.common.identity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.iemr.common.identity.utils.IEMRApplBeans;

/*@SpringBootApplication
@EnableJpaRepositories
@EnableTransactionManagement
public class IdentityApplication implements CommandLineRunner {
	private static final Logger logger = LoggerFactory.getLogger(IdentityApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(IdentityApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		logger.info("displaying all jsons: ");
		
		List<Identity> list = new ArrayList<Identity>();
		Identity ee1 = new Identity();
		Identity ee2 = new Identity();
		list.add(ee1);
		list.add(ee2);
		
		IdentityDTO i = new IdentityDTO();
		i.setCurrentAddress(new Address());
		i.setPermanentAddress(new Address());
		i.setEmergencyAddress(new Address());
		i.setIdentities(list);
		
		IdentityEditDTO ie = new IdentityEditDTO();
		ie.setCurrentAddress(new Address());
		ie.setPermanentAddress(new Address());
		ie.setEmergencyAddress(new Address());
		ie.setIdentities(list);
		
		BenFamilyDTO bfm1 = new BenFamilyDTO();
		BenFamilyDTO bfm2 = new BenFamilyDTO();
		List<BenFamilyDTO> bfmList = new ArrayList<BenFamilyDTO>();
		bfmList.add(bfm1);
		bfmList.add(bfm2);
		BenIdentityDTO bid1 = new BenIdentityDTO();
		BenIdentityDTO bid2 = new BenIdentityDTO();
		List<BenIdentityDTO> bidList = new ArrayList<BenIdentityDTO>();
		bidList.add(bid1);
		bidList.add(bid2);
		BenServiceDTO bsv1 = new BenServiceDTO();
		BenServiceDTO bsv2 = new BenServiceDTO();
		List<BenServiceDTO> bsvList = new ArrayList<BenServiceDTO>();
		bsvList.add(bsv1);
		bsvList.add(bsv2);
		Phone ph1 = new Phone();
		Phone ph2 = new Phone();
		List<Phone> phList = new ArrayList<Phone>();
		phList.add(ph1);
		phList.add(ph2);
		
		BeneficiariesDTO b1 = new BeneficiariesDTO();
		b1.setBeneficiaryDetails(new BenDetailDTO());
		b1.setBeneficiaryFamilyTags(bfmList);
		b1.setBeneficiaryIdentites(bidList);
		b1.setBeneficiaryServiceMap(bsvList);
		b1.setContacts(phList);
		
		BeneficiariesDTO b2 = new BeneficiariesDTO();
		b2.setBeneficiaryDetails(new BenDetailDTO());
		b2.setBeneficiaryFamilyTags(bfmList);
		b2.setBeneficiaryIdentites(bidList);
		b2.setBeneficiaryServiceMap(bsvList);
		b2.setContacts(phList);
		
		List<BeneficiariesDTO> benList = new ArrayList<BeneficiariesDTO>();
		benList.add(b1);
		benList.add(b2);
		
		logger.info("IdentityDTO: " + OutputMapper.getInstance().gson().toJson(i));		
		logger.info("IdentityEditDTO: " + OutputMapper.getInstance().gson().toJson(ie));
		//logger.info("IdentityFilterDTO: " + OutputMapper.getInstance().gson().toJson(new IdentityFilterDTO()));
		logger.info("IdentitySearchDTO: " + OutputMapper.getInstance().gson().toJson(new IdentitySearchDTO()));
		logger.info("List<BeneficiariesDTO>: " + OutputMapper.getInstance().gson().toJson(benList));
		
	}
}*/

@SpringBootApplication
@EnableJpaRepositories
@EnableTransactionManagement
public class IdentityApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(applicationClass, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(applicationClass);
	}

	private static Class<IdentityApplication> applicationClass = IdentityApplication.class;
	
	@Bean
	public IEMRApplBeans instantiateBeans(){
		
		return new IEMRApplBeans();
	}
}

