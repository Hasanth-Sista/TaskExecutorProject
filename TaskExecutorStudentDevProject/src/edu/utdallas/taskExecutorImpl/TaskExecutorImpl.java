package edu.utdallas.taskExecutorImpl;

import edu.utdallas.blockingFIFO.BlockingFIFOImplementation;
import edu.utdallas.taskExecutor.Task;
import edu.utdallas.taskExecutor.TaskExecutor;



public class TaskExecutorImpl implements TaskExecutor
{

	private BlockingFIFOImplementation queue=new BlockingFIFOImplementation(100);
	
	public TaskExecutorImpl(int n) {
		// TODO Auto-generated constructr stub
		
		for(int i=0;i<n;i++){
			Runnable thread = new Runnable() {
				public void run()
				{
					while(true){
					try {
						
						Task task=queue.take();
						//System.out.println(task.getName());
						task.execute();
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
					}
					}
				}
			};
			
			Thread execThread = new Thread(thread);
			execThread.setName("TaskExecutorImpl-Thread"+i);
			execThread.start();
		}
	}

	@Override
	public void addTask(Task task)
	{
		// TODO Complete the implementation
		try {
			queue.put(task);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			
		}
	}

}
