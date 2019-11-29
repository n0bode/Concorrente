import java.util.concurrent.Semaphore;
import java.util.Random;

public class Principal{
	public enum Estado{
		PENSANDO, FOME, COMENDO
	}
	
	public static class Test{
		private final int N = 5;
		public final Semaphore mutex = new Semaphore(1);
		public final Semaphore[] arr = new Semaphore[N];
		public final Estado[] estados = new Estado[N];
		
		public Test(){
			for(int i = 0; i < N; i++)
				this.arr[i] = new Semaphore(0);
		}

		public void pegarGarfos(int i){
			try{
				mutex.acquire();
				estados[i] = Estado.FOME;
				testarGarfos(i);
				mutex.release();
			}catch(Exception e){
				System.out.println(e);
			}

		}

		public int index(int i){
			return (i < 0) ? i+N : ((i >= N) ? i % N : i);
		}

		public void testarGarfos(int i){
			if (estados[i] == Estado.FOME && estados[index(i-1)] != Estado.COMENDO && estados[index(i+1)] != Estado.COMENDO){
				System.out.println("Filosofo " + i + " Comendo");
				estados[i] = Estado.COMENDO;
				arr[i].release();
			}
		}

		public void devolverGarfos(int i){
			try{
				mutex.acquire();
				estados[i] = Estado.PENSANDO;
				System.out.println("Filosofo " + i + " pensando");
				testarGarfos(index(i - 1));
				testarGarfos(index(i + 1));
				mutex.release();
			}catch(Exception e){}
		}

		public void filosofo(int i){
			Random rand = new Random();
			try{
				for(;;){
					Thread.sleep(100 + rand.nextInt(1000));	
					pegarGarfos(i);
					Thread.sleep(100 + rand.nextInt(1000));
					devolverGarfos(i);
				}
			}catch(Exception e){

			}
		}

		public void start(){
			for(int i = 0; i < N; i++){
				int index = i;
				Thread t = new Thread(()-> {
					filosofo(index);
				});
				t.start();
			}
		}
	}

	public static void main(String[] args){
		Test t = new Test();
		t.start();
	}
}
