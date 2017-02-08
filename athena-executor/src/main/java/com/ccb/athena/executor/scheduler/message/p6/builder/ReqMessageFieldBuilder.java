package com.ccb.athena.executor.scheduler.message.p6.builder;

import com.ccb.athena.executor.scheduler.message.p6.PublicFieldFactory;
import com.ccb.athena.executor.scheduler.message.p6.field.Field;
import com.ccb.athena.executor.scheduler.message.p6.field.TxField;

public class ReqMessageFieldBuilder extends MessageFieldBuilder {

	@Override
	public void buildEntity() {
		this.messageField.setPublicField(PublicFieldFactory.getReqPublic());
	}

	@Override
	public void buildPublic() {
		// CcyCd
		Field field1 = createTxField("CcyCd", "/TX/TX_BODY/ENTITY/CcyCd", 1, "C", 3, 0, false);
		this.messageField.addEntity(field1);
		// CrCrd_Co_Cst_ID
		Field field2 = createTxField("CrCrd_Co_Cst_ID", "/TX/TX_BODY/ENTITY/CrCrd_Co_Cst_ID", 1, "C", 18, 0, false);
		this.messageField.addEntity(field2);

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
