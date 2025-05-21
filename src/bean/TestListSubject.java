package bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;



public class TestListSubject implements Serializable {
	/**
	 * 入学年度:int
	 */
	private int entYear;
	/**
	 * 学生番号：String
	 */
	private String studentNo;
	/**
	 * 学生名：String
	 */
	private String studentName;
	/**
	 * クラス：String
	 */
	private String classNum;
	/**
	 * 回数とその得点：Map<integer, integer>
	 */
	private Map<Integer,Integer>points=new HashMap<>();

	/**
	 * ゲッタ・セッタ
	 */
	public int getEntYear() {
		return entYear;
	}
	public void setEntYear(int entYear) {
		this.entYear = entYear;
	}
	public String getStudentNo() {
		return studentNo;
	}
	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
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

	/**
	 *キー（テスト回数）から値（点数）をゲットするメソッド
	 *@param key テスト回数:int
	 *@return 点数（String）
	 */
	public String getPoint(int key){
		//Mapインスタンスの初期化
		Map<Integer, Integer>map = new HashMap<Integer, Integer>();
		//pointsフィールドから値をゲット
		map = getPoints();
		//キーのテスト回数から値の点数を返却
		return Integer.toString(map.get(key));
	}
	/**
	 *pointsフィールドにMapでテスト回数と点数をセットするメソッド
	 *@param key テスト回数:int
	 *@param value 点数：int
	 */
	public void putPoint(int key, int value){
		//Mapインスタンスの初期化
		Map<Integer, Integer>map = new HashMap<Integer, Integer>();
		//pointsフィールドから値をゲット
		map = getPoints();
		//mapにテスト回数と点数を格納
		map.put(key, value);
		//pointsフィールドにセット
		setPoints(map);
	}
}
