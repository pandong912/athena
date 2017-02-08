package com.ccb.athena.executor.scheduler.message.p6.builder;

import com.ccb.athena.executor.scheduler.message.p6.field.LoopField;
import com.ccb.athena.executor.scheduler.message.p6.field.TxField;

public class ResPublicFieldBuilder extends PublicFieldBuilder {

	@Override
	public void buildHeader() {
		// SYS_HDR_LEN
		TxField SYS_HDR_LEN = createTxField("SYS_HDR_LEN", "/TX/TX_HEADER/SYS_HDR_LEN", 1, "N", 5, 0, true);
		publicField.addHeader(SYS_HDR_LEN);

		// SYS_PKG_VRSN
		TxField SYS_PKG_VRSN = createTxField("SYS_PKG_VRSN", "/TX/TX_HEADER/SYS_PKG_VRSN", 1, "C", 2, 0, true);
		publicField.addHeader(SYS_PKG_VRSN);

		// SYS_TTL_LEN
		TxField SYS_TTL_LEN = createTxField("SYS_TTL_LEN", "/TX/TX_HEADER/SYS_TTL_LEN", 1, "N", 10, 0, true);
		publicField.addHeader(SYS_TTL_LEN);

		// SYS_SND_SEC_ID
		TxField SYS_SND_SEC_ID = createTxField("SYS_SND_SEC_ID", "/TX/TX_HEADER/SYS_SND_SEC_ID", 1, "C", 6, 0, true);
		publicField.addHeader(SYS_SND_SEC_ID);

		// SYS_REQ_SEC_ID
		TxField SYS_REQ_SEC_ID = createTxField("SYS_REQ_SEC_ID", "/TX/TX_HEADER/SYS_REQ_SEC_ID", 1, "C", 6, 0, true);
		publicField.addHeader(SYS_REQ_SEC_ID);

		// SYS_TX_TYPE
		TxField SYS_TX_TYPE = createTxField("SYS_TX_TYPE", "/TX/TX_HEADER/SYS_TX_TYPE", 1, "C", 6, 0, true);
		publicField.addHeader(SYS_TX_TYPE);

		// SYS_EVT_TRACE_ID
		TxField SYS_EVT_TRACE_ID = createTxField("SYS_EVT_TRACE_ID", "/TX/TX_HEADER/SYS_EVT_TRACE_ID", 1, "C", 25, 0, true);
		publicField.addHeader(SYS_EVT_TRACE_ID);

		// SYS_SND_SERIAL_NO
		TxField SYS_SND_SERIAL_NO = createTxField("SYS_SND_SERIAL_NO", "/TX/TX_HEADER/SYS_SND_SERIAL_NO", 1, "C", 10, 0, true);
		publicField.addHeader(SYS_SND_SERIAL_NO);

		// SYS_PKG_TYPE
		TxField SYS_PKG_TYPE = createTxField("SYS_PKG_TYPE", "/TX/TX_HEADER/SYS_PKG_TYPE", 1, "C", 1, 0, true);
		publicField.addHeader(SYS_PKG_TYPE);

		// SYS_MSG_LEN/>
		TxField SYS_MSG_LEN = createTxField("SYS_MSG_LEN", "/TX/TX_HEADER/SYS_MSG_LEN", 1, "N", 10, 0, true);
		publicField.addHeader(SYS_MSG_LEN);

		// SYS_IS_ENCRYPTED
		TxField SYS_IS_ENCRYPTED = createTxField("SYS_IS_ENCRYPTED", "/TX/TX_HEADER/SYS_IS_ENCRYPTED", 1, "C", 1, 0, true);
		publicField.addHeader(SYS_IS_ENCRYPTED);

		// SYS_ENCRYPT_TYPE
		TxField SYS_ENCRYPT_TYPE = createTxField("SYS_ENCRYPT_TYPE", "/TX/TX_HEADER/SYS_ENCRYPT_TYPE", 1, "C", 1, 0, true);
		publicField.addHeader(SYS_ENCRYPT_TYPE);

		// SYS_COMPRESS_TYPE
		TxField SYS_COMPRESS_TYPE = createTxField("SYS_COMPRESS_TYPE", "/TX/TX_HEADER/SYS_COMPRESS_TYPE", 1, "C", 1, 0, true);
		publicField.addHeader(SYS_COMPRESS_TYPE);

		// SYS_EMB_MSG_LEN
		TxField SYS_EMB_MSG_LEN = createTxField("SYS_EMB_MSG_LEN", "/TX/TX_HEADER/SYS_HDR_LEN", 1, "N", 5, 0, true);
		publicField.addHeader(SYS_EMB_MSG_LEN);

		// SYS_RECV_TIME
		TxField SYS_RECV_TIME = createTxField("SYS_RECV_TIME", "/TX/TX_HEADER/SYS_RECV_TIME", 1, "C", 17, 0, true);
		publicField.addHeader(SYS_RECV_TIME);

		// SYS_RESP_TIME
		TxField SYS_RESP_TIME = createTxField("SYS_RESP_TIME", "/TX/TX_HEADER/SYS_RESP_TIME", 1, "C", 17, 0, true);
		publicField.addHeader(SYS_RESP_TIME);

		// SYS_PKG_STS_TYPE
		TxField SYS_PKG_STS_TYPE = createTxField("SYS_PKG_STS_TYPE", "/TX/TX_HEADER/SYS_PKG_STS_TYPE", 1, "C", 2, 0, true);
		publicField.addHeader(SYS_PKG_STS_TYPE);

		// SYS_TX_STATUS
		TxField SYS_TX_STATUS = createTxField("SYS_TX_STATUS", "/TX/TX_HEADER/SYS_TX_STATUS", 1, "C", 2, 0, true);
		publicField.addHeader(SYS_TX_STATUS);

		// SYS_RESP_CODE
		TxField SYS_RESP_CODE = createTxField("SYS_RESP_CODE", "/TX/TX_HEADER/SYS_RESP_CODE", 1, "C", 12, 0, true);
		publicField.addHeader(SYS_RESP_CODE);

		// SYS_RESP_DESC_LEN
		TxField SYS_RESP_DESC_LEN = createTxField("SYS_RESP_DESC_LEN", "/TX/TX_HEADER/SYS_RESP_DESC_LEN", 1, "N", 3, 0, true);
		publicField.addHeader(SYS_RESP_DESC_LEN);

		// SYS_RESP_DESC
		TxField SYS_RESP_DESC = createTxField("SYS_RESP_DESC", "/TX/TX_HEADER/SYS_RESP_DESC", 0, "C", 200, 0, true);
		publicField.addHeader(SYS_RESP_DESC);

		// SYS_HOLDTEXT
		TxField SYS_HOLDTEXT = createTxField("SYS_HOLDTEXT", "/TX/TX_HEADER/SYS_HOLDTEXT", 1, "C", 64, 0, true);
		publicField.addHeader(SYS_HOLDTEXT);

	}

