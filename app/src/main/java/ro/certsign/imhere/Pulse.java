package ro.certsign.imhere;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.DateFormat;
import java.util.Calendar;

public class Pulse {
    String id;
    String name;
    String msg;
    int smoker;
    String room;
    int[] key;
    int[] PulseTime;

    public static Pulse getInstanceEmpty() {
        Pulse p = new Pulse();
        p.setName("--------------------------");
        return p;
    }

    public String getId() { return this.id!=null?this.id:""; }
    public Pulse setId(String _id) { this.id = _id; return this;}

    public String getName() { return this.name!=null?this.name:""; }
    public Pulse setName(String _name) { this.name = _name; return this;}

    public String getMsg() { return this.msg!=null?this.msg:""; }
    public Pulse setMsg(String _msg) { this.msg = _msg; return this;}

    public int getSmoker() { return this.smoker;  }
    public Pulse setSmoker(int _smoker) { this.smoker = _smoker; return this;}

    public String getRoom() { return this.room!=null?this.room:""; }
    public Pulse setRoom (String _room) { this.room = _room; return this;}

    public int[] getKey() { return this.key; }

    public String getDate() {
        // If PulseTime is not null, then use it as date. If not, check if key is not null
        int[] v = null;
        if( key != null) { v = key; }
        if( PulseTime != null ) { v = PulseTime; }
        if( v == null) { return ""; }

        Calendar cal = Calendar.getInstance();
        cal.set(v[0], v[1], v[2], v[3], v[4], v[5]);
        return DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM).format(cal.getTime());
    }

}
