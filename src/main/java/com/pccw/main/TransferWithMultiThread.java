package com.pccw.main;

import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.pccw.pojo.SrcInfo;
import com.pccw.pojo.TargetInfo;
import com.pccw.pojo.TransferInfo;
import com.pccw.util.DataConvert;
import com.pccw.util.DbHelper;

public class TransferWithMultiThread extends BaseTranser{
	
	private static final Logger log = Logger.getLogger(TransferWithMultiThread.class);
	
	public void transfer(TransferInfo t){
		long time = System.currentTimeMillis();
		TargetInfo target = t.getTargetInfo();
		SrcInfo src  = t.getSrcInfo();
		try{
			new DbHelper().executeQuery(t.getSrcInfo(), rs ->{
				DbHelper.createTableInDB(rs, t);
				ExecutorService service = Executors.newFixedThreadPool(InsertTask.queueSize);
				try {
					if (rs != null) {
						ResultSetMetaData rsmd = rs.getMetaData();
						int cc = rsmd.getColumnCount();
						String insertSql = DbHelper.getInserSql(target, rsmd);
						List<List<Object>> list = new ArrayList<>();
						while (rs.next()) {
							List<Object> elements = new ArrayList<>(cc);
							list.add(elements);
							for (int i = 1; i <= cc; i++) {
								elements.add(DataConvert.getVal(rs.getObject(i)));
							}
							if(rs.getRow()%5000==0){
								service.submit(new InsertTask(target.getDbName(), insertSql, list));
								log.info("提交了"+rs.getRow()+"行");
								list = new ArrayList<>();
							}
						}
						service.submit(new InsertTask(target.getDbName(), insertSql, list));
					}
				} catch (Exception e) {
					throw new RuntimeException(e);
				}finally{
					service.shutdown();
				}
			});
		}catch(Exception e){
			log.error("提取过程发生异常",e);
		}
		log.info("【extract】抽取表:"+src.getTable()!=null ? src.getTable() : src.getSql() +"耗时："+(System.currentTimeMillis()-time)+"毫秒");
	}
}
