package com.jsmartx.mybatis.plugins;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.config.GeneratedKey;

public class SkipGeneratedKeyPlugin extends PluginAdapter {

	private List<String> tables;
	private boolean autoIncrement;

	@Override
	public boolean validate(List<String> warnings) {
		tables = new ArrayList<>();
		String tablesString = properties.getProperty("tables");
		String isAutoString = properties.getProperty("autoIncrement");
		if (stringHasValue(tablesString)) {
			for (String table : tablesString.split(",")) {
				tables.add(table.trim());
			}
		}
		autoIncrement = Boolean.parseBoolean(isAutoString);
		return true;
	}

	@Override
	public void initialized(IntrospectedTable introspectedTable) {
		GeneratedKey gk = introspectedTable.getGeneratedKey();
		if (gk == null) {
			return;
		}
		String id = gk.getColumn();
		if (id == null) {
			return;
		}
		Optional<IntrospectedColumn> col = introspectedTable.getColumn(id);
		String table = introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime();
		if (!col.isPresent()) {
			return;
		}
		if ((autoIncrement && !col.get().isAutoIncrement()) || tables.contains(table)) {
			col.get().setSequenceColumn(true);
			col.get().setIdentity(false);
		}
	}

}
