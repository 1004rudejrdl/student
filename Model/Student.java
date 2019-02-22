package Model;

public class Student {
	private String no;
	private String name;
	private String grade;
	private String ban;
	private String gender;
	private String language;
	private String english;
	private String math;
	private String science;
	private String society;
	private String music;
	private String sum;
	private String avg;
	private String date;
	
	
	public Student(String no, String name, String grade, String ban, String gender, String language, String english, String math,
			String science, String society, String music, String sum, String avg, String date) {
		super();
		this.no = no;
		this.name = name;
		this.grade = grade;
		this.ban = ban;
		this.gender = gender;
		this.language = language;
		this.english = english;
		this.math = math;
		this.science = science;
		this.society = society;
		this.music = music;
		this.sum = sum;
		this.avg = avg;
		this.date = date;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getBan() {
		return ban;
	}
	public void setBan(String ban) {
		this.ban = ban;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getEnglish() {
		return english;
	}
	public void setEnglish(String english) {
		this.english = english;
	}
	public String getMath() {
		return math;
	}
	public void setMath(String math) {
		this.math = math;
	}
	public String getScience() {
		return science;
	}
	public void setScience(String science) {
		this.science = science;
	}
	public String getSociety() {
		return society;
	}
	public void setSociety(String society) {
		this.society = society;
	}
	public String getMusic() {
		return music;
	}
	public void setMusic(String music) {
		this.music = music;
	}
	public String getSum() {
		return sum;
	}
	public void setSum(String sum) {
		this.sum = sum;
	}
	public String getAvg() {
		return avg;
	}
	public void setAvg(String avg) {
		this.avg = avg;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}	
}
