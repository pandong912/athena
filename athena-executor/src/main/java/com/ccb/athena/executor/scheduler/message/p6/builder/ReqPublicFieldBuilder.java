package com.ccb.athena.executor.scheduler.message.p6.builder;

import com.ccb.athena.executor.scheduler.message.p6.field.LoopField;
import com.ccb.athena.executor.scheduler.message.p6.field.TxField;

public class ReqPublicFieldBuilder extends PublicFieldBuilder {

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

		// SYS_REQ_SEC_ID
		TxField SYS_REQ_SEC_ID = createTxField("SYS_REQ_SEC_ID", "/TX/TX_HEADER/SYS_REQ_SEC_ID", 1, "C", 6, 0, true);
		publicField.addHeader(SYS_REQ_SEC_ID);

		// SYS_SND_SEC_ID
		TxField SYS_SND_SEC_ID = createTxField("SYS_SND_SEC_ID", "/TX/TX_HEADER/SYS_SND_SEC_ID", 1, "C", 6, 0, true);
		publicField.addHeader(SYS_SND_SEC_ID);

		// SYS_TX_CODE
		TxField SYS_TX_CODE = createTxField("SYS_TX_CODE", "/TX/TX_HEADER/SYS_TX_CODE", 1, "C", 9, 0, true);
		publicField.addHeader(SYS_TX_CODE);

		// SYS_TX_VRSN
		TxField SYS_TX_VRSN = createTxField("SYS_TX_VRSN", "/TX/TX_HEADER/SYS_TX_VRSN", 1, "C", 2, 0, true);
		publicField.addHeader(SYS_TX_VRSN);

		// SYS_TX_TYPE
		TxField SYS_TX_TYPE = createTxField("SYS_TX_TYPE", "/TX/TX_HEADER/SYS_TX_TYPE", 1, "C", 6, 0, true);
		publicField.addHeader(SYS_TX_TYPE);

		// SYS_RESERVED
		TxField SYS_RESERVED = createTxField("SYS_RESERVED", "/TX/TX_HEADER/SYS_RESERVED", 1, "C", 20, 0, true);
		publicField.addHeader(SYS_RESERVED);

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

		// SYS_REQ_TIME
		TxField SYS_REQ_TIME = createTxField("SYS_REQ_TIME", "/TX/TX_HEADER/SYS_REQ_TIME", 1, "C", 17, 0, true);
		publicField.addHeader(SYS_REQ_TIME);

		// SYS_TIME_LEFT
		TxField SYS_TIME_LEFT = createTxField("SYS_TIME_LEFT", "/TX/TX_HEADER/SYS_TIME_LEFT", 1, "C", 9, 0, true);
		publicField.addHeader(SYS_TIME_LEFT);

		// SYS_PKG_STS_TYPE
		TxField SYS_PKG_STS_TYPE = createTxField("SYS_PKG_STS_TYPE", "/TX/TX_HEADER/SYS_PKG_STS_TYPE", 1, "C", 2, 0, true);
		publicField.addHeader(SYS_PKG_STS_TYPE);

