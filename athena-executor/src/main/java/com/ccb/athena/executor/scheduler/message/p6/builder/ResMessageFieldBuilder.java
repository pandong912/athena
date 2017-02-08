package com.ccb.athena.executor.scheduler.message.p6.builder;

import com.ccb.athena.executor.scheduler.message.p6.PublicFieldFactory;
import com.ccb.athena.executor.scheduler.message.p6.field.Field;
import com.ccb.athena.executor.scheduler.message.p6.field.TxField;

public class ResMessageFieldBuilder extends MessageFieldBuilder {

	public ResMessageFieldBuilder() {
	}

	@Override
	public void buildPublic() {
		this.messageField.setPublicField(PublicFieldFactory.getResPublic());
	}

	@Override
	public void buildEntity() {
		boolean b = true;
		if (b) {
			return;
		}
		// CcyCd
		Field CcyCd = createTxField("CcyCd", "/TX/TX_BODY/ENTITY/CcyCd", 1, "C", 3, 0, false);
		this.messageField.addEntity(CcyCd);

		// CrCrd_Co_Cst_ID
		Field CrCrd_Co_Cst_ID = createTxField("CrCrd_Co_Cst_ID", "/TX/TX_BODY/ENTITY/CrCrd_Co_Cst_ID", 1, "C", 180, 0, false);
		this.messageField.addEntity(CrCrd_Co_Cst_ID);

		// AptRpy_Acc_Br_No
		Field AptRpy_Acc_Br_No = createTxField("AptRpy_Acc_Br_No", "/TX/TX_BODY/ENTITY/AptRpy_Acc_Br_No", 1, "C", 9, 0, false);
		this.messageField.addEntity(AptRpy_Acc_Br_No);

		// Prsz_Inf_Nm_1
		Field Prsz_Inf_Nm_1 = createTxField("Prsz_Inf_Nm_1", "/TX/TX_BODY/ENTITY/Prsz_Inf_Nm_1", 1, "C", 60, 0, false);
		this.messageField.addEntity(Prsz_Inf_Nm_1);

		// AptRpy_Acc_ID
		Field AptRpy_Acc_ID = createTxField("AptRpy_Acc_ID", "/TX/TX_BODY/ENTITY/AptRpy_Acc_ID", 1, "C", 32, 0, false);
		this.messageField.addEntity(AptRpy_Acc_ID);

		// AptRpy_BuyExg_Ind
		Field AptRpy_BuyExg_Ind = createTxField("AptRpy_BuyExg_Ind", "/TX/TX_BODY/ENTITY/AptRpy_BuyExg_Ind", 1, "C", 1, 0, false);
		this.messageField.addEntity(AptRpy_BuyExg_Ind);

		// AptRpy_AutoDbt_Dt
		Field AptRpy_AutoDbt_Dt = createTxField("AptRpy_AutoDbt_Dt", "/TX/TX_BODY/ENTITY/AptRpy_AutoDbt_Dt", 1, "C", 8, 0, false);
		this.messageField.addEntity(AptRpy_AutoDbt_Dt);

		// CrCrd_Last_Udt_EmpID
		Field CrCrd_Last_Udt_EmpID = createTxField("CrCrd_Last_Udt_EmpID", "/TX/TX_BODY/ENTITY/CrCrd_Last_Udt_EmpID", 1, "C", 8, 0, false);
		this.messageField.addEntity(CrCrd_Last_Udt_EmpID);

		// CrCrd_Acc_Last_Mnt_Dt
		Field CrCrd_Acc_Last_Mnt_Dt = createTxField("CrCrd_Acc_Last_Mnt_Dt", "/TX/TX_BODY/ENTITY/CrCrd_Acc_Last_Mnt_Dt", 1, "C", 8, 0, false);
		this.messageField.addEntity(CrCrd_Acc_Last_Mnt_Dt);
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

}
