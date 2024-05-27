package service;

import domain.Temp;
import util.JSONController;

public class TempService {
    private Temp temp;
    private JSONController json;

    public TempService() {
        json = new JSONController("temp.txt");
        temp = (Temp) json.read(Temp.class);
    }

    public Temp getTemp() {
        return this.temp;
    }

    public Temp setTemp(Temp temp) {
        this.temp = temp;
        json.write(temp);
        return temp;
    }

    public void clearTemp() {
        json.write(new Temp());
    }
}
