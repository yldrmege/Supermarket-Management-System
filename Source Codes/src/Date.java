
public class Date {//To compare the dates of the products purchased by the customer
	private int day;
	private int  month;
	private int  year;
	
	public Date(int year,int month,int day) {
		this.day=day;
		this.month=month;
		this.year=year;
	}
	int getDay() {
		return day;
	}
	int getMonth() {
		return month;
	}
	int getYear() {
		return year;
	}

}
