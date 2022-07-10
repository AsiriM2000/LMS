package db;

import module.AllDetail;
import view.tm.AllDetailTm;

import java.util.ArrayList;

public class AllDetailDatabase {
    public static ArrayList<AllDetail> allDetailTms = new ArrayList<>();
    static {
        allDetailTms.add(new AllDetail("Sample","Sample","Sample",0,0));
    }
}
