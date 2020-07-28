package com.jsmartx.mybatis.plugins;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.IntrospectedTable;

/**
 * This plugin demonstrates overriding the initialized() method to rename the
 * generated example methods. Instead of xxxExample, the methods will be named
 * xxxQuery
 * 
 * This plugin accepts two properties:
 * <ul>
 * <li><tt>searchString</tt> (required) the regular expression of the name
 * search.</li>
 * <li><tt>replaceString</tt> (required) the replacement String.</li>
 * </ul>
 * 
 * For example, to change the name of the generated Example methods from
 * xxxExample to xxxQuery, specify the following:
 * 
 * <dl>
 * <dt>searchString</dt>
 * <dd>Example$</dd>
 * <dt>replaceString</dt>
 * <dd>Query</dd>
 * </dl>
 * 
 * 
 * @author JSmartX Inc.
 * 
 */

public class RenameExampleMethodPlugin extends PluginAdapter {
	private String searchString;
	private String replaceString;
	private Pattern pattern;

	@Override
	public boolean validate(List<String> warnings) {

		searchString = properties.getProperty("searchString");
		replaceString = properties.getProperty("replaceString");

		boolean valid = stringHasValue(searchString) && stringHasValue(replaceString);

		if (valid) {
			pattern = Pattern.compile(searchString);
		} else {
			String clz = this.getClass().getSimpleName();
			String tpl = "Property %s not set for plugin %s";
			if (!stringHasValue(searchString)) {
				warnings.add(String.format(tpl, "searchString", clz));
			}
			if (!stringHasValue(replaceString)) {
				warnings.add(String.format(tpl, "replaceString", clz));
			}
		}

		return valid;
	}

	public String replace(String oldType) {
		Matcher matcher = pattern.matcher(oldType);
		return matcher.replaceAll(replaceString);
	}

	@Override
	public void initialized(IntrospectedTable introspectedTable) {
		introspectedTable.setCountByExampleStatementId(replace("countByExample"));
		introspectedTable.setDeleteByExampleStatementId(replace("deleteByExample"));
		introspectedTable.setSelectByExampleStatementId(replace("selectByExample"));
		introspectedTable.setSelectByExampleWithBLOBsStatementId(replace("selectByExampleWithBLOBs"));
		introspectedTable.setUpdateByExampleStatementId(replace("updateByExample"));
		introspectedTable.setUpdateByExampleSelectiveStatementId(replace("updateByExampleSelective"));
		introspectedTable.setUpdateByExampleWithBLOBsStatementId(replace("updateByExampleWithBLOBs"));
		introspectedTable.setExampleWhereClauseId(replace("Example_Where_Clause"));
		introspectedTable.setMyBatis3UpdateByExampleWhereClauseId(replace("Update_By_Example_Where_Clause"));
	}
}
