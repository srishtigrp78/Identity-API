package com.iemr.common.identity.service.rmnch;

public interface RmnchDataSyncService {
	public String syncDataToAmrit(String requestOBJ) throws Exception;
	public String getBenData(String requestOBJ, String authorisation) throws Exception;
	public String getBenDataByAsha(String requestOBJ, String authorisation) throws Exception;
}
