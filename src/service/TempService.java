package service;

import domain.Temp;
import util.JSONController;

public class TempService {
    private Temp temp;
    private JSONController json = new JSONController("temp.txt");

    public TempService(){
        temp=(Temp)json.read(Temp.class);
    }
    public Temp getTemp(){
        return this.temp;
    }
    public Temp setTemp(Temp temp){
        this.temp=temp;
        return temp;
    }
}
