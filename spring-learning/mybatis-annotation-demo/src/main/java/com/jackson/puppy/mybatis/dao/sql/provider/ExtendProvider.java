package com.jackson.puppy.mybatis.dao.sql.provider;

import java.util.Map;

/**
 * @author Kevin
 * @since 4/14/2018
 */
public class ExtendProvider extends AbstractSqlProvider {

	public String joinAllByTeacherIdAndClassIdAndStudentId(Map<String, Integer> map) {

		final StringBuilder sb = new StringBuilder(
				"select t.id teacherId, t.name teacherName, " +
						"c.id classId, c.name className, " +
						"s.id studentId, s.name studentName " +
						"from teacher t " +
						"inner join class c on t.id = c.teacher_id " +
						"inner join student s on c.id = s.class_id " +
						"where 1 = 1 ");
		if (map == null) {
			return sb.toString();
		}
		final Integer teacherId = map.get("teacherId");
		final Integer classId = map.get("classId");
		final Integer studentId = map.get("studentId");
		if (teacherId != null) {
			sb.append("and t.id = #{teacherId} ");
		}
		if (classId != null) {
			sb.append("and c.id = #{classId} ");
		}
		if (studentId != null) {
			sb.append("and s.id = #{studentId} ");
		}
		return sb.toString();
	}

}
