package utils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "results")
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "results_id_seq")
    @SequenceGenerator(name= "results_id_seq" , sequenceName = "results_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Integer id;
    @Column(name ="x")
    private String x;
    @Column(name ="y")
    private String y;
    @Column(name ="r")
    private String r;
    @Column(name ="value")
    private String value;
    @Column(name ="exec_time")
    private String execTime;
    @Column(name ="time")
    private String time;

    public Result(Integer id ,String x, String y, String r, String value, String execTime, String time) {
        this.id=id;
        this.x = x;
        this.y = y;
        this.r = r;
        this.value = value;
        this.execTime = execTime;
        this.time = time;
    }

    public Result() {
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getR() {
        return r;
    }

    public void setR(String r) {
        this.r = r;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getExecTime() {
        return execTime;
    }

    public void setExecTime(String execTime) {
        this.execTime = execTime;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    
}
