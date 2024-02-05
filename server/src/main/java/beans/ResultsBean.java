package beans;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import utils.DatabaseHandler;
import utils.Result;


@Stateless
@LocalBean
public class ResultsBean {

    DatabaseHandler databaseHandler= new DatabaseHandler();
    private Result result;
    private ArrayList<Result> results= databaseHandler.load();


    public void add(float x, float y , float r){
        result= new Result();
        double start = System.nanoTime();
        result.setValue(String.valueOf(check(x, y, r)));
        double execTime = Math.round(((System.nanoTime() - start) * 0.00001) * 100.0) / 100.0;
        result.setExecTime(String.valueOf(execTime));
        result.setTime(String.valueOf(LocalDate.now()+" "+LocalTime.now().withNano(0)));
        result.setX(String.valueOf(x));
        result.setY(String.valueOf(y));
        result.setR(String.valueOf(r));
        results.add(result);
        databaseHandler.persist(result);
        result= new Result();
    }

    public Boolean check(float x, float y , float r){
        if (r>0){
            if (x>=0 && y>=0 && x<=r && y<=-x+r){
                return true;
            }
            if (x>=0 && y<=0 && x<=r && y>=-r){
                return true;
            }
            if (x<=0 && y<=0 && Math.sqrt(x*x+y*y)<=r){
                return true;
            }
            return false;
        }
        else{
        if (x>=0 && y>=0 && Math.sqrt(x*x+y*y)<=-r){
            return true;
          }
          if (x<=0 && y>=0 && x>=r && y<=-r){
            return true;
          }
          if (x<=0 && y<=0 && y>=-x+r){
            return true;
          }
          return false;
        }   
    }

    public ArrayList<Result> getResults(){
        return this.results;
    }

    public void clear(){
        results.clear();
        databaseHandler.clear();
    }


    
}
