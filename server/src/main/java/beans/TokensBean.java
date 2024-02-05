package beans;

import java.util.ArrayList;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;


@Singleton
public class TokensBean {

    public ArrayList<String> tokens= new ArrayList<>();

    public void add(String token){
        tokens.add(token);
    }

    public Boolean find(String token){
        return tokens.contains(token);
    }

    public void delete(String token){
        tokens.remove(token);
    }
    
}
