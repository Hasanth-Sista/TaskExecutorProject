package edu.utdallas.blockingFIFO;

import edu.utdallas.taskExecutor.Task;

public class BlockingFIFOImplementation{

	private Task queue[];
	private Object notFull, notEmpty;
	int nextIn,nextOut,count,capacity;


	public BlockingFIFOImplementation(int capacity) {
		// TODO Auto-generated method stub
		capacity = (capacity>100)?100:capacity;

		queue = new Task[capacity];

		this.capacity = capacity; 
		nextIn = 0;
		nextOut = 0;
		count = 0;
		notFull = new Object();
		notEmpty = new Object();
	}

	public void put(Task item) {
		//System.out.println("put method");
		while(true){
			try {

				synchronized (notFull) {
					if (count == capacity) 
						notFull.wait();
				}

				synchronized(notEmpty){

					if(count==capacity)
						continue;

					queue[nextIn]= item;
					nextIn=(nextIn+1) % capacity;
					count++;
					notEmpty.notify();
					return;
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
	}


	public Task take() {

		while(true){
			try {
				synchronized (notEmpty) {
					if (count == 0) 
						notEmpty.wait();
				}
				synchronized (notFull) {

					if(count==0)
						continue;

					Task task = queue[nextOut];
					nextOut=(nextOut+1) % capacity;
					count--;
					notFull.notify();
					return task;
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}

	}
}