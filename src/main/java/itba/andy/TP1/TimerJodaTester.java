package itba.andy.TP1;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class TimerJodaTester {
    public static void main(String[] args) throws Exception {
        TimerJoda t1 = new TimerJoda();
        TimerJoda t2 = new TimerJoda(1000);
        Thread.sleep(2000);
        // bla bla bla
        t1.stop();
        // bla bla bla
        t2.stop(1500);
        System.out.println(t1);
        System.out.println(t2);
//        t1 = new TimerJoda();
//        // bla bla bla
//        t1.stop(3000);
        t2 = new TimerJoda(100);
        Thread.sleep(200);
        // bla bla bla
        t2.stop();
    }
}