		// SYS_HOLDTEXT
		TxField SYS_HOLDTEXT = createTxField("SYS_HOLDTEXT", "/TX/TX_HEADER/SYS_HOLDTEXT", 1, "C", 64, 0, true);
		publicField.addHeader(SYS_HOLDTEXT);

	}

	@Override
	public void buildFixedCommon() {
		// TXN_INSID
		TxField TXN_INSID = createTxField("TXN_INSID", "/TX/TX_BODY/COMMON/COM1/TXN_INSID", 1, "C", 9, 0, true);
		publicField.addFixedCommon(TXN_INSID);

		// TXN_ITT_CHNL_CGY_CODE
		TxField TXN_ITT_CHNL_CGY_CODE = createTxField("TXN_ITT_CHNL_CGY_CODE", "/TX/TX_BODY/COMMON/COM1/TXN_ITT_CHNL_CGY_CODE", 1, "C", 8, 0, true);
		publicField.addFixedCommon(TXN_ITT_CHNL_CGY_CODE);

		// TXN_ITT_CHNL_ID
		TxField TXN_ITT_CHNL_ID = createTxField("TXN_ITT_CHNL_ID", "/TX/TX_BODY/COMMON/COM1/TXN_ITT_CHNL_ID", 1, "C", 23, 0, true);
		publicField.addFixedCommon(TXN_ITT_CHNL_ID);

		// TXN_DT
		TxField TXN_DT = createTxField("TXN_DT", "/TX/TX_BODY/COMMON/COM1/TXN_DT", 1, "C", 8, 0, true);
		publicField.addFixedCommon(TXN_DT);

		// TXN_TM
		TxField TXN_TM = createTxField("TXN_TM", "/TX/TX_BODY/COMMON/COM1/TXN_TM", 1, "C", 6, 0, true);
		publicField.addFixedCommon(TXN_TM);

		// MULTI_TENANCY_ID
		TxField MULTI_TENANCY_ID = createTxField("MULTI_TENANCY_ID", "/TX/TX_BODY/COMMON/COM1/MULTI_TENANCY_ID", 1, "C", 5, 0, true);
		publicField.addFixedCommon(MULTI_TENANCY_ID);

		// TXN_STFF_ID
		TxField TXN_STFF_ID = createTxField("TXN_STFF_ID", "/TX/TX_BODY/COMMON/COM1/TXN_STFF_ID", 1, "C", 8, 0, true);
		publicField.addFixedCommon(TXN_STFF_ID);

		// LNG_ID
		TxField LNG_ID = createTxField("LNG_ID", "/TX/TX_BODY/COMMON/COM1/LNG_ID", 1, "C", 5, 0, true);
		publicField.addFixedCommon(LNG_ID);

		// COMMUNICATE_STATUS
		TxField COMMUNICATE_STATUS = createTxField("COMMUNICATE_STATUS", "/TX/TX_BODY/COMMON/COM3/COMMUNICATE_STATUS", 1, "C", 8, 0, true);
		publicField.addFixedCommon(COMMUNICATE_STATUS);

		// FLOW_STATUS
		TxField FLOW_STATUS = createTxField("FLOW_STATUS", "/TX/TX_BODY/COMMON/COM3/FLOW_STATUS", 1, "C", 8, 0, true);
		publicField.addFixedCommon(FLOW_STATUS);

		// OPTIONAL_LL
		TxField OPTIONAL_LL = createTxField("OPTIONAL_LL", "/TX/TX_BODY/COMMON/COM3/OPTIONAL_LL", 1, "N", 5, 0, true);
		publicField.addFixedCommon(OPTIONAL_LL);

		// ENTITY_LL
		TxField ENTITY_LL = createTxField("ENTITY_LL", "/TX/TX_BODY/COMMON/COM3/ENTITY_LL", 1, "N", 10, 0, true);
		publicField.addFixedCommon(ENTITY_LL);

		// ENTITY_TOT_LL
		TxField ENTITY_TOT_LL = createTxField("ENTITY_TOT_LL", "/TX/TX_BODY/COMMON/COM3/ENTITY_TOT_LL", 1, "N", 10, 0, true);
		publicField.addFixedCommon(ENTITY_TOT_LL);

		// RMB_CLEAR_INSID
		TxField RMB_CLEAR_INSID = createTxField("RMB_CLEAR_INSID", "/TX/TX_BODY/COMMON/COM3/RMB_CLEAR_INSID", 1, "C", 9, 0, true);
		publicField.addFixedCommon(RMB_CLEAR_INSID);

		// FX_CLEAR_INSID
		TxField FX_CLEAR_INSID = createTxField("FX_CLEAR_INSID", "/TX/TX_BODY/COMMON/COM3/FX_CLEAR_INSID", 1, "C", 9, 0, true);
		publicField.addFixedCommon(FX_CLEAR_INSID);

		// BSN_MT_ENT_IDR
		TxField BSN_MT_ENT_IDR = createTxField("BSN_MT_ENT_IDR", "/TX/TX_BODY/COMMON/COM3/BSN_MT_ENT_IDR", 1, "C", 5, 0, true);
		publicField.addFixedCommon(BSN_MT_ENT_IDR);

		// SYS_HOLDTEXT2
		TxField SYS_HOLDTEXT2 = createTxField("SYS_HOLDTEXT2", "/TX/TX_BODY/COMMON/COM3/SYS_HOLDTEXT2", 1, "C", 41, 0, true);
		publicField.addFixedCommon(SYS_HOLDTEXT2);

	}

	@Override
	public void buildOptionalCommon() {
		// ORG_TX_ID
		TxField ORG_TX_ID = createTxField("ORG_TX_ID", "/TX/TX_BODY/COMMON/COM3/ORG_TX_ID", 1, "C", 9, 0, true);
		publicField.addOptionalCommon(ORG_TX_ID);

		LoopField pageLoop = createLoopField("PAGE", "");

		// PAGE_UP_DOWN
		TxField PAGE_UP_DOWN = createTxField("PAGE_UP_DOWN", "/TX/TX_BODY/COMMON/COM3/PAGE_UP_DOWN", 1, "C", 1, 0, true);
		pageLoop.add(PAGE_UP_DOWN);

		// PAGE_STA_KEY
		TxField PAGE_STA_KEY = createTxField("PAGE_STA_KEY", "/TX/TX_BODY/COMMON/COM3/PAGE_STA_KEY", 1, "C", 512, 0, true);
		pageLoop.add(PAGE_STA_KEY);

		// PAGE_END_KEY
		TxField PAGE_END_KEY = createTxField("PAGE_END_KEY", "/TX/TX_BODY/COMMON/COM3/PAGE_END_KEY", 1, "C", 512, 0, true);
		pageLoop.add(PAGE_END_KEY);

		publicField.addOptionalCommon(pageLoop);

		// COMMUNICATION_ITI_ID
		TxField COMMUNICATION_ITI_ID = createTxField("COMMUNICATION_ITI_ID", "/TX/TX_BODY/COMMON/COM3/COMMUNICATION_ITI_ID", 1, "C", 35, 0, true);
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

		// TIMESTAMP
		TxField TIMESTAMP = createTxField("TIMESTAMP", "/TX/TX_BODY/COMMON/COM3/TIMESTAMP", 1, "C", 23, 0, true);
		publicField.addOptionalCommon(TIMESTAMP);

		LoopField originalTx = createLoopField("originalTx", "");

		// EVT_TRACE_ID_EC
		TxField EVT_TRACE_ID_EC = createTxField("EVT_TRACE_ID_EC", "/TX/TX_BODY/COMMON/COM3/EVT_TRACE_ID_EC", 1, "C", 25, 0, true);
		originalTx.add(EVT_TRACE_ID_EC);

		// SND_SERIAL_NO_EC
		TxField SND_SERIAL_NO_EC = createTxField("SND_SERIAL_NO_EC", "/TX/TX_BODY/COMMON/COM3/SND_SERIAL_NO_EC", 1, "C", 10, 0, true);
		originalTx.add(SND_SERIAL_NO_EC);

		publicField.addOptionalCommon(originalTx);

		// REC_IN_PAGE
		TxField REC_IN_PAGE = createTxField("REC_IN_PAGE", "/TX/TX_BODY/COMMON/COM3/REC_IN_PAGE", 1, "C", 4, 0, true);
		publicField.addOptionalCommon(REC_IN_PAGE);

		// PAGE_JUMP
		TxField PAGE_JUMP = createTxField("PAGE_JUMP", "/TX/TX_BODY/COMMON/COM3/PAGE_JUMP", 1, "C", 8, 0, true);
		publicField.addOptionalCommon(PAGE_JUMP);

		LoopField auth = createLoopField("auth", "");
		// AHN_CD
		TxField AHN_CD1 = createTxField("AHN_CD", "/TX/TX_BODY/COMMON/COM5/AHN_INFO[1]/AHN_CD", 1, "C", 12, 0, true);
		TxField AHN_EMPE1 = createTxField("AHN_EMPE", "/TX/TX_BODY/COMMON/COM5/AHN_INFO[1]/AHN_EMPE", 1, "C", 8, 0, true);
		TxField AHN_RSN_CMNT1 = createTxField("AHN_RSN_CMNT", "/TX/TX_BODY/COMMON/COM5/AHN_INFO[1]/AHN_RSN_CMNT", 1, "C", 64, 0, true);

		auth.add(AHN_CD1);
		auth.add(AHN_EMPE1);
		auth.add(AHN_RSN_CMNT1);

		TxField AHN_CD2 = createTxField("AHN_CD", "/TX/TX_BODY/COMMON/COM5/AHN_INFO[2]/AHN_CD", 1, "C", 12, 0, true);
		TxField AHN_EMPE2 = createTxField("AHN_EMPE", "/TX/TX_BODY/COMMON/COM5/AHN_INFO[2]/AHN_EMPE", 1, "C", 8, 0, true);
		TxField AHN_RSN_CMNT2 = createTxField("AHN_RSN_CMNT", "/TX/TX_BODY/COMMON/AHN_INFO[2]/COM5/AHN_RSN_CMNT", 1, "C", 64, 0, true);

		auth.add(AHN_CD2);
		auth.add(AHN_EMPE2);
		auth.add(AHN_RSN_CMNT2);

		TxField AHN_CD3 = createTxField("AHN_CD", "/TX/TX_BODY/COMMON/COM5/AHN_INFO[3]/AHN_CD", 1, "C", 12, 0, true);
		TxField AHN_EMPE3 = createTxField("AHN_EMPE", "/TX/TX_BODY/COMMON/COM5/AHN_INFO[3]/AHN_EMPE", 1, "C", 8, 0, true);
		TxField AHN_RSN_CMNT3 = createTxField("AHN_RSN_CMNT", "/TX/TX_BODY/COMMON/COM5/AHN_INFO[3]/AHN_RSN_CMNT", 1, "C", 64, 0, true);

		auth.add(AHN_CD3);
		auth.add(AHN_EMPE3);
		auth.add(AHN_RSN_CMNT3);

		TxField AHN_CD4 = createTxField("AHN_CD", "/TX/TX_BODY/COMMON/COM5/AHN_INFO[4]/AHN_CD", 1, "C", 12, 0, true);
		TxField AHN_EMPE4 = createTxField("AHN_EMPE", "/TX/TX_BODY/COMMON/COM5/AHN_INFO[4]/AHN_EMPE", 1, "C", 8, 0, true);
		TxField AHN_RSN_CMNT4 = createTxField("AHN_RSN_CMNT", "/TX/TX_BODY/COMMON/COM5/AHN_INFO[4]/AHN_RSN_CMNT", 1, "C", 64, 0, true);

		auth.add(AHN_CD4);
		auth.add(AHN_EMPE4);
		auth.add(AHN_RSN_CMNT4);

		TxField AHN_CD5 = createTxField("AHN_CD", "/TX/TX_BODY/COMMON/COM5/AHN_INFO[5]/AHN_CD", 1, "C", 12, 0, true);
		TxField AHN_EMPE5 = createTxField("AHN_EMPE", "/TX/TX_BODY/COMMON/COM5/AHN_INFO[5]/AHN_EMPE", 1, "C", 8, 0, true);
		TxField AHN_RSN_CMNT5 = createTxField("AHN_RSN_CMNT", "/TX/TX_BODY/COMMON/COM5/AHN_INFO[5]/AHN_RSN_CMNT", 1, "C", 64, 0, true);

		auth.add(AHN_CD5);
		auth.add(AHN_EMPE5);
		auth.add(AHN_RSN_CMNT5);

		TxField AHN_CD6 = createTxField("AHN_CD", "/TX/TX_BODY/COMMON/COM5/AHN_INFO[6]/AHN_CD", 1, "C", 12, 0, true);
		TxField AHN_EMPE6 = createTxField("AHN_EMPE", "/TX/TX_BODY/COMMON/COM5/AHN_INFO[6]/AHN_EMPE", 1, "C", 8, 0, true);
		TxField AHN_RSN_CMNT6 = createTxField("AHN_RSN_CMNT", "/TX/TX_BODY/COMMON/COM5/AHN_INFO[6]/AHN_RSN_CMNT", 1, "C", 64, 0, true);

		auth.add(AHN_CD6);
		auth.add(AHN_EMPE6);
		auth.add(AHN_RSN_CMNT6);

		TxField AHN_CD7 = createTxField("AHN_CD", "/TX/TX_BODY/COMMON/COM5/AHN_INFO[7]/AHN_CD", 1, "C", 12, 0, true);
		TxField AHN_EMPE7 = createTxField("AHN_EMPE", "/TX/TX_BODY/COMMON/COM5/AHN_INFO[7]/AHN_EMPE", 1, "C", 8, 0, true);
		TxField AHN_RSN_CMNT7 = createTxField("AHN_RSN_CMNT", "/TX/TX_BODY/COMMON/COM5/AHN_INFO[7]/AHN_RSN_CMNT", 1, "C", 64, 0, true);

		auth.add(AHN_CD7);
		auth.add(AHN_EMPE7);
		auth.add(AHN_RSN_CMNT7);

		TxField AHN_CD8 = createTxField("AHN_CD", "/TX/TX_BODY/COMMON/COM5/AHN_INFO[8]/AHN_CD", 1, "C", 12, 0, true);
		TxField AHN_EMPE8 = createTxField("AHN_EMPE", "/TX/TX_BODY/COMMON/COM5/AHN_INFO[8]/AHN_EMPE", 1, "C", 8, 0, true);
		TxField AHN_RSN_CMNT8 = createTxField("AHN_RSN_CMNT", "/TX/TX_BODY/COMMON/COM5/AHN_INFO[8]/AHN_RSN_CMNT", 1, "C", 64, 0, true);

		auth.add(AHN_CD8);
		auth.add(AHN_EMPE8);
		auth.add(AHN_RSN_CMNT8);

		publicField.addOptionalCommon(auth);

		LoopField loInfo = createLoopField("loInfo", "");
		// LO_INF_CTLG
		TxField LO_INF_CTLG = createTxField("LO_INF_CTLG", "/TX/TX_BODY/COMMON/COM6/LO_INF_CTLG", 1, "C", 2, 0, true);
		loInfo.add(LO_INF_CTLG);
		// LO_INF
		TxField LO_INF = createTxField("LO_INF", "/TX/TX_BODY/COMMON/COM6/LO_INF", 1, "C", 30, 0, true);
		loInfo.add(LO_INF);
		// CMPN_EMPID
		TxField CMPN_EMPID = createTxField("CMPN_EMPID", "/TX/TX_BODY/COMMON/COM6/CMPN_EMPID", 1, "C", 8, 0, true);
		loInfo.add(CMPN_EMPID);
		// CMPN_PID
		TxField CMPN_PID = createTxField("CMPN_PID", "/TX/TX_BODY/COMMON/COM6/CMPN_PID", 1, "C", 12, 0, true);
		loInfo.add(CMPN_PID);

		publicField.addOptionalCommon(loInfo);

		LoopField callPrty = createLoopField("callPrty", "");

		// CALL_PRTY_LGC_SBM_ID
		TxField CALL_PRTY_LGC_SBM_ID = createTxField("CMPN_PID", "/TX/TX_BODY/COMMON/COM7/CALL_PRTY_LGC_SBM_ID", 1, "C", 5, 0, true);
		callPrty.add(CALL_PRTY_LGC_SBM_ID);

		// CALL_PRTY_SVC_ID
		TxField CALL_PRTY_SVC_ID = createTxField("CMPN_PID", "/TX/TX_BODY/COMMON/COM7/CALL_PRTY_SVC_ID", 1, "C", 9, 0, true);
		callPrty.add(CALL_PRTY_SVC_ID);

		// CALL_PRTY_TXNSRLNO
		TxField CALL_PRTY_TXNSRLNO = createTxField("CMPN_PID", "/TX/TX_BODY/COMMON/COM7/CALL_PRTY_TXNSRLNO", 1, "C", 35, 0, true);
		callPrty.add(CALL_PRTY_TXNSRLNO);

		publicField.addOptionalCommon(callPrty);

		// CHK_STFF_ID
		TxField CHK_STFF_ID = createTxField("CHK_STFF_ID", "/TX/TX_BODY/COMMON/CHK_STFF_ID", 1, "C", 8, 0, true);
		publicField.addOptionalCommon(CHK_STFF_ID);

		// SEC_ACCT_NO
		TxField SEC_ACCT_NO = createTxField("SEC_ACCT_NO", "/TX/TX_BODY/COMMON/SEC_ACCT_NO", 1, "C", 32, 0, true);
		publicField.addOptionalCommon(SEC_ACCT_NO);

		// CMPT_BSN_CGYCD
		TxField CMPT_BSN_CGYCD = createTxField("CMPT_BSN_CGYCD", "/TX/TX_BODY/COMMON/CMPT_BSN_CGYCD", 1, "C", 4, 0, true);
		publicField.addOptionalCommon(CMPT_BSN_CGYCD);

		// RECPT_SPCLPPS_IND_BIT
		TxField RECPT_SPCLPPS_IND_BIT = createTxField("RECPT_SPCLPPS_IND_BIT", "/TX/TX_BODY/COMMON/RECPT_SPCLPPS_IND_BIT", 1, "C", 4, 0, true);
		publicField.addOptionalCommon(RECPT_SPCLPPS_IND_BIT);

		// INM_DVV_VALUE
		TxField INM_DVV_VALUE = createTxField("INM_DVV_VALUE", "/TX/TX_BODY/COMMON/INM_DVV_VALUE", 1, "C", 3, 0, true);
		publicField.addOptionalCommon(INM_DVV_VALUE);

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
