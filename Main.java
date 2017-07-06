import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class Main {
	//	显示定义Lock对象
	private final Lock lock = new ReentrantLock();
	//	获得指定Lock对象对应的Condition
	private final Condition cond = lock.newCondition();
	public static void main(String[] args) {
		new Main().test();
	}
	
	private void test() {
		
		new InnerClassA().start();
		new InnerClassB().start();
	}
	class InnerClassA extends Thread{
		public  void run(){
			for(int i = 'a' ; i <= 'z'; i++) {
				lock.lock();
					System.out.print((char)i + " ");
					cond.signalAll();
					try {
						cond.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}finally{
						lock.unlock();
					}
			}System.out.println("A线程结束");
		}	
	}
	
		
	
	class InnerClassB extends Thread{
		
		public  void run(){
			for(int i = 1 ; i <= 26; i++) {
				lock.lock();
					System.out.println(i + " ");
					cond.signalAll();
					try {
						if(i<26)
						{
							cond.await();
							}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}finally{
						lock.unlock();
					}
			}System.out.println("B线程结束");
		}	
	}
	
}



