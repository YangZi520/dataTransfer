package com.pccw.main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.pccw.pojo.SrcInfo;
import com.pccw.pojo.TargetInfo;
import com.pccw.pojo.TransferInfo;
import com.pccw.util.DbHelper;

public class TransferInSingleThread extends BaseTranser{
	
	Logger log = Logger.getLogger(TransferInSingleThread.class);
	
	public void transfer(TransferInfo t){
		SrcInfo src = t.getSrcInfo();
		TargetInfo target = t.getTargetInfo();
		new DbHelper().executeQuery(src, rs->{
			DbHelper.createTableInDB(rs,t);
			Connection con = null;
			PreparedStatement ps = null;
			try{
				ResultSetMetaData rsmd = rs.getMetaData();
				int cc = rsmd.getColumnCount();
				con = DbHelper.getConnection(target.getDbName());
				con.setAutoCommit(Boolean.FALSE);
				ps = con.prepareStatement(DbHelper.getInserSql(target, rsmd));
				int curRow=0;
				if (rs != null) {
					while (rs.next()) {
						curRow++;
						for(int i=1;i<=cc;i++){
							ps.setObject(i, rs.getObject(i));
						}
						ps.addBatch();
						if(curRow%5000==0){
							ps.executeBatch();
							con.commit();
							log.info("导入"+curRow+"条");
						}
					}
					ps.executeBatch();
					con.commit();
					log.info("导入"+curRow+"条");
				}
			}catch (Exception e) {
				throw new RuntimeException(e);
			}finally{
				try {
					if(ps != null) ps.close();
					if(con != null) con.close();
				} catch (SQLException e) {
					log.error("关闭连接时发生异常",e);
				}
			}
		});
	}
}