	@Override
	public void buildFixedCommon() {

		// COMMUNICATE_STATUS
		TxField HOST_BUSN_DT = createTxField("HOST_BUSN_DT", "/TX/TX_BODY/COMMON/COMA/HOST_BUSN_DT", 1, "C", 8, 0, true);
		publicField.addFixedCommon(HOST_BUSN_DT);

		// COMMUNICATE_STATUS
		TxField CMPT_TRCNO = createTxField("CMPT_TRCNO", "/TX/TX_BODY/COMMON/COMA/CMPT_TRCNO", 1, "C", 19, 0, true);
		publicField.addFixedCommon(CMPT_TRCNO);

		// COMMUNICATE_STATUS
		TxField COMMUNICATE_STATUS = createTxField("COMMUNICATE_STATUS", "/TX/TX_BODY/COMMON/COMA/TXN_INSID", 1, "C", 8, 0, true);
		publicField.addFixedCommon(COMMUNICATE_STATUS);

		// FLOW_STATUS
		TxField FLOW_STATUS = createTxField("FLOW_STATUS", "/TX/TX_BODY/COMMON/COMA/FLOW_STATUS", 1, "C", 8, 0, true);
		publicField.addFixedCommon(FLOW_STATUS);

		// OPTIONAL_LL
		TxField OPTIONAL_LL = createTxField("OPTIONAL_LL", "/TX/TX_BODY/COMMON/COMA/OPTIONAL_LL", 1, "N", 5, 0, true);
		publicField.addFixedCommon(OPTIONAL_LL);

		// ENTITY_LL
		TxField ENTITY_LL = createTxField("ENTITY_LL", "/TX/TX_BODY/COMMON/COMA/ENTITY_LL", 1, "N", 10, 0, true);
		publicField.addFixedCommon(ENTITY_LL);

		// ENTITY_TOT_LL
		TxField ENTITY_TOT_LL = createTxField("ENTITY_TOT_LL", "/TX/TX_BODY/COMMON/COMA/ENTITY_TOT_LL", 1, "N", 10, 0, true);
		publicField.addFixedCommon(ENTITY_TOT_LL);

		// SYS_HOLDTEXT2
		TxField SYS_HOLDTEXT2 = createTxField("SYS_HOLDTEXT2", "/TX/TX_BODY/COMMON/COMA/SYS_HOLDTEXT2", 1, "C", 64, 0, true);
		publicField.addFixedCommon(SYS_HOLDTEXT2);

	}

