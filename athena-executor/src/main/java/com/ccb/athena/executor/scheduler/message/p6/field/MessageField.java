package com.ccb.athena.executor.scheduler.message.p6.field;

import java.util.ArrayList;
import java.util.List;

public class MessageField {
	
	private PublicField publicField;
	
	private List<Field> entityField;

	public PublicField getPublicField() {
		return publicField;
	}

	public void setPublicField(PublicField publicField) {
		this.publicField = publicField;
	}

	public List<Field> getEntityField() {
		return entityField;
	}

	public void setEntityField(List<Field> entityField) {
		this.entityField = entityField;
	}
	
	
	public void addEntity(Field field){
		if(this.entityField == null){
			this.entityField = new ArrayList<Field>();
		}
		this.entityField.add(field);
	}

}
