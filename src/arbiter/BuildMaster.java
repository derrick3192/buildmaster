package arbiter;



import java.io.File;

import com.typesafe.config.ConfigFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Address;
import akka.actor.Props;
import akka.remote.RemoteActorRefProvider;
import arbiter.main.Controller;
import arbiter.main.Controller.SetVisible;

@SuppressWarnings("deprecation")
public class BuildMaster {
	
	private static String path = "C:\\Arbiter\\SRCINFO";
	private static String root = "C:\\Arbiter";
	
	
	public final static String getPath() {return path;}
	public final static String getRoot() {return root;}
	
	public static void main(String[] args)
	{
		
		
		if (!new File(path).isDirectory())
		{
			new File(path).mkdirs();
		}
		
		
		
		ActorSystem system = ActorSystem.create("PongDaemon", ConfigFactory.load().getConfig("pongDaemon"));
		final ActorRef someActor = system.actorOf(new Props(Controller.class));	
		someActor.tell(new SetVisible());
		
		if (system.provider() instanceof RemoteActorRefProvider)
		{
			Address systemRemoteAddress = ((RemoteActorRefProvider) system.provider()).transport().address();
			System.out.println(systemRemoteAddress);
		}
	}
}
