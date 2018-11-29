package com.pccw.main;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.pccw.pojo.CallBackInfo;
import com.pccw.pojo.Item;
import com.pccw.util.DbHelper;

public class BaseTranser {
	private static final Logger log = Logger.getLogger(BaseTranser.class);

	// 执行一些sql
	public void executeCallBack(CallBackInfo call) throws Exception {
		if(call == null || call.getItem() == null) return;
		log.info("开始执行回调sql...");
		for (Item item : call.getItem()) {
			this.executeWithItem(item);
		}
	}

	private void executeWithItem(Item item) throws Exception {
		Connection conn = null;
		Statement st = null;
		try {
			conn = DbHelper.getConnection(item.getDbName());
			st = conn.createStatement();
			for (String sql : item.getSql()) {
				st.execute(sql);
			}
		} finally {
			if (st != null)
				st.close();
			if (conn != null)
				conn.close();
		}
	}
}
