package bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class TestListStudent implements Serializable {
	private int entYear = 0;
	private String studentNO = "";
	private String studentName = "";
	private String classNum = "";
	//回数とその得点
	private Map<Integer, Integer> points = new HashMap<>();


	public int getEntYear() {
		return entYear;
	}
	public void setEntYear(int entYear) {
		this.entYear = entYear;
	}
	public String getStudentNO() {
		return studentNO;
	}
	public void setStudentNO(String studentNO) {
		this.studentNO = studentNO;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getClassNum() {
		return classNum;
	}
	public void setClassNum(String classNum) {
		this.classNum = classNum;
	}
	public Map<Integer, Integer> getPoints() {
		return points;
	}
	public void setPoints(Map<Integer, Integer> points) {
		this.points = points;
	}
	public String getPoints(int key){
		Map<Integer,Integer> map = new HashMap<>();
		map = getPoints();
		//integer.toStringでintegerからStringにキャスト
		return Integer.toString(map.get(key));
		/**
		 * ためし書き
		 */
	}
	public void putPoint(int key, int value){
		Map<Integer,Integer> map = new HashMap<>();
		map = getPoints();
		map.put(key,value);
		setPoints(map);
	}
}
