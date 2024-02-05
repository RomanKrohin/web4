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
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_seq")
    @SequenceGenerator(name= "users_id_seq" , sequenceName = "users_id_seq", allocationSize = 1)
    @Column(name = "id")
    private Integer id;
    @Column(name ="login")
    private String login;
    @Column(name ="password")
    private String password;

    public User(){

    }

    public User(Integer id ,String login, String password){
        this.id = id;
        this.login=login;
        this.password = password;
    }
    
}
