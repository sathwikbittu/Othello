package project.game.Othello.model;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;



@Entity
@Table(name = "player")
public class Player {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "flag")
    private int flag;
    
    // constructors, getters, and setters
    
    public Player() {
    }
    
    public Player(String name, int flag) {
        this.name = name;
        this.flag = flag;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int isFlag() {
        return flag;
    }
    
    public void setFlag(int flag) {
        this.flag = flag;
    }
    
    // toString method
    
    @Override
    public String toString() {
        return "Player [id=" + id + ", name=" + name + ", flag=" + flag + "]";
    }
}

