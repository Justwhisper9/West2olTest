import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class PartAnsThread extends Thread{
    long partAns = 0;
    long i;
    int x;

    public PartAnsThread(ThreadGroup group, String name, long i, int x) {
        super(group, name);
        this.i = i;
        this.x = x;
    }

    @Override
    public void run() {
        for(long j = i;j < i+100000000; j++){
            if (contain(j, x)) partAns += j;
        }
    }

    private static boolean contain(long num, int x) {
        return String.valueOf(num).contains(String.valueOf(x));
    }
}
public class Multithreading {
    public static void main(String[] args) {
        long ans = 0;
        Scanner scanner = new Scanner(System.in);
        int x = scanner.nextInt();
        ThreadGroup partThreadGroup = new ThreadGroup("分块线程组");
        List<PartAnsThread> threads = new ArrayList<>();
        for (long i = 1; i < 1000000000; i += 100000000) {
            //list保存创建的分块线程以便获取分块计算的结果
            threads.add(new PartAnsThread(partThreadGroup,"分块线程"+i,i,x));
            threads.get(threads.size()-1).start();
        }
        while(true){                                 //等待分块线程组中的线程执行完毕
            if(partThreadGroup.activeCount() == 0){
                break;
            }
        }
        for(PartAnsThread t:threads){
            ans += t.partAns;
        }
        System.out.println(ans);
    }
}
