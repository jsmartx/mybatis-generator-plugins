package com.jsmartx.mybatis.plugins;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

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

	public RenameExampleMethodPlugin() {
	}

	@Override
	public boolean validate(List<String> warnings) {

		searchString = properties.getProperty("searchString"); //$NON-NLS-1$
		replaceString = properties.getProperty("replaceString"); //$NON-NLS-1$

		boolean valid = stringHasValue(searchString) && stringHasValue(replaceString);

		if (valid) {
			pattern = Pattern.compile(searchString);
		} else {
			if (!stringHasValue(searchString)) {
				warnings.add(getString("ValidationError.18", //$NON-NLS-1$
						"RenameExampleMethodPlugin", //$NON-NLS-1$
						"searchString")); //$NON-NLS-1$
			}
			if (!stringHasValue(replaceString)) {
				warnings.add(getString("ValidationError.18", //$NON-NLS-1$
						"RenameExampleMethodPlugin", //$NON-NLS-1$
						"replaceString")); //$NON-NLS-1$
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
		introspectedTable.setCountByExampleStatementId(replace("countByExample")); //$NON-NLS-1$
		introspectedTable.setDeleteByExampleStatementId(replace("deleteByExample")); //$NON-NLS-1$
		introspectedTable.setSelectByExampleStatementId(replace("selectByExample")); //$NON-NLS-1$
		introspectedTable.setSelectByExampleWithBLOBsStatementId(replace("selectByExampleWithBLOBs")); //$NON-NLS-1$
		introspectedTable.setUpdateByExampleStatementId(replace("updateByExample")); //$NON-NLS-1$
		introspectedTable.setUpdateByExampleSelectiveStatementId(replace("updateByExampleSelective")); //$NON-NLS-1$
		introspectedTable.setUpdateByExampleWithBLOBsStatementId(replace("updateByExampleWithBLOBs")); //$NON-NLS-1$
		introspectedTable.setExampleWhereClauseId(replace("Example_Where_Clause")); //$NON-NLS-1$
		introspectedTable.setMyBatis3UpdateByExampleWhereClauseId(replace("Update_By_Example_Where_Clause")); //$NON-NLS-1$
	}
}
