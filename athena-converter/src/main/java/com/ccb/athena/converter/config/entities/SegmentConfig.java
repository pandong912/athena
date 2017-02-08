package com.ccb.athena.converter.config.entities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.Stack;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ccb.athena.converter.components.DataType;
import com.ccb.athena.converter.components.ExcelHelper;
import com.ccb.athena.converter.components.Field;
import com.ccb.athena.converter.components.Group;
import com.ccb.athena.converter.components.MapRule;

public class SegmentConfig implements Serializable {
	
	protected String name;
	protected String root;
	protected String fileName;
	protected String sheetName;
	
	private Group group;

	public SegmentConfig(String name, String root, String fileName, String sheetName) {
		this.name = name;
		this.root = root;
		this.fileName = fileName;
		this.sheetName = sheetName;
	}

	public SegmentConfig(SegmentConfig orig) {
		this.name = orig.name;
		this.root = orig.root;
		this.fileName = orig.fileName;
		this.sheetName = orig.sheetName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fileName == null) ? 0 : fileName.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((root == null) ? 0 : root.hashCode());
		result = prime * result + ((sheetName == null) ? 0 : sheetName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SegmentConfig other = (SegmentConfig) obj;
		if (fileName == null) {
			if (other.fileName != null)
				return false;
		} else if (!fileName.equals(other.fileName))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (root == null) {
			if (other.root != null)
				return false;
		} else if (!root.equals(other.root))
			return false;
		if (sheetName == null) {
			if (other.sheetName != null)
				return false;
		} else if (!sheetName.equals(other.sheetName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SegmentConfig [name=" + name + ", root=" + root + ", fileName=" + fileName + ", sheetName=" + sheetName
				+ "]";
	}

	public String getName() {
		return name;
	}

	public String getRoot() {
		return root;
	}

	public String getFile() {
		return fileName;
	}

	public String getSheetName() {
		return sheetName;
	}
	
	public Group getGroup() {
		return group;
	}

	public static final class Builder {
		protected SegmentConfig target;

		public Builder(String name, String root, String fileName, String sheetName) {
			this.target = new SegmentConfig(name, root, fileName, sheetName);
		}

		public Builder(SegmentConfig orig) {
			this.target = new SegmentConfig(orig);
		}

		public Builder name(String name) {
			this.target.name = name;
			return this;
		}

		public Builder root(String root) {
			this.target.root = root;
			return this;
		}

		public Builder fileName(String fileName) {
			this.target.fileName = fileName;
			return this;
		}

		public Builder sheetName(String sheetName) {
			this.target.sheetName = sheetName;
			return this;
		}

		public SegmentConfig build() {
			embalmTarget();
			SegmentConfig result = this.target;
			this.target = new SegmentConfig(this.target);
			
			XSSFWorkbook workbook = null;
			try {
				workbook = new XSSFWorkbook(new FileInputStream(this.target.fileName));
				Sheet sheet = workbook.getSheet(this.target.sheetName);
				this.target.group = parseExcel(sheet);
			} catch (FileNotFoundException e) {
		        
			} catch (IOException e) {
				
			} finally {
				try {
					workbook.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return result;
		}

		protected void embalmTarget() {

		}
		
		private Group parseExcel(Sheet sheet) {
			int rows = sheet.getPhysicalNumberOfRows();
			Group rootGroup = new Group();
			Stack<Group> groups = new Stack<Group>();
			for (int i = 2; i < rows; i++) {
				String name = ExcelHelper.getCellValue(sheet.getRow(i).getCell(0));
				String nameZh = ExcelHelper.getCellValue(sheet.getRow(i).getCell(1));
				String length = ExcelHelper.getCellValue(sheet.getRow(i).getCell(3));
				String type = ExcelHelper.getCellValue(sheet.getRow(i).getCell(4));
				
				int index = StringUtils.lastIndexOf(name, ".") + 1;
				if(index > 0) {
					name = StringUtils.substring(name, index);
				}
				
				String pathTagName = "";

				int level = index / 2;
				if (index % 2 != 0)
					throw new RuntimeException("not valid");
				int size = groups.size();
				Group cutrGroup = null;
				if (level > 0) {
					if (level > size) {
						throw new RuntimeException("not valid");
					}
					cutrGroup = groups.get(level - 1);
					for (int j = level; j < size; j++) {
						groups.pop();
					}
				} else {
					cutrGroup = rootGroup;
				}

				if ("GROUP".equals(type)) {
					Group group = new Group();
					group.setTagName(name);
					group.setNameZh(nameZh);
					group.setLevel(level);
					group.setDataType(DataType.valueOf(type));
					
					length = StringUtils.substringAfter(length, "*");
					group.setTimes(Integer.parseInt(length));
					
					groups.push(group);
					
					cutrGroup.getEntries().add(group);
				} else {
					Field field = new Field();
					field.setTagName(name);
					field.setNameZh(nameZh);
					field.setLevel(level);
					field.setDataType(DataType.valueOf(type));
					field.setLength(length);
					
					String identifier = ExcelHelper.getCellValue(sheet.getRow(i).getCell(2));
					boolean required = "Y".equals(ExcelHelper.getCellValue(sheet.getRow(i).getCell(6))) ? true : false;
					String encoding = ExcelHelper.getCellValue(sheet.getRow(i).getCell(7));
					String secure = ExcelHelper.getCellValue(sheet.getRow(i).getCell(8));

					field.setRequired(required);
					field.setEncoding(encoding);
					field.setIdentifier(identifier);
					field.setSecure(secure);

					MapRule mapRule = new MapRule();
					String mapType = ExcelHelper.getCellValue(sheet.getRow(i).getCell(9));
					String parameter = ExcelHelper.getCellValue(sheet.getRow(i).getCell(10));
					if (parameter.startsWith("$")) {
						mapRule.setArray(true);
						parameter = StringUtils.substringAfter(parameter, "$");
					} else {
						mapRule.setArray(false);
					}
					mapRule.setMapType(mapType);
					mapRule.setParameter(parameter);

					cutrGroup.getEntries().add(field);
				}
			}
			return rootGroup;
		}
	}

}
