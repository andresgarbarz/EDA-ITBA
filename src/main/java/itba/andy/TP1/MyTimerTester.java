package itba.andy.TP1;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class MyTimerTester {
    public static void main(String[] args) {
        MyTimer t1= new MyTimer();
        MyTimer t2= new MyTimer(1000);
        // bla bla bla
        t1.stop();
        // bla bla bla
        t2.stop(2000);
        System.out.println(t1);
        System.out.println(t2);
        t1= new MyTimer();
        // bla bla bla
        t1.stop(3000);
        t2= new MyTimer(4000);
        // bla bla bla
        t2.stop();
    }
}