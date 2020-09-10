package util;

/**
 * @description:
 * @author: zhangwenzhi
 * @time: 2020/4/16 13:19
 */
public class LxDate {
    private String date;
    private int year;
    private int month;
    private int day;

    //构造函数1
    public LxDate(String date){
        this.date = date;
        this.year = Integer.valueOf(date.split("-")[0]);
        this.month = Integer.valueOf(date.split("-")[1]);
        this.day = Integer.valueOf(date.split("-")[2]);
    }

    //构造函数2
    public LxDate(int year,int month,int day){
        this.date = year+"-"+month+"-"+day;
        this.year = year;
        this.month = month;
        this.day = day;
    }

    /**
     * 方法描述: 增加天数
     * @author: zhangwenzhi
     * @time: 2020/4/16 13:42
     */
    public void addDays(int num){
        while(num > 0){
            if(day+num <= PubUtil.MonthContainDays(year,month)){
                //月份不变时
                day += num;
                date = year+"-"+month+"-"+day;
                num = 0;
            }else{
                //
                num = day+num- PubUtil.MonthContainDays(year,month)-1;
                //下个月一号
                addMonths(1);
            }
        }
    }

    /**
     * 方法描述: 增加月份
     * @author: zhangwenzhi
     * @time: 2020/4/16 14:12
     */
    public void addMonths(int num){
        if(month+num <= 12){
            month = month + num;
            day = 1;
            date = year+"-"+month+"-"+day;
        }else{
            year += (month+num) / 12;
            month += (month+num) % 12;
            day = 1;
            date = year+"-"+month+"-"+day;
        }
    }

    /**
     * 方法描述: 两个日期间隔
     * @author: zhangwenzhi
     * @time: 2020/4/16 14:25
     */
    public int daysAwayFrom(LxDate lxDate){

        return 0;
    }

    public int yearsAwayFrom(LxDate lxDate){
        return Math.abs(lxDate.getYear()-year);
    }

    //两日期间隔多少月
    public int monthsAwayFrom(LxDate lxDate){
        if(isLaterThan(lxDate)){
            return (year-lxDate.getYear())*12+month-lxDate.getMonth();
        }else{
            return (lxDate.getMonth()-year)*12+lxDate.getMonth()-month;
        }
    }

    //判断日期大小
    public boolean isLaterThan(LxDate lxDate){
        if(year > lxDate.getYear()) return true;
        if(month > lxDate.getMonth()) return true;
        if(day > lxDate.getDay()) return true;
        return true;
    }





    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public String getDate() {
        return date;
    }

    public void show(){
        System.out.println(date);
    }

    public String toString(){
        return date;
    }

}
