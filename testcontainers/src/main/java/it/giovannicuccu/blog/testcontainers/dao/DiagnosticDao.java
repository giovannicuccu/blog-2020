package it.giovannicuccu.blog.testcontainers.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import it.giovannicuccu.blog.testcontainers.model.DiagnosticEvent;

public interface DiagnosticDao {
	@Insert("insert into diagnostic_events(event_type,name,result,elapsed_millis,start_ts) values(#{type},#{name},#{result},#{elapsedMillis},#{start})")
	@Options(useGeneratedKeys = true,keyProperty = "id")
	public void insertEvent(DiagnosticEvent diagnosticEvent);
	
	@Select("select id, event_type as type, name, result, elapsed_millis as elapsedMillis, start_ts as start from diagnostic_events")
	public List<DiagnosticEvent> listAll();
	

}