	@Override
	public void buildOptionalCommon() {
		// ERR_MSG_NUM
		TxField ERR_MSG_NUM = createTxField("ERR_MSG_NUM", "/TX/TX_BODY/COMMON/COMB/ERR_MSG_NUM", 1, "C", 2, 0, true);
		publicField.addOptionalCommon(ERR_MSG_NUM);

		LoopField errLoop1 = createLoopField("errLoop1", "");

		TxField ERR_CODE1 = createTxField("ERR_CODE", "/TX/TX_BODY/COMMON/COMB/ERR_GROUP[1]/ERR_CODE", 1, "C", 12, 0, true);
		TxField ERR_DESCRIPTION_LL1 = createTxField("ERR_DESCRIPTION_LL", "/TX/TX_BODY/COMMON/COMB/ERR_GROUP[1]/ERR_DESCRIPTION_LL", 1, "C", 3, 0, true);
		TxField ERR_DESCRIPTION1 = createTxField("ERR_DESCRIPTION", "/TX/TX_BODY/COMMON/COMB/ERR_GROUP[1]/ERR_DESCRIPTION", 1, "C", 200, 0, true);
		errLoop1.add(ERR_CODE1);
		errLoop1.add(ERR_DESCRIPTION_LL1);
		errLoop1.add(ERR_DESCRIPTION1);
		publicField.addOptionalCommon(errLoop1);

		LoopField errLoop2 = createLoopField("errLoop2", "");

		TxField ERR_CODE2 = createTxField("ERR_CODE", "/TX/TX_BODY/COMMON/COMB/ERR_GROUP[2]/ERR_CODE", 1, "C", 12, 0, true);
		TxField ERR_DESCRIPTION_LL2 = createTxField("ERR_DESCRIPTION_LL", "/TX/TX_BODY/COMMON/OMB/ERR_GROUP[2]/ERR_DESCRIPTION_LL", 1, "C", 3, 0, true);
		TxField ERR_DESCRIPTION2 = createTxField("ERR_DESCRIPTION", "/TX/TX_BODY/COMMON/OMB/ERR_GROUP[2]/ERR_DESCRIPTION", 1, "C", 200, 0, true);
		errLoop1.add(ERR_CODE2);
		errLoop1.add(ERR_DESCRIPTION_LL2);
		errLoop1.add(ERR_DESCRIPTION2);
		publicField.addOptionalCommon(errLoop2);

		LoopField errLoop3 = createLoopField("errLoop3", "");

		TxField ERR_CODE3 = createTxField("ERR_CODE", "/TX/TX_BODY/COMMON/COMB/ERR_GROUP[3]/ERR_CODE", 1, "C", 12, 0, true);
		TxField ERR_DESCRIPTION_LL3 = createTxField("ERR_DESCRIPTION_LL", "/TX/TX_BODY/COMMON/COMB/ERR_GROUP[3]/ERR_DESCRIPTION_LL", 1, "C", 3, 0, true);
		TxField ERR_DESCRIPTION3 = createTxField("ERR_DESCRIPTION", "/TX/TX_BODY/COMMON/COMB/ERR_GROUP[3]/ERR_DESCRIPTION", 1, "C", 200, 0, true);
		errLoop1.add(ERR_CODE3);
		errLoop1.add(ERR_DESCRIPTION_LL3);
		errLoop1.add(ERR_DESCRIPTION3);
		publicField.addOptionalCommon(errLoop3);

		LoopField errLoop4 = createLoopField("errLoop4", "");

		TxField ERR_CODE4 = createTxField("ERR_CODE", "/TX/TX_BODY/COMMON/COMB/ERR_GROUP[4]/ERR_CODE", 1, "C", 12, 0, true);
		TxField ERR_DESCRIPTION_LL4 = createTxField("ERR_DESCRIPTION_LL", "/TX/TX_BODY/COMMON/COMB/ERR_GROUP[4]/ERR_DESCRIPTION_LL", 1, "C", 3, 0, true);
		TxField ERR_DESCRIPTION4 = createTxField("ERR_DESCRIPTION", "/TX/TX_BODY/COMMON/COMB/ERR_GROUP[4]/ERR_DESCRIPTION", 1, "C", 200, 0, true);
		errLoop1.add(ERR_CODE4);
		errLoop1.add(ERR_DESCRIPTION_LL4);
		errLoop1.add(ERR_DESCRIPTION4);
		publicField.addOptionalCommon(errLoop4);

		LoopField errLoop5 = createLoopField("errLoop5", "");

		TxField ERR_CODE5 = createTxField("ERR_CODE", "/TX/TX_BODY/COMMON/COMB/ERR_GROUP[5]/ERR_CODE", 1, "C", 12, 0, true);
		TxField ERR_DESCRIPTION_LL5 = createTxField("ERR_DESCRIPTION_LL", "/TX/TX_BODY/COMMON/COMB/ERR_GROUP[5]/ERR_DESCRIPTION_LL", 1, "C", 3, 0, true);
		TxField ERR_DESCRIPTION5 = createTxField("ERR_DESCRIPTION", "/TX/TX_BODY/COMMON/COMB/ERR_GROUP[5]/ERR_DESCRIPTION", 1, "C", 200, 0, true);
		errLoop1.add(ERR_CODE5);
		errLoop1.add(ERR_DESCRIPTION_LL5);
		errLoop1.add(ERR_DESCRIPTION5);
		publicField.addOptionalCommon(errLoop5);

		LoopField errLoop6 = createLoopField("errLoop6", "");

		TxField ERR_CODE6 = createTxField("ERR_CODE", "/TX/TX_BODY/COMMON/COMB/ERR_GROUP[6]/ERR_CODE", 1, "C", 12, 0, true);
		TxField ERR_DESCRIPTION_LL6 = createTxField("ERR_DESCRIPTION_LL", "/TX/TX_BODY/COMMON/COMB/ERR_GROUP[6]/ERR_DESCRIPTION_LL", 1, "C", 3, 0, true);
		TxField ERR_DESCRIPTION6 = createTxField("ERR_DESCRIPTION", "/TX/TX_BODY/COMMON/COMB/ERR_GROUP[6]/ERR_DESCRIPTION", 1, "C", 200, 0, true);
		errLoop1.add(ERR_CODE6);
		errLoop1.add(ERR_DESCRIPTION_LL6);
		errLoop1.add(ERR_DESCRIPTION6);
		publicField.addOptionalCommon(errLoop6);

		LoopField errLoop7 = createLoopField("errLoop7", "");

		TxField ERR_CODE7 = createTxField("ERR_CODE", "/TX/TX_BODY/COMMON/COMB/ERR_GROUP[7]/ERR_CODE", 1, "C", 12, 0, true);
		TxField ERR_DESCRIPTION_LL7 = createTxField("ERR_DESCRIPTION_LL", "/TX/TX_BODY/COMMON/COMB/ERR_GROUP[7]/ERR_DESCRIPTION_LL", 1, "C", 3, 0, true);
		TxField ERR_DESCRIPTION7 = createTxField("ERR_DESCRIPTION", "/TX/TX_BODY/COMMON/COMB/ERR_GROUP[7]/ERR_DESCRIPTION", 1, "C", 200, 0, true);
		errLoop1.add(ERR_CODE7);
		errLoop1.add(ERR_DESCRIPTION_LL7);
		errLoop1.add(ERR_DESCRIPTION7);
		publicField.addOptionalCommon(errLoop7);

		LoopField errLoop8 = createLoopField("errLoop8", "");

		TxField ERR_CODE8 = createTxField("ERR_CODE", "/TX/TX_BODY/COMMON/COMB/ERR_GROUP[8]/ERR_CODE", 1, "C", 12, 0, true);
		TxField ERR_DESCRIPTION_LL8 = createTxField("ERR_DESCRIPTION_LL", "/TX/TX_BODY/COMMON/COMB/ERR_GROUP[8]/ERR_DESCRIPTION_LL", 1, "C", 3, 0, true);
		TxField ERR_DESCRIPTION8 = createTxField("ERR_DESCRIPTION", "/TX/TX_BODY/COMMON/COMB/ERR_GROUP[8]/ERR_DESCRIPTION", 1, "C", 200, 0, true);
		errLoop1.add(ERR_CODE8);
		errLoop1.add(ERR_DESCRIPTION_LL8);
		errLoop1.add(ERR_DESCRIPTION8);
		publicField.addOptionalCommon(errLoop8);

		LoopField errLoop9 = createLoopField("errLoop9", "");

		TxField ERR_CODE9 = createTxField("ERR_CODE", "/TX/TX_BODY/COMMON/COMB/ERR_GROUP[9]/ERR_CODE", 1, "C", 12, 0, true);
		TxField ERR_DESCRIPTION_LL9 = createTxField("ERR_DESCRIPTION_LL", "/TX/TX_BODY/COMMON/COMB/ERR_GROUP[9]/ERR_DESCRIPTION_LL", 1, "C", 3, 0, true);
		TxField ERR_DESCRIPTION9 = createTxField("ERR_DESCRIPTION", "/TX/TX_BODY/COMMON/COMB/ERR_GROUP[9]/ERR_DESCRIPTION", 1, "C", 200, 0, true);
		errLoop1.add(ERR_CODE9);
		errLoop1.add(ERR_DESCRIPTION_LL9);
		errLoop1.add(ERR_DESCRIPTION9);
		publicField.addOptionalCommon(errLoop9);

		LoopField errLoop10 = createLoopField("errLoop10", "");

		TxField ERR_CODE10 = createTxField("ERR_CODE", "/TX/TX_BODY/COMMON/COMB/ERR_GROUP[10]/ERR_CODE", 1, "C", 12, 0, true);
		TxField ERR_DESCRIPTION_LL10 = createTxField("ERR_DESCRIPTION_LL", "/TX/TX_BODY/COMMON/COMB/ERR_GROUP[10]/ERR_DESCRIPTION_LL", 1, "C", 3, 0, true);
		TxField ERR_DESCRIPTION10 = createTxField("ERR_DESCRIPTION", "/TX/TX_BODY/COMMON/COMB/ERR_GROUP[10]/ERR_DESCRIPTION", 1, "C", 200, 0, true);
		errLoop1.add(ERR_CODE10);
		errLoop1.add(ERR_DESCRIPTION_LL10);
		errLoop1.add(ERR_DESCRIPTION10);
		publicField.addOptionalCommon(errLoop10);

		LoopField errLoop11 = createLoopField("errLoop11", "");

		TxField ERR_CODE11 = createTxField("ERR_CODE", "/TX/TX_BODY/COMMON/COMB/ERR_GROUP[11]/ERR_CODE", 1, "C", 12, 0, true);
		TxField ERR_DESCRIPTION_LL11 = createTxField("ERR_DESCRIPTION_LL", "/TX/TX_BODY/COMMON/COMB/ERR_GROUP[11]/ERR_DESCRIPTION_LL", 1, "C", 3, 0, true);
		TxField ERR_DESCRIPTION11 = createTxField("ERR_DESCRIPTION", "/TX/TX_BODY/COMMON/COMB/ERR_GROUP[11]/ERR_DESCRIPTION", 1, "C", 200, 0, true);
		errLoop1.add(ERR_CODE11);
		errLoop1.add(ERR_DESCRIPTION_LL11);
		errLoop1.add(ERR_DESCRIPTION11);
		publicField.addOptionalCommon(errLoop11);

		LoopField errLoop12 = createLoopField("errLoop12", "");

		TxField ERR_CODE12 = createTxField("ERR_CODE", "/TX/TX_BODY/COMMON/COMB/ERR_GROUP[12]/ERR_CODE", 1, "C", 12, 0, true);
		TxField ERR_DESCRIPTION_LL12 = createTxField("ERR_DESCRIPTION_LL", "/TX/TX_BODY/COMMON/COMB/ERR_GROUP[12]/ERR_DESCRIPTION_LL", 1, "C", 3, 0, true);
		TxField ERR_DESCRIPTION12 = createTxField("ERR_DESCRIPTION", "/TX/TX_BODY/COMMON/COMB/ERR_GROUP[12]/ERR_DESCRIPTION", 1, "C", 200, 0, true);
		errLoop1.add(ERR_CODE12);
		errLoop1.add(ERR_DESCRIPTION_LL12);
		errLoop1.add(ERR_DESCRIPTION12);
		publicField.addOptionalCommon(errLoop12);

		// TIMESTAMP
		TxField TIMESTAMP = createTxField("TIMESTAMP", "/TX/TX_BODY/COMMON/COM3/TIMESTAMP", 1, "C", 23, 0, true);
		publicField.addOptionalCommon(TIMESTAMP);

		LoopField pageLoop = createLoopField("page", "");
		// PAGE_STA_KEY
		TxField PAGE_STA_KEY = createTxField("PAGE_STA_KEY", "/TX/TX_BODY/COMMON/COM3/PAGE_STA_KEY", 1, "C", 512, 0, true);
		pageLoop.add(PAGE_STA_KEY);

		// PAGE_END_KEY
		TxField PAGE_END_KEY = createTxField("PAGE_END_KEY", "/TX/TX_BODY/COMMON/COM3/PAGE_END_KEY", 1, "C", 512, 0, true);
		pageLoop.add(PAGE_END_KEY);

		// CONV_NO_DATA
		TxField CONV_NO_DATA = createTxField("CONV_NO_DATA", "/TX/TX_BODY/COMMON/COM3/CONV_NO_DATA", 1, "C", 1, 0, true);
		pageLoop.add(CONV_NO_DATA);

		publicField.addOptionalCommon(pageLoop);

		// COMMUNICATION_ITI_ID
		TxField COMMUNICATION_ITI_ID = createTxField("COMMUNICATION_ITI_ID", "/TX/TX_BODY/COMMON/COM3/COMMUNICATION_ITI_ID", 35, "C", 12, 0, true);
		publicField.addOptionalCommon(COMMUNICATION_ITI_ID);

		// COMMUNICATION_PSN
		TxField COMMUNICATION_PSN = createTxField("COMMUNICATION_PSN", "/TX/TX_BODY/COMMON/COM3/COMMUNICATION_PSN", 1, "C", 8, 0, true);
		publicField.addOptionalCommon(COMMUNICATION_PSN);

		// FLOW_ITI_ID
		TxField FLOW_ITI_ID = createTxField("FLOW_ITI_ID", "/TX/TX_BODY/COMMON/COM3/FLOW_ITI_ID", 1, "C", 35, 0, true);
		publicField.addOptionalCommon(FLOW_ITI_ID);

		// FLOW_PSN
		TxField FLOW_PSN = createTxField("FLOW_PSN", "/TX/TX_BODY/COMMON/COM3/FLOW_PSN", 1, "C", 8, 0, true);
		publicField.addOptionalCommon(FLOW_PSN);

		LoopField totalPage = createLoopField("totalPage", "totalPage");
		// TOTAL_PAGE
		TxField TOTAL_PAGE = createTxField("TOTAL_PAGE", "/TX/TX_BODY/COMMON/COM3/TOTAL_PAGE", 1, "C", 8, 0, true);
		totalPage.add(TOTAL_PAGE);

		// TOTAL_REC
		TxField TOTAL_REC = createTxField("TOTAL_REC", "/TX/TX_BODY/COMMON/COM3/TOTAL_REC", 1, "C", 8, 0, true);
		totalPage.add(TOTAL_REC);

		// CURR_TOTAL_PAGE
		TxField CURR_TOTAL_PAGE = createTxField("CURR_TOTAL_PAGE", "/TX/TX_BODY/COMMON/COM3/CURR_TOTAL_PAGE", 1, "C", 8, 0, true);
		totalPage.add(CURR_TOTAL_PAGE);

		// CURR_TOTAL_REC
		TxField CURR_TOTAL_REC = createTxField("CURR_TOTAL_REC", "/TX/TX_BODY/COMMON/COM3/CURR_TOTAL_REC", 1, "C", 8, 0, true);
		totalPage.add(CURR_TOTAL_REC);

		publicField.addOptionalCommon(totalPage);

	}

	private TxField createTxField(String name, String path, int encoding, String barPro, int length, int decimalLen, boolean polishing) {
		TxField field = new TxField();
		field.setName(name);
		field.setPath(path);
		field.setEncoding(encoding);
		field.setBarPro(barPro);
		field.setLength(length);
		field.setPolishing(polishing);
		return field;
	}

	private LoopField createLoopField(String name, String path) {
		LoopField loopField = new LoopField();
		loopField.setName(name);
		loopField.setPath(path);
		return loopField;
	}

}
